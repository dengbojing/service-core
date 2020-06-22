package com.yichen.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbojing
 */
public class FileInfoParser {


   public static String getFileType(byte[] content){
       String hexStr = byte2HexStr(content);
       String type = "";
       if(hexStr.startsWith(HeaderHolder.JPG)){
           type = "image/jpeg";
       }else if(hexStr.startsWith(HeaderHolder.BMP)){
           type = "image/bmp";
       }else if(hexStr.startsWith(HeaderHolder.PNG)){
           type = "image/png";
       }
       return type;
   }


    public static String byte2HexStr(byte[] b){
        String hs = "";
        String stmp = "";

        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static class HeaderHolder{
        public static final String JPG = "FFD8FF";
        public static final String BMP = "89504E47";
        public static final String PNG = "424D";
    }



}
