package cn.l99.wehouse.recommendation.word2vec;


public class Word2VecFactory {

    private static Word2VEC word2VEC;

    public static Word2VEC getWord2VEC() {
        return word2VEC;
    }

    /**
     * 每日训练时使用
     */
    public static Word2VEC newWord2VECAndGet() {
        // 可以将魔法数改成文件配置
        word2VEC = new Word2VEC.Builder()
                .isCbow(false)
                .window(5)
                .layerSize(36)
                .build();
        return getWord2VEC();
    }
}
