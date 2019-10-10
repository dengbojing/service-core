package com.yichen.core.enums;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbojing
 */
public enum LoginStatusEnum {
    /**
     * 账号错误
     */
    WRONG_LOGIN_NAME("账号错误"),
    /**
     * 密码错误
     */
    WRONG_PASSWORD("密码错误"),
    /**
     * 账户冻结
     */
    ACCOUNT_FREEZE("账户冻结"),
    /**
     * 密码错误次数超过上限
     */
    ACCOUNT_TEMP_FREEZE("密码错误次数超过上限"),
    /**
     * 密码过期
     */
    PASSWORD_EXPIRE("密码过期"),
    /**
     * 账号信息错误
     */
    ACCOUNT_ERROR("账号信息错误"),
    /**
     * 登陆成功
     */
    SUCCESS("登陆成功");
    public String getDesc(){
        return desc;
    }
    private String desc;
    public static String getNameByType(String type){
        if(StringUtils.isEmpty(type)){
            return null;
        }
        for(LoginStatusEnum loginStatusEnum: LoginStatusEnum.values()){
            if(type.equals(loginStatusEnum.name())){
                return loginStatusEnum.getDesc();
            }
        }
        return  null;
    }
    public static Map<String,String> getLabelMap(){
        Map<String,String> map = new HashMap<>();
        for(LoginStatusEnum loginStatusEnum: LoginStatusEnum.values()){
            map.put(loginStatusEnum.name(),loginStatusEnum.getDesc());
        }
        return map;
    }
    LoginStatusEnum(String desc){
        this.desc = desc;
    }
}
