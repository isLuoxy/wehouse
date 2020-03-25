package cn.l99.wehouse.utils.id;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * id 编号生成
 *
 * @author L99
 */
public class IdGenerator {

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);

    public static final long nextId() {
        return snowflakeIdWorker.nextId();
    }

    public static void main(String[] args) {
        Date data = new Date();
        String s = JSONObject.toJSONString(data);
        System.out.println(s);
    }
}
