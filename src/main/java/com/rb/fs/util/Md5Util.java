package com.rb.fs.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密与解密
 */
public class Md5Util {

    /**
     * @param text
     * @param key
     * @return 密文
     */
    // 带秘钥加密
    public static String md5(String text, String key) {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
       // System.out.println("MD5加密后的字符串为:" + md5str);
        return md5str;
    }

    /**
     * 双层md5加密
     * @param text
     * @return
     */
    public static String twiceMd5(String text){
        String md1=Md5Util.md5(text,"bcb3813259b11e5776d4b5044a1196ce");
        String md2=Md5Util.md5(md1,"bcb3813259b11e5776d4b5044a1196ce");
        return md2;
    }

    // 不带秘钥加密
    public static String md52(String text)  {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
       // System.out.println("MD52加密后的字符串为:" + md5str + "\t长度：" + md5str.length());
        return md5str;
    }

    /**
     * MD5验证方法
     *
     * @param text
     * @param key
     * @param md5
     */
    // 根据传入的密钥进行验证
    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5str = md5(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
           // System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }




}
