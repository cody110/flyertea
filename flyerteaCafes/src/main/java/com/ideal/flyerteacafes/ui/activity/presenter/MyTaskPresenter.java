package com.ideal.flyerteacafes.ui.activity.presenter;

import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.interfaces.IMyTaskList;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/5/17.
 */

public class MyTaskPresenter extends BasePresenter<IMyTaskList> {


    public void getDoing() {
        getTaskData("doing");
    }

    public void getFailed() {
        getTaskData("failed");
    }


    public void getDone() {
        getTaskData("done");
    }

    private void getTaskData(String progress) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MY_TASK_LIST);
        params.addQueryStringParameter("progress", progress);
        XutilsHttp.Get(params, new PDataCallback<MyTaskAllBean>() {

            @Override
            public void flySuccess(DataBean<MyTaskAllBean> result) {
                if (!isViewAttached()) return;
                getView().callbackTaskAll(result.getDataBean());
            }

        });
    }

}
