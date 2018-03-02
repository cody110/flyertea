package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Intent;

import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.ReportreasonBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadJubao;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fly on 2016/12/20.
 */

public class ThreadJubaoPresenter extends BasePresenter<IThreadJubao> {


    public static final String BUNDLE_PID = "pid", BUNDLE_TID = "tid", BUNDLE_FID = "fid";
    private String pid, tid, fid;

    private List<String> datas = new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        Intent intent = activity.getIntent();
        pid = intent.getStringExtra(BUNDLE_PID);
        tid = intent.getStringExtra(BUNDLE_TID);
        fid = intent.getStringExtra(BUNDLE_FID);
        requestData();
    }

    /**
     * 举报原因
     */
    private void requestData() {
        getDialog().proDialogShow();
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_JUBAO_LIST), new PDataCallback<ReportreasonBean>() {
            @Override
            public void flySuccess(DataBean<ReportreasonBean> result) {
                if (!isViewAttached()) return;
                datas.clear();
                if (result.getDataBean().getReportreason() != null) {
                    Collections.addAll(datas, result.getDataBean().getReportreason());
                }
                getView().callbackData(datas);
            }
        });
    }

    /**
     * 提交举报
     *
     * @param message
     */
    public void actionCommit(String message) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_JUBAO);
        params.addQueryStringParameter("rid", pid);
        params.addQueryStringParameter("tid", tid);
        params.addQueryStringParameter("fid", fid);
        params.addQueryStringParameter("reason", message);

        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PStringCallback() {

            @Override
            public void flySuccess(String result) {
                if(!isViewAttached())return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    ToastUtils.showToast("举报成功");
                    getBaseView().endActivity();
                } else {
                    ToastUtils.showToast("举报失败");
                }
            }
        });
    }

}
