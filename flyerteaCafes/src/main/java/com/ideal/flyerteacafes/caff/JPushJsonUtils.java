package com.ideal.flyerteacafes.caff;

import org.json.JSONException;
import org.json.JSONObject;

public class JPushJsonUtils {

    public static String jsonToType(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String type = jsonObject.getString("type");
            return type;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    public static String jsonToValue(String jsonString, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String value = jsonObject.getString(key);
            return value;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
