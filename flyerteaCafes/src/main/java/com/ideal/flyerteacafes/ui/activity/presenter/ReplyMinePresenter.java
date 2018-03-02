package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IReplyMine;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2017/3/23.
 */
public class ReplyMinePresenter extends BasePresenter<IReplyMine> {

    private List<NotificationBean> replyMineList = new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestReplyMine();
    }

    /**
     * 回复我的列表
     */
    private void requestReplyMine() {
        getView().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPLY_MINE);
//        XutilsHttp.Get(params, new PStringCallback() {
//            @Override
//            public void flySuccess(String result) {
//                if (!isViewAttached()) return;
//                replyMineList.clear();
//                replyMineList = JsonAnalysis.jsonToReplyMineList(result, replyMineList);
//                getView().callbackReplyMine(replyMineList);
//            }
//        });
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_NOTIFICATION,NotificationBean.class) {

            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                replyMineList.clear();
                replyMineList = result.getDataList();
                getView().callbackReplyMine(replyMineList);
            }
        });
    }

    /**
     * 标记全部消息为已读
     */
    public void requestMarkMessageReads() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MARK_MESSAGE_READS);
        params.addQueryStringParameter("noticetype", "mark");
        params.addQueryStringParameter("marktype", "post");
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                getView().callbackMarkMessageReads(result);
            }
        });
    }

}
