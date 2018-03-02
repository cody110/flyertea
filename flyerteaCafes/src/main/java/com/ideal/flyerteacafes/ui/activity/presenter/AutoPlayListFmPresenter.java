package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import java.util.List;

/**
 * Created by fly on 2018/2/3.
 */

public abstract class AutoPlayListFmPresenter extends PullRefreshPresenter<ThreadVideoItem> {


    VideoPlayerManager<MetaData> mVideoPlayerManager;
    List<SmileyBean> smileyBeanList;

    @Override
    public void init(Activity activity) {
        smileyBeanList = BaseHelper.getInstance().getListAll(SmileyBean.class);
        super.init(activity);
    }


    /**
     * 必须调用
     *
     * @param mVideoPlayerManager
     */
    public void setVideoPlayerManager(VideoPlayerManager<MetaData> mVideoPlayerManager) {
        this.mVideoPlayerManager = mVideoPlayerManager;
    }


    protected class VideoListDataHandlerCallback extends ListDataHandlerCallback {

        public VideoListDataHandlerCallback() {
            this(ListDataCallback.LIST_KEY_FORUM_THREADLIST, ThreadSubjectBean.class);
        }

        public VideoListDataHandlerCallback(String listKey, Class tClass) {
            super(listKey, tClass);
        }

        @Override
        public void flySuccess(ListDataBean response) {
            if (!isViewAttached()) return;
            if (response.getDataList() != null) {
                int oldSize = datas.size();
                if (page == 1) {
                    datas.clear();
                }

                if (!DataUtils.isEmpty(response.getDataList())) {
                    for (int i = 0; i < response.getDataList().size(); i++) {
                        ThreadSubjectBean bean = (ThreadSubjectBean) response.getDataList().get(i);
                        ThreadVideoItem item = new ThreadVideoItem(context, mVideoPlayerManager, bean, smileyBeanList);
                        datas.add(item);
                    }
                }

                ver = response.getVer();
                getView().callbackData(datas);
                if (page == 1)
                    getView().headerRefreshSetListviewScrollLocation();
                if (page > 1) {
                    if (oldSize < datas.size()) {
                        getView().footerRefreshSetListviewScrollLocation(oldSize);
                    } else {
                        page--;
                        hasNext = false;
                        getView().notMoreData();
                    }
                }
            }
        }
    }


}
