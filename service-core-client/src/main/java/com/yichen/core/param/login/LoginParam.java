package com.yichen.core.param.login;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dengbojing
 */
@Setter
@Getter
public class LoginParam {

    private String loginName;

    /**
     * 密码MD5值+salt
     */
    private String password;
}
