package cn.l99.wehouse.recommendation;


public class Word2VecFactory {

    private static final Word2VEC word2VEC;

    static {
        // 可以将魔法数改成文件配置
        word2VEC = new Word2VEC.Builder()
                .isCbow(false)
                .window(5)
                .layerSize(36)
                .build();
    }

    public static Word2VEC getWord2VEC() {
        return word2VEC;
    }
}
