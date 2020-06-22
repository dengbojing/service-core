package com.yichen;

import com.mysql.cj.util.Base64Decoder;
import com.yichen.major.entity.Account;
import com.yichen.core.param.account.AccountParam;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;

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

    @Test
    public void testFile2Byte() throws IOException {
        byte[] b = Files.readAllBytes(Paths.get("C:\\Users\\weigx\\Desktop\\新建文件夹\\1.jpg"));
        System.out.println(Base64.getEncoder().encodeToString(b));
        System.out.println( byte2HexStr(b));
    }

    public static String byte2HexStr(byte[] b)
    {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++)
        {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

}
