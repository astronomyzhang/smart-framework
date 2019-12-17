package org.smart4j.framework.bean;

import org.smart4j.framework.utils.CastUtil;
import org.smart4j.framework.utils.CollectionUtil;
import org.smart4j.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *请求参数对象
 *@author Garwen
 *@date 2019/12/4 0:21
 */
public class Param {
    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public List<FormParam> getFormParamList() {
        return formParamList;
    }

    public List<FileParam> getFileParamList() {
        return fileParamList;
    }

    /**
     *获取请求参数映射
     *@author Garwen
     *@date 2019-12-17 9:49
     *@param
     *@return java.util.Map<java.lang.String,java.lang.Object>
     *@throws
     */
    public Map<String, Object> getFieldMap(){
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        if(CollectionUtil.isNotEmpty(formParamList)){
            for(FormParam formParam:formParamList){
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     *获取上传文件的映射
     *@author Garwen
     *@date 2019-12-17 10:12
     *@param
     *@return java.util.Map<java.lang.String,java.util.List<org.smart4j.framework.bean.FileParam>>
     *@throws
     */
    public Map<String, List<FileParam>> getFileMap(){
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if(CollectionUtil.isNotEmpty(fileParamList)){
            for(FileParam fileParam: fileParamList){
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParams;
                if(fileMap.containsKey(fieldName)){
                    fileParams = fileMap.get(fieldName);
                }else{
                    fileParams = new ArrayList<FileParam>();
                }
                fileParams.add(fileParam);
                fileMap.put(fieldName, fileParams);
            }
        }
        return fileMap;
    }

    /**
     *获取所有上传文件
     *@author Garwen
     *@date 2019-12-17 10:14
     *@param fieldName
     *@return java.util.List<org.smart4j.framework.bean.FileParam>
     *@throws
     */
    public List<FileParam> getFileList(String fieldName){
        return getFileMap().get(fieldName);
    }

    /**
     *获取唯一上传文件
     *@author Garwen
     *@date 2019-12-17 10:19
     *@param fieldName
     *@return org.smart4j.framework.bean.FileParam
     *@throws
     */
    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList = getFileList(fieldName);
        if(CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size()==1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     *判断参数是否为空
     *@author Garwen
     *@date 2019-12-17 9:21
     *@param
     *@return boolean
     *@throws
     */
    public boolean isEmpty(){
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    /**
     *根据参数名获取String类型参数值
     *@author Garwen
     *@date 2019-12-17 10:24
     *@param name
     *@return java.lang.String
     *@throws
     */
    public String getString(String name){
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     *根据参数名称获取Double类型参数值
     *@author Garwen
     *@date 2019-12-17 10:26
     *@param name
     *@return double
     *@throws
     */
    public double getDouble(String name){
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     *根据参数名称获取Int类型参数值
     *@author Garwen
     *@date 2019-12-17 10:30
     *@param name
     *@return int
     *@throws
     */
    public int getInt(String name){
        return CastUtil.castInt(getFieldMap().get(name));
    }

    /**
     *根据参数名称获取Long类型参数值
     *@author Garwen
     *@date 2019-12-17 10:31
     *@param name
     *@return long
     *@throws
     */
    public long getLong(String name){
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     *根据参数名称获取Boolean类型参数值
     *@author Garwen
     *@date 2019-12-17 10:32
     *@param name
     *@return boolean
     *@throws
     */
    public boolean getBoolean(String name){
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
