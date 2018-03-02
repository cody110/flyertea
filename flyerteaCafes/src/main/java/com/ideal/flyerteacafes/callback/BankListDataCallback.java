package com.ideal.flyerteacafes.callback;

import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.tools.GsonTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fly on 2017/4/14.
 */

public abstract class BankListDataCallback extends ListDataCallback {
    public BankListDataCallback(String listKey, Class tClass) {
        super(listKey, tClass);
    }


    @Override
    public ListDataBean parseNetworkResponse(String response) throws IOException, JSONException {
        ListDataBean listDataBean = (ListDataBean) JsonUtils.jsonToBaseBean(response, new ListDataBean());
        try {
            JSONObject jsonObject = new JSONObject(listDataBean.getData());
            if (listKey.indexOf(",") != -1) {
                String[] keyArray = listKey.split(",");
                listKey = keyArray[1];
                jsonObject = jsonObject.getJSONObject(keyArray[0]);
            }
            JSONObject bank = jsonObject.getJSONObject(listKey);
            Iterator iterator = bank.keys();

            List listAll = new ArrayList();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                List list = GsonTools.jsonToList(bank.getString(key), tClass);
                listAll.addAll(list);
            }

            listDataBean.setDataList(listAll);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (listDataBean.getDataList() == null) {
            listDataBean.setDataList(new ArrayList());
        }
        return listDataBean;
    }
}
