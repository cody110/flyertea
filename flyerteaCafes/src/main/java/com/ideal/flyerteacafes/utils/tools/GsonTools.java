package com.ideal.flyerteacafes.utils.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ideal.flyerteacafes.utils.LogFly;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2015/12/10.
 */
public class GsonTools {
    private final static Gson gson = new Gson();

    /**
     * @param <T>
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T jsonToBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
            LogFly.e(cls.getName());
            LogFly.e(e.getMessage());
        }
        return t;
    }

    public static <T> List<T> jsonToList(String json, Class<T> classOfT) {
        List<T> listOfT = new ArrayList<>();
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        try {
            ArrayList<JsonObject> jsonObjs = gson.fromJson(json, type);

            for (JsonObject jsonObj : jsonObjs) {
                listOfT.add(gson.fromJson(jsonObj, classOfT));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogFly.e(e.toString());
        }

        return listOfT;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {

            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public static Map<String, String> getMap(String jsonString) {
        Map<String, String> map = gson.fromJson(jsonString, new TypeToken<Map<String, String>>() {
        }.getType());
        return map;
    }

    public static Map<String, Integer> getMapInteger(String jsonString) {
        Map<String, Integer> map = gson.fromJson(jsonString, new TypeToken<Map<String, Integer>>() {
        }.getType());
        return map;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String objectToJsonString(Object obj) {
        if (obj == null) return null;
        return gson.toJson(obj);
    }
}
