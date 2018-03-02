package com.ideal.flyerteacafes.ui.activity;

import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.CreditCardChooseAdapter;
import com.ideal.flyerteacafes.model.entity.CreditCardBean;
import com.ideal.flyerteacafes.model.params.ChoosePosEvent;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.ToolBar;

import de.greenrobot.event.EventBus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_creditcard_point_choose)
public class CreditCardChooseActivity extends BaseActivity {

    @ViewInject(R.id.creditcard_point_choose_listview)
    private ListView mListView;
    @ViewInject(R.id.toolbar)
    private ToolBar toolBar;

    private int pos = 0;

    @SuppressWarnings({"unused", "unchecked"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String titleName = getIntent().getStringExtra("titleName");
        toolBar.setTitle(titleName);
        EventBus.getDefault().register(this);
        List<CreditCardBean> listBean = (List<CreditCardBean>) getIntent().getSerializableExtra("list");
        int pos = getIntent().getIntExtra("pos", 0);
        if (listBean == null)
            return;
        CreditCardChooseAdapter adapter = new CreditCardChooseAdapter(this, listBean, pos);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //接收adapter点击项
    public void onEventMainThread(ChoosePosEvent event) {
        this.pos = event.pos;
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void backClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;
            case R.id.toolbar_right:
                Intent intent = new Intent();
                intent.setClass(CreditCardChooseActivity.this, CreditCardPointActivity.class);
                intent.putExtra("pos", pos);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }

}
