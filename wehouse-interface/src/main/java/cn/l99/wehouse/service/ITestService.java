package cn.l99.wehouse.service;


import java.text.ParseException;

public interface ITestService {

    void readDate();

    /**
     * 构造用户浏览历史
     */
    void constructHistory() throws ParseException;

    void training();
}
