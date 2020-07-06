package com.rb.fs.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

//@Component
public class PasswordUtil {
    public static void main(String[] arggf) {
        /*Integer.valueOf(5);
        int p=0;
        Integer integer=new Integer(8);
        integer.intValue();
        Integer m1=9;
        Integer m2=9;
        System.out.println(m1==m2);
        Integer m3=128;
        Integer m4=128;
        System.out.println(m3==m4);*/

        String str=null;
        String str1="";
        String m="qq";
        if(StringUtils.isBlank(str)){
            System.out.println("我是空");
            System.out.println(!StringUtils.isBlank(m));
        }
        if(!StringUtils.isBlank(str1)){
            System.out.println("我是空字符");
        }
    }

}