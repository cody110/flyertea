package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.ui.activity.presenter.AutoPlayListFmPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fly on 2016/12/22.
 */

public class CommunityFollowListPresenter extends AutoPlayListFmPresenter {

    private TypeMode typeMode;
    private Map<String, String> view;
    private String fid, sum;
    public String module = def_module;
    public static final String def_module = "forumdisplay";
    public static final String feed_module = "feed";


    public TypeMode getTypeMode() {
        return typeMode;
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        if (view == null) {
            view = new HashMap<>();
            view.put("filter", "lastpost");
            view.put("orderby", "lastpost");
        }
    }

    public void actionSelectStatus(Map<String, String> view) {
        this.view = view;
        page = 1;

        setDataChagneNeedHeaderRefresh(!isViewAttached());

        if (isViewAttached())
            getView().headerRefreshing();
    }

    public CommunityFollowListPresenter actionSelectTab(TypeMode typeMode) {
        this.typeMode=typeMode;
        String value = typeMode.getType();
        if (TextUtils.equals(value, "all")) {
            module = def_module;
            fid = "";
            sum = "all";
        } else if (TextUtils.equals(value, "follow")) {
            fid = "1";
            sum = "1";
            module = def_module;
        } else if (TextUtils.equals(value, feed_module)) {
            module = feed_module;
            fid = "";
            sum = "";
        } else {
            fid = value;
            sum = value;
            module = def_module;
        }
        page = 1;
        return this;
    }

    @Override
    public void requestDatas() {
        final FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_TOPIC_LIST);
        params.addQueryStringParameter("module", module);
        if (page > 1 && !TextUtils.isEmpty(ver))
            params.addQueryStringParameter("ver", ver);
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("appversion", Tools.getVersion(context));

        if (TextUtils.equals(module, feed_module)) {
            params.addQueryStringParameter("version", "6");
            params.addQueryStringParameter("orderby", "lastpost");
            XutilsHttp.Get(params, new VideoListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, ThreadSubjectBean.class) {
                @Override
                public FlyRequestParams getRequestParams() {
                    return params;
                }
            });
        } else {
            if (view != null) {
                for (Map.Entry<String, String> entry : view.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    params.addQueryStringParameter(entry.getKey(), entry.getValue());
                }
            }
            params.addQueryStringParameter("fid", fid);
            params.addQueryStringParameter("sum", sum);
            params.addQueryStringParameter("version", "5");

            XutilsHttp.Get(params, new VideoListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_FORUM_THREADLIST, ThreadSubjectBean.class) {
                @Override
                public FlyRequestParams getRequestParams() {
                    return params;
                }
            });

        }

    }

}
