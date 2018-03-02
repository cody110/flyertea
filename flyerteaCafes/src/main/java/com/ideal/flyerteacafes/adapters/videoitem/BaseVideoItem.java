package com.ideal.flyerteacafes.adapters.videoitem;

import android.graphics.Rect;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.volokh.danylo.video_player_manager.manager.VideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.video_player_manager.utils.Logger;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * 基本视频项, 实现适配项和列表项
 */
public abstract class BaseVideoItem implements VideoItem, ListItem {


    private static final boolean SHOW_LOGS = false;
    private static final String TAG = BaseVideoItem.class.getName();

    /**
     * An object that is filled with values when {@link #getVisibilityPercents} method is called.
     * This object is local visible rect filled by {@link android.view.View#getLocalVisibleRect}
     */

    private final Rect mCurrentViewRect = new Rect();
    private final VideoPlayerManager<MetaData> mVideoPlayerManager;

    // 构造器, 输入视频播放管理器
    protected BaseVideoItem(VideoPlayerManager<MetaData> videoPlayerManager) {
        mVideoPlayerManager = videoPlayerManager;
    }


    /**
     * When this item becomes active we start playback on the video in this item
     */
    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        ViewHolder viewHolder = (ViewHolder) newActiveView.getTag();
        VideoPlayerView mPlayer = viewHolder.getView(R.id.videoPlayerView);
        playNewVideo(new CurrentItemMetaData(newActiveViewPosition, newActiveView), mPlayer, mVideoPlayerManager);
    }

    /**
     * When this item becomes inactive we stop playback on the video in this item.
     */
    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mVideoPlayerManager);
    }


    /**
     * 显示可视的百分比程度
     * This method calculates visibility percentage of currentView.
     * This method works correctly when currentView is smaller then it's enclosure.
     *
     * @param currentView - view which visibility should be calculated
     * @return currentView visibility percents
     */
    @Override
    public int getVisibilityPercents(View currentView) {
        if (SHOW_LOGS) Logger.v(TAG, ">> getVisibilityPercents currentView " + currentView);

        int percents = 100;

        if (currentView != null) {
            currentView.getLocalVisibleRect(mCurrentViewRect);
            if (SHOW_LOGS)
                Logger.v(TAG, "getVisibilityPercents mCurrentViewRect top " + mCurrentViewRect.top + ", left " + mCurrentViewRect.left + ", bottom " + mCurrentViewRect.bottom + ", right " + mCurrentViewRect.right);

            int height = currentView.getHeight();
            if (SHOW_LOGS) Logger.v(TAG, "getVisibilityPercents height " + height);

            if (viewIsPartiallyHiddenTop()) {
                // view is partially hidden behind the top edge
                percents = (height - mCurrentViewRect.top) * 100 / height;
            } else if (viewIsPartiallyHiddenBottom(height)) {
                percents = mCurrentViewRect.bottom * 100 / height;
            }

            setVisibilityPercentsText(currentView, percents);
            if (SHOW_LOGS) Logger.v(TAG, "<< getVisibilityPercents, percents " + percents);
        }

        return percents;
    }

    private void setVisibilityPercentsText(View currentView, int percents) {
//        if(SHOW_LOGS) Logger.v(TAG, "setVisibilityPercentsText percents " + percents);
//        VideoViewHolder videoViewHolder = (VideoViewHolder) currentView.getTag();
//        String percentsText = "Visibility percents: " + String.valueOf(percents);
//
//        videoViewHolder.mVisibilityPercents.setText(percentsText);
    }

    // 底部出现
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    // 顶部出现
    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }
}
