package com.ideal.flyerteacafes.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.NoticeFragment;
import com.ideal.flyerteacafes.ui.fragment.page.RemindFragment;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 提醒页面
 *
 * @author fly
 */
@SuppressLint("CommitTransaction")
@ContentView(R.layout.activity_remind)
public class RemindActivity extends BaseActivity {

    @ViewInject(R.id.include_title_right_btn)
    private View showPopBtn;
    @ViewInject(R.id.include_left_title_text)
    private TextView titleView;
    @ViewInject(R.id.mythread_mythread_text)
    private TextView mythread_text;
    @ViewInject(R.id.mythread_reply_text)
    private TextView reply_text;
    @ViewInject(R.id.mythread_mythread_bottom)
    private View mythread_bottom;
    @ViewInject(R.id.mythread_reply_bottom)
    private View reply_bottom;
    @ViewInject(R.id.mythread_mythread_text)
    private TextView remindText;
    @ViewInject(R.id.mythread_reply_text)
    private TextView noticeText;

    private FragmentManager fm;
    private RemindFragment remindFragment;
    private NoticeFragment noticeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String type = getIntent().getStringExtra("type");
        if (!!DataUtils.isEmpty(type))
            type = "remind";
        fm = getSupportFragmentManager();
        initView();
        if (type.equals("remind")) {
            chooseTabLayout(R.id.mythread_mythread_layout);
            ChooseTabFragment(R.id.mythread_mythread_layout);
        } else {
            chooseTabLayout(R.id.mythread_reply_layout);
            ChooseTabFragment(R.id.mythread_reply_layout);
        }
    }

    private void initView() {
        titleView.setText(getResources().getString(R.string.title_name_remind));
        remindText.setText(getResources().getString(R.string.remind_remind));
        noticeText.setText(getResources().getString(R.string.remind_notice));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        String type = intent.getStringExtra("type");
        if (!DataUtils.isEmpty(type)) {
            if (type.equals("remind")) {
                chooseTabLayout(R.id.mythread_mythread_layout);
                ChooseTabFragment(R.id.mythread_mythread_layout);
            } else {
                chooseTabLayout(R.id.mythread_reply_layout);
                ChooseTabFragment(R.id.mythread_reply_layout);
            }
        }
    }

    @Event(R.id.include_left_title_back_layout)
    private void backClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * tab切换
     *
     * @param v
     */
    @Event({R.id.mythread_mythread_layout, R.id.mythread_reply_layout})
    private void tabChange(View v) {
        chooseTabLayout(v.getId());
        ChooseTabFragment(v.getId());
    }

    /**
     * tab切换，改变ui
     *
     * @param id
     */
    private void chooseTabLayout(int id) {
        if (id == R.id.mythread_mythread_layout) {
            mythread_text.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_bottom.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_text.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            reply_bottom.setBackground(null);
        } else {
            reply_text.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_bottom.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_text.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            mythread_bottom.setBackground(null);
        }
    }

    private void ChooseTabFragment(int id) {
        FragmentTransaction transaction = fm.beginTransaction();
        // 先隐藏掉显示的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        if (id == R.id.mythread_mythread_layout) {
            if (remindFragment == null) {
                remindFragment = new RemindFragment();
                transaction.add(R.id.remind_fragment_layout, remindFragment);
            } else {
                transaction.show(remindFragment);
            }
        } else if (id == R.id.mythread_reply_layout) {
            if (noticeFragment == null) {
                noticeFragment = new NoticeFragment();
                transaction.add(R.id.remind_fragment_layout, noticeFragment);
            } else {
                transaction.show(noticeFragment);
            }
        }

        transaction.commitAllowingStateLoss();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (remindFragment != null) {
            transaction.hide(remindFragment);
        }
        if (noticeFragment != null) {
            transaction.hide(noticeFragment);
        }
    }

}
