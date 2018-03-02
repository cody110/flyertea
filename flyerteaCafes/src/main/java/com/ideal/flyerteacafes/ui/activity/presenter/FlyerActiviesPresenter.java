package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IFlyerActivies;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2017/3/23.
 */
public class FlyerActiviesPresenter extends BasePresenter<IFlyerActivies>{

    private Activity activity;
    private List<NotificationBean> flyerActiviesList=new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        this.activity=activity;

    }

    public List<NotificationBean> getActivies(){
        return flyerActiviesList;
    }

    public void  requestFlyerActivies(int page) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FLYER_ACTIVIES);
        params.addQueryStringParameter("page",String.valueOf(page));
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                flyerActiviesList.clear();
                if (result.getDataList() != null)
                    flyerActiviesList.addAll(result.getDataList());
                    getView().callbackFlyerActivies(flyerActiviesList);
            }
        });
    }
}
