package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.DiscountListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportPresenter;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class DiscountFragment extends BaseFragment {


    @ViewInject(R.id.tv_flyholder)
    private TextView tv_flyholder;
    @ViewInject(R.id.tv_hotel)
    private TextView tv_hotel;
    @ViewInject(R.id.tv_flyer)
    private TextView tv_flyer;
    @ViewInject(R.id.tv_card)
    private TextView tv_card;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discount_fm, container, false);
        x.view().inject(this, view);
        setSelectTv(tv_hotel);
        addFragment();
        return view;
    }


    @Event({R.id.tv_flyholder, R.id.tv_hotel, R.id.tv_flyer, R.id.tv_card})
    private void click(View view) {

        setSelectTv((TextView) view);
        switch (view.getId()) {
            case R.id.tv_flyholder:
                fragment.setType("youhui_type", "1");
                fragment.setDetailsTitle("飞客专享优惠");
                break;
            case R.id.tv_hotel:
                fragment.setType("youhui_type", "19");
                fragment.setDetailsTitle("酒店优惠");
                break;
            case R.id.tv_flyer:
                fragment.setType("youhui_type", "57");
                fragment.setDetailsTitle("航空优惠");
                break;
            case R.id.tv_card:
                fragment.setType("youhui_type", "226");
                fragment.setDetailsTitle("信用卡优惠");
                break;
        }
        String name = ((TextView) view).getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.coupon_tab_click, map);
    }


    private void setSelectTv(TextView tv) {
        setNotSelectTvStyle(tv_flyholder);
        setNotSelectTvStyle(tv_hotel);
        setNotSelectTvStyle(tv_flyer);
        setNotSelectTvStyle(tv_card);
        setSelectTvStyle(tv);
    }


    private void setSelectTvStyle(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.app_black));
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void setNotSelectTvStyle(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.app_body_grey));
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }


    ReportListFragment fragment;
    String fmTagName;

    private void addFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (fmTagName != null)
            fragment = (ReportListFragment) getChildFragmentManager().findFragmentByTag(fmTagName);
        if (fragment == null) {
            fragment = createFragment();
            fmTagName = fragment.getFmTagName();
            transaction.add(R.id.fragment_layout, fragment, fmTagName);
            fragment.setDetailsTitle("酒店优惠");
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }


    private ReportListFragment createFragment() {
        ReportListFragment reportListFragment = ReportListFragment.setArguments(new DiscountListFragment(), Utils.HttpRequest.HTTP_DISCOUNT, "youhui_type", "19");
        reportListFragment.setIGetSortid(new ReportPresenter.IGetSortid() {
            @Override
            public String getSortid() {
                if (BaseDataManger.getInstance().getTypeBaseBean() == null) return null;
                if (BaseDataManger.getInstance().getTypeBaseBean().getDiscountRaiders() == null)
                    return null;
                return BaseDataManger.getInstance().getTypeBaseBean().getDiscountRaiders().getSortid();
            }
        });
        return reportListFragment;
    }
}
