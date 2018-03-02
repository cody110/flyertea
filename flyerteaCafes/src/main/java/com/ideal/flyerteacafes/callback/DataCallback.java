package com.ideal.flyerteacafes.callback;

import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by fly on 2016/5/24.
 */
public abstract class DataCallback<T> extends Callback<DataBean<T>> {


    public static final String DATA="data";
    /**刷卡任务详情*/
    public static final String DATA_KEY_TASK_DETAILS="data,mission";
    /**手机号获取验证码*/
    public static final String DATA_KEY_GET_CODE="list";

    public static final String DATA_USER="user";





    protected String listKey=DATA;
    protected Class<T> tClass;

    public DataCallback(){
        Class< T >  entityClass  =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
        this.tClass=entityClass;
    }

    public DataCallback(String listKey){
        this.listKey=listKey;
        Class< T >  entityClass  =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
        this.tClass=entityClass;
    }

    @Override
    public DataBean<T> parseNetworkResponse(String response) throws IOException, JSONException {
        return JsonUtils.jsonToDataBean(response, listKey, tClass);
    }

}
