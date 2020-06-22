package com.yichen.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Getter
public enum OpenApiStatus {
    /**
     * 请求成功
     */
    OK(200,"success"),
    /**
     * 未找到有效头部(appkey,token,timespan)
     */
    EMPTY_HEADER(432,"未找到有效头部信息"),
    /**
     * appkey查询未找到结果
     */
    APP_KEY_EXPIRED(433,"appkey失效"),
    /**
     * toekn过期
     */
    TIME_EXPIRED(434,"token有效时间失效"),
    /**
     * 加密验证失败
     */
    VALIDATE_FAILURE(440,"token验证失败");


    private Integer code;

    private String desc;
    OpenApiStatus(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
