package com.yichen;

import com.yichen.major.entity.Account;
import com.yichen.core.param.account.AccountParam;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;

/**
 * @author dengbojing
 */
public class SpringTest {

    public void testBeanCopy(){
        Account account = new Account();
        AccountParam accountParam = new AccountParam();
    }

    @Test
    public void testLocalDate(){
        LocalDate now = LocalDate.now();
        LocalDate after = LocalDate.of(2019,10, 18);
        System.out.println(now);
        System.out.println(after);
        System.out.println(now.compareTo(after));
    }

}
