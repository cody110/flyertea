package com.ideal.flyerteacafes.callback;

import android.text.TextUtils;

import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.GsonTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/6/2.
 */
public class JsonUtils {

    public static final BaseBean jsonToBaseBean(String jsonString, BaseBean bean) {
        try {
            bean.setJsonString(jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("code") && jsonObject.has("description") && jsonObject.has("data")) {
                bean.setJsonType(BaseBean.type_2);
                bean.setCode(jsonObject.getString("code"));
                bean.setMessage(jsonObject.getString("description"));
                bean.setData(jsonObject.getString("data"));
            } else if (jsonObject.has("Variables")) {
                bean.setJsonType(BaseBean.type_1);
                bean.setData(jsonObject.getString("Variables"));
                if (jsonObject.has("Message")) {
                    JSONObject msgObject = jsonObject.getJSONObject("Message");
                    bean.setCode(msgObject.getString("messageval"));
                    bean.setMessage(msgObject.getString("messagestr"));
                }

                jsonObject = jsonObject.getJSONObject("Variables");
                if (jsonObject.has("success"))
                    bean.setSuccess(jsonObject.optString("success"));
                if (jsonObject.has("msg"))
                    bean.setMessage(jsonObject.optString("msg"));

            } else {
                bean.setJsonType(BaseBean.type_3);
                bean.setData(jsonString);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static final BaseBean jsonToBaseBean(String jsonString) {
        BaseBean bean = new BaseBean();
        return jsonToBaseBean(jsonString, bean);
    }

    public static final ListDataBean jsonToListData(String jsonString, String listKey, Class classT) {
        ListDataBean listDataBean = (ListDataBean) jsonToBaseBean(jsonString, new ListDataBean());
        try {
            if (TextUtils.isEmpty(listDataBean.getData())) {
                return listDataBean;
            }
            JSONObject jsonObject = new JSONObject(listDataBean.getData());

            String listString = null;
            if (listKey.contains(",")) {
                String[] keyArray = listKey.split(",");
                for (int i = 0; i < keyArray.length; i++) {
                    if (i == keyArray.length - 1) {
                        //TODO 最后一个key未list的键
                        listString = jsonObject.getString(keyArray[i]);
                    } else {
                        jsonObject = jsonObject.getJSONObject(keyArray[i]);
                    }
                }
            } else {
                listString = jsonObject.getString(listKey);
            }
            List list = GsonTools.jsonToList(listString, classT);
            listDataBean.setDataList(list);

            if (jsonObject.has("totalpage")) {
                listDataBean.setTotalpage(jsonObject.getInt("totalpage"));
            }
            if (jsonObject.has("has_next")) {
                listDataBean.setHas_next(jsonObject.getInt("has_next"));
            }
            if (jsonObject.has("hasnext")) {
                listDataBean.setHas_next(jsonObject.getInt("hasnext"));
            }
            if (jsonObject.has("ver")) {
                listDataBean.setVer(jsonObject.getString("ver"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            LogFly.e("jsonlistError:" + e.getMessage());
        }


        if (listDataBean.getDataList() == null) {
            listDataBean.setDataList(new ArrayList());
        }

        return listDataBean;
    }

    public static final <T> DataBean<T> jsonToDataBean(String jsonString, String key, Class<T> classT) {
        DataBean dataBean = (DataBean) jsonToBaseBean(jsonString, new DataBean());
        try {
            if (TextUtils.isEmpty(dataBean.getData())) {
                return dataBean;
            }
            JSONObject jsonObject = new JSONObject(dataBean.getData());
            if (key.indexOf(",") != -1) {
                String[] keyArray = key.split(",");
                key = keyArray[1];
                jsonObject = jsonObject.getJSONObject(keyArray[0]);
            }
            String feed_topics = jsonObject.getString(key);
            T t = GsonTools.jsonToBean(feed_topics, classT);
            dataBean.setDataBean(t);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataBean;
    }

    public static final MapBean jsonToMapBean(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String data = jsonObject.getString("Variables");
            MapBean bean = GsonTools.jsonToBean(data, MapBean.class);
            if (jsonObject.has("Variables")) {
                if (jsonObject.has("success")) {
                    bean.setSuccess(jsonObject.getJSONObject("Variables").getString("success"));
                }
            }
            if (jsonObject.has("Message")) {
                JSONObject msgObject = jsonObject.getJSONObject("Message");
                bean.setCode(msgObject.getString("messageval"));
                bean.setMessage(msgObject.getString("messagestr"));
            } else if (jsonObject.has("msg")) {
                bean.setMessage(jsonObject.getString("msg"));
            }

            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
