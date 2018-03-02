package com.ideal.flyerteacafes.callback;

import com.ideal.flyerteacafes.model.entity.MapBean;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by fly on 2016/12/6.
 */

public abstract class MapCallback extends Callback<MapBean> {


    @Override
    public MapBean parseNetworkResponse(String response) throws IOException, JSONException {
        return JsonUtils.jsonToMapBean(response);
    }

}
