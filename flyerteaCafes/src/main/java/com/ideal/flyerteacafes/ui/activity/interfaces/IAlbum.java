package com.ideal.flyerteacafes.ui.activity.interfaces;

import android.os.Bundle;

import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;

import java.util.List;

/**
 * Created by fly on 2016/11/15.
 */

public interface IAlbum {

    /**
     * 返回相册数据，设置adapter
     *
     * @param albumItemInfoList
     */
    void actionSetAlbumAdapter(List<AlbumItemInfo> albumItemInfoList, List<String> mSelectedImage);

    void actionOk(List<String> mSelectImage);


}
