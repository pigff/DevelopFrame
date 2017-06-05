package com.androiddev.zf.devframe.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by greedy on 2017/6/5.
 */

public class GsonUtils {

    public static String beanToGson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, cls);
    }

    public static <T> T gsonToBean(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, new TypeToken<T>() {
        }.getType());
    }

    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
    }

    public Object getObjectFromObject(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
