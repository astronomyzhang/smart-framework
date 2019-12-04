package org.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *输入流处理工具类
 *@author Garwen
 *@date 2019-12-4 17:24
 */

public final class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     *从输入流获取字符串
     *@author Garwen
     *@date 2019-12-04 17:14
     *@param is
     *@return java.lang.String
     *@throws
     */
    public static String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;

        try {
            while((line=reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("Load Stream error ", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
