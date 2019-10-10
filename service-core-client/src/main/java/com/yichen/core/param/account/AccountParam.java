package com.yichen.core.param.account;

import com.yichen.core.enums.ChargeTypeEnum;
import com.yichen.core.param.PageParam;
import com.yichen.request.AbstractParam;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class AccountParam extends AbstractParam {

    private String loginName;

    private String phone;

    private String password;

    private String name;

    private String email;

    private String orgId;

    private String orgName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long count;

    private ChargeTypeEnum chargeTypeEnum;

    private PageParam pageParam;
}
