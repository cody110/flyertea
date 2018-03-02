package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.BankAdapter;
import com.ideal.flyerteacafes.callback.BankListDataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
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
 * Created by fly on 2017/4/21.
 */
@ContentView(R.layout.activity_bank)
public class BankActivity extends BaseActivity {

    @ViewInject(R.id.listview)
    ListView listView;

    BankAdapter adapter;

    List<BankBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        requestBank();
    }

    @Event(R.id.toolbar_left)
    private void onclick(View v) {
        finish();
    }

    @Event(value = R.id.listview, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas.get(position));
        jumpActivitySetResult(bundle);
    }

    /**
     * 银行
     */
    private void requestBank() {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_BANK);
        XutilsHttp.Get(params, new BankListDataCallback(ListDataCallback.LIST_KEY_BANK, BankBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                datas.clear();
                datas.addAll(result.getDataList());
                adapter = new BankAdapter(BankActivity.this, datas, R.layout.item_bank);
                listView.setAdapter(adapter);
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }
}
