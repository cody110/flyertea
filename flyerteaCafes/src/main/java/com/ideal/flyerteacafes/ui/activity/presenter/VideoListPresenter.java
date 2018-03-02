package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.AlbumListActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IVideoList;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fly on 2018/2/6.
 */

public class VideoListPresenter extends BasePresenter<IVideoList> {

    private Activity activity;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 手机上所有图片文件夹
     */
    public Map<String, ImageFloder> floderMap = new HashMap<>();

    /**
     * 所有选中的图片集
     */
    public List<String> mSelectImage = new ArrayList<>();

    /**
     * 当前显示的图片文件夹
     */
    private ImageFloder selectFloder;
    /**
     * 当前显示文件夹的图片集
     */
    List<AlbumItemInfo> albumItemInfoList = new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        this.activity = activity;
        TaskUtil.postOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getImagesFiles();
            }
        });

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImagesFiles() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ToastUtils.showToast("暂无外部存储");
            return;
        }
        queryLoadMedia();
    }


    /**
     * 相册列表
     */
    public void actionAlbumList() {
        List<ImageFloder> imageFloderList = new ArrayList<>();
        Collection<ImageFloder> floders = floderMap.values();
        imageFloderList.addAll(floders);
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", (Serializable) imageFloderList);
        getBaseView().jumpActivityForResult(AlbumListActivity.class, bundle, AlbumActivity.REQUEST_ALBUMLIST);
    }


    /**
     * 选中指定文件夹
     */
    public void selectFloder(ImageFloder floder) {
        if (floder != null) {
            albumItemInfoList.clear();

            Map<String, AlbumItemInfo> resultMap = sortMapByKey(floder.getItem());
            albumItemInfoList.addAll(resultMap.values());

            getView().actionSetAlbumAdapter(albumItemInfoList, mSelectImage);
        } else {
            ToastUtils.showToast("相册还没有照片，多去拍两张美图吧");
        }
    }


    public void queryLoadMedia() {
        Cursor cursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                String time = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));//时间

                time += "000";
                File f = new File(path);
                if (f.exists()) {
                    time = String.valueOf(f.lastModified());// 最后一次修改时间
                }


                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();

                if (parentFile == null)
                    continue;
                String dirPath = parentFile.getAbsolutePath();
                ImageFloder imageFloder = floderMap.get(dirPath);
                // 利用一个HashSet防止多次扫描同一个文件jia

                if (imageFloder == null) {
                    mDirPaths.add(dirPath);
                    // 初始化imageFloder
                    imageFloder = new ImageFloder();
                    imageFloder.setDir(dirPath);
                    imageFloder.setFirstImagePath(path);
                    imageFloder.setType(ImageFloder.TYPE_VIDEO);

                    if (parentFile.list() == null) continue;
//                    int picSize = parentFile.list(new FilenameFilter() {
//                        @Override
//                        public boolean accept(File dir, String filename) {
//                            if (filename.endsWith(".jpg")
//                                    || filename.endsWith(".png")
//                                    || filename.endsWith(".jpeg")
//                                    || filename.endsWith(".bmp"))
//                                return true;
//                            return false;
//                        }
//                    }).length;
//
//                    imageFloder.setCount(picSize);
                    floderMap.put(dirPath, imageFloder);

                    if (parentFile.getPath().contains("DCIM/Camera")) {
                        selectFloder = imageFloder;
                    }
                }

                String date = DateUtil.getDateToString(time);

                AlbumItemInfo item = imageFloder.getItem().get(date);
                if (item == null) {
                    item = new AlbumItemInfo();
                    item.setDateline(time);
                    item.setType(ImageFloder.TYPE_VIDEO);
                    imageFloder.getItem().put(date, item);
                }

                item.getImgList().add(path);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            TaskUtil.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isViewAttached()) {
                        if (selectFloder == null && floderMap.size() > 0) {
                            Collection<ImageFloder> floders = floderMap.values();
                            selectFloder = floders.iterator().next();
                        }
                        selectFloder(selectFloder);
                    }
                }
            });
        }

    }


    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, AlbumItemInfo> sortMapByKey(Map<String, AlbumItemInfo> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, AlbumItemInfo> sortMap = new TreeMap<>(new AlbumPresenter.MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }


}
