package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.ui.activity.presenter.MyTaskPresenter;
import com.ideal.flyerteacafes.ui.controls.VerticalScrollView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMyFragment;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.interfaces.IMyTaskList;
import com.ideal.flyerteacafes.ui.layout.TaskListGroupLayout;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/5/17.
 */

public class MyTaskListFm extends MVPBaseFragment<IMyTaskList, MyTaskPresenter> implements VerticalScrollView.ScrollViewListener {

    private static final int done = 1, failed = 2;

    public static MyTaskListFm getMyTaskDoneFm() {
        MyTaskListFm fm = new MyTaskListFm();
        Bundle bundle = new Bundle();
        bundle.putInt("status", done);
        fm.setArguments(bundle);
        return fm;
    }

    public static MyTaskListFm getMyTaskOverFm() {
        MyTaskListFm fm = new MyTaskListFm();
        Bundle bundle = new Bundle();
        bundle.putInt("status", failed);
        fm.setArguments(bundle);
        return fm;
    }


    @ViewInject(R.id.scrollView)
    VerticalScrollView scrollView;
    @ViewInject(R.id.task_layout)
    TaskListGroupLayout layout;
    @ViewInject(R.id.normal_icon)
    ImageView normal_icon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(iMyTaskList);
        View mView = inflater.inflate(R.layout.fm_my_task_list_fm, null);
        x.view().inject(this, mView);
        if (getArguments().getInt("status") == done) {
            mPresenter.getDone();
        } else {
            mPresenter.getFailed();
        }
        scrollView.setScrollViewListener(this);
        return mView;
    }

    @Override
    protected MyTaskPresenter createPresenter() {
        return new MyTaskPresenter();
    }

    IMyTaskList iMyTaskList = new IMyTaskList() {
        @Override
        public void callbackTaskAll(MyTaskAllBean allBean) {
            List<MyTaskBean> datas = new ArrayList<>();

            if (!DataUtils.isEmpty(allBean.getBirth_tasks())) {
                datas.addAll(allBean.getBirth_tasks());
            }
            if (!DataUtils.isEmpty(allBean.getDaily_tasks())) {
                datas.addAll(allBean.getDaily_tasks());
            }
            if (!DataUtils.isEmpty(allBean.getAdvanced_tasks())) {
                datas.addAll(allBean.getAdvanced_tasks());
            }
            if (!DataUtils.isEmpty(allBean.getFreshman_tasks())) {
                datas.addAll(allBean.getFreshman_tasks());
            }

            if (!DataUtils.isEmpty(datas)) {
                layout.bindData(datas);
                setItemclick(layout, datas);
                normal_icon.setVisibility(View.GONE);
            }

        }
    };

    int y = 0;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        this.y = y;
    }

    public boolean isScrollTop() {
        return y == 0;
    }

    private void setItemclick(TaskListGroupLayout layout, final List<MyTaskBean> datas) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final int finalI = i;
            layout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(TaskDetailsActivity.BUNDLE_DATA, datas.get(finalI));
                    jumpActivityForResult(TaskDetailsActivity.class, bundle, 0);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (getArguments().getInt("status") == done) {
                mPresenter.getDone();
            } else {
                mPresenter.getFailed();
            }
        }
    }
}
