package com.yichen.util.POJO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class Subject {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 组织类型
     */
    private OrganizationType organizationType;

    @Getter
    public enum OrganizationType {
        /**
         * 个人
         */
        PERSONAL,
        /**
         * 企业
         */
        COMPANY
    }

}
