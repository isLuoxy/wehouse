package cn.l99.wehouse.test;

import cn.l99.wehouse.recommendation.Word2VEC;
import cn.l99.wehouse.recommendation.pojo.WordEntry;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class test {

    public static void main(String[] args) throws IOException {
//        File file = new File("D:\\IdeaProjects\\wehouse\\wehouse-recommendation-service\\src\\test\\resources\\library\\corpus.bin");
//        Word2VEC word2VEC = new Word2VEC.Builder()
//                .isCbow(false)
//                .window(5)
//                .layerSize(36)
//                .build();
//        word2VEC.learnFile(file);
//        word2VEC.saveModel(new File("D:\\IdeaProjects\\wehouse\\wehouse-recommendation-service\\src\\test\\resources\\model\\vector.mod"));
//        word2VEC.loadJavaModel("D:\\IdeaProjects\\wehouse\\wehouse-recommendation-service\\src\\test\\resources\\model\\vector.mod");
//        Set<WordEntry> wordEntrySet = word2VEC.wordsNearest("毛泽东", 10);
//        System.out.println(wordEntrySet);
        long l = System.currentTimeMillis();
        for (long i = 0; i < Long.MAX_VALUE ; i++) {
            long j = i;
        }
        long e = System.currentTimeMillis();
        System.out.println(e-l);

    }
}
