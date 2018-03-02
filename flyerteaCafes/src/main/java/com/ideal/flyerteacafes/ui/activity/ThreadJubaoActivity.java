package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.JubaoAdapter;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadJubao;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadJubaoPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2016/12/20.
 * 帖子举报原因
 */
@ContentView(R.layout.activity_thread_jubao)
public class ThreadJubaoActivity extends MVPBaseActivity<IThreadJubao, ThreadJubaoPresenter> implements IThreadJubao {

    @ViewInject(R.id.listview)
    ListView listView;


    private JubaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPresenter.init(this);
    }

    @Override
    protected ThreadJubaoPresenter createPresenter() {
        return new ThreadJubaoPresenter();
    }


    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.toolbar_right:
                mPresenter.actionCommit(adapter.getItem(adapter.getSelectIndex()));
                break;
        }
    }

    @Event(value = R.id.listview, type = AdapterView.OnItemClickListener.class)
    private void gridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectIndex(position);
    }

    @Override
    public void callbackData(List<String> datas) {
        if (adapter == null) {
            adapter = new JubaoAdapter(this, datas, R.layout.listview_jubao_item);
            listView.setAdapter(adapter);
            adapter.setSelectIndex(0);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
