package org.smart4j.framework.bean;

import java.io.InputStream;

/**
 *封装上传文件参数
 *@author Garwen
 *@date 2019-12-17 9:36
 */

public class FileParam {
    private String fieldName;
    private String fileName;
    private long fileSize;
    private String ContentType;
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        ContentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return ContentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
