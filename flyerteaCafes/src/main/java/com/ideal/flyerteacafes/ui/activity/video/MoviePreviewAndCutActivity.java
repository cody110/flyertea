/**
 * TuSDKVideoDemo
 * MoviePreviewAndCutActivity.java
 *
 * @author Yanlin
 * @Date Feb 21, 2017 8:52:11 PM
 * @Copright (c) 2016 tusdk.com. All rights reserved.
 */
package com.ideal.flyerteacafes.ui.activity.video;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyer.tusdk.Config;
import com.flyer.tusdk.TuSdkManger;
import com.flyer.tusdk.model.VideoInfo;
import com.flyer.tusdk.views.HVScrollView;
import com.flyer.tusdk.views.MovieRangeSelectionBar;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.dialog.UpProgressDialog;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.encoder.video.TuSDKVideoEncoderSetting;
import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.tusdk.FilterWrap;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.utils.RectHelper;
import org.lasque.tusdk.core.video.TuSDKVideoResult;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.movie.player.TuSDKMoviePlayer;
import org.lasque.tusdk.movie.player.TuSDKMoviePlayer.OnSeekToPreviewListener;
import org.lasque.tusdk.movie.player.TuSDKMoviePlayer.PlayerState;
import org.lasque.tusdk.movie.player.TuSDKMoviePlayer.TuSDKMoviePlayerDelegate;
import org.lasque.tusdk.video.editor.TuSDKMovieEditor;
import org.lasque.tusdk.video.editor.TuSDKMovieEditorOptions;
import org.lasque.tusdk.video.editor.TuSDKTimeRange;
import org.lasque.tusdk.video.editor.TuSDKVideoImageExtractor;
import org.lasque.tusdk.video.editor.TuSDKVideoImageExtractor.TuSDKVideoImageExtractorDelegate;
import org.lasque.tusdk.video.mixer.TuSDKMediaDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 视频预览 + 获取视频裁剪范围
 *
 * @author leone.xia
 */
public class MoviePreviewAndCutActivity extends SimpleCameraActivity implements TuSDKMoviePlayerDelegate {
    /**
     * requestCode
     */
    private static final int REQUEST_CODE = 0;
    /**
     * 非比例自适应
     */
    protected boolean RATIO_ADAPTION = false;
    /**
     * 播放  Button
     */
    private Button mPlayButton;
    /**
     * 返回  TextView
     */
    private View mBackTextView;
    /**
     * 标题  TextView
     */
    private TextView mTitleTextView;
    /**
     * 下一步  TextView
     */
    private TextView mNextTextView;
    /**
     * PLAY TIME TextView
     */
    private TextView mPlayTextView;
    /**
     * LEFT TIME TextView
     */
    private TextView mLeftTextView;
    /**
     * RIGHT TIME TextView
     */
    private TextView mRightTextView;
    /**
     * 视频裁剪控件
     */
    private MovieRangeSelectionBar mRangeSelectionBar;
    /**
     * 记录裁剪控件的宽度
     */
    private int mSeekBarWidth;
    /**
     * 视频显示样式
     */
    private int mShowStyle;
    /**
     * 视频横屏样式
     */
    private final int SHOW_HORIZONETAL = 1;
    /**
     * 视频竖屏样式
     */
    private final int SHOW_VERTICAL = 2;
    /**
     * 选取视频的左端位置,范围0.0f-1.0f
     */
    private static float mCutRectLeftPercent;
    /**
     * 选取视频的顶端位置,范围0.0f-1.0f
     */
    private static float mCutRectTopPercent;
    /**
     * 选取视频的右端位置,范围0.0f-1.0f
     */
    private static float mCutRectRightPercent;
    /**
     * 选取视频的底端位置,范围0.0f-1.0f
     */
    private static float mCutRectBottomPercent;
    /**
     * 记录滚动条X坐标
     */
    private static int mScrollViewContentOffseX;
    /**
     * 记录滚动条Y坐标
     */
    private static int mScrollViewContentOffseY;
    /**
     * 滚动控件
     */
    private HVScrollView mHVScrollView;
    /**
     * 用于显示视频
     */
    protected SurfaceView mSurfaceView;
    /**
     * 视频播放模块布局
     */
    private FrameLayout mMovieLayout;
    /**
     * 选择时间范围模块布局
     */
    private LinearLayout mSelectTimeLayout;
    /**
     * TuSDKMoviePlayer 播放器
     */
    private TuSDKMoviePlayer mPlayer;
    /**
     * 视频播放地址
     */
    private String mVideoPath;
    /**
     * 记录时间时长
     */
    private float mVideoTime;
    /**
     * 裁剪视频的开始时间
     */
    private static float mStartTime;
    /**
     * 裁剪视频的结束时间
     */
    private static float mEndTime;
    /**
     * 是否播放视频
     */
    private boolean mIsPlaying;
    /**
     * 视频是否播放完成
     */
    private boolean mIsVideoFinished = false;
    /**
     * 是否暂停视频
     */
    private boolean mIsPaused;
    /**
     * 记录缩略图列表容器
     */
    private List<Bitmap> mThumbList;
    /**
     * 记录屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 记录屏幕高度
     */
    private int mScreenHeight;
    /**
     * 跳转的类
     */
    private Class<?> mIntentClass = MovieEditorActivity.class;


    /**
     * 编辑器
     */
    private TuSDKMovieEditor mMovieEditor;

    /**
     * 上传进度dialog
     */
    private UpProgressDialog upProgressDialog;

    /**
     * 视频最大时长5分钟 （单位毫秒）
     */
    private int videoMaxTime = 5 * 60 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.movie_range_selection_activity);
        initView();
        setIntentClass(MovieEditorActivity.class);
    }

    /**
     * 初始化视图
     */
    protected void initView() {
        mVideoPath = getIntent().getStringExtra("videoPath");
        Log.e(getClass().getName(), mVideoPath);

        mBackTextView = this.findViewById(R.id.lsq_back);
        mBackTextView.setOnClickListener(mOnClickListener);


        mNextTextView = (TextView) this.findViewById(R.id.lsq_next);
        mNextTextView.setOnClickListener(mOnClickListener);

        mPlayTextView = (TextView) this.findViewById(R.id.lsq_play_time);
        mLeftTextView = (TextView) this.findViewById(R.id.lsq_left_time);
        mRightTextView = (TextView) this.findViewById(R.id.lsq_right_time);

        mPlayTextView.setText(R.string.lsq_text_time_tv);
        mLeftTextView.setText(R.string.lsq_text_time_tv);
        mRightTextView.setText(R.string.lsq_text_time_tv);

        mPlayButton = (Button) this.findViewById(R.id.lsq_play_btn);
        mPlayButton.setOnClickListener(mOnClickListener);

        mHVScrollView = (HVScrollView) this.findViewById(R.id.hvScrollView);

        mSurfaceView = (SurfaceView) this.findViewById(R.id.lsq_video_view);
        mSurfaceView.setOnClickListener(mOnClickListener);

        mMovieLayout = (FrameLayout) this.findViewById(R.id.movie_layout);
        mSelectTimeLayout = (LinearLayout) this.findViewById(R.id.time_layout);


        showPlayButton();

        initMoviePlayer();
        initRangeSelectionBar();
    }


    /**
     * 视频编辑初始化
     */
    protected void initMovieEditor() {
        startLoadVideo(getEditorOption(), getCameraView());
    }

    /**
     * 生成视频
     */
    protected void generateVideo() {
        String msg = getStringFromResource("new_movie_saving");
        TuSdk.messageHub().setStatus(MoviePreviewAndCutActivity.this, msg);
        // 生成视频文件
        mMovieEditor.startRecording();
    }

    protected void startLoadVideo(TuSDKMovieEditorOptions options, RelativeLayout cameraView) {
        mMovieEditor = new TuSDKMovieEditor(this.getBaseContext(), cameraView, options);

        // 视频原音音量0-1
        mMovieEditor.setVideoSoundVolume(1);
        // 打开美颜
        mMovieEditor.setEnableBeauty(true);


        //设置默认不保存相册
        mMovieEditor.setSaveToAlbum(false);

        // 设置水印，默认为空
        mMovieEditor.setWaterMarkImage(null);
//        mMovieEditor.setWaterMarkImage(BitmapHelper.getBitmapFormRaw(this, R.raw.sample_watermark));
//        mMovieEditor.setWaterMarkPosition(WaterMarkPosition.TopLeft);

//        TuSDKVideoEncoderSetting encoderSetting = mMovieEditor.getVideoEncoderSetting();
//        encoderSetting.videoSize = TuSdkSize.create(Config.EDITORWIDTH, Config.EDITORHEIGHT);
//        encoderSetting.videoQuality = TuSDKVideoEncoderSetting.VideoQuality.RECORD_HIGH1.setBitrate(Config.EDITORBITRATE * 1000).setFps(Config.EDITORFPS);

        mMovieEditor.setDelegate(mEditorDelegate);
        mMovieEditor.loadVideo();
    }


    private void initMoviePlayer() {
        mPlayer = TuSDKMoviePlayer.createMoviePlayer();
        mPlayer.setLooping(false);

        Uri uri = mVideoPath == null ? null : Uri.fromFile(new File(mVideoPath));
        mPlayer.initVideoPlayer(this, uri, mSurfaceView);
        mPlayer.setDelegate(this);
    }

    private void initRangeSelectionBar() {
        mRangeSelectionBar = (MovieRangeSelectionBar) this.findViewById(R.id.lsq_seekbar);
        mRangeSelectionBar.setShowPlayCursor(false);
        mRangeSelectionBar.setType(MovieRangeSelectionBar.Type.Clip);
        mRangeSelectionBar.setLeftSelection(0);
        mRangeSelectionBar.setPlaySelection(0);
        mRangeSelectionBar.setRightSelection(100);
        mRangeSelectionBar.setOnCursorChangeListener(mOnCursorChangeListener);

        setBarSpace();
    }

    /**
     * 加载视频缩略图
     */
    public void loadVideoThumbList() {
        if (mRangeSelectionBar != null && mRangeSelectionBar.getList() == null) {
            TuSdkSize tuSdkSize = TuSdkSize.create(TuSdkContext.dip2px(56), TuSdkContext.dip2px(56));

            TuSDKVideoImageExtractor extractor = TuSDKVideoImageExtractor.createExtractor();

            extractor.setOutputImageSize(tuSdkSize)
                    .setVideoDataSource(TuSDKMediaDataSource.create(mVideoPath))
                    .setExtractFrameCount(6);


            mThumbList = new ArrayList<Bitmap>();
            mRangeSelectionBar.setList(mThumbList);
            extractor.asyncExtractImageList(new TuSDKVideoImageExtractorDelegate() {
                @Override
                public void onVideoImageListDidLoaded(List<Bitmap> images) {
                    mThumbList = images;
                    mRangeSelectionBar.invalidate();
                }

                @Override
                public void onVideoNewImageLoaded(Bitmap bitmap) {
                    mThumbList.add(bitmap);
                    mRangeSelectionBar.invalidate();
                }
            });


        }
    }

    /**
     * 开始播放
     */
    public void startPlayer() {
        if (mPlayer == null) return;

        mPlayer.start();
        hidePlayButton();
        mIsPlaying = true;
        mIsVideoFinished = false;
    }

    /**
     * 暂停播放
     */
    public void pausePlayer() {
        if (mPlayer == null) return;

        mPlayer.pause();
        showPlayButton();
        mIsPlaying = false;
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        if (mPlayer == null) return;

        mPlayer.stop();
        showPlayButton();
        mIsPlaying = false;
    }

    /**
     * 显示播放按钮
     */
    public void showPlayButton() {
        if (mPlayButton != null) {
            mPlayButton.setVisibility(View.VISIBLE);
            mPlayButton.setBackgroundResource(R.mipmap.play_video);
        }
    }

    /**
     * 隐藏播放按钮
     */
    public void hidePlayButton() {
        if (mPlayButton != null) {
            mPlayButton.setVisibility(View.VISIBLE);
            mPlayButton.setBackgroundResource(R.mipmap.stop_video);
        }
    }

    /**
     * 隐藏裁剪控件播放指针
     */
    public void hidePlayCursor() {
        if (mRangeSelectionBar != null) {
            mRangeSelectionBar.setPlaySelection(-1);
            mRangeSelectionBar.setShowPlayCursor(false);
        }
    }

    /**
     * 点击控制播放与暂停
     */
    private void handleClickSurfaceView() {
        if (!mIsPlaying && !mIsVideoFinished) {
            startPlayer();
        } else if (!mIsPlaying && mIsVideoFinished) {
            seekToPreview(mStartTime, new OnSeekToPreviewListener() {
                @Override
                public void onSeekToComplete() {
                    startPlayer();
                }
            });
        } else {
            pausePlayer();
        }
    }

    /**
     * 点击下一步按钮
     */
    private void handleClickNextButton() {
        Intent intent = new Intent(this, mIntentClass);
        intent.putExtra("startTime", mStartTime);
        intent.putExtra("endTime", mEndTime);
        intent.putExtra("videoPath", mVideoPath);
        intent.putExtra("movieLeft", mCutRectLeftPercent);
        intent.putExtra("movieTop", mCutRectTopPercent);
        intent.putExtra("movieRight", mCutRectRightPercent);
        intent.putExtra("movieBottom", mCutRectBottomPercent);
        intent.putExtra("ratioAdaption", RATIO_ADAPTION);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void setIntentClass(Class<?> clazz) {
        if (clazz == null) clazz = MovieEditorActivity.class;
        this.mIntentClass = clazz;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideoThumbList();
        if (mIsPaused) {
            startPlayer();
            mIsPaused = false;
        }
        if (mMovieEditor != null) {
            mMovieEditor.resumeProcessing();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mIsPaused && mIsPlaying) {
            pausePlayer();
            mIsPaused = true;
        }
        if (mMovieEditor != null) {
            mMovieEditor.pauseProcessing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMovieEditor != null) {
            mMovieEditor.destroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) return;

        switch (resultCode) {
            case RESULT_OK:
                // 关闭界面
                setResult(RESULT_OK);
                this.finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private OnClickListener mOnClickListener = new TuSdkViewHelper.OnSafeClickListener() {

        @Override
        public void onSafeClick(View v) {

            if (v.getId() == R.id.lsq_back) {
                onBackPressed();
            } else if (v.getId() == R.id.lsq_play_btn) {
                handleClickSurfaceView();
            } else if (v.getId() == R.id.lsq_next) {
//                handleClickNextButton();

                //时间长了
                if (mEndTime - mStartTime > videoMaxTime) {
                    showRemindMaxOvertime();
                } else {
                    initMovieEditor();
                    mNextTextView.setEnabled(false);
                }
            }

        }
    };

    /**
     * 用于监听裁剪控件
     */
    private MovieRangeSelectionBar.OnCursorChangeListener mOnCursorChangeListener = new MovieRangeSelectionBar.OnCursorChangeListener() {

        @Override
        public void onSeeekBarChanged(int width, int height) {
            mSeekBarWidth = width;
            setBarSpace();
        }

        @Override
        public void onLeftCursorChanged(int percent) {
            mIsPlaying = false;

            mStartTime = percent * mVideoTime / 100;
            seekToPreview(mStartTime, null);
            updateLeftCursorTime();
            updatePlayCursorTime();
            showPlayButton();
            hidePlayCursor();
        }

        @Override
        public void onPlayCursorChanged(int percent) {
        }

        @Override
        public void onRightCursorChanged(int percent) {
            if (mPlayer == null) return;

            mIsPlaying = false;
            mEndTime = percent * mVideoTime / (float) 100;
            seekToPreview(mEndTime, null);
            updateRightCursorTime();
            updatePlayCursorTime();
            showPlayButton();
            hidePlayCursor();
        }

        @Override
        public void onLeftCursorUp() {
            seekToPreview(mStartTime, null);
        }

        @Override
        public void onRightCursorUp() {
            seekToPreview(mStartTime, null);
        }
    };

    public void seekToPreview(final float time, final OnSeekToPreviewListener listener) {
        if (mPlayer == null) return;

        mPlayer.seekToPreview((int) time, listener);
    }

    /**
     * 设置裁剪控件开始与结束的最小间隔距离
     */
    public void setBarSpace() {
        if (mVideoTime == 0 || mRangeSelectionBar == null) return;

        double percent = 1000 / (double) Math.max(mVideoTime, 2000);
        mSeekBarWidth = mSeekBarWidth == 0 ? 640 : mSeekBarWidth;
        int space = (int) (percent * mSeekBarWidth);
        mRangeSelectionBar.setCursorSpace(space);
    }

    public void updateTextViewTime(TextView textView, float time) {
        if (textView != null) {
            StringBuffer sb = new StringBuffer();
            int tmpTime = (int) (time / 1000);

            int temp = (tmpTime % 3600 / 60);
            sb.append((temp < 10) ? ("0" + temp + ":") : (temp + ":"));

            temp = (int) (tmpTime % 3600 % 60);
            sb.append((temp < 10) ? ("0" + temp + "") : (temp + ""));

            textView.setText(sb.toString());
            textView.invalidate();
        }
    }

    public void updateLeftCursorTime() {
        updateTextViewTime(mLeftTextView, mStartTime);
    }

    public void updatePlayCursorTime() {
        updateTextViewTime(mPlayTextView, (mEndTime - mStartTime));
    }

    public void updateRightCursorTime() {
        updateTextViewTime(mRightTextView, mEndTime);
    }


    public void setupHVScrollView(final int w, final int h) {
        // 监听滚动坐标
        mHVScrollView.setOnScrollChangeListener(new HVScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(HVScrollView v, int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                mScrollViewContentOffseX = (scrollX > w - mScreenWidth) ? (w - mScreenWidth) : scrollX;
                mScrollViewContentOffseY = (scrollY > h - mScreenWidth) ? (h - mScreenWidth) : scrollY;
                mCutRectLeftPercent = mScrollViewContentOffseX / (float) w;
                mCutRectTopPercent = mScrollViewContentOffseY / (float) h;
                mCutRectRightPercent = mCutRectLeftPercent + mScreenWidth / (float) w;
                mCutRectBottomPercent = mCutRectTopPercent + mScreenWidth / (float) h;
            }
        });

        if (w < h) {
            mHVScrollView.setScrollOrientation(HVScrollView.SCROLL_ORIENTATION_VERTICAL);
            mShowStyle = SHOW_VERTICAL;
        } else if (w > h) {
            mHVScrollView.setScrollOrientation(HVScrollView.SCROLL_ORIENTATION_HORIZONTAL);
            mShowStyle = SHOW_HORIZONETAL;
        } else {
            mHVScrollView.setScrollOrientation(HVScrollView.SCROLL_ORIENTATION_NONE);
        }
    }

    public void updateMovieLayout(final int titleHeight) {
        if (mMovieLayout == null) return;

        FrameLayout.LayoutParams time_lp = new FrameLayout
                .LayoutParams(mScreenWidth, mScreenWidth);
        time_lp.setMargins(0, titleHeight, 0, 0);
        mMovieLayout.setLayoutParams(time_lp);
    }

    public void updateSelectTimeLayout(final int selectTimeHeight) {
        if (mSelectTimeLayout == null) return;

        FrameLayout.LayoutParams time_lp = new FrameLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, selectTimeHeight);
        time_lp.setMargins(0, mScreenHeight - selectTimeHeight, 0, 0);
        mSelectTimeLayout.setLayoutParams(time_lp);
    }

    public void updateHVScrollViewLaytout() {
        if (mHVScrollView == null) return;

        FrameLayout.LayoutParams hv_lp = new FrameLayout
                .LayoutParams(mScreenWidth, mScreenWidth);
        mHVScrollView.setLayoutParams(hv_lp);
    }

    public void updateHVScrollViewScolling(final RectF rect, final int w, final int h) {
        if (mHVScrollView == null) return;

        mHVScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mShowStyle == SHOW_HORIZONETAL) {
                    int x0 = (int) ((rect.right - mScreenWidth) / 2);
                    mHVScrollView.scrollTo(x0, 0);

                    mCutRectLeftPercent = x0 / (float) w;
                    mCutRectTopPercent = 0.0f;
                    ;
                    mCutRectRightPercent = mCutRectLeftPercent + mScreenWidth / (float) w;
                    mCutRectBottomPercent = mCutRectTopPercent + mScreenWidth / (float) h;
                } else if (mShowStyle == SHOW_VERTICAL) {
                    int y0 = (int) ((rect.bottom - mScreenWidth) / 2);
                    mHVScrollView.scrollTo(0, y0);

                    mCutRectLeftPercent = 0.0f;
                    mCutRectTopPercent = y0 / (float) h;
                    mCutRectRightPercent = mCutRectLeftPercent + mScreenWidth / (float) w;
                    mCutRectBottomPercent = mCutRectTopPercent + mScreenWidth / (float) h;
                }
            }
        }, 100);
    }

    public void updateSurfaceViewLayout(final int w, final int h) {
        if (mSurfaceView == null) return;

        RelativeLayout.LayoutParams lp = new RelativeLayout
                .LayoutParams(w, h);
        mSurfaceView.setLayoutParams(lp);
    }

    /**
     * 对原始视频进行缩放
     *
     * @param width
     * @param height
     */
    private void scaleVideoViews(int width, int height) {
        if (mHVScrollView == null || mMovieLayout == null
                || mSurfaceView == null || mSelectTimeLayout == null) return;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 显示屏幕区域
        mScreenWidth = (int) dm.widthPixels;
        mScreenHeight = (int) dm.heightPixels;

        Resources resources = getResources();
        // 显示title布局高度 ,单位像素
        int titleHeight = (int) resources.getDimension(R.dimen.lsq_title_height);
        // 显示时间选择范围布局高度
        int selectTimeHeight = (int) (mScreenHeight - titleHeight - mScreenWidth);
        RectF boundingRect = new RectF();
        boundingRect.left = 0;
        boundingRect.right = mScreenWidth;
        boundingRect.top = 0;
        boundingRect.bottom = mScreenWidth;
        RectF rect = RectHelper.makeRectWithAspectRatioOutsideRect(new TuSdkSize(width, height), boundingRect);
        int w = (int) rect.width();
        int h = (int) rect.height();
        mScrollViewContentOffseX = 0;
        mScrollViewContentOffseY = 0;
        mCutRectLeftPercent = 0.0f;
        mCutRectTopPercent = 0.0f;
        ;
        mCutRectRightPercent = mCutRectLeftPercent + mScreenWidth / (float) w;
        mCutRectBottomPercent = mCutRectTopPercent + mScreenWidth / (float) h;

        // 动态设置滚动控件
        setupHVScrollView(w, h);

        // 重新调整播放视频的显示区域
        updateMovieLayout(titleHeight);

        // 重新调整选择时间范围的显示区域
        updateSelectTimeLayout(selectTimeHeight);

        // 重新设置滚动控件布局的显示区域
        updateHVScrollViewLaytout();

        // 滚动控件设置延迟滚动
        updateHVScrollViewScolling(rect, w, h);

        // 重新设置SurfaceView控件的显示区域
        updateSurfaceViewLayout(w, h);
    }

    public void playerStateChanged(PlayerState state) {
        if (mPlayer == null) return;

        if (state == PlayerState.INITIALIZED) {
            mStartTime = 0;
            mEndTime = mPlayer.getDuration();
            mVideoTime = mPlayer.getDuration();
            mPlayer.seekTo(1);
            initRangeSelectionBar();
            updateLeftCursorTime();
            updateRightCursorTime();
            updatePlayCursorTime();
        }
    }

    public void updateProgress(int progress) {
        if (mPlayer == null || mRangeSelectionBar == null || !mIsPlaying) return;

        int time = (int) (progress * mVideoTime / 100);
        if (time < mStartTime) return;
        if (time < mEndTime) {
            if (!mRangeSelectionBar.isShowPlayCursor())
                mRangeSelectionBar.setShowPlayCursor(true);
            mRangeSelectionBar.setPlaySelection(progress);
        } else {
            if (mRangeSelectionBar.isShowPlayCursor())
                mRangeSelectionBar.setShowPlayCursor(false);
            mIsVideoFinished = true;
            pausePlayer();
            showPlayButton();
        }
    }

    @Override
    public void onStateChanged(PlayerState state) {
        playerStateChanged(state);
    }

    @Override
    public void onVideSizeChanged(MediaPlayer mp, int width, int height) {
        scaleVideoViews(width, height);
    }

    @Override
    public void onProgress(int progress) {
        updateProgress(progress);
    }

    @Override
    public void onSeekComplete() {
    }

    @Override
    public void onCompletion() {
        if (mRangeSelectionBar.isShowPlayCursor())
            mRangeSelectionBar.setShowPlayCursor(false);

        mIsVideoFinished = true;
        pausePlayer();
        showPlayButton();
    }


    /**
     * you拍云编辑视频必须要视频容器，隐藏后台实现
     *
     * @return
     */
    public RelativeLayout getCameraView() {
        return (RelativeLayout) findViewById(R.id.lsq_cameraView);
    }

    public TuSDKMovieEditorOptions getEditorOption() {
        float movieLeft = mCutRectLeftPercent;
        float movieTop = mCutRectTopPercent;
        float movieRight = mCutRectRightPercent;
        float movieBottom = mCutRectBottomPercent;

        //视频剪裁时间区域
        TuSDKTimeRange mCutTimeRange = TuSDKTimeRange.makeRange(mStartTime / (float) 1000, mEndTime / (float) 1000);

        TuSDKMovieEditorOptions defaultOptions = TuSDKMovieEditorOptions.defaultOptions();
        defaultOptions.setMoviePath(mVideoPath)
                .setCutTimeRange(mCutTimeRange)
                // 是否需要按原视频比例显示
                .setOutputRegion(RATIO_ADAPTION ? null : new RectF(movieLeft, movieTop, movieRight, movieBottom))
                .setEnableBeauty(true) // 设置是否开启美颜
                .setIncludeAudioInVideo(true) // 设置是否保存或者播放原音
                .setLoopingPlay(true) // 设置是否循环播放视频
                .setAutoPlay(true) // 设置视频加载完成后是否自动播放
                .setClearAudioDecodeCacheInfoOnDestory(false); // 设置MovieEditor销毁时是否自动清除缓存音频解码信息

        defaultOptions.setSaveToAlbum(true);//设置是否保存到相册（默认：true）
        defaultOptions.setSaveToAlbumName(CacheFileManger.getCacheVideoPath());//设置保存到相册的名称（需在 DCIM 目录下），默认保存到系统默认相册

        return defaultOptions;
    }

    private String TAG = this.getClass().getName();
    private TuSDKMovieEditor.TuSDKMovieEditorDelegate mEditorDelegate = new TuSDKMovieEditor.TuSDKMovieEditorDelegate() {
        /**
         * 视频处理完成
         *
         * @param tuSDKVideoResult
         *            生成的新视频信息，预览时该对象为 null
         */
        @Override
        public void onMovieEditComplete(final TuSDKVideoResult tuSDKVideoResult) {

            String msg = tuSDKVideoResult == null ? getStringFromResource("new_movie_error_saving")
                    : getStringFromResource("new_movie_saved");
            TuSdk.messageHub().showError(MoviePreviewAndCutActivity.this, msg);


            if (tuSDKVideoResult != null && tuSDKVideoResult.videoPath != null) {
                final File file = tuSDKVideoResult.videoPath;
                final String name = TuSdkManger.getInstance().getAttachmentName(System.currentTimeMillis());
                Log.e(TAG, "视频size:" + file.length() / 1024 + "kb");

                //TODO 生成缩略图保存本地
                TuSdkManger.getInstance().saveThumbnailImage(MoviePreviewAndCutActivity.this, tuSDKVideoResult.videoPath.getPath(), new TuSdkManger.iSaveThumbnailListener() {
                    @Override
                    public void saveThumbnailStatus(boolean bol) {

                        Log.e(getClass().getName(), bol ? "保存本地成功" : "保存本地失败");
                        //TODO 上传缩略图
                        if (bol) {
                            final String thumbnailPath = TuSdkManger.getInstance().getThumbnailPath(tuSDKVideoResult.videoPath.getPath());
                            File thumbnailFile = new File(thumbnailPath);
                            final String thumbnailYunPath = "/forum/" + name + ".jpg";

                            //表单上传
                            final Map<String, Object> paramsMap = new HashMap<>();
                            //上传空间
                            paramsMap.put(Params.BUCKET, Config.BUCKET);
                            //保存路径，任选其中一个
                            paramsMap.put(Params.SAVE_KEY, thumbnailYunPath);
                            UploadEngine.getInstance().formUpload(thumbnailFile, paramsMap, Config.OPERATER, UpYunUtils.md5(Config.PASSWORD), new UpCompleteListener() {
                                @Override
                                public void onComplete(boolean isSuccess, String result) {
                                    Log.e(getClass().getName(), isSuccess + ":" + result);
                                    //TODO 上传视频
                                    if (isSuccess) {
                                        initUpProgressDialog();
                                        final String videoYunPath = "/forum/" + name + ".mp4";
                                        //表单上传
                                        final Map<String, Object> paramsMap = new HashMap<>();
                                        //上传空间
                                        paramsMap.put(Params.BUCKET, Config.BUCKET);
                                        //保存路径，任选其中一个
                                        paramsMap.put(Params.SAVE_KEY, videoYunPath);

                                        //上传结果回调
                                        UpCompleteListener completeListener = new UpCompleteListener() {
                                            @Override
                                            public void onComplete(boolean isSuccess, String result) {
                                                Log.e(TAG, isSuccess + ":" + result);
                                                if (isSuccess) {
                                                    VideoInfo videoInfo = new VideoInfo();
                                                    videoInfo.setVideoInitPath(mVideoPath);
                                                    videoInfo.setThumbnailUrl(thumbnailYunPath);
                                                    videoInfo.setThumbnailLocalPath(thumbnailPath);
                                                    videoInfo.setVideoUrl(videoYunPath);
                                                    videoInfo.setVideoLocalPath(file.getPath());
                                                    videoInfo.setVideoWidth(tuSDKVideoResult.videoInfo.width);
                                                    videoInfo.setVideoHeight(tuSDKVideoResult.videoInfo.height);
                                                    videoInfo.setTimelength((int) ((mEndTime - mStartTime) / 1000));
                                                    EventBus.getDefault().post(videoInfo);
                                                    setResult(RESULT_OK);
                                                    finish();
                                                }
                                            }
                                        };
                                        //进度条回调
                                        UpProgressListener progressListener = new UpProgressListener() {
                                            @Override
                                            public void onRequestProgress(final long bytesWrite, final long contentLength) {
                                                Log.e(TAG, (100 * bytesWrite) / contentLength + "%");
                                                upProgressDialog.updateViewProgress((int) (100 * bytesWrite / contentLength));
                                            }
                                        };
                                        UploadEngine.getInstance().formUpload(file, paramsMap, Config.OPERATER, UpYunUtils.md5(Config.PASSWORD), completeListener, progressListener);
                                    }
                                }
                            }, null);
                        }
                    }
                });


            }


        }

        /**
         * 进度
         */
        public void initUpProgressDialog() {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("UpProgressDialog");
            if (null != fragment) {
                ft.remove(fragment);
            }
            upProgressDialog = new UpProgressDialog();
            upProgressDialog.show(getSupportFragmentManager(), "UpProgressDialog");
        }


        @Override
        public void onMovieEditProgressChanged(float durationTimes, float progress) {
            //更新缩略图时间进度
        }

        /**
         * 编辑状态
         * @param status
         */
        @Override
        public void onMovieEditorStatusChanged(TuSDKMovieEditor.TuSDKMovieEditorStatus status) {
            TuSdk.messageHub().dismissRightNow();
            if (status == TuSDKMovieEditor.TuSDKMovieEditorStatus.Loaded) {  //加载完毕,生成新视频
                generateVideo();
                LogFly.e("onMovieEditorStatusChanged   " + "Loaded");
            } else if (status == TuSDKMovieEditor.TuSDKMovieEditorStatus.Recording) { //开始保存
                String msg = getStringFromResource("new_movie_saving");
                TuSdk.messageHub().setStatus(MoviePreviewAndCutActivity.this, msg);

                LogFly.e("onMovieEditorStatusChanged   " + "Recording");

            } else if (status == TuSDKMovieEditor.TuSDKMovieEditorStatus.LoadVideoFailed) {//加载失败
                String msg = getStringFromResource("lsq_loadvideo_failed");
                TuSdk.messageHub().showError(MoviePreviewAndCutActivity.this, msg);

                LogFly.e("onMovieEditorStatusChanged   " + "LoadVideoFailed");

                mBackTextView.setEnabled(true);
            } else if (status == TuSDKMovieEditor.TuSDKMovieEditorStatus.RecordingFailed) {//保存失败
                mBackTextView.setEnabled(true);

                LogFly.e("onMovieEditorStatusChanged   " + "RecordingFailed");
                String msg = getStringFromResource("new_movie_error_saving");
                TuSdk.messageHub().showError(MoviePreviewAndCutActivity.this, msg);
            } else if (status == TuSDKMovieEditor.TuSDKMovieEditorStatus.PreviewingCompleted) {

                LogFly.e("onMovieEditorStatusChanged   " + "PreviewingCompleted");
                // 当再次启动预览或者预览完成后禁用 PlaygroundTimeRange （开发者可根据需要设置）
//				mRangeSelectionBar.setPlaySelection(0);
            }
        }

        /**
         * 视频原音和音效状态
         */
        @Override
        public void onMovieEditorSoundStatusChanged(TuSDKMovieEditor.TuSDKMovieEditorSoundStatus status) {
            TuSdk.messageHub().dismissRightNow();

            if (status == TuSDKMovieEditor.TuSDKMovieEditorSoundStatus.Loading) {
                String msg = getStringFromResource("new_movie_audio_effect_loading");
                TuSdk.messageHub().setStatus(MoviePreviewAndCutActivity.this, msg);
            }
        }

        @Override
        public void onFilterChanged(FilterWrap selesOutInput) {


        }
    };

    public void onEventMainThread(TagEvent tagEvent) {
        //TODO 取消上传视频
        if (tagEvent.getTag() == TagEvent.TAG_CANCLE_SEND_VIDEO) {

        }
    }

    /**
     * 视频超时提醒
     */
    private void showRemindMaxOvertime() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(MoviePreviewAndCutActivity.this);
        builder.setMessage("视频时长需小于5分钟");
        builder.setIsOneButton(true);
        builder.create().show();
    }
}