package cn.l99.wehouse.recommendation.schedule;

import cn.l99.wehouse.dao.UserOperationDao;
import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.OperationType;
import cn.l99.wehouse.recommendation.word2vec.Word2VEC;
import cn.l99.wehouse.recommendation.word2vec.Word2VecFactory;
import cn.l99.wehouse.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 *
 */
@Component
@Slf4j
public class RecommendedTasks {

    @Autowired
    public UserOperationDao userOperationDao;

    /**
     * 每日全量更新词向量
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledTask() {
        Date s = DateUtils.now();
        log.info("=== 开始调度任务，当前时间：{} ====", DateUtils.now());
        // 从数据库读取用户点击记录，并且进行数据收集和清洗
        Date start = DateUtils.minusMonths(DateUtils.get0Am(), 1);
        List<UserOperation> userOperations = userOperationDao.findUserOperationByOperationTime(start);
        Map<String, List<UserOperation>> userOperationMap = new HashMap<>();
        for (UserOperation userOperation : userOperations) {
            String userId = String.valueOf(userOperation.getUserId());
            List<UserOperation> operations = userOperationMap.get(userId);
            if (operations == null) {
                List<UserOperation> userOperationList = new ArrayList<>();
                userOperationList.add(userOperation);
                userOperationMap.put(userId, userOperationList);
            } else {
                operations.add(userOperation);
            }
        }
        // 进行数据整理
        List<String> dataCollation = dataCollation(userOperationMap);
        // 进行数据训练
        Word2VEC word2VEC = Word2VecFactory.newWord2VECAndGet();
        word2VEC.learnList(dataCollation);
        Date e = DateUtils.now();
        log.info("=== 任务完成，当前时间：{} ====", e);
        log.info("耗时：{}ms", e.getTime() - s.getTime());
    }

    private void writeFile() throws IOException {
        Word2VEC word2VEC = Word2VecFactory.getWord2VEC();
    }

    private List<String> dataCollation(Map<String, List<UserOperation>> userOperationMap) {
        List<String> resultList = new LinkedList<>();
        // 根据会话时间对用户进行切割，并写入文件
        for (List<UserOperation> userOperations : userOperationMap.values()) {
            Date preStartTime = null;
            StringBuilder stringBuilder = new StringBuilder();
            for (UserOperation userOperation : userOperations) {
                OperationType operationType = userOperation.getOperationType();
                // 如果是收藏操作和预约操作则直接跳过
                if (OperationType.R.equals(operationType) || OperationType.F.equals(operationType)) {
                    stringBuilder.append(userOperation.getHouseId()).append(" ");
                    continue;
                }

                // 判断是否需要切换会话时间
                /*
                 *  1、当 preStartTime == null 时，说明是某个用户历史数据的第一个值，此时不需要切换
                 *  2、当 preStartTime 不为空时，如果用户的某项点击时间时长超过30分钟，则以结束时间代替开始时间，并且不切换会话
                 *  3、如果当前点击开始时间和上次开始时间相差不超过30分钟，则不需要切换会话
                 */
                Date currStartTime = userOperation.getOperationStartTime();
                Date currEndTime = userOperation.getOperationEndTime();
                if (preStartTime != null && userOperation.getPageOnTime() < DateUtils.minutes2Mills(30) && DateUtils.intervalsExceeding(preStartTime, currStartTime, DateUtils.minutes2Mills(30))) {
                    // 此时两次点击事件的间隔时间超过30分钟，需要重新创建会话,在这里的体现为重新开始新的一行，并将旧的一行写入文件中
                    resultList.add(stringBuilder.toString().trim());
                    stringBuilder.setLength(0);
                }

                // 添加词语到会话中
                stringBuilder.append(userOperation.getHouseId()).append(" ");
                // 设置上一个的开始时间
                if (userOperation.getPageOnTime() >= DateUtils.minutes2Mills(30)) {
                    // 在一个页面的耗时超过30分钟
                    preStartTime = userOperation.getOperationEndTime();
                } else {
                    preStartTime = userOperation.getOperationStartTime();
                }
            }

            // 一个用户完成后，写入文件
            String result = stringBuilder.toString().trim();
            if (!StringUtils.isEmpty(result)) {
                resultList.add(stringBuilder.toString().trim());
            }
        }
        return resultList;
    }
}
