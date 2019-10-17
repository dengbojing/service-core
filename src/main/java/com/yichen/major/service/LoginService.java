package com.yichen.major.service;

import com.yichen.core.dto.login.LoginDTO;
import com.yichen.core.param.login.LoginParam;
import com.yichen.response.CommonResponse;

/**
 * @author dengbojing
 */
public interface LoginService {

    /**
     * 登录
     * @param loginParam 登录信息
     * @return 用户信息以及token
     */
    CommonResponse<LoginDTO> login(LoginParam loginParam);
}
