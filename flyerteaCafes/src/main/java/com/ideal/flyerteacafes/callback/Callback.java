package com.ideal.flyerteacafes.callback;

import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.CacheDataManger;
import com.ideal.flyerteacafes.ui.interfaces.IFlyCallBack;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by fly on 2016/5/18.
 */
public abstract class Callback<T> implements org.xutils.common.Callback.CommonCallback<String>, IFlyCallBack<T> {

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(String response) throws IOException, JSONException;

    @Override
    public void onSuccess(final String result) {

        TaskUtil.postOnBackgroundThread(new Runnable() {
            @Override
            public void run() {

                if (getRequestParams() != null) {
                    if (getRequestParams().isLoadCache()) {
                        boolean bol = CacheDataManger.cacheDataSdcard(getRequestParams().getCacheName(), result);
                    }
                }

                T t = null;
                try {
                    t = parseNetworkResponse(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final T finalT = t;
                TaskUtil.postOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flySuccess(finalT);
                    }
                });
            }
        });

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        flyError();
        LogFly.e(ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        flyCancelled();
    }

    @Override
    public void onFinished() {
        flyFinished();
    }


    @Override
    public void flyStart() {
        if (getRequestParams() != null) {
            if (getRequestParams().isLoadCache()) {
                final String cacheText = CacheDataManger.getCacheDataBySdcard(getRequestParams().getCacheName());
                if (!TextUtils.isEmpty(cacheText)) {
                    TaskUtil.postOnBackgroundThread(new Runnable() {
                        @Override
                        public void run() {
                            T t = null;
                            try {
                                t = parseNetworkResponse(cacheText);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            final T finalT = t;
                            TaskUtil.postOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flySuccess(finalT);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    @Override
    public void flyError() {

    }


    @Override
    public void flyCancelled() {

    }

    @Override
    public void flyFinished() {

    }

    public FlyRequestParams getRequestParams() {
        return null;
    }

}
