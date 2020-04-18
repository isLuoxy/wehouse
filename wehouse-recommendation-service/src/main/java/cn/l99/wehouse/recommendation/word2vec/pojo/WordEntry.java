package cn.l99.wehouse.recommendation.word2vec.pojo;

/**
 * 用于获取某个词语最近的词语时，构建最大堆使用，其中score为两个词语词向量的余弦相似度
 */
public class WordEntry implements Comparable<WordEntry> {

    public String value;
    public float score;

    public WordEntry(String value, float score) {
        this.value = value;
        this.score = score;
    }

    @Override
    public String toString() {
        return this.value + "\t" + score;
    }

    @Override
    public int compareTo(WordEntry o) {
        // TODO Auto-generated method stub
        if (this.score < o.score) {
            return 1;
        } else {
            return -1;
        }
    }
}
