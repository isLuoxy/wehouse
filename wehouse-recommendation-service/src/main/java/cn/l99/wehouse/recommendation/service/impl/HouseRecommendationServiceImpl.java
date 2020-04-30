package cn.l99.wehouse.recommendation.service.impl;

import cn.l99.wehouse.recommendation.word2vec.Word2VEC;
import cn.l99.wehouse.recommendation.word2vec.Word2VecFactory;
import cn.l99.wehouse.recommendation.word2vec.pojo.WordEntry;
import cn.l99.wehouse.recommendation.word2vec.utils.Word2VECUtils;
import cn.l99.wehouse.service.recommendation.IHouseRecommendationService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * 房源推荐服务实现类
 *
 * @author L99
 */
@Service(version = "${wehouse.service.version}")
@Slf4j
public class HouseRecommendationServiceImpl implements IHouseRecommendationService {

    private Word2VEC word2VEC = Word2VecFactory.getWord2VEC();

    @Override
    public List<String> getRecommendationByCenterHouse(String HouseId, int amount) {
        log.info("房源id:{},推荐房源", HouseId);
        if (word2VEC == null) {
            log.info("word2Vec 为空");
            return Collections.emptyList();
        }
        List<String> result = word2VEC.wordsNearest(HouseId, amount);
        return result;
    }

    @Override
    @Async(value = "taskExecutor")
    public void addHouseVector(String houseId, List<String> referenceHouse) {
        // 查找参照房源的向量
        if (word2VEC == null) {
            return;
        }
        List<float[]> referenceHouseVector = new ArrayList<>();
        log.info("word2VEC:{}", word2VEC);
        referenceHouse.forEach(house -> {
            float[] wordVector = word2VEC.getWordVector(house);
            if (wordVector == null) {
                return;
            }
            referenceHouseVector.add(wordVector);
        });

        if (referenceHouseVector.isEmpty()) {
            return;
        }

        int layerSize = word2VEC.getLayerSize();
        float[] result = new float[layerSize];

        // 计算平均的词向量
        for (int i = 0; i < referenceHouseVector.size(); i++) {
            float[] floats = referenceHouseVector.get(i);
            for (int j = 0; j < floats.length; j++) {
                result[j] += floats[j];
            }
        }
        for (int i = 0; i < layerSize; i++) {
            result[i] /= referenceHouseVector.size();
        }

        log.info("新房源id:{}的词向量为:{}", houseId, result);
        word2VEC.setWordVecMap(houseId, result);
    }

    @Override
    public List<String> sortHouse(List<String> reference, List<String> candidate) {

        List<float[]> referenceVector = new ArrayList<>();

        // 兼容向量初始为null
        if (word2VEC == null) {
            return candidate;
        }

        reference.stream().forEach(r -> {
            float[] wordVector = word2VEC.getWordVector(r);
            if (wordVector != null) {
                referenceVector.add(wordVector);
            }
        });

        float[] avg = new float[word2VEC.getLayerSize()];
        for (int i = 0; i < referenceVector.size(); i++) {
            float[] floats = referenceVector.get(i);
            for (int j = 0; j < floats.length; j++) {
                avg[j] += floats[j];
            }
        }

        // 取得参照房源词向量的均值
        for (int i = 0; i < avg.length; i++) {
            avg[i] /= referenceVector.size();
        }

        // 存放没有词向量的候选房源
        List<String> noVectorList = new ArrayList<>();

        TreeSet<WordEntry> candidateWithVectorSet = new TreeSet<>();
        for (int i = 0; i < candidate.size(); i++) {
            String curr = candidate.get(i);
            float[] candidateVec = word2VEC.getWordVector(curr);
            if (candidateVec == null) {
                noVectorList.add(curr);
                continue;
            }
            // 计算向量内积
            float dist = Word2VECUtils.calCosineValue(avg, candidateVec);
            candidateWithVectorSet.add(new WordEntry(curr, dist));
        }

        // 合并【没有词向量的候选房源】+【TreeSet集合】
        List<String> result = new ArrayList<>();
        while (!candidateWithVectorSet.isEmpty()) {
            result.add(candidateWithVectorSet.pollFirst().value);
        }

        noVectorList.forEach(s -> {
            result.add(s);
        });

        return result;
    }
}
