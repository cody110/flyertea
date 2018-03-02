/**
 * TuSDKVideoDemo
 * MovieEditorActivity.java
 *
 * @author Yanlin
 * @Date Feb 21, 2017 8:52:11 PM
 * @Copright (c) 2016 tusdk.com. All rights reserved.
 */
package com.ideal.flyerteacafes.ui.activity.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.ui.dialog.UpProgressDialog;
import com.flyer.tusdk.Config;
import com.flyer.tusdk.TuSdkManger;
import com.flyer.tusdk.model.VideoInfo;
import com.flyer.tusdk.utils.Constants;
import com.flyer.tusdk.views.FilterCellView;
import com.flyer.tusdk.views.FilterConfigSeekbar;
import com.flyer.tusdk.views.FilterConfigView;
import com.flyer.tusdk.views.FilterListView;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.encoder.video.TuSDKVideoEncoderSetting;
import org.lasque.tusdk.core.seles.SelesParameters;
import org.lasque.tusdk.core.seles.SelesParameters.FilterArg;
import org.lasque.tusdk.core.seles.tusdk.FilterWrap;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.core.video.TuSDKVideoResult;
import org.lasque.tusdk.core.view.TuSdkViewHelper;
import org.lasque.tusdk.core.view.recyclerview.TuSdkTableView;
import org.lasque.tusdk.video.editor.TuSDKMovieEditor;
import org.lasque.tusdk.video.editor.TuSDKMovieEditor.TuSDKMovieEditorDelegate;
import org.lasque.tusdk.video.editor.TuSDKMovieEditor.TuSDKMovieEditorSoundStatus;
import org.lasque.tusdk.video.editor.TuSDKMovieEditor.TuSDKMovieEditorStatus;
import org.lasque.tusdk.video.editor.TuSDKMovieEditorOptions;
import org.lasque.tusdk.video.editor.TuSDKTimeRange;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 视频编辑示例
 * <p>
 * 功能：
 * 1. 预览视频，添加滤镜查看效果
 * 2. 导出新的视频
 *
 * @author Yanlin
 */
public class MovieEditorActivity extends SimpleCameraActivity {
    private static final String TAG = "MovieEditorActivity";
    /**
     * 保存按钮
     */
    protected TextView mSaveButton;
    /**
     * 返回按钮
     */
    protected View mBackTextView;
    /**
     * 编辑器
     */
    private TuSDKMovieEditor mMovieEditor;
    /**
     * 开始播放按钮
     */
    protected Button mActionButton;
    /**
     * 滤镜栏
     */
    private FilterListView mFilterListView;
    /**
     * 滤镜参数调节栏
     */
    private FilterConfigView mConfigView;


    // 记录当前滤镜
    private FilterWrap mSelesOutInput;

    // 记录上一个选中的滤镜
    private FilterCellView lastSelectedCellView;

    private LinearLayout mFilterWrap;

    /**
     * 记录缩略图列表容器
     */
    private List<Bitmap> mVideoThumbList;


    /**
     * 用于记录当前调节栏磨皮系数
     */
    private float mSmoothingProgress = -1.0f;
    /**
     * 用于记录当前调节栏效果系数
     */
    private float mMixiedProgress = -1.0f;

    /**
     * 记录是否是首次进入
     */
    private boolean mIsFirstEntry = true;
    /**
     * 用于记录焦点位置
     */
    private int mFocusPostion = 1;

    /**
     * 视频路径
     */
    protected String mVideoPath;
    /**
     * 视频裁切区域
     */
    private TuSDKTimeRange mCutTimeRange;

    /**
     * 上传进度dialog
     */
    private UpProgressDialog upProgressDialog;

    protected int getLayoutId() {
        return R.layout.movie_editor_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(getLayoutId());

        mIsFirstEntry = true;
        mSaveButton = (TextView) findViewById(R.id.lsq_next);
        mSaveButton.setText(R.string.lsq_save);
        mSaveButton.setOnClickListener(mButtonSafeClickListener);
        mSaveButton.setEnabled(false);

        mActionButton = (Button) findViewById(R.id.lsq_actButton);
        mActionButton.setVisibility(View.INVISIBLE);
        mBackTextView = findViewById(R.id.lsq_back);
        mBackTextView.setOnClickListener(mButtonClickListener);

        hideNavigationBar();

        mFilterWrap = (LinearLayout) findViewById(R.id.lsq_filter_layout);
        mFilterWrap.setVisibility(View.GONE);

        initFilterListView();

        setCameraViewSize(TuSdkContext.getScreenSize().width, TuSdkContext.getScreenSize().width);
        Intent intent = getIntent();
        mVideoPath = intent.getStringExtra("videoPath");

        // 视频裁切区域时间
        mCutTimeRange = TuSDKTimeRange.makeRange(intent.getFloatExtra("startTime", 0) / (float) 1000, intent.getFloatExtra("endTime", 0) / (float) 1000);
        // 如果没有传递开始和结束时间，默认视频编辑时长为总时长
        if (mCutTimeRange.duration() == 0 && mVideoPath != null) {
            mCutTimeRange = TuSDKTimeRange.makeRange(0, getVideoDuration(mVideoPath));
        }

        // 初始化编辑器
        initMovieEditor();
        // 设置弹窗提示是否在隐藏虚拟键的情况下使用
        TuSdk.messageHub().applyToViewWithNavigationBarHidden(true);


        //初始化滤镜
        toggleFilterMode();

    }


    /**
     * 获取Activity ContentView
     */
    public View getContentView(Activity activity) {
        ViewGroup view = (ViewGroup) activity.getWindow().getDecorView();
        FrameLayout content = (FrameLayout) view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }


    @SuppressWarnings("deprecation")
    private GestureDetector detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // 开始播放视频
            handleActionButton();
            return true;
        }
    });


    private DisplayMetrics getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        // getRealMetrics()方法可以获取包含虚拟键的屏幕高度
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return dm;
    }


    @Override
    protected void onResume() {
        hideNavigationBar();
        super.onResume();
        mMovieEditor.resumeProcessing();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TuSdk.messageHub().dismissRightNow();
        mMovieEditor.pauseProcessing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMovieEditor != null) {
            mMovieEditor.destroy();
        }
        EventBus.getDefault().unregister(this);
    }


    protected void initMovieEditor() {
        startLoadVideo(getEditorOption(), getCameraView());
    }

    @SuppressLint("ClickableViewAccessibility")
    protected OnTouchListener mCameraViewOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            detector.onTouchEvent(event);
            return true;
        }
    };

    protected RelativeLayout getCameraView() {
        RelativeLayout cameraView = (RelativeLayout) findViewById(R.id.lsq_cameraView);

        // 播放视图设置触摸事件
        cameraView.setOnTouchListener(mCameraViewOnTouchListener);
        return cameraView;
    }

    /**
     * 设置视频播放区域大小
     */
    protected void setCameraViewSize(int width, int height) {
//        RelativeLayout.LayoutParams lp = (LayoutParams) getCameraView().getLayoutParams();
//        lp.width = width;
//        lp.height = height;

        WidgetUtils.setWidthHeight(getCameraView(), width, height);
    }

    protected TuSDKMovieEditorOptions getEditorOption() {
        float movieLeft = getIntent().getFloatExtra("movieLeft", 0.0f);
        float movieTop = getIntent().getFloatExtra("movieTop", 0.0f);
        float movieRight = getIntent().getFloatExtra("movieRight", 1.0f);
        float movieBottom = getIntent().getFloatExtra("movieBottom", 1.0f);

        boolean isRatioAdaption = getIntent().getBooleanExtra("ratioAdaption", true);

        TuSDKMovieEditorOptions defaultOptions = TuSDKMovieEditorOptions.defaultOptions();
        defaultOptions.setMoviePath(mVideoPath)
                .setCutTimeRange(mCutTimeRange)
                // 是否需要按原视频比例显示
                .setOutputRegion(isRatioAdaption ? null : new RectF(movieLeft, movieTop, movieRight, movieBottom))
                .setEnableBeauty(true) // 设置是否开启美颜
                .setIncludeAudioInVideo(true) // 设置是否保存或者播放原音
                .setLoopingPlay(true) // 设置是否循环播放视频
                .setAutoPlay(true) // 设置视频加载完成后是否自动播放
                .setClearAudioDecodeCacheInfoOnDestory(false); // 设置MovieEditor销毁时是否自动清除缓存音频解码信息

        return defaultOptions;
    }

    protected void startLoadVideo(TuSDKMovieEditorOptions options, RelativeLayout cameraView) {
        mMovieEditor = new TuSDKMovieEditor(this.getBaseContext(), cameraView, options);

        // 视频原音音量
        mMovieEditor.setVideoSoundVolume(1f);
        // 打开美颜
        mMovieEditor.setEnableBeauty(true);


        //设置默认不保存相册
        mMovieEditor.setSaveToAlbum(false);

        // 设置水印，默认为空
        mMovieEditor.setWaterMarkImage(null);
//        mMovieEditor.setWaterMarkImage(BitmapHelper.getBitmapFormRaw(this, R.raw.sample_watermark));
//        mMovieEditor.setWaterMarkPosition(WaterMarkPosition.TopLeft);

        TuSDKVideoEncoderSetting encoderSetting = mMovieEditor.getVideoEncoderSetting();
        encoderSetting.videoSize = TuSdkSize.create(Config.EDITORWIDTH, Config.EDITORHEIGHT);
        encoderSetting.videoQuality = TuSDKVideoEncoderSetting.VideoQuality.RECORD_LOW1.setBitrate(Config.EDITORBITRATE * 1000).setFps(Config.EDITORFPS);

        mMovieEditor.setDelegate(mEditorDelegate);
        mMovieEditor.loadVideo();
    }


    /**
     * 初始化滤镜栏视图
     */
    protected void initFilterListView() {
        getFilterConfigView().setVisibility(View.INVISIBLE);
        getFilterListView();

        if (mFilterListView == null) return;

        this.mFilterListView.setModeList(Arrays.asList(Constants.VIDEOFILTERS));
    }

    /**
     * 滤镜栏视图
     *
     * @return
     */
    private FilterListView getFilterListView() {
        if (mFilterListView == null) {
            mFilterListView = (FilterListView) findViewById(R.id.lsq_filter_list_view);

            if (mFilterListView == null) return null;

            mFilterListView.loadView();
            mFilterListView.setCellLayoutId(R.layout.filter_list_cell_view);
            mFilterListView.setCellWidth(TuSdkContext.dip2px(62));
            mFilterListView.setItemClickDelegate(mFilterTableItemClickDelegate);
            mFilterListView.reloadData();
            mFilterListView.selectPosition(mFocusPostion);
        }
        return mFilterListView;
    }

    /**
     * 滤镜seekbar调节拦
     *
     * @return
     */
    private FilterConfigView getFilterConfigView() {
        if (mConfigView == null) {
            mConfigView = (FilterConfigView) findViewById(R.id.lsq_filter_config_view);
        }

        return mConfigView;
    }

    /**
     * 滤镜组列表点击事件
     */
    private TuSdkTableView.TuSdkTableViewItemClickDelegate<String, FilterCellView> mFilterTableItemClickDelegate = new TuSdkTableView.TuSdkTableViewItemClickDelegate<String, FilterCellView>() {
        @Override
        public void onTableViewItemClick(String itemData,
                                         FilterCellView itemView, int position) {
            onFilterGroupSelected(itemData, itemView, position);
        }
    };

    /**
     * 滤镜组选择事件
     *
     * @param itemView
     * @param position
     */
    protected void onFilterGroupSelected(String itemData, FilterCellView itemView, int position) {

        playMovie();

        mFocusPostion = position;
        mFilterListView.selectPosition(mFocusPostion);

        changeVideoFilterCode(itemData);
        deSelectLastFilter(lastSelectedCellView, position);
        selectFilter(itemView, position);
        getFilterConfigView().setVisibility((position == 0) ? View.INVISIBLE : View.VISIBLE);
    }

    private void playMovie() {
        if (mMovieEditor == null) return;
        mMovieEditor.startPreview();
        updateActionButtonStatus(false);
    }

    /**
     * 滤镜选中状态
     *
     * @param itemView
     * @param position
     */
    private void selectFilter(FilterCellView itemView, int position) {
        updateFilterBorderView(itemView, false);
        itemView.setFlag(position);

        TextView titleView = itemView.getTitleView();
        titleView.setBackground(TuSdkContext.getDrawable("tusdk_view_filter_selected_text_roundcorner"));
        // 记录本次选中的滤镜
        lastSelectedCellView = itemView;
    }

    /**
     * 设置滤镜单元边框是否可见
     *
     * @param lastFilter
     * @param isHidden
     */
    private void updateFilterBorderView(FilterCellView lastFilter, boolean isHidden) {
        RelativeLayout filterBorderView = lastFilter.getBorderView();
        filterBorderView.setVisibility(isHidden ? View.GONE : View.VISIBLE);
    }

    /**
     * 取消上一个滤镜的选中状态
     *
     * @param lastFilter
     * @param position
     */
    private void deSelectLastFilter(FilterCellView lastFilter, int position) {
        if (lastFilter == null) return;

        lastFilter.setFlag(-1);
        updateFilterBorderView(lastFilter, true);
        lastFilter.getTitleView().setBackground(TuSdkContext.getDrawable("tusdk_view_filter_unselected_text_roundcorner"));
        lastFilter.getImageView().invalidate();
    }


    /**
     * 更新操作按钮
     *
     * @param isRunning 是否直播中
     */
    protected void updateActionButtonStatus(Boolean isRunning) {
        mActionButton.setVisibility(isRunning ? View.VISIBLE : View.INVISIBLE);
    }


    /**
     * 选择滤镜
     */
    private void toggleFilterMode() {
        toggleFilterAndMVModel();

        if (mSelesOutInput == null) return;

        getFilterConfigView().post(new Runnable() {

            @Override
            public void run() {
                getFilterConfigView().setSelesFilter(mSelesOutInput.getFilter());
                getFilterConfigView().setVisibility(View.VISIBLE);
            }
        });

        getFilterConfigView().setSeekBarDelegate(mConfigSeekBarDelegate);
        getFilterConfigView().invalidate();
    }


    /**
     * 滤镜模式
     */
    private void toggleFilterAndMVModel() {
        mFilterWrap.setVisibility(View.VISIBLE);
        getFilterConfigView().setVisibility(View.VISIBLE);

    }


    /**
     * 按钮点击事件处理
     */
    private View.OnClickListener mButtonSafeClickListener = new TuSdkViewHelper.OnSafeClickListener() {
        public void onSafeClick(View v) {
            if (v == mSaveButton) {
                String msg = getStringFromResource("new_movie_saving");
                TuSdk.messageHub().setStatus(MovieEditorActivity.this, msg);
                // 生成视频文件
                mMovieEditor.startRecording();

                if (mMovieEditor.isRecording()) {
                    updateActionButtonStatus(false);
                }
            }

        }
    };

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == mBackTextView) {
                finish();
            }
        }
    };


    /**
     * 滤镜拖动条监听事件
     */
    private FilterConfigView.FilterConfigViewSeekBarDelegate mConfigSeekBarDelegate = new FilterConfigView.FilterConfigViewSeekBarDelegate() {

        @Override
        public void onSeekbarDataChanged(FilterConfigSeekbar seekbar, FilterArg arg) {
            if (arg == null) return;

            if (arg.equalsKey("mixied"))
                mMixiedProgress = arg.getPrecentValue();
        }

    };


    /**
     * 处理开始、暂停事件
     */
    private void handleActionButton() {
        if (mMovieEditor.isPreviewing()) {
            updateActionButtonStatus(true);
            mMovieEditor.stopPreview();
        } else {
            playMovie();
        }
    }

    protected void changeVideoFilterCode(String code) {
        mMovieEditor.switchFilter(code);
    }


    private TuSDKMovieEditorDelegate mEditorDelegate = new TuSDKMovieEditorDelegate() {
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
            TuSdk.messageHub().showError(MovieEditorActivity.this, msg);


            if (tuSDKVideoResult != null && tuSDKVideoResult.videoPath != null) {
                final File file = tuSDKVideoResult.videoPath;
                final String name = TuSdkManger.getInstance().getAttachmentName(System.currentTimeMillis());
                Log.e(TAG, "视频size:" + file.length() / 1024 + "kb");

                //TODO 生成缩略图保存本地
                TuSdkManger.getInstance().saveThumbnailImage(MovieEditorActivity.this, tuSDKVideoResult.videoPath.getPath(), new TuSdkManger.iSaveThumbnailListener() {
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

        @Override
        public void onMovieEditorStatusChanged(TuSDKMovieEditorStatus status) {
            TuSdk.messageHub().dismissRightNow();

            if (status == TuSDKMovieEditorStatus.Loaded) {

                mSaveButton.setEnabled(true);

            } else if (status == TuSDKMovieEditorStatus.Recording) {
                String msg = getStringFromResource("new_movie_saving");
                TuSdk.messageHub().setStatus(MovieEditorActivity.this, msg);

            } else if (status == TuSDKMovieEditorStatus.LoadVideoFailed) {
                String msg = getStringFromResource("lsq_loadvideo_failed");
                TuSdk.messageHub().showError(MovieEditorActivity.this, msg);
            } else if (status == TuSDKMovieEditorStatus.RecordingFailed) {
                updateActionButtonStatus(true);
                mMovieEditor.stopPreview();

                String msg = getStringFromResource("new_movie_error_saving");
                TuSdk.messageHub().showError(MovieEditorActivity.this, msg);
            } else if (status == TuSDKMovieEditorStatus.PreviewingCompleted) {
                // 当再次启动预览或者预览完成后禁用 PlaygroundTimeRange （开发者可根据需要设置）
//				mRangeSelectionBar.setPlaySelection(0);
            }
        }

        /**
         * 视频原音和音效状态
         */
        @Override
        public void onMovieEditorSoundStatusChanged(TuSDKMovieEditorSoundStatus status) {
            TuSdk.messageHub().dismissRightNow();

            if (status == TuSDKMovieEditorSoundStatus.Loading) {
                String msg = getStringFromResource("new_movie_audio_effect_loading");
                TuSdk.messageHub().setStatus(MovieEditorActivity.this, msg);
            }
        }

        @Override
        public void onFilterChanged(FilterWrap selesOutInput) {
            LogFly.e("onFilterChanged:" + selesOutInput);
            if (selesOutInput == null) return;

            SelesParameters params = selesOutInput.getFilterParameter();
            List<FilterArg> list = params.getArgs();
            for (FilterArg arg : list) {
                if (arg.equalsKey("smoothing") && mSmoothingProgress != -1.0f)
                    arg.setPrecentValue(mSmoothingProgress);
                else if (arg.equalsKey("smoothing") && mSmoothingProgress == -1.0f)
                    mSmoothingProgress = arg.getPrecentValue();
                else if (arg.equalsKey("mixied") && mMixiedProgress != -1.0f)
                    arg.setPrecentValue(mMixiedProgress);
                else if (arg.equalsKey("mixied") && mMixiedProgress == -1.0f)
                    mMixiedProgress = arg.getPrecentValue();

            }
            selesOutInput.setFilterParameter(params);

            mSelesOutInput = selesOutInput;

            if (getFilterConfigView() != null) {
                getFilterConfigView().setSelesFilter(selesOutInput.getFilter());

            }

            if (mIsFirstEntry) {
                mIsFirstEntry = false;
            }
        }
    };

    public void onEventMainThread(TagEvent tagEvent) {
        //TODO 取消上传视频
        if (tagEvent.getTag() == TagEvent.TAG_CANCLE_SEND_VIDEO) {

        }
    }
}