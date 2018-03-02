package com.ideal.flyerteacafes.utils.xutils;


import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.HmacSha256;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.tools.Tools;

import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.body.BodyItemWrapper;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.InputStreamBody;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.http.body.UrlEncodedParamsBody;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by fly on 2015/12/8.
 */
public class FlyRequestParams extends RequestParams {


    public FlyRequestParams(String uri) {
        super(uri);
        //uri拼接token 安全验证
        String token;
        if (UserManger.isLogin()) {
            token = UserManger.getUserInfo().getMember_uid() + "|" + DateUtil.getDateline();
        } else {
            token = 0 + "|" + DateUtil.getDateline();
        }
        String newToken = HmacSha256.getSignature(token, "feb0e9a398bf6a79892a114825316a93");
        newToken = StringTools.encodeBase64((token + "|" + newToken).getBytes());
        this.addQueryStringParameter("appkey", "98bf6a79892a1148a1");
        this.addQueryStringParameter("token", newToken);
        this.addQueryStringParameter("appversion", Tools.getVersion());
        String cookie = UserManger.getCookie();
        if (!TextUtils.isEmpty(cookie))
            this.addHeader("Cookie", cookie);
        
        addHeader("User-Agent", SharedPreferencesString.getInstances().getUserAgent() + " AppVersion/" + Tools.getVersion());

    }

    /**
     * 重写添加参数到URL，进行urlencoder编码
     *
     * @param name
     * @param value
     */
    @Override
    public void addQueryStringParameter(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            value = URLEncoder.encode(value);
        }
        super.addQueryStringParameter(name, value);
    }

    /**
     * 默认是进行的编码 但有些特殊字符编码了就有问题，特提供此方法
     *
     * @param name
     * @param value
     * @param isencode
     */
    public void addQueryStringParameter(String name, String value, boolean isencode) {
        if (isencode) {
            addQueryStringParameter(name, value);
        } else {
            super.addQueryStringParameter(name, value);
        }
    }

    /**
     * 设置json
     *
     * @param entity
     * @return
     */
    public FlyRequestParams setBodyJson(Object entity) {
        String jsonString = JsonAnalysis.objectToJsonString(entity);
        jsonString = StringTools.replaceAll(jsonString, "null", "\"\"");
        this.setAsJsonContent(true);
        this.setBodyContent(jsonString);
        return this;
    }

    /**
     * 默认urt-8
     * 设置请求GBK编码
     *
     * @return
     */
    public FlyRequestParams setCharsetGBK() {
        this.setCharset("gbk");
        return this;
    }


    private boolean loadCache = false;

    public boolean isLoadCache() {
        return loadCache;
    }

    public void setLoadCache(boolean is) {
        loadCache = is;
    }

    public String getCacheName() {
        return DataTools.getMD5(getUri());
    }


}
