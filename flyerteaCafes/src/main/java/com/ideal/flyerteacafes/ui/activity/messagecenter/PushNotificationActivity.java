package com.ideal.flyerteacafes.ui.activity.messagecenter;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.SwitchButton;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送通知
 * Created by Cindy on 2017/3/13.
 */
@ContentView(R.layout.activity_push_notification)
public class PushNotificationActivity extends BaseActivity {

    /** 接收新消息通知 **/
    @ViewInject(R.id.mrl_message_notification)
    private RelativeLayout mrl_message_notification;
    /** 回复我的 **/
    @ViewInject(R.id.mrl_reply_mine)
    private RelativeLayout mrl_reply_mine;
    /** 私信 **/
    @ViewInject(R.id.mrl_private_letter)
    private RelativeLayout mrl_private_letter;
    /** 送我鲜花 **/
    @ViewInject(R.id.mrl_send_flowers)
    private RelativeLayout mrl_send_flowers;
    /** 加我好友 **/
    @ViewInject(R.id.mrl_add_friends)
    private RelativeLayout mrl_add_friends;
    /** 接收消息时段 **/
    @ViewInject(R.id.mrl_receive_message_time)
    private RelativeLayout mrl_receive_message_time;

    private SwitchButton mSbtnNotification,mSbtnReplyMine,mSbtnPrivateLetter,
            mSbtnSendFlowers,mSbtnAddFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initViews();
        statusChangeLinster();
    }

    @Override
    public void initViews() {
        TextView labelNotification = V.f(mrl_message_notification, R.id.comment_person_name);
        labelNotification.setText(R.string.push_notification_ac_message_notification);
        mSbtnNotification=V.f(mrl_message_notification,R.id.mSwitchButton);

        TextView labelReplyMine = V.f(mrl_reply_mine, R.id.comment_person_name);
        labelReplyMine.setText(R.string.push_notification_ac_reply_mine);
        mSbtnReplyMine=V.f(mrl_reply_mine,R.id.mSwitchButton);

        TextView labelPrivateLetter = V.f(mrl_private_letter, R.id.comment_person_name);
        labelPrivateLetter.setText(R.string.push_notification_ac_private_letter);
        mSbtnPrivateLetter=V.f(mrl_private_letter,R.id.mSwitchButton);

        TextView labelSendFlowers = V.f(mrl_send_flowers, R.id.comment_person_name);
        labelSendFlowers.setText(R.string.push_notification_ac_send_flowers);
        mSbtnSendFlowers=V.f(mrl_send_flowers,R.id.mSwitchButton);

        TextView labelAddFriends = V.f(mrl_add_friends, R.id.comment_person_name);
        labelAddFriends.setText(R.string.push_notification_ac_add_friends);
        mSbtnAddFriends=V.f(mrl_add_friends,R.id.mSwitchButton);

        TextView labelMessageTime = V.f(mrl_receive_message_time, R.id.comment_person_name);
        labelMessageTime.setText(R.string.push_notification_ac_receive_message_times);
        TextView labelMessageTimehint = V.f(mrl_receive_message_time, R.id.comment_person_name_hint);
        labelMessageTimehint.setText(R.string.push_notification_ac_receive_message_times_hint);

        if (SetCommonManger.isPushMode()) {
            WidgetUtils.setVisible(mrl_reply_mine, true);
            WidgetUtils.setVisible(mrl_private_letter, true);
            WidgetUtils.setVisible(mrl_add_friends, true);
            WidgetUtils.setVisible(mrl_send_flowers, true);
            WidgetUtils.setVisible(mrl_receive_message_time, true);
            mSbtnReplyMine.setChecked(true);
            mSbtnPrivateLetter.setChecked(true);
            mSbtnAddFriends.setChecked(true);
            mSbtnSendFlowers.setChecked(false);
            mSbtnNotification.setChecked(true);
        } else {
            WidgetUtils.setVisible(mrl_reply_mine, false);
            WidgetUtils.setVisible(mrl_private_letter, false);
            WidgetUtils.setVisible(mrl_add_friends, false);
            WidgetUtils.setVisible(mrl_send_flowers, false);
            WidgetUtils.setVisible(mrl_receive_message_time, false);
            mSbtnNotification.setChecked(false);
        }
    }

    @Event({R.id.toolbar_left})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left://返回
                finish();
                break;
        }
    }

    /**
     * switch 改变监听
     */
    private void statusChangeLinster() {
        //接收消息通知
        mSbtnNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showOrHideView(isChecked);
            }
        });
        //回复我的
        mSbtnReplyMine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mSbtnReplyMine.setChecked(true);
                }else {
                    mSbtnReplyMine.setChecked(false);
                }
            }
        });
        //私信
        mSbtnPrivateLetter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mSbtnPrivateLetter.setChecked(true);
                }else{
                    mSbtnPrivateLetter.setChecked(false);
                }
            }
        });

        //加我好友
        mSbtnAddFriends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mSbtnAddFriends.setChecked(true);
                }else{
                    mSbtnAddFriends.setChecked(false);
                }
            }
        });
        //送我鲜花
        mSbtnSendFlowers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mSbtnSendFlowers.setChecked(true);
                }else{
                    mSbtnSendFlowers.setChecked(false);
                }
            }
        });
    }

    /**
     * 接收消息通知
     * @param isChecked
     */
    private void showOrHideView(Boolean isChecked){
        if(isChecked){
            WidgetUtils.setVisible(mrl_reply_mine, true);
            WidgetUtils.setVisible(mrl_private_letter, true);
            WidgetUtils.setVisible(mrl_add_friends, true);
            WidgetUtils.setVisible(mrl_send_flowers, true);
            WidgetUtils.setVisible(mrl_receive_message_time, true);
            mSbtnReplyMine.setChecked(true);
            mSbtnPrivateLetter.setChecked(true);
            mSbtnAddFriends.setChecked(true);
            mSbtnSendFlowers.setChecked(false);
            mSbtnNotification.setChecked(true);
            SetCommonManger.openPushMode();
            JPushInterface.resumePush(PushNotificationActivity.this);
        }else{
            WidgetUtils.setVisible(mrl_reply_mine, false);
            WidgetUtils.setVisible(mrl_private_letter, false);
            WidgetUtils.setVisible(mrl_add_friends, false);
            WidgetUtils.setVisible(mrl_send_flowers, false);
            WidgetUtils.setVisible(mrl_receive_message_time, false);
            SetCommonManger.closePushMode();
            mSbtnNotification.setChecked(false);
            JPushInterface.stopPush(PushNotificationActivity.this);
        }
    }
}
