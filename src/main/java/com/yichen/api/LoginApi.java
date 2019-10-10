package com.yichen.api;

import com.yichen.core.param.login.LoginParam;
import com.yichen.major.service.LoginService;
import com.yichen.response.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengbojing
 */
@RestController
public class LoginApi {

    private final LoginService loginService;

    public LoginApi(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
