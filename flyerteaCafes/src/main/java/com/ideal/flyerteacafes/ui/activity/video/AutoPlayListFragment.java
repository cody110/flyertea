package com.ideal.flyerteacafes.ui.activity.video;

import android.widget.AbsListView;

import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.ui.activity.presenter.AutoPlayListFmPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;

import java.util.List;

/**
 * Created by fly on 2018/2/3.
 */

public abstract class AutoPlayListFragment extends NewPullRefreshFragment<ThreadVideoItem> {


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


        }
    });

    AbsListView.OnScrollListener onScrollListener;


    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;


    @Override
    public void initVariables() {
        super.initVariables();
        if (mPresenter instanceof AutoPlayListFmPresenter) {
            ((AutoPlayListFmPresenter) mPresenter).setVideoPlayerManager(mVideoPlayerManager);
        }
    }


    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public void initViews() {
        super.initViews();

    }


    private void initAutoPlay() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                mItemsPositionGetter = new HeaderListViewItemPositionGetter(listView);
                mListItemVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), datas);


                if (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount() != -1 && adapter != null && adapter.getCount() > 0) {
                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            listView.getFirstVisiblePosition() - listView.getHeaderViewsCount(),
                            listView.getLastVisiblePosition() - listView.getHeaderViewsCount());
                }
                /**
                 * We need to set onScrollListener after we create {@link #mItemsPositionGetter}
                 * because {@link android.widget.AbsListView.OnScrollListener#onScroll(AbsListView, int, int, int)}
                 * is called immediately and we will get {@link NullPointerException}
                 */
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (onScrollListener != null)
                            onScrollListener.onScrollStateChanged(view, scrollState);
                        mScrollState = scrollState;

//                        LogFly.e("headerCount:" + listView.getHeaderViewsCount() + "adapterCount:" + adapter.getCount() + "getFirstVisiblePosition:" + (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount()) + "getLastVisiblePosition:" + (listView.getLastVisiblePosition() - listView.getHeaderViewsCount()));

                        if (scrollState == SCROLL_STATE_IDLE && !datas.isEmpty()) {
                            if (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount() != -1) {
                                mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition() - listView.getHeaderViewsCount(), view.getLastVisiblePosition() - listView.getHeaderViewsCount());
                            }
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (onScrollListener != null)
                            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                        if (!datas.isEmpty()) {
                            // on each scroll event we need to call onScroll for mListItemVisibilityCalculator
                            // in order to recalculate the items visibility
                            if (firstVisibleItem - listView.getHeaderViewsCount() != -1) {

                                if (firstVisibleItem - listView.getHeaderViewsCount() == 0) {
                                    mListItemVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter, view.getFirstVisiblePosition() - listView.getHeaderViewsCount(), view.getLastVisiblePosition() - listView.getHeaderViewsCount());
                                }
                                mListItemVisibilityCalculator.onScroll(mItemsPositionGetter, firstVisibleItem - listView.getHeaderViewsCount(), visibleItemCount, mScrollState);
                            }
                        }
                    }
                });
            }
        });

    }

    @Override
    public void callbackData(List<ThreadVideoItem> datas) {
        this.datas = datas;
        if (adapter == null) {
            adapter = createAdapter(datas);
            listView.setAdapter(adapter);

            initAutoPlay();

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!DataUtils.isEmpty(datas)) {
            // need to call this method from list view handler in order to have list filled previously

            listView.post(new Runnable() {
                @Override
                public void run() {
                    if (mListItemVisibilityCalculator != null) {
                        if (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount() != -1 && adapter != null && adapter.getCount() > 0) {
                            mListItemVisibilityCalculator.onScrollStateIdle(
                                    mItemsPositionGetter,
                                    listView.getFirstVisiblePosition() - listView.getHeaderViewsCount(),
                                    listView.getLastVisiblePosition() - listView.getHeaderViewsCount());

                        }
                    }

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoPlayerManager.resetMediaPlayer();
    }


}
