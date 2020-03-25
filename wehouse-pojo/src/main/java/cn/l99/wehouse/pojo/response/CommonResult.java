package cn.l99.wehouse.pojo.response;


import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult implements Serializable {

    private static final long serialVersionUID = 2745260992031618742L;
    /**
     * 错误码 正确返回 200
     */
    private Integer errorCode = 200;

    /**
     * 错误信息 正确返回空字符串
     */
    private String errorMessage = "";

    /**
     * 返回值对象
     */
    private Object data;


    /**
     * 正确响应构造函数
     *
     * @param data
     */
    public CommonResult(Object data) {
        this.data = data;
    }

    /**
     * 空响应
     *
     * @return
     */
    public static CommonResult success() {
        return new CommonResult();
    }

    /**
     * 带响应的成功请求
     * @param data
     * @return
     */
    public static CommonResult success(Object data) {
        return new CommonResult(data);
    }

    /**
     * 错误响应
     *
     * @param errorMessage
     * @return
     */
    public static CommonResult failure(Integer code, String errorMessage) {

        return new CommonResult(code, errorMessage, null);
    }

    /**
     * 通过错误码枚举构造错误响应
     *
     * @param errorCode 错误码枚举
     * @return
     */
    public static CommonResult failure(ErrorCode errorCode) {
        return new CommonResult(errorCode.getCode(), errorCode.getDesc(), null);
    }

}
