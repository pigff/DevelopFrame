package com.androiddev.zf.devframe.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by greedy on 2017/5/25.
 */

public class ParamUtils {

    public static Map<String, RequestBody> getRequestBody(File... files) {
        Map<String, RequestBody> partMap = new HashMap<>();
        for (File file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            partMap.put("files\"; filename=\"" + file.getName(), fileBody);
        }
        return partMap;
    }

    @SafeVarargs
    public static <V extends Object> Map<String, V> getParamMap(String[] keys, V... v) {
        if (keys.length != v.length) {
            try {
                throw new Exception("key的长度必须和value的长度一致");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        Map<String, V> paramMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            paramMap.put(keys[i], v[i]);
        }
        return paramMap;
    }
}
