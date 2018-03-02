package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.content.Context;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.HomePagerBean;
import com.ideal.flyerteacafes.model.entity.TakeNotesBean;
import com.ideal.flyerteacafes.ui.fragment.interfaces.HomePagerImpl;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/1/21.
 */
public class HomePagerPresenter {

    private Context context;
    private HomePagerImpl viewImpl;
    private boolean isSignin = false;// 是否签到

    public HomePagerPresenter(HomePagerImpl impl){
        viewImpl=impl;
        context=FlyerApplication.getContext();
    }


    /**
     * 签到
     */
    public void toSingin(){
        if (UserManger.isLogin()) {
            if (!isSignin)
                requestSignin();
        } else {
            viewImpl.notLogin();
        }
    }

    /**
     * 每天24点，重置签到状态
     */
    public void resetSignStatus() {
        isSignin = false;
        viewImpl.chooseSigninView(isSignin);
    }

    /**
     * 获取选项卡的相关数据
     * @return
     */
    public List<HomePagerBean> getTabData(){
        List<HomePagerBean> tabBean=new ArrayList<>();
        String[] tabNameArray= getTabNameArray();
        String[] tabUrlArray={Utils.HttpRequest.HTTP_HOME_TAB_TUIJIAN,Utils.HttpRequest.HTTP_HOME_TAB_YOUHUI,Utils.HttpRequest.HTTP_HOME_TAB_LVXING,Utils.HttpRequest.HTTP_HOME_TAB_FEIXING,Utils.HttpRequest.HTTP_HOME_TAB_GONGLUE,Utils.HttpRequest.HTTP_HOME_TAB_FAXIAN,Utils.HttpRequest.HTTP_HOME_TAB_JIUDIAN,Utils.HttpRequest.HTTP_HOME_TAB_WEIWEN,Utils.HttpRequest.HTTP_HOME_TAB_HANGKONG,Utils.HttpRequest.HTTP_HOME_TAB_XINGYONGKA};
        int[] tabType=context.getResources().getIntArray(R.array.home_tab_type);
        for (int i = 0; i <tabNameArray.length ; i++) {
            HomePagerBean homePagerBean=new HomePagerBean(tabNameArray[i],tabUrlArray[i],tabType[i]);
            tabBean.add(homePagerBean);
        }
        return tabBean;
    }

    /**
     * 获取tab上的名称
     * @return
     */
    public String[] getTabNameArray(){
        String[] tabNameArray= context.getResources().getStringArray(R.array.home_tab_name);
        return tabNameArray;
    }

    /**
     * 签到
     */
    private void requestSignin() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_SIGNIN), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.jsonToSignin(result);
                if (bean.getCode().equals("success")) {
                    isSignin = true;
                    EventBus.getDefault().post(FinalUtils.SIGNINTRUE);
                    MobclickAgent
                            .onEvent(context, FinalUtils.EventId.sign_in);// 签到统计
                }
                if (bean != null)
                    SmartUtil.BToast(context, bean.getMessage());
                viewImpl.chooseSigninView(isSignin);
            }

        });
    }

    /**
     * 是否签到
     */
    public void requestIsSignin() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_IS_SIGNIN), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.jsonToSignin(result);
                if (bean.getCode().equals("error")) {//已经签到
                    isSignin=true;
                } else {
                    isSignin=false;
                }
                viewImpl.chooseSigninView(isSignin);
            }
        });
    }
}
