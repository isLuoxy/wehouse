package cn.l99.wehouse.recommendation.pojo;

import java.util.List;

/**
 * 训练模型接口
 */
public interface Model {

    /**
     * @param houseSentence 当前句子
     * @param index         当前词的位置
     * @param window        上下文窗口
     * @param start         在上下文窗口随机确定开始词，即不一定是从 index-window 开始，而是从index-window+start开始，其中 0< start < window
     * @param layerSize     向量的特证数
     * @param alpha
     */
    void training(List<HouseNeuron> houseSentence, int index, int window, int start, int layerSize, double alpha);
}
