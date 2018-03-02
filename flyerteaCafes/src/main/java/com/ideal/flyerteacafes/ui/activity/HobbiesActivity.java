package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.HobbiesBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * 兴趣爱好
 *
 * @author fly
 */
@ContentView(R.layout.activity_creditcard_point_choose)
public class HobbiesActivity extends BaseActivity {

    @ViewInject(R.id.creditcard_point_choose_listview)
    private ListView mListView;
    @ViewInject(R.id.include_title_text)
    private TextView titleView;
    @ViewInject(R.id.include_title_right_btn)
    private View rightView;
    @ViewInject(R.id.include_title_right_img)
    private ImageView rightImg;
    @ViewInject(R.id.include_title_right_text)
    private TextView rightText;

    private List<HobbiesBean> beanList;
    private CommonAdapter<HobbiesBean> adapter;
    private String[] hobbiesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        rightView.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        rightText.setText("完成");
        titleView.setText(getString(R.string.hobbies));
        String hobbies = getIntent().getStringExtra("hobbies");
        if (!DataUtils.isEmpty(hobbies)) {
            hobbiesArray = hobbies.split("\t\t");
        }
        requestInterest();
    }

    /**
     * 兴趣
     */
    private void requestInterest() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_INTEREST), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                beanList = JsonAnalysis.getHobbiesList(result, "interest");
                if (!DataUtils.isEmpty(beanList)) {
                    //修改兴趣标识，是否选中
                    if (hobbiesArray != null && hobbiesArray.length > 0) {
                        for (HobbiesBean bean : beanList) {
                            for (String str : hobbiesArray) {
                                if (bean.getHobbies().equals(str)) {
                                    bean.setMark(1);
                                }
                            }
                        }
                    }
                    //初始化适配器
                    adapter = new CommonAdapter<HobbiesBean>(FlyerApplication.getContext(), beanList, R.layout.listview_creditcard_choose_item) {

                        @Override
                        public void convert(ViewHolder holder, HobbiesBean t) {
                            // TODO Auto-generated method stub
                            if (t.getHobbies() != null)
                                holder.setText(R.id.creditcard_choose_item_name, t.getHobbies());
                            if (t.getMark() == 0)
                                holder.setVisible(R.id.creditcard_choose_item_img, false);
                            else
                                holder.setVisible(R.id.creditcard_choose_item_img, true);
                        }
                    };
                    mListView.setAdapter(adapter);
                }
            }
        });
    }

    @Event(value = R.id.creditcard_point_choose_listview,type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view,
                          int position, long id) {
        HobbiesBean bean = adapter.getItem(position);
        if (bean.getMark() == 0) {
            bean.setMark(1);
        } else {
            bean.setMark(0);
        }
        adapter.notifyDataSetChanged();
    }

    @Event({R.id.include_title_menu_btn, R.id.include_title_right_btn})
    private void onActionClick(View v) {
        switch (v.getId()) {
            case R.id.include_title_menu_btn:
                finish();
                break;

            case R.id.include_title_right_btn:
                Bundle bundle = new Bundle();
                if (beanList != null)
                    bundle.putSerializable("hobbies", (Serializable) beanList);
                jumpActivitySetResult(EditDataActivity.class, bundle);
                break;
        }

    }

}
