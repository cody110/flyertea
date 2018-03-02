package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.LocationSearchFragment;
import com.ideal.flyerteacafes.ui.view.SerachEdittext;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/11/2.
 */
@ContentView(R.layout.activity_location_search)
public class LocationSearchActivity extends BaseActivity {

    @ViewInject(R.id.search_edittext)
    SerachEdittext thread_search_edittext;

    LocationSearchFragment threadSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        threadSearchFragment = (LocationSearchFragment) getSupportFragmentManager().findFragmentById(R.id.locationsearchfragment);

        thread_search_edittext.setHint("输入搜索字词");


        thread_search_edittext.setISearchClick(new SerachEdittext.ISearchClick() {
            @Override
            public void onSearchClick(String text) {
                requestSearch(text);
            }
        });
        
    }

    private void requestSearch(String text) {
        threadSearchFragment.requestSearch(text);

    }
}
