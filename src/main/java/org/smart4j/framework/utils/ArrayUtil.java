package org.smart4j.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 *数组工具类
 *
 * @Author Garwen
 * @Date 2019-12-3
 */
public class ArrayUtil {
    /**
     *数组为空
     *@author Garwen
     *@date 2019-12-03 9:36
     *@param array
     *@return boolean
     *@throws
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

    /**
     *数组不为空
     *@author Garwen
     *@date 2019-12-03 9:36
     *@param array
     *@return boolean
     *@throws
     */
    public static boolean isNotEmpty(Object[] array){
        return ArrayUtils.isNotEmpty(array);
    }
}
