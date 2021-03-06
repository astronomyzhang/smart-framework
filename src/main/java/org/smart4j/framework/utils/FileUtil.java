package org.smart4j.framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 *文件操作工具类
 *@author Garwen
 *@date 2019-12-17 11:45
 */

public final class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     *获取真实文件名（自动去掉文件路径）
     *@author Garwen
     *@date 2019-12-17 11:49
     *@param fileName
     *@return java.lang.String
     *@throws
     */
    public static String getRealFileName(String fileName){
        return FilenameUtils.getName(fileName);
    }

    /**
     *创建文件
     *@author Garwen
     *@date 2019-12-17 11:55
     *@param filePath
     *@return java.io.File
     *@throws
     */
    public static File createFile(String filePath){
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                FileUtils.forceMkdir(parentDir);
            }
        }catch (IOException e) {
            LOGGER.error("create file failure ", e);
            throw new RuntimeException(e);
        }
        return file;
    }
}
