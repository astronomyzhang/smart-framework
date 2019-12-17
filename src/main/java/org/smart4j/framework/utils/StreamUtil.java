package org.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

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

    /**
     *将输入流复制到输出流
     *@author Garwen
     *@date 2019-12-17 14:11
     *@param inputStream
     *@param outputStream
     *@return void
     *@throws
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream){
        try{
            int length;
            byte[] buffer = new byte[4 * 1024];
            while((length=inputStream.read(buffer, 0, buffer.length))!=-1){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        }catch(Exception e){
            LOGGER.error("copy stream failure ", e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("cloase stream failure ", e);
            }
        }
    }
}
