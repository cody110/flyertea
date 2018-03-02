package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISwingCardTaskDetails;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardTaskDetails;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.HashMap;

/**
 * Created by fly on 2017/5/8.
 */

public class SwingCardTaskDetailsPresenter extends BasePresenter<ISwingCardTaskDetails> {


    public TaskDetailsBean taskDetailsBean;
    int missionid;
    public boolean isStatusChange;

    @Override
    public void init(Activity activity) {
        super.init(activity);
        missionid = activity.getIntent().getIntExtra(SwingCardTaskDetails.BUNDLE_MISSIONID, 0);
        isStatusChange = activity.getIntent().getBooleanExtra("isStatusChange", false);
        requestDetails();
    }

    public void requestDetails() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TASK_DETAILS);
        params.addQueryStringParameter("missionid", String.valueOf(missionid));
        XutilsHttp.Get(params, new PDataCallback<TaskDetailsBean>(DataCallback.DATA_KEY_TASK_DETAILS) {
            @Override
            public void flySuccess(DataBean<TaskDetailsBean> result) {
                if (!isViewAttached()) return;
                taskDetailsBean = result.getDataBean();
                getView().callbackTaskDetailsBean(taskDetailsBean);
            }

        });
    }

    public void requestDelete() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DELETE_USER_TASK);
        HashMap<String, String> map = new HashMap<>();
        map.put("myCardMissionId", taskDetailsBean.getMyCardMissionId());
        params.setBodyJson(map);
        XutilsHttp.Post(params, new PMapCallback() {
            @Override
            public void flySuccess(MapBean result) {
                if(!isViewAttached())return;
                getView().callbackDelete(result);
            }


        });
    }


}
