package com.rb.fs.util;

import java.util.UUID;

public class RandomDate {
    /**
     * 随机生成32位的id
     * @return
     */
    public static String setUUID(){
        //随机生成id
        UUID uuid = UUID.randomUUID();
        String id=uuid.toString();
        return id;
    }

    /**
     * 字符串中有空格转为下划线
     * @param str
     * @return
     */
    public static String nullToStr(String str){
        String st="";
        String[] strs=str.split(" ");
        for(int i=0;i<strs.length;i++){
            st+=strs[i]+"_";
        }
        st=st.substring(0,st.length()-1);
        return st;
    }

    public static void main(String[] args) {
        System.out.println(nullToStr("02 0B 泰耐"));
    }
}
