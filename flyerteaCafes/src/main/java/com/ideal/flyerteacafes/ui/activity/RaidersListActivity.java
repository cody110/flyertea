package com.ideal.flyerteacafes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.RaidersListFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportPresenter;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2017/8/31.
 */

public class RaidersListActivity extends AddFmActivity {

    public static void startRaidersListActivity(Context context, String title, String requestUrl, String sortid, String typeKey, String typeValue, String catid) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("requestUrl", requestUrl);
        bundle.putString("sortid", sortid);
        bundle.putString("typeKey", typeKey);
        bundle.putString("typeValue", typeValue);
        bundle.putString("catid", catid);
        Intent intent = new Intent(context, RaidersListActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    String title, requestUrl, sortid, typeKey, typeValue, catid;

    @Override
    public void initVariables() {
        super.initVariables();
        title = getIntent().getStringExtra("title");
        requestUrl = getIntent().getStringExtra("requestUrl");
        sortid = getIntent().getStringExtra("sortid");
        typeKey = getIntent().getStringExtra("typeKey");
        typeValue = getIntent().getStringExtra("typeValue");
        catid = getIntent().getStringExtra("catid");
    }

    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle(title);
        toolBar.setTitleMaxEmsEnd(10);
        toolBar.setRightImgResource(R.drawable.icon_search);
        toolBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreadSearchActivity.toSearchrAiders(RaidersListActivity.this);
            }
        });
    }

    @Override
    protected BaseFragment createFragment() {
        ReportListFragment reportListFragment = ReportListFragment.setArguments(new RaidersListFragment(), requestUrl, typeKey, typeValue);
        reportListFragment.setIGetSortid(new ReportPresenter.IGetSortid() {
            @Override
            public String getSortid() {
                return sortid;
            }
        });

        String detailsTilte = null;
        if (TextUtils.equals(catid, "2")) {
            detailsTilte = "酒店攻略";
        } else if (TextUtils.equals(catid, "3")) {
            detailsTilte = "航空攻略";
        } else if (TextUtils.equals(catid, "314")) {
            detailsTilte = "信用卡攻略";
        }
        reportListFragment.setDetailsTitle(detailsTilte);

        return reportListFragment;
    }

}
