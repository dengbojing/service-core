package com.yichen.util;

import javax.imageio.ImageIO;

/**
 * @author dengbojing
 */
public class ImageInfoParser {


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
        StringBuilder hs = new StringBuilder();
        String stmp = "";

        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static class HeaderHolder{
        public static final String JPG = "FFD8FF";
        public static final String PNG = "89504E47";
        public static final String BMP = "424D";
    }





}
