package com.ideal.flyerteacafes.callback;


import com.ideal.flyerteacafes.caff.SharedPreferencesString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by fly on 2016/1/9.
 */
public abstract class StringCallback extends Callback<String> {

    @Override
    public String parseNetworkResponse(String response) throws IOException, JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            jsonObject = jsonObject.optJSONObject("Variables");
            if (jsonObject.has("formhash")) {
                SharedPreferencesString.getInstances().savaToString("formhash", jsonObject.getString("formhash"));
                SharedPreferencesString.getInstances().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void flyError() {

    }
}
