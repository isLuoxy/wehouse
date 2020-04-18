package cn.l99.wehouse.recommendation.word2vec.utils;

/**
 * 词向量工具
 */
public class Word2VECUtils {

    // 计算两个向量的内积
    public static float calCosineValue(float[] vec1, float[] vec2) {
        float dist = 0;
        for (int i = 0; i < vec1.length; i++) {
            dist += vec1[i] * vec2[i];
        }
        return dist;
    }
}
