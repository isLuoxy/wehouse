package cn.l99.wehouse.recommendation.word2vec.utils;

/**
 * 训练工具类
 */
public class TrainingUtils {

    // 长度为1000
    public static final int EXP_TABLE_SIZE = 1000;

    public static final int MAX_EXP = 6;

    public static final double[] expTable = new double[EXP_TABLE_SIZE];

    static {
        createExpTable();
    }

    /**
     * Precompute the exp() table f(x) = x / (x + 1)
     */
    public static void createExpTable() {
        for (int i = 0; i < EXP_TABLE_SIZE; i++) {
            // 将[-MAX_EXP,MAX_EXP]均等分成 EXP_TABLE_SIZE 份，即将1-1000落入[-6,6]中，即 0 -> -6; 500 -> 0; 1000 -> 6
            expTable[i] = Math.exp(((i / (double) EXP_TABLE_SIZE * 2 - 1) * MAX_EXP));
            expTable[i] = expTable[i] / (expTable[i] + 1);
        }
    }

    public static boolean rightValueRange(int index) {
        return Math.abs(index) < MAX_EXP;
    }

    public static double getExpValue(int index) {
        return expTable[index];
    }
}
