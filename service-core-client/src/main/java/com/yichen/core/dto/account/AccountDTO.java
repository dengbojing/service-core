package com.yichen.core.dto.account;

import com.yichen.core.enums.AccountStatusEnum;
import com.yichen.core.enums.ChargeTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class AccountDTO  {

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账户注册时间
     */
    private LocalDate startDate;

    /**
     * 账户到期时间
     */
    private LocalDate endDate;

    /**
     * 账户剩余使用次数
     */
    private Long count;

    /**
     * 账户状态 @see {@link com.yichen.core.enums.AccountStatusEnum}
     */
    private AccountStatusEnum status;

    /**
     * 计费类型 @see  {@link com.yichen.core.enums.ChargeTypeEnum}
     */
    private ChargeTypeEnum chargeType;
}
