package cn.l99.wehouse.recommendation.word2vec.utils;

import cn.l99.wehouse.recommendation.word2vec.pojo.HiddenNeuron;
import cn.l99.wehouse.recommendation.word2vec.pojo.Neuron;

import java.util.Collection;
import java.util.TreeSet;

/**
 * <h> 参考开源项目 https://github.com/NLPchina/Word2VEC_java </h>
 * <p>
 * 构建哈夫曼树
 */
public class Haffman {

    // specifies the number of features in the word vector
    private int layerSize;

    public Haffman(int layerSize) {
        this.layerSize = layerSize;
    }

    private TreeSet<Neuron> set = new TreeSet<>();

    public void construct(Collection<Neuron> neurons) {
        set.addAll(neurons);
        constructNode();
    }

    /**
     * 构建哈夫曼树的非叶子节点
     */
    private void constructNode() {
        while (set.size() > 1) {
            HiddenNeuron hn = new HiddenNeuron(layerSize);
            Neuron min1 = set.pollFirst();
            Neuron min2 = set.pollFirst();
//        hn.category = min2.category;
            hn.setFreq(min1.getFreq() + min2.getFreq());
            min1.setParent(hn);
            min2.setParent(hn);
            min1.setCode(0);
            min2.setCode(1);
            set.add(hn);
        }
    }
}
