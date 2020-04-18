package cn.l99.wehouse.recommendation.word2vec.pojo;

/**
 * 哈夫曼树的非叶子节点
 */
public class HiddenNeuron extends Neuron {

    private double[] syn1; //hidden->out

    public HiddenNeuron(int layerSize) {
        syn1 = new double[layerSize];
    }


    public double[] getSyn1() {
        return syn1;
    }

    public void setSyn1(double[] syn1) {
        this.syn1 = syn1;
    }
}
