package com.ideal.flyerteacafes.callback;

import com.ideal.flyerteacafes.model.entity.BaseBean;

import java.io.IOException;

/**
 * Created by fly on 2016/4/1.
 */
public abstract class BaseBeanCallback extends Callback<BaseBean>{

    @Override
    public BaseBean parseNetworkResponse(String response) throws IOException {
        return JsonUtils.jsonToBaseBean(response);
    }
}
