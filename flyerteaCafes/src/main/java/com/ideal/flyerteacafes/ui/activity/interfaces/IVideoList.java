package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;

import java.util.List;

/**
 * Created by fly on 2018/2/6.
 */

public interface IVideoList {

    /**
     * 返回相册数据，设置adapter
     *
     * @param albumItemInfoList
     */
    void actionSetAlbumAdapter(List<AlbumItemInfo> albumItemInfoList, List<String> mSelectedImage);
}
