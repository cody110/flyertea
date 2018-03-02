package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ServiceBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.CreditCardPointActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.web.TripWebActivity;
import com.ideal.flyerteacafes.ui.activity.YouzanActivity;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardHome;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardNormalHome;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.layout.ServiceGroupLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/3/30.
 */

public class ServiceFragment extends BaseFragment {


    @ViewInject(R.id.service_content)
    LinearLayout service_content;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fm_service, null);
        x.view().inject(this, mView);
        initViews();
        request();
        return mView;
    }


    private void request() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SERVICE);
        params.addQueryStringParameter("appversion", Tools.getVersion(mActivity));
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_SERVICE, ServiceBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                for (int i = 0; i < result.getDataList().size(); i++) {
                    ServiceBean bean = (ServiceBean) result.getDataList().get(i);
                    List<ServiceBean.SubBean> subList = bean.getSub();

                    View titleItem = LayoutInflater.from(mActivity).inflate(R.layout.include_service_title_layout, null);
                    TextView titleTv = V.f(titleItem, R.id.service_service_title);
                    TextView desTv = V.f(titleItem, R.id.service_service_title_des);
                    WidgetUtils.setText(titleTv, bean.getName());
                    WidgetUtils.setText(desTv, bean.getName_des());
                    WidgetUtils.setVisible(desTv, !TextUtils.isDigitsOnly(bean.getName_des()));


                    service_content.addView(titleItem);


                    LinearLayout linearLayout = new LinearLayout(mActivity);
                    linearLayout.setBackgroundResource(R.drawable.frames_top_bottom);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    service_content.addView(linearLayout, layoutParams);


                    switch (i) {
                        case 0:
                            serviceLineOneView(linearLayout, subList);
                            break;
                        case 1:
                            serviceLineTwoView(linearLayout, subList);
                            break;
                        case 2:
                            serviceLineTwoView(linearLayout, subList);
                            break;
                    }
                }
            }
        });
    }


    private void serviceLineOneView(LinearLayout service_layout, List<ServiceBean.SubBean> serviceSubList) {

        for (int i = 0; i < serviceSubList.size(); i++) {
            if (i != 0) {
                View lineView = new View(mActivity);
                lineView.setBackgroundResource(R.color.app_line_color);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1);
                service_layout.addView(lineView, layoutParams);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ServiceGroupLayout layout = new ServiceGroupLayout(mActivity, null);
            layout.bindData(serviceSubList.get(i), true);
            layout.setOnClickListener(new ServiceGroupLayoutClick(serviceSubList.get(i)));
            service_layout.addView(layout, layoutParams);

        }

    }

    private void serviceLineTwoView(LinearLayout service_layout, List<ServiceBean.SubBean> serviceSubList) {
        for (int i = 0; i < serviceSubList.size(); i++) {
            //TODO line layout
            LinearLayout linearLayout;
            if (i % 2 == 0) {
                linearLayout = new LinearLayout(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                service_layout.addView(linearLayout, layoutParams);
            } else {
                linearLayout = (LinearLayout) service_layout.getChildAt(service_layout.getChildCount() - 1);
            }

            //TODO item layout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;

            ServiceGroupLayout layout = new ServiceGroupLayout(mActivity, null);
            layout.bindData(serviceSubList.get(i), false);
            layout.setOnClickListener(new ServiceGroupLayoutClick(serviceSubList.get(i)));
            linearLayout.addView(layout, layoutParams);

            //TODO 中间线
            if (i % 2 == 0 && i != serviceSubList.size() - 1) {
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                        1, DensityUtil.dip2px(30));
                lineParams.gravity = Gravity.CENTER_VERTICAL;
                View lineView = new View(mActivity);
                lineView.setBackgroundResource(R.color.app_line_color);
                linearLayout.addView(lineView, lineParams);
            }

            //TODO 底线
            if (i % 2 != 0 && i != serviceSubList.size() - 1) {
                View lineView = new View(mActivity);
                lineView.setBackgroundResource(R.color.app_line_color);
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1);
                service_layout.addView(lineView, lineParams);
            }

        }
    }

    class ServiceGroupLayoutClick implements View.OnClickListener {

        ServiceBean.SubBean bean;

        public ServiceGroupLayoutClick(ServiceBean.SubBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View view) {

            Bundle bundle = new Bundle();
            if (TextUtils.equals(bean.getId(), "1")) {//TODO 旅行 需要处理URL,追加标识 Android 端
                String url = bean.getMobileurl() + "Android_TabBar";
                bundle.putString("title", bean.getSubject());
                bundle.putString("url", url);
                jumpActivity(TripWebActivity.class, bundle);
            } else if (TextUtils.equals(bean.getId(), "6")) {//TODO 飞客优选，有赞
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.youzan);
                Intent intent = new Intent(getActivity(), YouzanActivity.class);
                intent.putExtra(YouzanActivity.SIGN_URL, bean.getMobileurl());
                startActivity(intent);

            } else if (TextUtils.equals(bean.getId(), "5")) {//TODO 飞米商城
                bundle.putString("title", bean.getSubject());
                bundle.putString("url", DataUtils.getFeimiUrl(bean.getMobileurl()));
                jumpActivity(TbsWebActivity.class, bundle);
            } else if (TextUtils.equals(bean.getId(), "10")) {//TODO MCC查询
                jumpActivity(CreditCardPointActivity.class, null);
            } else if (TextUtils.equals(bean.getId(), "14")) {//TODO 刷卡任务
                if (UserManger.isLogin()) {
                    if (UserManger.getInstance().getMissions() == 0) {
                        jumpActivity(SwingCardNormalHome.class, null);
                    } else {
                        jumpActivity(SwingCardHome.class, null);
                    }
                } else {
                    DialogUtils.showDialog(mActivity);
                }
            } else {

                if (DataUtils.isYouZanUrl(bean.getMobileurl())) {
                    bundle.putString(YouzanActivity.SIGN_URL, bean.getMobileurl());
                    jumpActivity(YouzanActivity.class, bundle);
                } else {
                    bundle.putString("title", bean.getSubject());
                    bundle.putString("url", bean.getMobileurl());
                    jumpActivity(TbsWebActivity.class, bundle);
                }


            }


        }
    }


}
