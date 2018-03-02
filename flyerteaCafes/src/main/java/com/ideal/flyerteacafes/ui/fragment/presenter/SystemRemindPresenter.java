package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/12/21.
 */

public class SystemRemindPresenter extends PullRefreshPresenter<NotificationBean> {


    @Override
    public void init(Activity activity) {
        super.init(activity);
        //全部标记为已读
        BaseDataManger.getInstance().requestMarkTypeReads(Marks.MarkType.SYSTEM);
    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SYSTEM_MESSAGE_REMIND);
        params.addQueryStringParameter("page", String.valueOf(page));
        params.addQueryStringParameter("perpage", String.valueOf(perpage));
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class));
    }

    /**
     * 标记某条消息为已读
     */
    public void requestMarkPositionReads(int index) {
        datas.get(index).setIsnew(Marks.MESSAGE_READ);
        getView().callbackData(datas);
    }
}
