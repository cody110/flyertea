package com.ideal.flyerteacafes.ui.activity.video;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;
import android.widget.TableLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseToolbarActivity;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.LogFly;
import com.upyun.upplayer.widget.UpVideoView;
import com.upyun.upplayer.widget.UpVideoView2;

/**
 * Created by fly on 2018/2/7.
 */

public class PlayVideoActivity extends BaseToolbarActivity {

    private UpVideoView2 mVideoView;
    private TableLayout mHudView;

    @Override
    protected void setTitleBar(ToolBar mToolbar) {
        mToolbar.setTitle("视频");
    }


    @Override
    protected View createBodyView(Bundle savedInstanceState) {
        View view = getView(R.layout.inclued_upvideo);
        String videourl = getIntent().getStringExtra("videourl");
        if (TextUtils.isEmpty(videourl)) return view;
        //TODO 该播放器不支持https
        videourl = videourl.replace("https://", "http://");
        LogFly.e(videourl);
//        videourl = "http://uprocess.b0.upaiyun.com/demo/short_video/UPYUN_0.flv";
        mVideoView = (UpVideoView2) view.findViewById(com.flyer.tusdk.R.id.uv_demo);
        //设置播放地址
        mVideoView.setVideoPath(videourl);
        //开始播放
        mVideoView.start();

        //关联 MediaController
        MediaController controller = new MediaController(this);
        mVideoView.setMediaController(controller);
        controller.setMediaPlayer(mVideoView);

        mHudView = (TableLayout) view.findViewById(com.flyer.tusdk.R.id.hud_view);
        mVideoView.setHudView(mHudView);
        mHudView.setVisibility(View.GONE);


        //省流模式 wify未连接 网络视频
        //if (SetCommonManger.isFlowbyMode() && !FlyerApplication.wifiIsConnected && videourl.startsWith("http://")){}

        return view;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //释放播放器
        mVideoView.release(true);
    }


}
