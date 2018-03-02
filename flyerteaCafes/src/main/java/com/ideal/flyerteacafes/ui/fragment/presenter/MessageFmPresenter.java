package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.entity.PersonalBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMessageFm;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by Cindy on 2017/3/23.
 */
public class MessageFmPresenter extends BasePresenter<IMessageFm> {


    private PersonalBean personalBean;

    public PersonalBean getPersonalBean() {
        return personalBean;
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestPersonal();
    }


    /***
     * 消息中心消息数量
     */
    public void requestPersonal() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PERSONAL_HOME);
        XutilsHttp.Get(params, new PDataCallback<PersonalBean>() {
            @Override
            public void flySuccess(DataBean<PersonalBean> result) {
                if (isViewAttached()) {
                    if (result.getDataBean() != null) {
                        personalBean = result.getDataBean();
                        getView().callbackPersonal(result.getDataBean());
                    }
                }
            }
        });
    }
}
