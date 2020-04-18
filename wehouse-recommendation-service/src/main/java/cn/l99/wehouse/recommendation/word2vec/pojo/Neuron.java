package cn.l99.wehouse.recommendation.word2vec.pojo;

public class Neuron implements Comparable<Neuron> {

    // 频率
    protected double freq;
    // 父节点
    protected Neuron parent;
    protected int code;
//    // 语料预分类
//    private int category = -1;

    @Override
    public int compareTo(Neuron neuron) {
        if (this.freq > neuron.freq) {
            return 1;
        } else {
            return -1;
        }
    }

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }

    public Neuron getParent() {
        return parent;
    }

    public void setParent(Neuron parent) {
        this.parent = parent;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
