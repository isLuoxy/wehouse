package cn.l99.wehouse.recommendation;

import cn.l99.wehouse.recommendation.pojo.*;
import cn.l99.wehouse.recommendation.utils.Haffman;
import cn.l99.wehouse.recommendation.utils.MapCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * <h> 参考开源项目 https://github.com/NLPchina/Word2VEC_java </h>
 */
public class Word2VEC {

    private final Logger logger = LoggerFactory.getLogger(Word2VEC.class);

    // 训练后的模型存放集合，值为对应的向量
    private HashMap<String, float[]> wordVecMap = new HashMap<>();

    // 存放训练前的词集合+对应哈夫曼树的叶子节点，随着训练过程叶子节点的词向量会变化
    private Map<String, Neuron> wordMap = new HashMap<>();

    // 词语数量
    private int words;

    // 大小
    private int size;

    // ====
    // specifies the number of features in the word vector
    private int layerSize;

    // 上下文窗口大小
    private int window;

    private boolean isCbow;

    // =======
    private int trainWordsCount = 0;

    private double sample;

    private double alpha;

    private double startingAlpha;

    private Word2VEC(Builder builder) {
        this.layerSize = builder.layerSize;
        this.window = builder.window;
        this.isCbow = builder.isCbow;
        this.sample = builder.sample;
        this.alpha = builder.alpha;
        this.startingAlpha = builder.startingAlpha;
    }

    public static class Builder {

        // specifies the number of features in the word vector
        private int layerSize;

        // 上下文窗口大小
        private int window;

        private boolean isCbow;

        private double sample = 1e-3;

        private double alpha = 0.025;

        private double startingAlpha = alpha;

        public Builder layerSize(int layerSize) {
            this.layerSize = layerSize;
            return this;
        }

        public Builder window(int window) {
            this.window = window;
            return this;
        }

        public Builder isCbow(Boolean isCbow) {
            this.isCbow = isCbow;
            return this;
        }

        public Builder sample(double sample) {
            this.sample = sample;
            return this;
        }

        public Builder alpha(double alpha) {
            this.alpha = alpha;
            this.startingAlpha = alpha;
            return this;
        }

        public Word2VEC build() {
            return new Word2VEC(this);
        }
    }


    /**
     * 根据文件学习
     *
     * @param file
     * @throws IOException
     */
    public void learnFile(File file) throws IOException {
        logger.info("=== start ===");
        long startTime = System.currentTimeMillis();

        readVocab(file);
        long loadDataEndTime = System.currentTimeMillis();
        logger.info("load data cost time:{}ms", loadDataEndTime - startTime);

        new Haffman(layerSize).construct(wordMap.values());
        // 查找每个神经元
        for (Neuron neuron : wordMap.values()) {
            ((HouseNeuron) neuron).makeNeurons();
        }
        long constructHaffmanEndTime = System.currentTimeMillis();
        logger.info("construct haffman cost time:{}ms", constructHaffmanEndTime - loadDataEndTime);

        trainModel(file);
        long trainnningEndTime = System.currentTimeMillis();
        logger.info("trainning model cost time:{}ms", trainnningEndTime - constructHaffmanEndTime);
        logger.info("costTime: {}ms", trainnningEndTime - startTime);
        logger.info("=== end ===");
    }

    /**
     * 统计词频
     *
     * @param file
     * @throws IOException
     */
    private void readVocab(File file) throws IOException {
        logger.info("Load & Vectorize data....");
        MapCount<String> mc = new MapCount<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)))) {
            String temp = null;
            while ((temp = br.readLine()) != null) {
                String[] split = temp.split(" ");
                trainWordsCount += split.length;
                for (String string : split) {
                    mc.add(string);
                }
            }
        }
        for (Map.Entry<String, Integer> element : mc.get().entrySet()) {

            wordMap.put(element.getKey(), new HouseNeuron(element.getKey(),
                    (double) element.getValue() / mc.size(), layerSize));
        }
    }

    /**
     * 训练模型
     *
     * @throws IOException
     */
    private void trainModel(File file) throws IOException {
        logger.info("start train model....");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)))) {
            String temp = null;
            long nextRandom = 5;
            int wordCount = 0;
            int lastWordCount = 0;
            int wordCountActual = 0;
            while ((temp = br.readLine()) != null) {
                if (wordCount - lastWordCount > 10000) {
                    logger.info("alpha:{} Progress:{}%", alpha, (int) (wordCountActual / (double) (trainWordsCount + 1) * 100));
                    wordCountActual += wordCount - lastWordCount;
                    lastWordCount = wordCount;
                    alpha = startingAlpha
                            * (1 - wordCountActual / (double) (trainWordsCount + 1));
                    if (alpha < startingAlpha * 0.0001) {
                        alpha = startingAlpha * 0.0001;
                    }
                }
                String[] strs = temp.split(" ");
                wordCount += strs.length;
                List<HouseNeuron> sentence = new ArrayList<>();
                // 对于每一行数据，都会将每个词进行
                for (int i = 0; i < strs.length; i++) {
                    Neuron entry = wordMap.get(strs[i]);
                    if (entry == null) {
                        continue;
                    }
                    // The subsampling randomly discards frequent words while keeping the
                    // ranking same
                    if (sample > 0) {
                        // freq = 单词出现的次数/不重复的所有单词
                        // trainWordsCount -- 所有单词总和（包括重复）
                        double ran = (Math.sqrt(entry.getFreq() / (sample * trainWordsCount)) + 1)
                                * (sample * trainWordsCount) / entry.getFreq();
                        nextRandom = nextRandom * 25214903917L + 11;
                        if (ran < (nextRandom & 0xFFFF) / (double) 65536) {
                            continue;
                        }
                    }
                    sentence.add((HouseNeuron) entry);
                }

                Model model = isCbow ? new CbowGramModel() : new SkipGramModel();

                for (int index = 0; index < sentence.size(); index++) {
                    nextRandom = nextRandom * 25214903917L + 11;
                    model.training(sentence, index, window, (int) nextRandom % window, layerSize, alpha);
                }

            }
            logger.info("Vocab size: {}", wordMap.size());
            logger.info("Words in train file: {}", trainWordsCount);
            logger.info("Success train over!");
        }
    }

    /**
     * 保存模型
     */
    public void saveModel(File file) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            dataOutputStream.writeInt(wordMap.size());
            dataOutputStream.writeInt(layerSize);
            double[] syn0 = null;
            for (Map.Entry<String, Neuron> element : wordMap.entrySet()) {
                dataOutputStream.writeUTF(element.getKey());
                syn0 = ((HouseNeuron) element.getValue()).getSyn0();
                for (double d : syn0) {
                    dataOutputStream.writeFloat(((Double) d).floatValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载模型
     *
     * @param path 模型的路径
     * @throws IOException
     */
    public void loadJavaModel(String path) throws IOException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            words = dis.readInt();
            size = dis.readInt();

            float vector = 0;

            String key = null;
            float[] value = null;
            for (int i = 0; i < words; i++) {
                double len = 0;
                key = dis.readUTF();
                value = new float[size];
                for (int j = 0; j < size; j++) {
                    vector = dis.readFloat();
                    len += vector * vector;
                    value[j] = vector;
                }

                len = Math.sqrt(len);

                for (int j = 0; j < size; j++) {
                    value[j] /= len;
                }
                wordVecMap.put(key, value);
            }
        }
    }

    /**
     * 获取距离某个词语最近的 topNSize个词向量
     *
     * @param queryWord
     * @param topNSize
     * @return
     */
    public Set<WordEntry> wordsNearest(String queryWord, int topNSize) {

        float[] center = wordVecMap.get(queryWord);
        if (center == null) {
            return Collections.emptySet();
        }

        int resultSize = wordVecMap.size() < topNSize ? wordVecMap.size() : topNSize;
        TreeSet<WordEntry> result = new TreeSet<WordEntry>();

        double min = Float.MIN_VALUE;
        for (Map.Entry<String, float[]> entry : wordVecMap.entrySet()) {
            float[] vector = entry.getValue();
            float dist = 0;
            for (int i = 0; i < vector.length; i++) {
                dist += center[i] * vector[i];
            }

            // 使用最小堆构造 Top N
            if (dist > min) {
                result.add(new WordEntry(entry.getKey(), dist));
                if (resultSize < result.size()) {
                    result.pollLast();
                }
                min = result.last().score;
            }
        }
        result.pollFirst();

        return result;
    }


}
