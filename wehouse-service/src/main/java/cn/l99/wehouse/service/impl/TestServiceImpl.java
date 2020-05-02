package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.dao.UserOperationDao;
import cn.l99.wehouse.map.utils.GeoCodeUtils;
import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.OperationType;
import cn.l99.wehouse.pojo.baseEnum.Orientation;
import cn.l99.wehouse.pojo.vo.HouseVo;
import cn.l99.wehouse.service.IHouseService;
import cn.l99.wehouse.service.ITestService;
import cn.l99.wehouse.service.recommendation.IHouseRecommendationService;
import cn.l99.wehouse.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class TestServiceImpl implements ITestService {

    Random random = new Random();

    @Autowired
    IHouseService houseService;

    @Autowired
    HouseDao houseDao;

    @Autowired
    UserOperationDao userOperationDao;

    @Reference(version = "${wehouse.service.version}")
    IHouseRecommendationService houseRecommendationService;

    @Override
    @Async
    public void readDate() {
        long start = System.currentTimeMillis();
        String path = "D:\\data";
        try {
            File file = new File(path);
            String[] filelist = file.list();
            for (String childFile : filelist) {
                log.info("当前文件名：" + childFile);
                String fileName = path + "\\" + childFile;
                deal(new File(fileName));
                log.info("==========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("总耗时: {}ms", end - start);
    }

    @Override
    @Async
    public void constructHistory() throws ParseException {
        // 获取所有的房源id
        List<String> houseId = houseDao.getTotalId();
        log.info("共有房源数量 {}", houseId.size());

        // 随机选择用户，和选择房源id，并生成时间
        String startDate = "2020-02-01 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = simpleDateFormat.parse(startDate);

        long startT = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            long lineStartT = System.currentTimeMillis();
            int lineNumber = random.nextInt(30);
            // 上个浏览结束时间
            Date endTmp = DateUtils.plusMinutes(start, random.nextInt(100000));

            // 随机用户
            int user = random.nextInt(10000);
            for (int j = 0; j < lineNumber; j++) {
                // 随机房源
                String house = houseId.get(random.nextInt(houseId.size()));
                // 随机开始时间
                Date startTmp = DateUtils.plusMinutes(endTmp, random.nextInt(30));
                endTmp = DateUtils.plusMinutes(startTmp, random.nextInt(30));
                int pageOnTime = (int) DateUtils.millsBetweenMills(startTmp, endTmp);
                log.info("开始时间:{},结束时间:{},页面停留时间:{}", startTmp, endTmp, pageOnTime);
                UserOperation userOperation = new UserOperation();
                userOperation.setHouseId(house);
                userOperation.setUserId(user);
                userOperation.setOperationType(OperationType.C);
                userOperation.setOperationStartTime(startTmp);
                userOperation.setOperationEndTime(endTmp);
                userOperation.setPageOnTime(pageOnTime);
                userOperationDao.insertUserOperation(userOperation);
            }
            long lineEndT = System.currentTimeMillis();
            log.info("行数有{}个数据，耗时{}ms", lineNumber, lineEndT - lineStartT);
        }
        long endT = System.currentTimeMillis();
        log.info("总耗时:{}ms", endT - startT);
    }

    private void deal(File file) throws IOException, InvalidFormatException, ParseException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Long start = System.currentTimeMillis();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);//取得第i行数据
            if (row == null) {
                return;
            }
            String[] temp = new String[row.getLastCellNum()];
            log.info("当前第{}行,列长度为：{}", i, row.getLastCellNum());
            if (temp.length <= 1) {
                return;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);//取得第j列数据
                cell.setCellType(CellType.STRING);
                temp[j] = cell.getStringCellValue().trim();
            }

            HouseVo houserVo = getHouserVo(temp);
            houseService.addHouse(houserVo);
        }
        long end = System.currentTimeMillis();
        log.info("文件:{} 新增数据耗时: {}ms", file.getName(), end - start);
    }

    @Override
    @Async
    public void training() {
        houseRecommendationService.test3();
    }

    private HouseVo getHouserVo(String[] temp) throws ParseException {
        HouseVo houseVo = new HouseVo();
        houseVo.setProvinceCnName("广东省");
        houseVo.setCityCnName("深圳市");
        houseVo.setRegionCnName(temp[1]);
        houseVo.setAddress(temp[2]);
        houseVo.setRentalType(temp[3]);
        houseVo.setVillage(temp[4]);
        houseVo.setName(temp[4]);
        houseVo.setHouseType(temp[5]);
        if (temp[6].indexOf("/") != -1) {
            houseVo.setRentalRoom(temp[6].substring(0, temp[6].indexOf("/")));
        }
        if ("随时入住".equals(temp[7]) || "暂无数据".equals(temp[7])) {
            houseVo.setCheckInTime(null);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = simpleDateFormat.parse(temp[7]);
            houseVo.setCheckInTime(data);
        }
        houseVo.setFloor(temp[8]);
        if ("有".equals(temp[9])) {
            houseVo.setElevator("是");
        } else {
            houseVo.setElevator("否");
        }
        String area = temp[10].substring(0, temp[10].indexOf("平"));
        houseVo.setArea(area);
        houseVo.setPrice(Double.valueOf(temp[11]));
        houseVo.setTelevision(temp[12]);
        houseVo.setFridge(temp[13]);
        houseVo.setWashingMachine(temp[14]);
        houseVo.setAirConditioning(temp[15]);
        houseVo.setHeater(temp[16]);
        houseVo.setBed(temp[17]);
        houseVo.setBroadband(temp[18]);
        houseVo.setWardrobe(temp[19]);

        String local = GeoCodeUtils.getGeoCode(houseVo.getName(), "深圳");
        if (!StringUtils.isEmpty(local)) {
            String[] split = local.split(",");
            houseVo.setLongitude(split[0]);
            houseVo.setLatitude(split[1]);
        }

        int i = random.nextInt(1000);
        houseVo.setOwnerId(i);
        houseVo.setDescription("暂无介绍");
        Orientation[] values = Orientation.values();
        houseVo.setOrientation(values[random.nextInt(4)].getValue());
        return houseVo;
    }

}
