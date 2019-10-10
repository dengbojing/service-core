package com.yichen.core.enums;

import lombok.Getter;

/**
 * @author dengbojing
 */
@Getter
public enum ChargeTypeEnum {

    /**
     * 计次
     */
    COUNT("count", "计次"),
    /**
     * 计时
     */
    TIME("time", "计时");

    private String type;

    private String desc;


    ChargeTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
