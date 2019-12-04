package org.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *URL编码解码工具类
 *@author Garwen
 *@date 2019-12-4 17:23
 */

public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     *编码
     *@author Garwen
     *@date 2019-12-04 17:23
     *@param source
     *@return java.lang.String
     *@throws
     */
    public static String encode(String source){
        String result;
        try {
            result = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Encode failure ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     *解码
     *@author Garwen
     *@date 2019-12-04 17:23
     *@param source
     *@return java.lang.String
     *@throws
     */
    public static String decode(String source){
        String result;
        try {
            URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Decode Failure ", e);
            throw new RuntimeException(e);
        }
        return source;
    }
}
