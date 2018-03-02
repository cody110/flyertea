package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.entity.TeaBean;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by fly on 2017/3/30.
 */

public class BaseDataManger {

    private static BaseDataManger instance;

    private BaseDataManger() {
    }

    public static BaseDataManger getInstance() {
        if (instance == null) {
            synchronized (BaseDataManger.class) {
                instance = new BaseDataManger();
            }
        }
        return instance;
    }

    /**
     * 全局变量isActive = false 记录当前已经进入后台
     * 默认true 在前台
     */
    private boolean isActive = true;
    //记录进入后台的时间点
    private long toForegroundTime;

    public long getToForegroundTime() {
        return toForegroundTime;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        if (!isActive) {
            toForegroundTime = DateUtil.getDateline();
        }
    }

    public boolean isActive() {
        return isActive;
    }


    private TeaBean teaBean;

    public TeaBean getTeaBean() {
        return teaBean;
    }

    /**
     * 获取我的消息数量
     */
    //TODO homefragment onstart 进行调用刷新数据
    public void requestGetNum() {
        if (UserManger.isLogin()) {
            FlyRequestParams params1 = new FlyRequestParams(Utils.HttpRequest.HTTP_GET_NUMBER);
            XutilsHttp.Get(params1, new DataCallback<NumberBean>() {
                @Override
                public void flySuccess(DataBean<NumberBean> result) {
                    if (result.getDataBean() == null) return;
                    for (IMsgNum i : iMsgNumList) {
                        i.msgNum(result.getDataBean());
                    }
                    UserManger.getInstance().savaMissions(result.getDataBean().getMissions());
                    if (UserManger.getUserInfo() != null) {
                        UserManger.getUserInfo().setCredits(result.getDataBean().getCredit());
                    }
                }
            });
        }
    }

    /**
     * 请求基础数据
     */
    public void requestTea() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_TEA), new DataCallback<TeaBean>() {
            @Override
            public void flySuccess(DataBean<TeaBean> result) {
                teaBean = result.getDataBean();
            }
        });
    }


    public interface IMsgNum {
        void msgNum(NumberBean bean);
    }

    private List<IMsgNum> iMsgNumList = new ArrayList<>();


    public void registerIMsgNum(IMsgNum iMsgNum) {
        iMsgNumList.add(iMsgNum);
    }

    public void unRegisterIMsgNum(IMsgNum iMsgNum) {
        if (iMsgNum != null)
            iMsgNumList.remove(iMsgNum);
    }

    public interface ITypeBaseBean {
        void callbackTypeBaseBean(TypeBaseBean bean);
    }

    private List<ITypeBaseBean> iTypeBaseBeanList = new ArrayList<>();


    public void registerITypeBaseBean(ITypeBaseBean iTypeBaseBean) {
        iTypeBaseBeanList.add(iTypeBaseBean);
    }

    public void unRegisterITypeBaseBean(ITypeBaseBean iTypeBaseBean) {
        iTypeBaseBeanList.remove(iTypeBaseBean);
    }


    /**
     * 分类接口
     */
    public void requestType() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_TYPE), new DataCallback<TypeBaseBean>() {
            @Override
            public void flySuccess(DataBean<TypeBaseBean> result) {
                baseBean = result.getDataBean();
                if (baseBean != null) {
                    for (ITypeBaseBean i : iTypeBaseBeanList) {
                        i.callbackTypeBaseBean(baseBean);
                    }
                }
            }
        });
    }

    TypeBaseBean baseBean;

    public TypeBaseBean getTypeBaseBean() {
        if (baseBean == null)
            requestType();
        return baseBean;
    }

    public void getTypeBaseBean(ITypeBaseBean iTypeBaseBean) {
        iTypeBaseBeanList.add(iTypeBaseBean);
        if (baseBean != null) {
            iTypeBaseBean.callbackTypeBaseBean(baseBean);
        }
    }


    /**
     * 标记某条消息为已读
     */
    public void requestMarkPositionReads(String markid) {
        if (TextUtils.isEmpty(markid)) return;
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MARK_MESSAGE_READS);
        params.addQueryStringParameter("markid", markid);
        params.addQueryStringParameter("noticetype", "mark");
        XutilsHttp.Get(params, null);
    }

    /**
     * 标记某个分类为已读
     */
    public void requestMarkTypeReads(String marktype) {
        if (TextUtils.isEmpty(marktype)) return;
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MARK_MESSAGE_READS);
        params.addQueryStringParameter(Marks.MarkType.MARKTYPE, marktype);
        params.addQueryStringParameter("noticetype", "mark");
        XutilsHttp.Get(params, null);
    }


    /**
     * 现APP已删除此功能，本地测试用
     * 注册新账号
     *
     * @param userName
     * @param passWord
     */
    public void registNewUser(String userName, String passWord) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REGISTER);
        params.addQueryStringParameter("regtype", "2");
        params.addQueryStringParameter("uname",
                StringTools.encodeBase64(userName.getBytes()));
        params.addQueryStringParameter("pwd1",
                StringTools.encodeBase64(passWord.getBytes()));
        params.addQueryStringParameter("pwd2",
                StringTools.encodeBase64(passWord.getBytes()));
        String registrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addQueryStringParameter("Registrationid", registrationID);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                if (loginBase != null && loginBase.getMessage() != null) {
                    LogFly.e(loginBase.getMessage().getCode() + "  " + loginBase.getMessage().getMessage());
                } else {
                    LogFly.e(result);
                }
            }
        });
    }


}
