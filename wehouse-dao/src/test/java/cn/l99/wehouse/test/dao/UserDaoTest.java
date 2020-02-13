package cn.l99.wehouse.test.dao;

import cn.l99.wehouse.dao.UserDao;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.dto.UserDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class UserDaoTest extends AbstractTest {


    @Test
    public void selectUserByUserName() {
        String userName = "root";
        UserDao userDao = session.getMapper(UserDao.class);
        log.info("{}", userDao.selectUserByUserName(userName));
    }

    @Test
    public void selectUserByphone() {
        String userPhone = "1234567891234";
        UserDao mapper = session.getMapper(UserDao.class);
        User user = mapper.selectUserByUserPhone(userPhone);
        log.info("{}", user);
    }

    @Test
    public void selectHouseAndCollectionByUserId() {
        String userId = "2";
        UserDao mapper = session.getMapper(UserDao.class);
        List<HouseCollection> houseCollections = mapper.selectHouseAndCollectionByUserId(userId);
        String s = JSONObject.toJSONString(houseCollections);
        log.info("{}", s);
    }

    @Test
    public void updateUserStudentAuthentication() {
        UserDao userDao = session.getMapper(UserDao.class);
        userDao.updateUserStudentAuthentication("3", CommonType.Y);
    }


    public static void main(String[] args) {
        double a = Math.random();
        System.out.println(a);
        int d = (int)((Math.random() * 9 + 1)) * 100000;
        System.out.println(d);
    }
}
