package com.peng.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
public class MD5Utils {
    public static String encode(String str){
        try{
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] bytes=md.digest();
            int i;
            StringBuffer sb=new StringBuffer();
            for(int offset=0;offset<bytes.length;offset++){
                i=bytes[offset];
                if(i<0) i += 256;
                if(i<16) sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            //32位加密
            return sb.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
}
