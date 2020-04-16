package cn.l99.wehouse.recommendation.pojo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 哈夫曼树的叶子节点
 */
public class HouseNeuron extends Neuron {

    // 叶子节点的词名称
    private String value;

    // input->hidden
    private double[] syn0 = null;

    // 路径神经元
    private List<Neuron> neurons = null;

    // 路径非叶子节点的编码
    private int[] codeArr = null;

    public HouseNeuron(String value, double freq, int layerSize) {
        this.value = value;
        this.freq = freq;
        this.syn0 = new double[layerSize];
        Random random = new Random();
        for (int i = 0; i < syn0.length; i++) {
            syn0[i] = (random.nextDouble() - 0.5) / layerSize;
        }
    }

    public List<Neuron> makeNeurons() {
        if (neurons != null) {
            return neurons;
        }
        Neuron neuron = this;
        neurons = new LinkedList<>();
        while ((neuron = neuron.getParent()) != null) {
            neurons.add(neuron);
        }
        Collections.reverse(neurons);
        codeArr = new int[neurons.size()];

        for (int i = 1; i < neurons.size(); i++) {
            codeArr[i - 1] = neurons.get(i).getCode();
        }
        codeArr[codeArr.length - 1] = this.getCode();

        return neurons;
    }

    public double[] getSyn0() {
        return syn0;
    }

    public void setSyn0(double[] syn0) {
        this.syn0 = syn0;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public int[] getCodeArr() {
        return codeArr;
    }

    public void setCodeArr(int[] codeArr) {
        this.codeArr = codeArr;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
