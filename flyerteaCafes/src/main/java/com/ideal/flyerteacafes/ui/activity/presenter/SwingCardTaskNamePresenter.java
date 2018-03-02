package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.BankListDataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.entity.CardGroupBean;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISwingCardTaskName;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/4/13.
 */

public class SwingCardTaskNamePresenter extends BasePresenter<ISwingCardTaskName> {


    private String bankid, channelid, keyword;
    public List<TaskNameBean> mDatas = new ArrayList<>();
    private List<BankBean> bankBeanList = new ArrayList<>();
    private List<CardGroupBean> cardGroupBeanList = new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestTaskName();
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
        requestTaskName();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        requestTaskName();
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
        requestTaskName();
    }

    public void showCardGroup() {
        if (cardGroupBeanList.isEmpty()) {
            requestCardGroup();
        } else {
            getView().bindDataCardGroupList(cardGroupBeanList);
        }
    }

    public void showBank() {
        if (bankBeanList.isEmpty()) {
            requestBank();
        } else {
            getView().bindDataBankList(bankBeanList);
        }
    }

    /**
     * 任务名称
     */
    private void requestTaskName() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SWINGCARD_TASKNAME);
        params.addQueryStringParameter("bankid", bankid);
        params.addQueryStringParameter("channelid", channelid);
        params.addQueryStringParameter("keyword", keyword);
        params.addQueryStringParameter("type", "system");
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_TASKNAME, TaskNameBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                mDatas.clear();
                mDatas.addAll(result.getDataList());
                getView().bindDataTaskNameList(mDatas);
            }
        });
    }

    /**
     * 卡组织
     */
    private void requestCardGroup() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CARD_GROUP);
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_CHANNELS, CardGroupBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                cardGroupBeanList.clear();
                CardGroupBean bean = new CardGroupBean();
                bean.setCardChannelName("全部");
                cardGroupBeanList.add(bean);
                cardGroupBeanList.addAll(result.getDataList());
                getView().bindDataCardGroupList(cardGroupBeanList);
            }
        });

    }


    /**
     * 银行
     */
    private void requestBank() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_BANK);
        XutilsHttp.Get(params, new BankListDataCallback(ListDataCallback.LIST_KEY_BANK, BankBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (isViewAttached()) {
                    bankBeanList.clear();
                    BankBean bean = new BankBean();
                    bean.setBankName("全部");
                    bean.setIndex("#");
                    bankBeanList.add(bean);
                    bankBeanList.addAll(result.getDataList());
                    getView().bindDataBankList(bankBeanList);
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                getDialog().proDialogDissmiss();
            }
        });
    }

}
