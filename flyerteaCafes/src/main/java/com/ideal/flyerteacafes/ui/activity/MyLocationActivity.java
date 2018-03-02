package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.LocationFragment;
import com.ideal.flyerteacafes.ui.fragment.page.ViewpagerLocationFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/10/17.
 */
@ContentView(R.layout.activity_mylocation)
public class MyLocationActivity extends BaseActivity {


    @ViewInject(R.id.content_layout)
    FrameLayout content_layout;
    LocationListBean locationListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        locationListBean = (LocationListBean) getIntent().getSerializableExtra("data");


        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();


        int isDataSize = 0;
        List<LocationListBean.LocationBean> datas = null;
        List<TagBean> tags = null;

        if (!DataUtils.isEmpty(locationListBean.getHotels())) {
            isDataSize++;
            datas = locationListBean.getHotels();
            tags = locationListBean.getHoteltags();
        }

        if (!DataUtils.isEmpty(locationListBean.getAirports())) {
            isDataSize++;
            datas = locationListBean.getAirports();
            tags = locationListBean.getAirporttags();
        }

        if (!DataUtils.isEmpty(locationListBean.getLounges())) {
            isDataSize++;
            datas = locationListBean.getLounges();
            tags = locationListBean.getLoungetags();
        }

        if (isDataSize < 1) return;

        if (isDataSize > 1) {
            ViewpagerLocationFragment fm = (ViewpagerLocationFragment) getSupportFragmentManager().findFragmentByTag("ViewpagerLocationFragment");
            if (fm == null) {
                fm = ViewpagerLocationFragment.getFragment(locationListBean);
                transaction.add(R.id.content_layout, fm, "ViewpagerLocationFragment");
            } else {
                transaction.show(fm);
            }
        } else {
            LocationFragment fm = (LocationFragment) getSupportFragmentManager().findFragmentByTag("LocationFragment");
            if (fm == null) {
                fm = LocationFragment.getFragment(datas, tags);
                transaction.add(R.id.content_layout, fm, "LocationFragment");
            } else {
                transaction.show(fm);
            }
        }
        transaction.commitAllowingStateLoss();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data.getSerializableExtra("data"));
                bundle.putSerializable("tags", data.getSerializableExtra("tags"));
                bundle.putFloat("star", data.getFloatExtra("star", 0));
                bundle.putString("flight", data.getStringExtra("flight"));
                bundle.putString("flightid", data.getStringExtra("flightid"));
                jumpActivitySetResult(bundle);
            } else if (requestCode == 1) {
                Bundle bundle = new Bundle();
                bundle.putString("cityname", data.getStringExtra("cityname"));
                bundle.putString("location", data.getStringExtra("location"));
                jumpActivitySetResult(bundle);
            } else if (requestCode == 3) {
                Bundle bundle = new Bundle();
                LocationListBean.LocationBean locationBean = (LocationListBean.LocationBean) data.getSerializableExtra("data");
                bundle.putSerializable("data", data.getSerializableExtra("data"));
                if (TextUtils.equals(locationBean.getType(), "airport")) {
                    bundle.putSerializable("tags", (Serializable) locationListBean.getAirporttags());
                } else if (TextUtils.equals(locationBean.getType(), "hotel")) {
                    bundle.putSerializable("tags", (Serializable) locationListBean.getHoteltags());
                } else if (TextUtils.equals(locationBean.getType(), "lounge")) {
                    bundle.putSerializable("tags", (Serializable) locationListBean.getLoungetags());
                }
                jumpActivityForResult(RatingActivity.class, bundle, 0);

            }
        }
    }

    @Event({R.id.see_more_btn, R.id.toolbar_left})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.see_more_btn:
                jumpActivityForResult(LocationListActivity.class, null, 1);
                break;

            case R.id.toolbar_left:
                finish();
                break;
        }
    }
}
