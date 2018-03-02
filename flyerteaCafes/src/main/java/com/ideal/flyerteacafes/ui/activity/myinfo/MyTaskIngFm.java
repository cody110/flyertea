package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.ui.activity.presenter.MyTaskPresenter;
import com.ideal.flyerteacafes.ui.controls.VerticalScrollView;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.interfaces.IMyTaskList;
import com.ideal.flyerteacafes.ui.layout.TaskListGroupLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/5/17.
 */

public class MyTaskIngFm extends MVPBaseFragment<IMyTaskList, MyTaskPresenter> implements VerticalScrollView.ScrollViewListener {


    public static MyTaskIngFm getMyTaskIngFm() {
        MyTaskIngFm fm = new MyTaskIngFm();
        return fm;
    }


    @ViewInject(R.id.birthday_task_title)
    View birthday_task_title;
    @ViewInject(R.id.birthday_task_layout)
    TaskListGroupLayout birthday_task_layout;
    @ViewInject(R.id.recommended_task_title)
    View recommended_task_title;
    @ViewInject(R.id.newbie_task_title)
    View newbie_task_title;
    @ViewInject(R.id.advanced_task_title)
    View advanced_task_title;
    @ViewInject(R.id.recommended_task_layout)
    TaskListGroupLayout recommended_task_layout;
    @ViewInject(R.id.newbie_task_layout)
    TaskListGroupLayout newbie_task_layout;
    @ViewInject(R.id.advanced_task_layout)
    TaskListGroupLayout advanced_task_layout;
    @ViewInject(R.id.scrollView)
    VerticalScrollView scrollView;
    @ViewInject(R.id.normal_icon)
    ImageView normal_icon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fm_my_task_ing_fm, null);
        x.view().inject(this, mView);
        scrollView.setScrollViewListener(this);
        mPresenter.attachView(iMyTaskList);
        mPresenter.getDoing();
        return mView;
    }


    @Override
    protected MyTaskPresenter createPresenter() {
        return new MyTaskPresenter();
    }

    IMyTaskList iMyTaskList = new IMyTaskList() {
        @Override
        public void callbackTaskAll(final MyTaskAllBean allBean) {
            if (DataUtils.isEmpty(allBean.getBirth_tasks())) {
                birthday_task_title.setVisibility(View.GONE);
                birthday_task_layout.setVisibility(View.GONE);
            } else {
                birthday_task_title.setVisibility(View.VISIBLE);
                birthday_task_layout.setVisibility(View.VISIBLE);
                birthday_task_layout.bindData(allBean.getBirth_tasks());
                setItemclick(birthday_task_layout, allBean.getBirth_tasks());
                normal_icon.setVisibility(View.GONE);
            }

            if (DataUtils.isEmpty(allBean.getAdvanced_tasks())) {
                advanced_task_title.setVisibility(View.GONE);
                advanced_task_layout.setVisibility(View.GONE);
            } else {
                advanced_task_title.setVisibility(View.VISIBLE);
                advanced_task_layout.setVisibility(View.VISIBLE);
                advanced_task_layout.bindData(allBean.getAdvanced_tasks());
                setItemclick(advanced_task_layout, allBean.getAdvanced_tasks());
                normal_icon.setVisibility(View.GONE);
            }

            if (DataUtils.isEmpty(allBean.getDaily_tasks())) {
                recommended_task_title.setVisibility(View.GONE);
                recommended_task_layout.setVisibility(View.GONE);
            } else {
                recommended_task_title.setVisibility(View.VISIBLE);
                recommended_task_layout.setVisibility(View.VISIBLE);
                recommended_task_layout.bindData(allBean.getDaily_tasks());
                setItemclick(recommended_task_layout, allBean.getDaily_tasks());
                normal_icon.setVisibility(View.GONE);
            }

            if (DataUtils.isEmpty(allBean.getFreshman_tasks())) {
                newbie_task_title.setVisibility(View.GONE);
                newbie_task_layout.setVisibility(View.GONE);
            } else {
                newbie_task_title.setVisibility(View.VISIBLE);
                newbie_task_layout.setVisibility(View.VISIBLE);
                newbie_task_layout.bindData(allBean.getFreshman_tasks());
                setItemclick(newbie_task_layout, allBean.getFreshman_tasks());
                normal_icon.setVisibility(View.GONE);
            }
        }
    };


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


    int y = 0;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        this.y = y;
    }

    public boolean isScrollTop() {
        return y == 0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.getDoing();
        }
    }
}
