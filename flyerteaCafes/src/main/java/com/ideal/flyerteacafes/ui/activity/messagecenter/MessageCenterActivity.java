package com.ideal.flyerteacafes.ui.activity.messagecenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.entity.PersonalBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.RemindFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.PrivateLetterFragment;
import com.ideal.flyerteacafes.utils.FinalUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V6.8.0
 * @description: 消息中心
 * @author: Cindy
 * @date: 2017/3/10 17:22
 */
@ContentView(R.layout.activity_message_center)
public class MessageCenterActivity extends BaseActivity {

    @ViewInject(R.id.fm_message_center_vp)
    ViewPager viewPager;
    @ViewInject(R.id.fm_message_center_message)
    TextView fm_message_center_message;
    @ViewInject(R.id.fm_message_center_private_message)
    TextView fm_message_center_private_message;
    @ViewInject(R.id.migv_remind_message)
    ImageView migv_remind_message;
    @ViewInject(R.id.migv_private_remind_message)
    ImageView migv_private_remind_message;

    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();


    BaseDataManger.IMsgNum iMsgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initPage();


        BaseDataManger.getInstance().registerIMsgNum(iMsgNum = new BaseDataManger.IMsgNum() {
            @Override
            public void msgNum(NumberBean numBean) {
                migv_private_remind_message.setVisibility(numBean.getNewpm() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseDataManger.getInstance().unRegisterIMsgNum(iMsgNum);
    }

    private void initPage() {

        fmList.add(new RemindFragment());
        fmList.add(new PrivateLetterFragment());

        typeList.add(new TypeMode().setName("提醒"));
        typeList.add(new TypeMode().setName("消息"));

        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);

        setSelectTv(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectTv(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Event({R.id.img_back, R.id.fm_message_center_message, R.id.fm_message_center_private_message, R.id.fm_message_center_setting})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.fm_message_center_setting://设置
                jumpActivity(PushNotificationActivity.class, null);
                break;

            case R.id.fm_message_center_message://消息
                viewPager.setCurrentItem(0);
                break;

            case R.id.fm_message_center_private_message://私信
                viewPager.setCurrentItem(1);
                break;
        }
    }


    private void setSelectTv(int position) {
        if (position == 0) {
            fm_message_center_message.setTextColor(getResources().getColor(R.color.text_black));
            fm_message_center_message.setBackground(getResources().getDrawable(R.drawable.title_bg_text_bottom_line_bg));
            fm_message_center_private_message.setTextColor(getResources().getColor(R.color.text_black));
            fm_message_center_private_message.setBackground(null);
        } else {
            fm_message_center_message.setTextColor(getResources().getColor(R.color.text_black));
            fm_message_center_private_message.setTextColor(getResources().getColor(R.color.text_black));
            fm_message_center_private_message.setBackground(getResources().getDrawable(R.drawable.title_bg_text_bottom_line_bg));
            fm_message_center_message.setBackground(null);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == FinalUtils.REQUEST_CODE_PRIVATE_LETTER) {
            BaseDataManger.getInstance().requestGetNum();
        }
    }
}
