package com.yichen.core.param.account;

import com.yichen.request.AbstractParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * @author dengbojing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateParam extends AbstractParam {

    @NotEmpty(message = "用户id不能为空")
    private String id;

    private String password;

    private String name;

    private String loginName;

    private String orgId;

    private String count;

    private LocalDate startDate;

    private LocalDate endDate;

}
