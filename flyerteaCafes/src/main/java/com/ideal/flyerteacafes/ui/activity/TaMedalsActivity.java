package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.MedalsBean;
import com.ideal.flyerteacafes.model.entity.MyMedalsSubBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.NoScrollGridView;
import com.ideal.flyerteacafes.ui.popupwindow.MedalsPopupWindow;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/10/19.
 */
@ContentView(R.layout.activity_ta_medals)
public class TaMedalsActivity extends BaseActivity {

    @ViewInject(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        List<MedalsBean> medalsBeanList = (List<MedalsBean>) getIntent().getSerializableExtra("data");
        CommonAdapter<MedalsBean> adapter = new CommonAdapter<MedalsBean>(this, medalsBeanList, R.layout.item_medals_layout) {
            @Override
            public void convert(ViewHolder holder, final MedalsBean medalsBean) {
                holder.setText(R.id.name_tv, medalsBean.getName());
                holder.setText(R.id.num_tv, "（已获得" + medalsBean.getMedals().size() + ")");

                final CommonAdapter<MyMedalsSubBean> subAdapter = new CommonAdapter<MyMedalsSubBean>(mContext, medalsBean.getMedals(), R.layout.item_medals_sub_layout) {
                    @Override
                    public void convert(ViewHolder holder, MyMedalsSubBean medalsSubBean) {
                        ImageView igv = holder.getView(R.id.media_igv);
                        x.image().bind(igv, medalsSubBean.getImage(), XutilsHelp.image_FIT_XY);
                        holder.setText(R.id.name_tv, medalsSubBean.getName());
                        holder.setVisible(R.id.time_tv,false);
                    }
                };

                final NoScrollGridView gridview = holder.getView(R.id.gridview);
                gridview.setAdapter(subAdapter);

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MedalsPopupWindow popupWindow = new MedalsPopupWindow(TaMedalsActivity.this);
                        popupWindow.showAtLocation(gridview, Gravity.CENTER, 0, 0);
                        popupWindow.bindData(subAdapter.getItem(i));
                    }
                });

            }
        };
        listView.setAdapter(adapter);

    }

    @Event(R.id.toolbar_left)
    private void click(View v) {
        finish();
    }
}
