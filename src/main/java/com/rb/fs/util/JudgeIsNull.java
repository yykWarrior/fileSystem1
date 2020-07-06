package com.rb.fs.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 判断字符串是否为空
 */
public class JudgeIsNull {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static Boolean IsOrNull(String str){
        if(str!=null&&!str.equals("")){
          return true;
        }else {
            return false;
        }
    }

    /**
     * 设置字符串为null
     * @param str
     * @return
     */
    public static String setStringToNull(String str){
        if(str.equals(",")){
            return null+",";
        }else if(str.equals("")){
            return null+"";
        }
        else if (str.endsWith(",,")) {
            return str.substring(0,str.length()-1)+null+",";
        }else{
            return str;
            }
    }


    /**
     * 设置尾部为两个逗号的值为空
     * @param str
     * @return
     */
    public static String setEndToNull(String str){
        if(str.endsWith(",,")){
           return str.substring(0,str.length()-1)+null;
        }else{
            return str;
        }
    }

    public static void main(String[] args) {
        System.out.println( setEndToNull("1,2,1,,"));
    }
}
