package cn.l99.wehouse.recommendation.word2vec.pojo;

import cn.l99.wehouse.recommendation.word2vec.utils.TrainingUtils;

import java.util.List;

/**
 * 词带模型
 */
public class CbowGramModel implements Model {

    @Override
    public void training(List<HouseNeuron> houseSentence, int index, int window, int start, int layerSize, double alpha) {
        HouseNeuron word = houseSentence.get(index);
        int a, c = 0;

        List<Neuron> neurons = word.getNeurons();
        double[] neu1e = new double[layerSize];// 误差项
        double[] neu1 = new double[layerSize];// 误差项
        HouseNeuron last_word;

        for (a = start; a < window * 2 + 1 - start; a++)
            if (a != window) {
                c = index - window + a;
                if (c < 0)
                    continue;
                if (c >= houseSentence.size())
                    continue;
                last_word = houseSentence.get(c);
                if (last_word == null)
                    continue;
                for (c = 0; c < layerSize; c++)
                    neu1[c] += last_word.getSyn0()[c];
            }

        // HIERARCHICAL SOFTMAX
        for (int d = 0; d < neurons.size(); d++) {
            HiddenNeuron out = (HiddenNeuron) neurons.get(d);
            double f = 0;
            // Propagate hidden -> output
            for (c = 0; c < layerSize; c++)
                f += neu1[c] * out.getSyn1()[c];
            if (!TrainingUtils.rightValueRange((int) f)) {
                continue;
            } else {
                int MAX_EXP = TrainingUtils.MAX_EXP;
                int EXP_TABLE_SIZE = TrainingUtils.EXP_TABLE_SIZE;
                f = (f + MAX_EXP) * (EXP_TABLE_SIZE / MAX_EXP / 2);
                f = TrainingUtils.getExpValue((int) f);
            }
            // 'g' is the gradient multiplied by the learning rate
            // double g = (1 - word.codeArr[d] - f) * alpha;
            // double g = f*(1-f)*( word.codeArr[i] - f) * alpha;
            double g = f * (1 - f) * (word.getCodeArr()[d] - f) * alpha;
            //
            for (c = 0; c < layerSize; c++) {
                neu1e[c] += g * out.getSyn1()[c];
            }
            // Learn weights hidden -> output
            for (c = 0; c < layerSize; c++) {
                out.getSyn1()[c] += g * neu1[c];
            }
        }
        for (a = start; a < window * 2 + 1 - start; a++) {
            if (a != window) {
                c = index - window + a;
                if (c < 0)
                    continue;
                if (c >= houseSentence.size())
                    continue;
                last_word = houseSentence.get(c);
                if (last_word == null)
                    continue;
                for (c = 0; c < layerSize; c++)
                    last_word.getSyn0()[c] += neu1e[c];
            }

        }
    }
}
