package com.ideal.flyerteacafes.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by fly on 2016/8/30.
 */
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {


    private VideoView mVvv;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String path = getIntent().getStringExtra(IntentKey.VIDEO_URL);
        LogFly.e(path);
        //一定要初始化
        Vitamio.initialize(this);

        mVvv = (VideoView) findViewById(R.id.videoView);
        mVvv.setVideoURI(Uri.parse(path));
        mVvv.setMediaController(mediaController = new MediaController(this));

        //设置监听
        mVvv.setOnPreparedListener(this);
        mVvv.setOnErrorListener(this);
        mVvv.setOnCompletionListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.setFileName("");
            }
        }, 100);


    }

    /**
     * 播放完成
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    /**
     * 播放异常
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 准备好了
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mVvv.start();
    }
}
