package com.ideal.flyerteacafes.adapters.videoitem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.caff.AdvertStatisticsManger;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.controls.NoScrollGridView;
import com.ideal.flyerteacafes.ui.view.CenterAlignImageSpan;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.ScalableTextureView;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2018/1/29.
 */

public class ThreadVideoItem extends BaseVideoItem {
    String TAG = "ThreadVideoItem";

    int imgLayoutWidth;
    private ThreadSubjectBean threadSubjectBean;
    List<SmileyBean> smileyBeanList;
    Context mContext;
    private VideoPlayerManager<MetaData> mVideoPlayerManager;

    public ThreadSubjectBean getData() {
        return threadSubjectBean;
    }


    public ThreadVideoItem(Context mContext, VideoPlayerManager<MetaData> videoPlayerManager, ThreadSubjectBean data, List<SmileyBean> smileyBeanList) {
        super(videoPlayerManager);
        this.mVideoPlayerManager = videoPlayerManager;
        this.mContext = mContext;
        this.threadSubjectBean = data;
        this.smileyBeanList = smileyBeanList;
        int w_screen = SharedPreferencesString.getInstances().getIntToKey("w_screen");
        imgLayoutWidth = w_screen - mContext.getResources().getDimensionPixelOffset(R.dimen.app_commen_margin) * 2;
    }


    //TODO 滚动卡顿，播放不流畅，暂时屏蔽该功能
    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        //TODO 非空，省流模式 判断
//        if (threadSubjectBean.getVideos() != null && !TextUtils.isEmpty(threadSubjectBean.getVideos().getVideourl()) && !SetCommonManger.isFlowbyMode()) {
//            LogFly.e(TAG, threadSubjectBean.getSubject() + "===>" + threadSubjectBean.getVideos().getVideourl());
//            videoPlayerManager.playNewVideo(currentItemMetaData, player, threadSubjectBean.getVideos().getVideourl());
////            try {
////                videoPlayerManager.playNewVideo(currentItemMetaData, player, mContext.getAssets().openFd("video_sample_4.mp4"));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//        }
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
        LogFly.e(TAG, "stopPlayback");
    }


    /**
     * 初始化，创建view是调用，执行一次
     *
     * @param holder
     * @param iGridViewItemClick
     */
    public void bindListenerItem(final ViewHolder holder, final IGridViewItemClick iGridViewItemClick, ThreadVideoItem threadSubjectBean) {
        final VideoPlayerView mPlayer = holder.getView(R.id.videoPlayerView);
        mPlayer.setScaleType(ScalableTextureView.ScaleType.FILL);
        mPlayer.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
            @Override
            public void onVideoSizeChangedMainThread(int width, int height) {
//                LogFly.e("position:" + holder.getPosition());
//                LogFly.e(TAG, "onVideoSizeChangedMainThread");
            }

            @Override
            public void onVideoPreparedMainThread() {
                // When video is prepared it's about to start playback. So we hide the cover
                LogFly.e("position:" + holder.getPosition());
                LogFly.e(TAG, "onVideoPreparedMainThread");
                holder.setVisible(R.id.videoPlayerView_btn, false);
                holder.setVisible(R.id.videoPlayerView_cover, false);
                holder.setVisible(R.id.videoPlayerView, true);
            }

            @Override
            public void onVideoCompletionMainThread() {
                LogFly.e("position:" + holder.getPosition());
                LogFly.e(TAG, "onVideoCompletionMainThread");
                holder.setVisible(R.id.videoPlayerView_btn, true);
                holder.setVisible(R.id.videoPlayerView_cover, true);
                holder.setVisible(R.id.videoPlayerView, false);
            }

            @Override
            public void onErrorMainThread(int what, int extra) {
                LogFly.e("position:" + holder.getPosition());
                LogFly.e(TAG, "onErrorMainThread");
                holder.setVisible(R.id.videoPlayerView_btn, true);
                holder.setVisible(R.id.videoPlayerView_cover, true);
                holder.setVisible(R.id.videoPlayerView, false);
            }

            @Override
            public void onBufferingUpdateMainThread(int percent) {
//                LogFly.e("position:" + holder.getPosition());
//                LogFly.e(TAG, "onBufferingUpdateMainThread");
            }

            @Override
            public void onVideoStoppedMainThread() {
                LogFly.e("position:" + holder.getPosition());
                LogFly.e(TAG, "onVideoStoppedMainThread");
                // Show the cover when video stopped
                holder.setVisible(R.id.videoPlayerView_btn, true);
                holder.setVisible(R.id.videoPlayerView_cover, true);
                holder.setVisible(R.id.videoPlayerView, false);
            }
        });

        holder.setOnClickListener(R.id.item_community_follow_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGridViewItemClick != null)
                    iGridViewItemClick.gridViewItemClick(holder.getPosition());
            }
        });

//        holder.setOnClickListener(R.id.videoPlayerView_btn, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playNewVideo(null, mPlayer, mVideoPlayerManager);
//            }
//        });

    }

    /**
     * 每次调用，绑定数据
     *
     * @param holder
     * @param isShowType
     * @param isShowFroumNmae
     * @param is_feed
     * @param isShowHeat
     */
    public void bindItem(final ViewHolder holder, boolean isShowType, boolean isShowFroumNmae, final boolean is_feed, boolean isShowHeat) {


        if (YueManger.getInstance().isRead(threadSubjectBean.getTid())) {
            holder.setAlpha(R.id.item_community_follow_face, 0.5f);
            holder.setAlpha(R.id.item_community_follow_title, 0.5f);
        } else {
            holder.setAlpha(R.id.item_community_follow_face, 1.0f);
            holder.setAlpha(R.id.item_community_follow_title, 1.0f);
        }

        holder.setVisible(R.id.item_community_follow_forumname, isShowType);


        TextView item_community_follow_title = holder.getView(R.id.item_community_follow_title);
        setContentText(item_community_follow_title, threadSubjectBean, isShowHeat, is_feed);


        if (threadSubjectBean.getAuthorprofile() != null)
            holder.setImageUrl(R.id.item_community_follow_face, threadSubjectBean.getAuthorprofile().getAvatar(), R.drawable.def_face);
        holder.setText(R.id.item_community_follow_username, threadSubjectBean.getAuthor());
        holder.setText(R.id.item_community_follow_forumname, isShowFroumNmae ? threadSubjectBean.getForumname() : threadSubjectBean.getShowtypename());
        holder.setText(R.id.item_community_follow_comment_num, threadSubjectBean.getReplies() == null ? "" : threadSubjectBean.getReplies());
        holder.setText(R.id.item_community_follow_time, DataUtils.conversionTime(threadSubjectBean.getDbdateline()));


        holder.setText(R.id.item_community_follow_flower, threadSubjectBean.getFlowers());
        holder.setVisible(R.id.item_community_follow_flower, DataTools.getInteger(threadSubjectBean.getFlowers()) > 0);

        setCommentText(holder, threadSubjectBean, smileyBeanList);


        holder.setVisible(R.id.videoPlayer_layout, threadSubjectBean.getVideos() != null);
        holder.setVisible(R.id.item_community_follow_img_layout, !(SetCommonManger.isThreadStreamlineMode() || threadSubjectBean.getAttachments() == null || threadSubjectBean.getAttachments().isEmpty()));
        holder.setVisible(R.id.item_community_follow_user_layout, !TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER));
        holder.setVisible(R.id.item_community_follow_time_layout, !TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER));


        holder.setVisible(R.id.item_community_follow_comment, !(TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER) || SetCommonManger.isThreadStreamlineMode() || threadSubjectBean.getPosts() == null || threadSubjectBean.getPosts().isEmpty()));


        holder.setVisible(R.id.item_community_follow_adv_text, TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER));//TODO 广告文字


        ImageView item_community_follow_adv_img = holder.getView(R.id.item_community_follow_adv_img);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) item_community_follow_adv_img.getLayoutParams();
        LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) item_community_follow_title.getLayoutParams();
        holder.setVisible(R.id.topic_content_layout, View.GONE);
        holder.setVisible(R.id.item_community_follow_title, View.VISIBLE);
        if (TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER)) {//TODO 广告
            if (threadSubjectBean.getAttachments() == null || threadSubjectBean.getAttachments().isEmpty()) {

            } else {
                holder.setVisible(R.id.item_community_follow_img_layout, true);
                holder.setImageUrl(R.id.item_community_follow_adv_img, threadSubjectBean.getAttachments().get(0), R.drawable.post_def);
            }
            holder.setVisible(R.id.item_community_follow_gridview, false);
            holder.setVisible(R.id.item_community_follow_adv_img, true);
            tvParams.setMargins(0, 0, 0, 0);
//            layoutParams.width = imgLayoutWidth;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 0, 0);
            item_community_follow_adv_img.setLayoutParams(layoutParams);
            AdvertStatisticsManger.getInstance().executeCode(mContext, threadSubjectBean.getPvtrackcode());

            if (TextUtils.equals(threadSubjectBean.getApptemplatetype(), Marks.THREAD_LIST_TYPE_TOPIC)) {
                holder.setVisible(R.id.topic_content_layout, View.VISIBLE);
                holder.setText(R.id.topic_desc, threadSubjectBean.getName());
                holder.setText(R.id.topic_views, "浏览" + threadSubjectBean.getViews());
                holder.setText(R.id.topic_users, "参与" + threadSubjectBean.getUsers());
                holder.setVisible(R.id.item_community_follow_adv_text, false);
                holder.setVisible(R.id.item_community_follow_title, false);

            } else {

            }

        } else {

            int marginleft = DensityUtil.dip2px(35);
            tvParams.setMargins(marginleft, 0, 0, 0);

            if (SetCommonManger.isThreadStreamlineMode()) {//TODO 无图模式
            } else {//TODO 有图模式

                if (threadSubjectBean.getVideos() != null) {//视频
                    holder.setImageUrl(R.id.videoPlayerView_cover, threadSubjectBean.getVideos().getThumbnail(), R.drawable.post_def);
                    holder.setVisible(R.id.item_community_follow_img_layout, true);
                    holder.setVisible(R.id.item_community_follow_adv_img, false);
                } else if (threadSubjectBean.getAttachments() == null || threadSubjectBean.getAttachments().isEmpty()) {//TODO 没有图片
                    holder.setVisible(R.id.item_community_follow_gridview, false);
                    holder.setVisible(R.id.item_community_follow_adv_img, false);
                } else {//TODO 有图
                    if (threadSubjectBean.getAttachments().size() == 1) {
                        holder.setVisible(R.id.item_community_follow_gridview, false);
                        holder.setVisible(R.id.item_community_follow_adv_img, true);

                        holder.setImageUrl(R.id.item_community_follow_adv_img, threadSubjectBean.getAttachments().get(0), R.drawable.post_def);


                        layoutParams.width = imgLayoutWidth / 3 * 2;

                        layoutParams.setMargins(marginleft, 0, 0, 0);
                        item_community_follow_adv_img.setLayoutParams(layoutParams);
                    } else {

                        holder.setVisible(R.id.item_community_follow_gridview, true);
                        holder.setVisible(R.id.item_community_follow_adv_img, false);

                        final NoScrollGridView gridView = holder.getView(R.id.item_community_follow_gridview);
                        CommonAdapter<String> adapter = new CommonAdapter<String>(mContext, threadSubjectBean.getAttachments(), R.layout.gridview_zhibo_tupian) {
                            @Override
                            public void convert(ViewHolder holder, String url) {
                                holder.setImageUrl(R.id.zhibo_gridview_img, url, R.drawable.post_def);
                            }

                            @Override
                            public int getCount() {
                                int max = is_feed ? 9 : 3;
                                return threadSubjectBean.getAttachments().size() > max ? max : threadSubjectBean.getAttachments().size();
                            }
                        };
                        gridView.setAdapter(adapter);
                        gridView.setClickable(false);
                        gridView.setPressed(false);
                        gridView.setEnabled(false);
                    }


                }


            }
        }

        item_community_follow_adv_img.setLayoutParams(layoutParams);


    }

    /**
     * 设置评论
     *
     * @param holder
     * @param threadSubjectBean
     */
    private void setCommentText(ViewHolder holder, ThreadSubjectBean threadSubjectBean, List<SmileyBean> smileyBeanList) {
        if (threadSubjectBean.getPosts() == null || threadSubjectBean.getPosts().isEmpty()) {
        } else {
            ThreadSubjectBean.PostsBean replys = threadSubjectBean.getPosts().get(0);
            List<SegmentedStringMode> modeList = new ArrayList<>();
            SegmentedStringMode modeAuthor = new SegmentedStringMode(replys.getAuthor() + ": ", R.dimen.zb_list_comment_size, R.color.grey, null);
            modeList.add(modeAuthor);

            SegmentedStringMode modeContent = new SegmentedStringMode(replys.getMessage(), R.dimen.zb_list_comment_size, R.color.app_body_black, null);
            modeList.add(modeContent);
            holder.setText(R.id.item_community_follow_comment, DataUtils.getSegmentedDisplaySs(modeAuthor, modeContent, smileyBeanList));

        }
    }

    /**
     * 设置正文内容 + 热帖标识图标
     *
     * @param item_community_follow_title
     * @param threadSubjectBean
     */
    private void setContentText(TextView item_community_follow_title, ThreadSubjectBean threadSubjectBean, boolean isShowHeat, boolean is_feed) {

        if (TextUtils.equals(threadSubjectBean.getType(), Marks.THREAD_LIST_TYPE_ADVER) || is_feed) {//TODO 广告 直播
            item_community_follow_title.setText(threadSubjectBean.getSubject());
        } else {
            int showIconNum = 0;
            StringBuffer markSb = new StringBuffer();
            String mark = "icon";
            String spacing = " ";
            int markSpacingLenght = mark.length() + spacing.length();
            ImageSpan heatlevel_span = null, pushedaid_span = null, digest_span = null;

            if (isShowHeat) {//只在我的帖子列表，跟他人的帖子列表展示火帖标识
                if (!TextUtils.equals(threadSubjectBean.getHeatlevel(), "0")) {
                    Bitmap heatlevel_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.heatlevel);
                    heatlevel_span = new CenterAlignImageSpan(mContext, heatlevel_btm);
                    markSb.append(mark);
                    markSb.append(spacing);
                }
            }
            if (!TextUtils.equals(threadSubjectBean.getPushedaid(), "0")) {
                Bitmap pushedaid_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pushedaid);
                pushedaid_span = new CenterAlignImageSpan(mContext, pushedaid_btm);
                markSb.append(mark);
                markSb.append(spacing);
            }
            if (!TextUtils.equals(threadSubjectBean.getDigest(), "0")) {
                Bitmap digest_btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.digest);
                digest_span = new CenterAlignImageSpan(mContext, digest_btm);
                markSb.append(mark);
                markSb.append(spacing);
            }

            SpannableString spanString = new SpannableString(markSb.toString());
            if (isShowHeat) {//读帖不显示，其他显示
                if (!TextUtils.equals(threadSubjectBean.getHeatlevel(), "0")) {
                    spanString.setSpan(heatlevel_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showIconNum++;
                }
            }
            if (!TextUtils.equals(threadSubjectBean.getPushedaid(), "0")) {
                spanString.setSpan(pushedaid_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                showIconNum++;
            }
            if (!TextUtils.equals(threadSubjectBean.getDigest(), "0")) {
                spanString.setSpan(digest_span, showIconNum * markSpacingLenght, (showIconNum + 1) * markSpacingLenght - spacing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                showIconNum++;
            }
            item_community_follow_title.setText(spanString);
            item_community_follow_title.append(threadSubjectBean.getSubject());
        }
    }

}
