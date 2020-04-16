package cn.l99.wehouse.recommendation.pojo;

import cn.l99.wehouse.recommendation.utils.TrainingUtils;

import java.util.List;

/**
 * 跳字模型
 */
public class SkipGramModel implements Model {

    @Override
    public void training(List<HouseNeuron> houseSentence, int index, int window, int start, int layerSize, double alpha) {
        HouseNeuron word = houseSentence.get(index);
        int a, c = 0;
        for (a = start; a < window * 2 + 1 - start; a++) {
            if (a == window) {
                continue;
            }
            c = index - window + a;
            if (c < 0 || c >= houseSentence.size()) {
                continue;
            }

            double[] neu1e = new double[layerSize];// 误差项
            // HIERARCHICAL SOFTMAX
            List<Neuron> neurons = word.getNeurons();
            HouseNeuron we = houseSentence.get(c);
            for (int i = 0; i < neurons.size(); i++) {
                HiddenNeuron out = (HiddenNeuron) neurons.get(i);
                double f = 0;
                // Propagate hidden -> output
                for (int j = 0; j < layerSize; j++) {
                    f += we.getSyn0()[j] * out.getSyn1()[j];
                }
                if (!TrainingUtils.rightValueRange((int) f)) {
                    // 超过exp的数组范围
                    continue;
                } else {
                    int MAX_EXP = TrainingUtils.MAX_EXP;
                    int EXP_TABLE_SIZE = TrainingUtils.EXP_TABLE_SIZE;
                    f = (f + MAX_EXP) * (EXP_TABLE_SIZE / MAX_EXP / 2);
                    f = TrainingUtils.getExpValue((int) f);
                }
                // 'g' is the gradient multiplied by the learning rate
                double g = (1 - word.getCodeArr()[i] - f) * alpha;
                // Propagate errors output -> hidden
                for (c = 0; c < layerSize; c++) {
                    neu1e[c] += g * out.getSyn1()[c];
                }
                // Learn weights hidden -> output
                for (c = 0; c < layerSize; c++) {
                    out.getSyn1()[c] += g * we.getSyn0()[c];
                }
            }

            // Learn weights input -> hidden
            for (int j = 0; j < layerSize; j++) {
                we.getSyn0()[j] += neu1e[j];
            }
        }
    }
}
