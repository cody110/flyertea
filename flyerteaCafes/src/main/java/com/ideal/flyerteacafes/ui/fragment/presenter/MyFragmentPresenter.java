package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.model.entity.UserProfileBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMyFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/17.
 */

public class MyFragmentPresenter extends BasePresenter<IMyFragment> {


    MyTaskAllBean myTaskAllBean;


    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestIsSignin();
        requestUserProfile();
    }

    public void uploadFace(Uri originalUri) {
        if (originalUri == null) return;
        Bitmap bitmap = BitmapTools.revitionImageSize(originalUri, 200, FlyerApplication.getContext());
        if (bitmap != null) {
            for (int i = 1; i < 4; i++) {
                uploadThreeToUpy(bitmap, UserManger.getUserInfo().getMember_uid(), "face", i);
            }
        } else {
            ToastUtils.showToast("获取图片失败");
        }

    }


    private void uploadThreeToUpy(Bitmap bitmap, String uid,
                                  String name, int index) {
        String sdFile = CacheFileManger.getCacheImagePath();

        String urlPath = null;

        UploadTask task = new UploadTask();
        task.uploadFace(uid, index);
        switch (index) {
            case 1:
                // big
                Bitmap big = BitmapTools.zoomImage(bitmap, 200, 133);
                String bigPath = sdFile + "/" + name + "_big.jpg";
                urlPath = bigPath;
                File bigFile = new File(bigPath);
                boolean bigBol = BitmapTools.saveJPGE_After(big, bigFile);
                if (bigBol) {
                    task.execute(bigPath);
                }
                break;
            case 2:
                // middle
                Bitmap middle = BitmapTools.zoomImage(bitmap, 120, 120);
                String middlePath = sdFile + "/" + name + "_middle.jpg";
                urlPath = middlePath;
                File middleFile = new File(middlePath);
                boolean middleBol = BitmapTools.saveJPGE_After(middle, middleFile);
                if (middleBol) {
                    task.execute(middlePath);
                }
                break;
            case 3:
                // small
                Bitmap small = BitmapTools.zoomImage(bitmap, 48, 48);
                String smallPath = sdFile + "/" + name + "_small.jpg";
                urlPath = smallPath;
                File smallFile = new File(smallPath);
                boolean smallBol = BitmapTools.saveJPGE_After(small, smallFile);
                if (smallBol) {
                    task.execute(smallPath);
                }
                break;
        }

        final String finalUrlPath = urlPath;
        task.setIUploadStatus(new UploadTask.IUploadStatus() {
            @Override
            public void uploadStatus(boolean result, Object data) {
                if (isViewAttached()) {
                    if (result) {
                        TagEvent event = new TagEvent(TagEvent.TAG_FACE_CHANGE);
                        Bundle bundle = new Bundle();
                        bundle.putString("data", finalUrlPath);
                        event.setBundle(bundle);
                        EventBus.getDefault().post(event);
                        getView().callbackFaceLocaPath(finalUrlPath);
                        ToastUtils.showToast("成功");
                    } else {
                        ToastUtils.showToast("失败");
                    }
                }
            }
        });

    }


    /**
     * 获取任务
     */
    public void getTaskData() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MY_TASK_LIST);
        params.addQueryStringParameter("progress", "doing");
        XutilsHttp.Get(params, new PDataCallback<MyTaskAllBean>() {

            @Override
            public void flySuccess(DataBean<MyTaskAllBean> result) {
                if (!isViewAttached()) return;
                if (result.getDataBean() == null) return;
                myTaskAllBean = result.getDataBean();
                getView().callbackTaskAll(result.getDataBean());
            }

        });
    }

    public boolean isSignin;

    /**
     * 签到
     */
    public void toSingin() {
        if (!isSignin)
            requestSignin();
    }


    /**
     * 签到
     */
    private void requestSignin() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_SIGNIN), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (isViewAttached()) {
                    BaseBean bean = JsonAnalysis.jsonToSignin(result);
                    if (bean.getCode().equals("success")) {
                        isSignin = true;
                        EventBus.getDefault().post(FinalUtils.SIGNINTRUE);
                        MobclickAgent.onEvent(context, FinalUtils.EventId.sign_in);// 签到统计
                    }
                    if (bean != null)
                        SmartUtil.BToast(context, bean.getMessage());
                    getView().setViewByIsSigin(isSignin);
                }
            }

        });
    }

    /**
     * 是否签到
     */
    public void requestIsSignin() {
        if (UserManger.isLogin())
            XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_IS_SIGNIN), new StringCallback() {
                @Override
                public void flySuccess(String result) {
                    if (isViewAttached()) {
                        BaseBean bean = JsonAnalysis.jsonToSignin(result);
                        if (bean.getCode().equals("error")) {//已经签到
                            isSignin = true;
                        } else {
                            isSignin = false;
                        }
                        getView().setViewByIsSigin(isSignin);
                    }
                }
            });
    }


    /**
     * 获取任务数据
     *
     * @return
     */
    public MyTaskAllBean getMyTaskAllBean() {
        return myTaskAllBean;
    }

    /**
     * 请求个人相关信息
     * 个性签名，实名信息
     */
    private void requestUserProfile() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_USERPROFILE);
        XutilsHttp.Get(params, new DataCallback<UserProfileBean>() {
            @Override
            public void flySuccess(DataBean<UserProfileBean> result) {
                if (result == null) return;
                getView().isSetBirthday(isUpdateBirthday(result.getDataBean()));
            }
        });
    }

    /**
     * true=可以修改生日
     * true=还没有没有设置过生日
     *
     * @return
     */
    private boolean isUpdateBirthday(UserProfileBean userProfileBean) {
        if (userProfileBean == null) return true;
        if (userProfileBean.getRealename() == null) return true;
        if (!TextUtils.isEmpty(userProfileBean.getRealename().getBirthyear()) && !TextUtils.equals(userProfileBean.getRealename().getBirthyear(), "0")
                && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthmonth()) && !TextUtils.equals(userProfileBean.getRealename().getBirthmonth(), "0")
                && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthday()) && !TextUtils.equals(userProfileBean.getRealename().getBirthday(), "0")) {
            return false;
        }
        return true;
    }


}
