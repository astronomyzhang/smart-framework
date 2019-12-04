package org.smart4j.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     *将POJO转化为Json
     *@author Garwen
     *@date 2019-12-04 17:41
     *@param obj
     *@return java.lang.String
     *@throws
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("To JSON Failure ", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     *将Json转化为POJO
     *@author Garwen
     *@date 2019-12-04 17:41
     *@param json
     *@param type
     *@return T
     *@throws
     */
    public static <T> T fromJson(String json, Class<T> type){
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("From Json Failure ", e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
