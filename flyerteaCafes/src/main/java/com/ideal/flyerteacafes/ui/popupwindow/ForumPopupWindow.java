package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.ForumWheelOneAdapter;
import com.ideal.flyerteacafes.ui.wheelview.adapters.ForumWheelThreeAdapter;
import com.ideal.flyerteacafes.ui.wheelview.adapters.ForumWheelTwoAdapter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2016/11/18.
 */

public class ForumPopupWindow extends PopupWindow implements OnWheelChangedListener {

    @ViewInject(R.id.pop_forum_wheel_layout)
    LinearLayout pop_forum_wheel_layout;
    @ViewInject(R.id.pop_forum_wheel_one_level)
    WheelView pop_forum_wheel_one_level;
    @ViewInject(R.id.pop_forum_wheel_two_level)
    WheelView pop_forum_wheel_two_level;
    @ViewInject(R.id.pop_forum_wheel_three_level)
    WheelView pop_forum_wheel_three_level;

    private ForumWheelOneAdapter oneAdapter;
    private ForumWheelTwoAdapter twoAdapter;
    private ForumWheelThreeAdapter threeAdapter;

    private int oneLevelSelectIndex = 0, twoLevelSelectIndex = 0, threeLevelSelectIndex = 0;
    private List<CommunityBean> communityBeanList;
    private List<CommunitySubTypeBean> communitySubTypeBeanList;
    private String fid1, fid2, fid3, name1, name2, name3;

    /**
     * 3种情况，只需要3级，和需要全部,问答进入，3级默认问答
     */
    private int IS_NEED_ONCE_THREE = 2, IS_NEED_ALL = 1, IS_WENDA = 3;
    private int type = IS_NEED_ALL;

    private Context context;
    private Handler handler;

    public ForumPopupWindow(final Context context, final Handler handler) {
        this.context = context;
        this.handler = handler;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mMenuView = inflater.inflate(R.layout.popupwindow_froum, null);
        x.view().inject(this, mMenuView);
        pop_forum_wheel_one_level.addChangingListener(this);
        pop_forum_wheel_two_level.addChangingListener(this);
        pop_forum_wheel_three_level.addChangingListener(this);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_forum_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 根据数据初始化页面
     */
    public void chooseAllFroum(List<CommunityBean> communityBeanList) {
        this.communityBeanList = communityBeanList;

        setOneLevelAdapter();

        getOneSelectIndex(fid1);
        setTwoLevelAdapter();

        getTwoSelectIndex(fid2);
        setThreeLevelAdapter();

    }

    /**
     * 问答入口
     */
    public void chooseWenda(List<CommunityBean> communityBeanList) {
        this.communityBeanList = communityBeanList;
        type = IS_WENDA;
        pop_forum_wheel_three_level.setVisibility(View.GONE);
        setOneLevelAdapter();
        getOneSelectIndex(fid1);
        setTwoLevelAdapter();
    }

    /**
     * 只显示3级分类,的初始化
     */
    public void chooseThreeFroum(List<CommunitySubTypeBean> communitySubTypeBeanList) {
        this.communitySubTypeBeanList = communitySubTypeBeanList;
        type = IS_NEED_ONCE_THREE;
        pop_forum_wheel_one_level.setVisibility(View.GONE);
        pop_forum_wheel_two_level.setVisibility(View.GONE);
        setThreeLevelAdapter(communitySubTypeBeanList);
    }


    private int getOneSelectIndex(String fid) {
        for (int i = 0; i < communityBeanList.size(); i++) {
            if (TextUtils.equals(communityBeanList.get(i).getFid(), fid))
                return oneLevelSelectIndex = i;
        }
        return oneLevelSelectIndex;
    }


    private int getTwoSelectIndex(String fid) {
        List<CommunitySubBean> communitySubBeanList = communityBeanList.get(oneLevelSelectIndex).getSubforums();
        for (int i = 0; i < communitySubBeanList.size(); i++) {
            if (TextUtils.equals(communitySubBeanList.get(i).getFid(), fid))
                return oneLevelSelectIndex = i;
        }
        return oneLevelSelectIndex;
    }


    private void setOneLevelAdapter() {
        oneAdapter = new ForumWheelOneAdapter(context, communityBeanList);
        pop_forum_wheel_one_level.setViewAdapter(oneAdapter);
    }

    private void setTwoLevelAdapter() {
        twoLevelSelectIndex = 0;
        List<CommunitySubBean> communitySubBeanList = communityBeanList.get(oneLevelSelectIndex).getSubforums();
        twoAdapter = new ForumWheelTwoAdapter(context, communitySubBeanList);
        pop_forum_wheel_two_level.setViewAdapter(twoAdapter);
        pop_forum_wheel_two_level.setCurrentItem(twoLevelSelectIndex);
    }

    private void setThreeLevelAdapter() {
        threeLevelSelectIndex = 0;
        List<CommunitySubTypeBean> communitySubTypeBeanList = communityBeanList.get(oneLevelSelectIndex).getSubforums().get(twoLevelSelectIndex).getSubforumList();
        if (communitySubTypeBeanList != null) {
            threeAdapter = new ForumWheelThreeAdapter(context, communitySubTypeBeanList);
            pop_forum_wheel_three_level.setViewAdapter(threeAdapter);
            pop_forum_wheel_three_level.setCurrentItem(threeLevelSelectIndex);
        }
    }

    private void setThreeLevelAdapter(List<CommunitySubTypeBean> communitySubTypeBeanList) {
        if (communitySubTypeBeanList != null) {
            threeAdapter = new ForumWheelThreeAdapter(context, communitySubTypeBeanList);
            pop_forum_wheel_three_level.setViewAdapter(threeAdapter);
            pop_forum_wheel_three_level.setCurrentItem(threeLevelSelectIndex);
        }
    }


    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                dismiss();
                break;

            case R.id.toolbar_right:
                btnOk();
                break;
        }
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == pop_forum_wheel_one_level) {
            oneLevelSelectIndex = newValue;
            setTwoLevelAdapter();
            if (pop_forum_wheel_three_level.getVisibility() != View.GONE) {
                setThreeLevelAdapter();
            }
        } else if (wheel == pop_forum_wheel_two_level) {
            twoLevelSelectIndex = newValue;
            setThreeLevelAdapter();
        } else {
            threeLevelSelectIndex = newValue;
        }
    }


    /**
     * 点击完成
     * 返回选中数据
     */
    private void btnOk() {

        if (type == IS_NEED_ONCE_THREE) {
            CommunitySubTypeBean bean3 = communitySubTypeBeanList.get(threeLevelSelectIndex);
            fid3 = bean3.getFid();
            name3 = bean3.getName();
        } else if (type == IS_WENDA) {
            CommunityBean bean1 = communityBeanList.get(oneLevelSelectIndex);
            fid1 = bean1.getFid();
            name1 = bean1.getName();

            CommunitySubBean bean2 = communityBeanList.get(oneLevelSelectIndex).getSubforums().get(twoLevelSelectIndex);
            fid2 = bean2.getFid();
            name2 = bean2.getName();

            List<CommunitySubTypeBean> communitySubTypeBeanList = communityBeanList.get(oneLevelSelectIndex).getSubforums().get(twoLevelSelectIndex).getSubforumList();
            if (communitySubTypeBeanList != null && communitySubTypeBeanList.size() > 0) {
                for (CommunitySubTypeBean typeBean : communitySubTypeBeanList) {
                    if (TextUtils.equals(typeBean.getName(), "求助问答")) {
                        fid3 = typeBean.getFid();
                        name3 = typeBean.getName();
                    }
                }
            }
        } else {
            CommunityBean bean1 = communityBeanList.get(oneLevelSelectIndex);
            fid1 = bean1.getFid();
            name1 = bean1.getName();

            CommunitySubBean bean2 = communityBeanList.get(oneLevelSelectIndex).getSubforums().get(twoLevelSelectIndex);
            fid2 = bean2.getFid();
            name2 = bean2.getName();

            List<CommunitySubTypeBean> communitySubTypeBeanList = communityBeanList.get(oneLevelSelectIndex).getSubforums().get(twoLevelSelectIndex).getSubforumList();
            if (communitySubTypeBeanList != null && communitySubTypeBeanList.size() > 0) {
                CommunitySubTypeBean bean3 = communitySubTypeBeanList.get(threeLevelSelectIndex);
                fid3 = bean3.getFid();
                name3 = bean3.getName();
            }else{
                fid3="";
                name3="";
            }
        }

        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        msg.what = WriteThreadPresenter.HANDLER_WHAT_FORUM;
        msg.setData(bundle);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, fid1);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, fid2);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_3, fid3);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_1, name1);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_2, name2);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_3, name3);
        handler.sendMessage(msg);
        dismiss();
    }

}
