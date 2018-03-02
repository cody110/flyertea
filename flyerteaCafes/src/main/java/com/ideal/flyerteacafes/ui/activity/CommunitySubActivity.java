package com.ideal.flyerteacafes.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.CommunitySubFragment;
import com.ideal.flyerteacafes.ui.view.CommunitySubTypeLayout;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2016/11/29.
 */
public class CommunitySubActivity extends BaseActivity implements View.OnClickListener {


    View include_community_subtype_layout_root;
    CommunitySubTypeLayout community_sub_community_subtype_layout;
    View community_sub_choose_layout;
    public TextView community_sub_choose_type_name;
    public ImageView community_sub_choose_type_icon;
    TextView community_sub_forumname;
    View community_title_layout;
    View community_sub_write_thread_btn;

    CommunitySubFragment subFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_community_sub);
        findViewById(R.id.community_sub_back_btn).setOnClickListener(this);
        findViewById(R.id.community_sub_serach_btn).setOnClickListener(this);
        findViewById(R.id.community_sub_write_thread_btn).setOnClickListener(this);

        community_title_layout = findViewById(R.id.community_title_layout);
        community_sub_write_thread_btn=findViewById(R.id.community_sub_write_thread_btn);
        community_sub_forumname = (TextView) findViewById(R.id.community_sub_forumname);
        include_community_subtype_layout_root = findViewById(R.id.top_community_sub_type_layout);
        include_community_subtype_layout_root.setBackground(getResources().getDrawable(R.drawable.frames_bottom_headercolor));
        community_sub_community_subtype_layout = (CommunitySubTypeLayout) include_community_subtype_layout_root.findViewById(R.id.community_sub_community_subtype_layout);
        community_sub_choose_layout = include_community_subtype_layout_root.findViewById(R.id.community_sub_choose_layout);
        community_sub_choose_type_name = (TextView) include_community_subtype_layout_root.findViewById(R.id.community_sub_choose_type_name);
        community_sub_choose_type_icon = (ImageView) include_community_subtype_layout_root.findViewById(R.id.community_sub_choose_type_icon);
//        setIsShowSubtypeLayout(false);


        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList.size() > 0)
            subFragment = (CommunitySubFragment) fragmentList.get(0);

    }

    /**
     * 设置分类layout 显示隐藏
     *
     * @param bol
     */
    public void setIsShowSubtypeLayout(final boolean bol) {
        include_community_subtype_layout_root.setVisibility(bol ? View.VISIBLE : View.GONE);
    }

    public void setIsShowTitleName(boolean bol) {
        community_sub_forumname.setVisibility(bol ? View.VISIBLE : View.GONE);
    }

    public void setTitleBg(String thrmeColor) {
        int color = DataTools.getInteger(thrmeColor);
        if (color == 0) {
            if (!TextUtils.isEmpty(thrmeColor)) {
                try {
                    community_title_layout.setBackgroundColor(Color.parseColor(thrmeColor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            community_title_layout.setBackgroundColor(color);
        }
    }

    public void setTitleName(String name) {
        WidgetUtils.setText(community_sub_forumname, name);
    }

    /**
     * 设置点击显示分类页面
     */
    public void setSelectClick(View.OnClickListener listener) {
        community_sub_choose_layout.setOnClickListener(listener);
    }

    public CommunitySubTypeLayout getCommunitySubTypeLayout() {
        return community_sub_community_subtype_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.community_sub_back_btn:
                finish();
                break;
            case R.id.community_sub_write_thread_btn:
                if (UserManger.isLogin()) {
                    if (subFragment != null)
                        subFragment.toWriteThread();
                } else {
                    DialogUtils.showDialog(this);
                }
                break;

            case R.id.community_sub_serach_btn:
                subFragment.toSearchThread();
                break;
        }
    }

    @Override
    public boolean isSystemBarTransparent() {
        return true;
    }

    @Override
    public boolean isSetSystemBar() {
        return false;
    }


    public void settingWriteThreadBtn(int scrollState){
//        switch (scrollState) {
//            // 当不滚动时
//            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
//                community_sub_write_thread_btn.setAlpha(1.0f);
//                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//                alphaAnimation.setDuration(2000);    //深浅动画持续时间
//                alphaAnimation.setFillAfter(true);   //动画结束时保持结束的画面
//                community_sub_write_thread_btn.setAnimation(alphaAnimation);
//                alphaAnimation.start();
//                break;
//            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
//                community_sub_write_thread_btn.setAlpha(0.0f);
//                AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
//                alphaAnimation2.setDuration(2000);    //深浅动画持续时间
//                alphaAnimation2.setFillAfter(true);   //动画结束时保持结束的画面
//                community_sub_write_thread_btn.setAnimation(alphaAnimation2);
//                alphaAnimation2.start();
//                break;
//            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
//                break;
//        }
    }
}
