package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.MyDraftFragment;
import com.ideal.flyerteacafes.ui.fragment.page.MyReplyFragment;
import com.ideal.flyerteacafes.ui.fragment.page.MyThreadFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 帖子
 *
 * @author fly
 */
@ContentView(R.layout.activity_my_thread)
public class MyThreadActivity extends BaseActivity {


    @ViewInject(R.id.toolbar)
    private ToolBar mToolbar;
    @ViewInject(R.id.mythread_mythread_text)
    private TextView mythread_text;
    @ViewInject(R.id.mythread_reply_text)
    private TextView reply_text;
    @ViewInject(R.id.mythread_draft_text)
    private TextView mythread_draft_text;

    private FragmentManager fm;
    private MyThreadFragment myThreadFragment;
    private MyReplyFragment myReplyFragment;
    private MyDraftFragment draftFragment;

    public static final int THREAD = R.id.mythread_mythread_text, REPLY = R.id.mythread_reply_text, DRAFT = R.id.mythread_draft_text;
    private int selectFlag = THREAD;
    public static final String BUNDLE_DEF_SELECT_KEY = "select_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        fm = getSupportFragmentManager();
        selectFlag = getIntent().getIntExtra(BUNDLE_DEF_SELECT_KEY, THREAD);
        initView();
        setSelectView();
    }

    private void initView() {
        mToolbar.setTitle(getResources()
                .getString(R.string.title_name_mythread));
    }

    @Event(R.id.toolbar_left)
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
    @Event({R.id.mythread_draft_text, R.id.mythread_reply_text, R.id.mythread_mythread_text})
    private void tabChange(View v) {
        selectFlag = v.getId();
        setSelectView();
    }


    /**
     * 设置选中项
     */
    private void setSelectView() {
        setTvDefStatus(mythread_text);
        setTvDefStatus(reply_text);
        setTvDefStatus(mythread_draft_text);
        if (selectFlag == THREAD) {
            setTvIsSelectStatus(mythread_text);
        } else if (selectFlag == REPLY) {
            setTvIsSelectStatus(reply_text);
        } else if (selectFlag == DRAFT) {
            setTvIsSelectStatus(mythread_draft_text);
        }

        ChooseTabFragment();

    }


    /**
     * 设置textview为选中的样式
     *
     * @param tv
     */
    private void setTvIsSelectStatus(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.app_body_black));
        tv.setBackground(getResources().getDrawable(R.drawable.top_grey_text_bottom_line_bg));
    }

    /**
     * 设置textvew为默认样式
     *
     * @param tv
     */
    private void setTvDefStatus(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.app_body_grey));
        tv.setBackground(null);
    }


    private void ChooseTabFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        // 先隐藏掉显示的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        if (selectFlag == THREAD) {
            if (myThreadFragment == null) {
                myThreadFragment = new MyThreadFragment();
                transaction.add(R.id.remind_fragment_layout, myThreadFragment);
            } else {
                transaction.show(myThreadFragment);
            }
        } else if (selectFlag == REPLY) {
            if (myReplyFragment == null) {
                myReplyFragment = new MyReplyFragment();
                transaction.add(R.id.remind_fragment_layout, myReplyFragment);
            } else {
                transaction.show(myReplyFragment);
            }
        } else if (selectFlag == DRAFT) {
            if (draftFragment == null) {
                draftFragment = new MyDraftFragment();
                transaction.add(R.id.remind_fragment_layout, draftFragment);
            } else {
                transaction.show(draftFragment);
            }
        }

        transaction.commitAllowingStateLoss();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (myThreadFragment != null) {
            transaction.hide(myThreadFragment);
        }
        if (myReplyFragment != null) {
            transaction.hide(myReplyFragment);
        }
        if (draftFragment != null) {
            transaction.hide(draftFragment);
        }
    }

}
