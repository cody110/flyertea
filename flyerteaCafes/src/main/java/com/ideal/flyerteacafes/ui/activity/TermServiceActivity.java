package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 服务条款
 *
 * @author fly
 */
@ContentView(R.layout.activity_term_service)
public class TermServiceActivity extends BaseActivity {

    @ViewInject(R.id.include_left_title_text)
    TextView titleView;
    @ViewInject(R.id.term_service_body_text)
    TextView bodyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleView.setText(getResources().getString(R.string.title_name_term_service));
        bodyText.setText(getResources().getString(R.string.term_of_service));
    }

    @Event(R.id.include_left_title_back_layout)
    public void cancle(View v) {
        finish();
    }


}
