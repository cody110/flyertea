package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotelReservationAdapter;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.HotelBean;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 酒店预订
 *
 * @author fly
 */
@ContentView(R.layout.activity_hotel_reservation)
public class HotelReservationActivity extends BaseActivity {

    @ViewInject(R.id.hotel_reservation_gridview)
    private GridView mGridView;

    private List<HotelBean> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, FinalUtils.EventId.hotel_reservation_1);//友盟统计
        x.view().inject(this);
        initData();
    }

    private void initData() {
        request();
    }

    @Event(value = R.id.hotel_reservation_gridview,type = AdapterView.OnItemClickListener.class)
    public void toHotelDetailsActivity(AdapterView<?> parent, View view,
                                       int position, long id) {
        Intent intent = new Intent(HotelReservationActivity.this,
                HotelDetailsActivity.class);
        intent.putExtra("bean", hotelList.get(position));
        intent.putExtra("id", position);
        startActivity(intent);
    }

    private void request() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOTEL), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                ListObjectBean dataBean = JsonAnalysis.jsonToHotelListBean(result);
                hotelList = dataBean.getDataList();
                if (!DataUtils.isEmpty(hotelList)) {
                    HotelReservationAdapter adapter = new HotelReservationAdapter(
                            HotelReservationActivity.this, hotelList);
                    mGridView.setAdapter(adapter);
                } else {
                    BToast(dataBean.getMessage());
                }
            }
        });
    }

}
