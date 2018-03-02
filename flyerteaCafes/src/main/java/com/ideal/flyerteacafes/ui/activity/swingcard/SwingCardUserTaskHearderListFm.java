package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotTaskAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.utils.LogFly;

import java.util.List;

/**
 * Created by fly on 2017/4/12.
 * 刷卡任务 -待完成 -已完成
 */

public class SwingCardUserTaskHearderListFm extends SwingCardHotTaskListFm {


    public static final int REQUEST_CODE = 1;

    @Override
    public void initViews() {
        super.initViews();
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.header_swingcard_normal, null);
        listView.addHeaderView(headerView);
        headerView.findViewById(R.id.add_task_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivityForResult(SwingCardAddTask.class, null, REQUEST_CODE);
            }
        });
    }

    @Override
    protected CommonAdapter<TaskNameBean> createAdapter(List<TaskNameBean> datas) {
        return new HotTaskAdapter(mActivity, datas, R.layout.item_hot_task).setIClickListener(new HotTaskAdapter.IClickListener() {
            @Override
            public void toAddTask(TaskNameBean taskNameBean) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", taskNameBean);
                jumpActivityForResult(SwingCardAddTask.class, bundle, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            jumpActivity(SwingCardHome.class, null);
            mActivity.finish();
        }
    }
}
