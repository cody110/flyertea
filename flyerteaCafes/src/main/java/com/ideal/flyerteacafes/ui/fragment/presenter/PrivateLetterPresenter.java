package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.PersonalLetterBean;
import com.ideal.flyerteacafes.model.entity.SystemMessageBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/12/7.
 */

public class PrivateLetterPresenter extends PullRefreshPresenter<PersonalLetterBean> {


    private PersonalLetterBean systemMessageBean;

    @Override
    public void init(Activity activity) {
        requestSystemMessage();
    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MYPM);
        params.addQueryStringParameter("page", page + "");
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.List_KEY_LIST, PersonalLetterBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (!isViewAttached()) return;
                if (response.getDataList() != null) {
                    int oldSize = datas.size();
                    if (page == 1) {
                        datas.clear();
                        if (systemMessageBean != null) {
                            datas.add(systemMessageBean);
                        }
                    }
                    datas.addAll(response.getDataList());
                    ver = response.getVer();
                    getView().callbackData(datas);
                    if (page == 1)
                        getView().headerRefreshSetListviewScrollLocation();
                    if (page > 1) {
                        if (oldSize < datas.size()) {
                            getView().footerRefreshSetListviewScrollLocation(oldSize);
                        } else {
                            page--;
                            setHasNext(false);
                            getView().notMoreData();
                        }
                    }
                }
            }

        });
    }


    /**
     * 获取系统消息
     */
    private void requestSystemMessage() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SYSTEM_MESSAGE);
        params.addQueryStringParameter("page", String.valueOf(page));
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.List_KEY_LIST, SystemMessageBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!DataUtils.isEmpty(result.getDataList())) {
                    String count = JsonAnalysis.getValueByKey(result.getData(), "count");
                    SystemMessageBean bean = (SystemMessageBean) result.getDataList().get(0);
                    systemMessageBean = new PersonalLetterBean();
                    systemMessageBean.setFace(bean.getFace());
                    systemMessageBean.setMsgfrom("系统消息");
                    systemMessageBean.setSubject(bean.getMessage());
                    systemMessageBean.setDateline(bean.getDateline());
                    systemMessageBean.setIsnew(DataTools.getInteger(count) == 0 ? "0" : "1");
                }
                requestDatas();
            }
        });
    }
}
