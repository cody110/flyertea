package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.HotelsInfo;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/11/27.
 */
@ContentView(R.layout.activity_hotel_serach)
public class HotelSerachActivity extends BaseActivity {

    @ViewInject(R.id.hotel_serach_listview)
    ListView hotel_serach_listview;
    @ViewInject(R.id.hotel_search_et)
    EditText hotel_serach_et;
    @ViewInject(R.id.hotel_search_clear_img)
    ImageView hotel_search_clear_img;

    CommonAdapter<HotelsInfo> adapter;
    List<HotelsInfo> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        hotel_serach_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                requestHotelserch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Event(R.id.hotel_serach_cancle)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.hotel_serach_cancle://取消
                finish();
                break;
        }
    }

    @Event(value = R.id.hotel_serach_listview, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(WriteThreadPresenter.BUNDLE_RESULT_HOTELSINFO, listData.get(position));
        jumpActivitySetResult(bundle);
    }

    private void requestHotelserch(String key) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_HOTEL_SEARCH);
        params.addQueryStringParameter("search_key", key);
        String uri = params.getUri();
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                listData.clear();
                ListDataBean listDataBean = JsonAnalysis.getHotelSerachList(result);
                if (listDataBean.getDataList() != null && listDataBean.getDataList().size() > 0) {
                    listData.addAll(listDataBean.getDataList());
                }
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        if (adapter == null) {
            hotel_serach_listview.setAdapter(adapter = new CommonAdapter<HotelsInfo>(this, listData, R.layout.item_hotel_serach) {
                @Override
                public void convert(ViewHolder holder, HotelsInfo hotelsInfo) {
                    holder.setText(R.id.item_hotel_serach_tv, hotelsInfo.getTitle());
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }


}
