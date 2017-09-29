package com.cao.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

public class GsonUtils {
    private static Gson gson = new GsonBuilder().disableHtmlEscaping()
            .serializeNulls().create();

    public static <T> T fromJson(String json, Class clazz)
    {
        if (StringUtils.isEmpty(json))
            return null;
        return (T) gson.fromJson(json, clazz);
    }

    public static String toJson(Object src)
    {
        if (src == null)
            return null;
        return gson.toJson(src);
    }
}
