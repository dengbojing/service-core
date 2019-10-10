package com.yichen.core.enums;

import lombok.Getter;

/**
 * @author dengbojing
 */
@Getter
public enum AccountStatusEnum {

    /**
     * 开通
     */
    OPEN("开通"),
    /**
     * 冻结
     */
    FREEZE( "冻结"),
    /**
     * 未开通
     */
    CLOSE("未开通");


    private String desc;

    AccountStatusEnum(String desc) {
        this.desc = desc;
    }
}
