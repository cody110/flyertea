package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/6/5.
 */
@ContentView(R.layout.activity_boot_video)
public class BootVideoActivity extends BaseActivity {


    @ViewInject(R.id.videoView)
    private VideoView mVvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initVideo();
    }

    @Override
    public boolean isSystemBarTransparent() {
        return true;
    }

    @Override
    public boolean isSetSystemBar() {
        return false;
    }


    @Event(R.id.btn_ok)
    private void click(View v) {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void initVideo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.login_bg;
//        String path = "http://flv2.bn.netease.com/tvmrepo/2017/6/I/G/ECKTM6SIG/SD/ECKTM6SIG-mobile.mp4";

        mVvv.setVideoURI(Uri.parse(path));
        mVvv.start();
        //播放结束侦听
        mVvv.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

    }
}
