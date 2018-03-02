package com.ideal.flyerteacafes.utils.xutils;

import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.CacheDataManger;
import com.ideal.flyerteacafes.callback.Callback;

import org.json.JSONException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

/**
 * Created by fly on 2015/12/21.
 */
public class XutilsHttp {

    public static <T> void Get(FlyRequestParams params, Callback<T> callback) {
        request(HttpMethod.GET, params, callback);
    }

    public static <T> void Post(FlyRequestParams params, Callback<T> callback) {
        request(HttpMethod.POST, params, callback);
    }

    private static <T> void request(HttpMethod method, final FlyRequestParams params, Callback<T> callback) {
        if (callback == null) {
            callback = new Callback<T>() {
                @Override
                public T parseNetworkResponse(String response) throws IOException, JSONException {
                    return null;
                }

                @Override
                public FlyRequestParams getRequestParams() {
                    return params;
                }

                @Override
                public void flySuccess(T result) {

                }
            };
        }

        callback.flyStart();
        x.http().request(method, params, callback);
    }

}
