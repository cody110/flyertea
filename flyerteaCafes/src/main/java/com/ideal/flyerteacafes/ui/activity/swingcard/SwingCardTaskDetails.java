package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISwingCardTaskDetails;
import com.ideal.flyerteacafes.ui.activity.presenter.SwingCardTaskDetailsPresenter;
import com.ideal.flyerteacafes.ui.activity.viewholder.SwingCardTaskDetailsVH;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.ToastUtils;

/**
 * Created by fly on 2017/4/17.
 */

public class SwingCardTaskDetails extends MVPBaseActivity<ISwingCardTaskDetails, SwingCardTaskDetailsPresenter> {


    public static final int REQUEST_PROGRESS = 1;
    public static final String BUNDLE_MISSIONID = "missionid";

    SwingCardTaskDetailsVH vh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(modelListener);
        setContentView(R.layout.activity_task_details);
        View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        vh = new SwingCardTaskDetailsVH(view, iActionListener);
        mPresenter.init(this);
    }

    @Override
    protected SwingCardTaskDetailsPresenter createPresenter() {
        return new SwingCardTaskDetailsPresenter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PROGRESS) {
                mPresenter.isStatusChange = true;
                mPresenter.requestDetails();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean bol = intent.getBooleanExtra("isStatusChange", false);
        if (bol) {
            mPresenter.isStatusChange = true;
            mPresenter.requestDetails();
        }


    }

    private void deleteRemeindDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("是否确认删除？");
        builder.setNegativeButton("删除", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.requestDelete();
            }
        });

        builder.setPositiveButton("取消");
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        if (mPresenter.isStatusChange) {
            jumpActivitySetResult(SwingCardHome.class, null);
        } else {
            super.onBackPressed();
        }
    }


    SwingCardTaskDetailsVH.IActionListener iActionListener = new SwingCardTaskDetailsVH.IActionListener() {
        @Override
        public void actionClick(View view) {
            switch (view.getId()) {
                case SwingCardTaskDetailsVH.IActionListener.ID_BACK:
                    onBackPressed();
                    break;
                case SwingCardTaskDetailsVH.IActionListener.ID_PROGRESS:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SwingCardUpdateTaskProgress.BUNDLE_DATA, mPresenter.taskDetailsBean);
                    jumpActivityForResult(SwingCardUpdateTaskProgress.class, bundle, REQUEST_PROGRESS);
                    break;

                case SwingCardTaskDetailsVH.IActionListener.ID_DELETE:
                    deleteRemeindDialog();
                    break;
            }
        }
    };


    ISwingCardTaskDetails modelListener = new ISwingCardTaskDetails() {
        @Override
        public void callbackTaskDetailsBean(TaskDetailsBean taskDetailsBean) {
            vh.bindData(taskDetailsBean);
        }

        @Override
        public void callbackDelete(MapBean result) {
            if (result.getData() != null) {
                jumpActivitySetResult(null);
            }
            ToastUtils.showToast(result.getMessage());
        }
    };

}
