package com.yichen;

import com.yichen.core.enums.OrganizationTypeEnum;
import com.yichen.util.POJO.Subject;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceProjectApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
        String url = encryptor.encrypt("jdbc:mysql://121.41.2.219:3306/yichen_manager?characterEncoding=utf-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&serverTimezone=UTC");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt(" mnnn8e");
        System.out.println(url);
        System.out.println(name);
        System.out.println(password);
        System.out.println(Subject.OrganizationType.valueOf(OrganizationTypeEnum.PERSONAL.name()));
    }


}
