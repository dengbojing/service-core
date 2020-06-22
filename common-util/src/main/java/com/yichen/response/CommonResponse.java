package com.yichen.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

/**
 * @author dengbojing
 */
@Setter
@Getter
public class CommonResponse<T> {
    private Integer code = 200;

    private String message;

    private T data = null;

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>();
    }

    public static <T> CommonResponse<T> success(T data) {
        val res = new CommonResponse<T>();
        res.setData(data);
        return res;
    }

    public static <T> CommonResponse<T> failed(String message){
        val res = new CommonResponse<T>();
        res.setCode(500);
        res.setMessage(message);
        return res;
    }

    public static <T> T handle(CommonResponse<T> response) {
        if (response.getCode() == null || !response.getCode().equals(ResponseCodeEnum.SUCCESS.code)) {
            throw new RuntimeException("服务调用失败：" + response.getMessage());
        }
        return response.getData();
    }

    public static void check(CommonResponse response) {
        if (response.getCode() == null || !response.getCode().equals(ResponseCodeEnum.SUCCESS.code)) {
            throw new RuntimeException("服务调用失败：" + response.getMessage());
        }
    }

    @Getter
    public enum ResponseCodeEnum {
        /**
         * 成功
         */
        SUCCESS(200),
        /**
         * 失败
         */
        FAIL(500);

        private Integer code;

        ResponseCodeEnum(int code) {
            this.code = code;
        }
    }
}
