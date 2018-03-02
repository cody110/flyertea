package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.MedalsBean;
import com.ideal.flyerteacafes.model.entity.MyMedalsBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.viewholder.XunzhangVH;
import com.ideal.flyerteacafes.ui.fragment.page.MyMedalsFragment;
import com.ideal.flyerteacafes.ui.popupwindow.ShareMedalsPopupWindow;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/11.
 * 我的勋章
 */

public class XunzhangActivity extends BaseActivity {


    private String showmedalid;
    private XunzhangVH vh;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showmedalid = getIntent().getStringExtra("showmedalid");
        setContentView(R.layout.activity_xunzhang);
        view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        vh = new XunzhangVH(view, iActionListener);
        getMyMedalsData();
    }

    XunzhangVH.IActionListener iActionListener = new XunzhangVH.IActionListener() {
        @Override
        public void actionClick(View view) {
            switch (view.getId()) {
                case ID_TO_CLOSE:
                    finish();
                    break;
            }
        }
    };

    /**
     * 获取到我的勋章
     */
    private void getMyMedalsData() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_GET_MY_MEDALS_DATA);
        proDialogShow();
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.DATA, MyMedalsBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                vh.initPage(getSupportFragmentManager(), result.getDataList(), showmedalid, getSelectIndex(result.getDataList()));
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

    /**
     * 根据showmedalid 获取在哪个大类下
     * @param datas
     * @return
     */
    private int getSelectIndex(List<MyMedalsBean> datas) {
        for (int i = 0; i < datas.size(); i++) {
            MyMedalsBean bean = datas.get(i);
            if (!TextUtils.isEmpty(showmedalid)) {
                for (int j = 0; j < bean.getMedals().size(); j++) {
                    for (int k = 0; k < bean.getMedals().get(j).getMedals().size(); k++) {
                        if (TextUtils.equals(bean.getMedals().get(j).getMedals().get(k).getMedalid(), showmedalid)) {
                            return i;
                        }
                    }
                }
            }

        }
        return 0;
    }


}
