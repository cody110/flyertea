package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.PullToZoomListView;

/**
 * Created by fly on 2016/12/27.
 */
public class TestActivity extends BaseActivity {


    PullToZoomListView listView;
    private String[] adapterData = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

//        View headerView = LayoutInflater.from(this).inflate(R.layout.test_header, null, false);
//        ImageView headerImg = (ImageView) headerView.findViewById(R.id.image);

        listView = (PullToZoomListView) findViewById(R.id.listview);


        for (int i = 0; i < adapterData.length; i++) {
            adapterData[i] = "cody" + i;
        }


        listView.setAdapter(new ArrayAdapter<>(TestActivity.this,
                android.R.layout.simple_list_item_1, adapterData));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaseDataManger.getInstance().registNewUser(adapterData[i], "cody1314");
            }
        });

    }


}
