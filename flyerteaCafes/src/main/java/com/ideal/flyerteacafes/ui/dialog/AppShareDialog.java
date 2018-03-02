package com.ideal.flyerteacafes.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UmengShare;
import com.ideal.flyerteacafes.model.loca.ThreadBottomInfo;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/11/25.
 */

public class AppShareDialog extends DialogFragment {


    private List<ThreadBottomInfo> dataList = new ArrayList<>();


    @ViewInject(R.id.thread_bottom_gridview)
    private GridView gridView;
    @ViewInject(R.id.shar_bottom_layout)
    private View shar_bottom_layout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        initShareData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_app_share, container, false);
        x.view().inject(this, view);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(new CommonAdapter<ThreadBottomInfo>(getActivity(), dataList, R.layout.thread_share_item) {
            @Override
            public void convert(ViewHolder holder, ThreadBottomInfo threadBottomInfo) {
                holder.setText(R.id.share_item_title, threadBottomInfo.getTitle());
                holder.setImageResource(R.id.share_item_icon, threadBottomInfo.getResId());
            }
        });

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_in);
        shar_bottom_layout.startAnimation(anim);

        return view;
    }


    /**
     * 初始化分享数据
     */
    private void initShareData() {
        ThreadBottomInfo wechat_moment = new ThreadBottomInfo("朋友圈", R.drawable.share_wechat_moment, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN_CIRCLE);
        ThreadBottomInfo wechat_friend = new ThreadBottomInfo("微信好友", R.drawable.share_wechat, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN);
        ThreadBottomInfo qq_friend = new ThreadBottomInfo("QQ好友", R.drawable.share_qq, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.QQ);
        ThreadBottomInfo sine = new ThreadBottomInfo("新浪微博", R.drawable.share_sine, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.SINA);

        dataList.add(wechat_moment);
        dataList.add(wechat_friend);
        dataList.add(qq_friend);
        dataList.add(sine);

    }


    @Event({R.id.thread_bottom_top, R.id.thread_bottom_cancle})
    private void click(View v) {
        showEndAnimation();
    }

    @Event(value = R.id.thread_bottom_gridview, type = AdapterView.OnItemClickListener.class)
    private void gridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        ThreadBottomInfo info = dataList.get(position);
        UmengShare.setShareFriends(getActivity(), info.getPlatform());
        MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.recommend_flyertea_to_friends);
        LogFly.e(FinalUtils.EventId.recommend_flyertea_to_friends);
    }

    /**
     * 显示关闭动画
     */
    public void showEndAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_out);
        shar_bottom_layout.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
