package org.smart4j.framework.bean;

import org.smart4j.framework.utils.CastUtil;

import java.util.Map;

/**
 *请求参数对象
 *@author Garwen
 *@date 2019/12/4 0:21
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

    /**
     *根据参数名获取long型参数值
     *@author Garwen
     *@date 2019/12/4 0:22
     *@params [name]
     *@return long
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     *获取所有  字段信息
     *@author Garwen
     *@date 2019/12/4 0:22
     *@params []
     *@return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> getParamMap(){
        return paramMap;
    }
}
