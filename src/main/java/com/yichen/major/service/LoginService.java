package com.yichen.major.service;

import com.yichen.core.param.login.LoginParam;
import com.yichen.response.CommonResponse;

/**
 * @author dengbojing
 */
public interface LoginService {

    CommonResponse login(LoginParam loginParm);
}
