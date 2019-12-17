package org.smart4j.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public final class StringUtil {
    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        if(str!=null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     *切分字符串
     *@author Garwen
     *@date 2019/12/4 21:11
     *@params [str, separator]
     *@return java.lang.String[]
     */
    public static String[] splitString(String str, String separator){
        String[] strs;
        if(StringUtil.isNotEmpty(str)){
            strs = StringUtils.split(str, separator);
        }else{
            strs=null;
        }

        return strs;
    }
}
