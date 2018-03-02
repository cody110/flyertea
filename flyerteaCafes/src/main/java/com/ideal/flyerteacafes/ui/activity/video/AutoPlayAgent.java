package com.ideal.flyerteacafes.ui.activity.video;

import android.widget.AbsListView;
import android.widget.ListView;

import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;

import java.util.List;

/**
 * Created by fly on 2018/2/2.
 * listview滚动到指定位置播放视频，代理类
 */

public class AutoPlayAgent {


    private List<ThreadVideoItem> mList;

    /**
     * 只有一个（最可见的）视图应该是活动的（和播放）。
     * 计算我们使用的视图的可见性
     */
    private ListItemsVisibilityCalculator mListItemVisibilityCalculator;


    /**
     * ItemsPositionGetter is used by {@link ListItemsVisibilityCalculator} for getting information about
     * items position in the ListView
     * 获取在list里的位置
     */
    private ItemsPositionGetter mItemsPositionGetter;


    /**
     * onPlayerItemChanged
     */
    public final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
            LogFly.e("ThreadVideoItem:"+metaData.toString());
        }
    });

    AbsListView.OnScrollListener onScrollListener;


    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

    private ListView mListView;


    public void init(ListView mListView, List<ThreadVideoItem> datas) {
        this.mListView = mListView;
        this.mList = datas;

        mListItemVisibilityCalculator =
                new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);
        mItemsPositionGetter = new ListViewItemPositionGetter(mListView);
        /**
         * We need to set onScrollListener after we create {@link #mItemsPositionGetter}
         * because {@link android.widget.AbsListView.OnScrollListener#onScroll(AbsListView, int, int, int)}
         * is called immediately and we will get {@link NullPointerException}
         */
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(view, scrollState);
                mScrollState = scrollState;
                if (scrollState == SCROLL_STATE_IDLE && !mList.isEmpty()) {
                    mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition(), view.getLastVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null)
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                if (!mList.isEmpty()) {
                    // on each scroll event we need to call onScroll for mListItemVisibilityCalculator
                    // in order to recalculate the items visibility
                    mListItemVisibilityCalculator.onScroll(mItemsPositionGetter, firstVisibleItem, visibleItemCount, mScrollState);
                }
            }
        });
    }


    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    public void onResume() {
        if (!DataUtils.isEmpty(mList)) {
            // need to call this method from list view handler in order to have list filled previously

            mListView.post(new Runnable() {
                @Override
                public void run() {

                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mListView.getFirstVisiblePosition(),
                            mListView.getLastVisiblePosition());

                }
            });
        }
    }

    public void onStop() {
        // we have to stop any playback in onStop
        mVideoPlayerManager.resetMediaPlayer();
    }

}
