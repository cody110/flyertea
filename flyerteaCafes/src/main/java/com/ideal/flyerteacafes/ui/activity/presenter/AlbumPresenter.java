package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;
import com.ideal.flyerteacafes.model.params.AlbumImgaeNumEvent;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.AlbumListActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.ui.activity.YulanTupianActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IAlbum;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/15.
 */

public class AlbumPresenter extends BasePresenter<IAlbum> implements Handler.Callback, Runnable {

    private Activity activity;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 手机上所有图片文件夹
     */
    private Map<String, ImageFloder> floderMap = new HashMap<>();

    /**
     * 所有选中的图片集
     */
    private List<String> mSelectImage = new ArrayList<>();

    /**
     * 当前显示的图片文件夹
     */
    private ImageFloder selectFloder;
    /**
     * 当前显示文件夹的图片集
     */
    List<AlbumItemInfo> albumItemInfoList = new ArrayList<>();

    Handler handler = new Handler(this);

    public static final String BUNDLE_NEED_SIZE = "need_size";//这一次还需要多少张
    public int maxPic;
    public static final String BUNDLE_FROM_TYPE = "from_type";
    public static final String BUNDLE_FROM_MAJOR_THREAD = "from_major_thread";
    public static final String BUNDLE_FROM_MAJOR_THREAD_FIRST = "from_major_thread_first";
    private String from_type;

    @Override
    public void init(Activity activity) {
        super.init(activity);
        this.activity = activity;
        maxPic = activity.getIntent().getIntExtra(BUNDLE_NEED_SIZE, 30);
        from_type = activity.getIntent().getStringExtra(BUNDLE_FROM_TYPE);
        getImagesFiles();
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
        getDialog().proDialogShow();
        new Thread(this).start();
    }

    /**
     * Handler
     *
     * @param message
     * @return
     */
    @Override
    public boolean handleMessage(Message message) {
        if (isViewAttached()) {
            getDialog().proDialogDissmiss();

            if (selectFloder == null && floderMap.size() > 0) {
                Collection<ImageFloder> floders = floderMap.values();
                selectFloder = floders.iterator().next();
            }

            selectFloder(selectFloder);
        }
        return false;
    }

    /**
     * 预览图片返回
     *
     * @param data
     */
    public void actionPreviewBack(Intent data) {
        int mark = data.getIntExtra("mark", 0);
        ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("mSelectedImage");
        mSelectImage.clear();
        mSelectImage.addAll(list);
        if (mark == YulanTupianActivity.fBack) {
            getView().actionSetAlbumAdapter(albumItemInfoList, mSelectImage);
        } else if (mark == YulanTupianActivity.fOk) {
            actionOk();
        }
        EventBus.getDefault().post(new AlbumImgaeNumEvent(mSelectImage.size()));
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
     * 预览
     */
    public void actionPreview() {
        if (mSelectImage.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mSelectedImage", (Serializable) mSelectImage);
            bundle.putSerializable("urlList", (Serializable) mSelectImage);
            bundle.putInt("pos", 1);
            bundle.putInt("activity", YulanTupianActivity.fFromPictureYulan);
            getBaseView().jumpActivityForResult(YulanTupianActivity.class, bundle, AlbumActivity.REQUEST_PREVIEW);
        }
    }

    /**
     * 完成
     */
    public void actionOk() {
        if (isViewAttached()) {
            getView().actionOk(mSelectImage);
        }
    }


    @Override
    public void run() {
        queryImages();
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

    /**
     * 通过ContentResolver查询图片，并进行分类处理
     */
    private void queryImages() {
        String firstImage = null;

        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = activity.getContentResolver();

        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DATE_ADDED},
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png", "image/bmp"},
                MediaStore.Images.Media.DATE_ADDED + " DESC");

        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));


            //时间
            String time = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            time += "000";

            File f = new File(path);
            if (f.exists()) {
                time = String.valueOf(f.lastModified());// 最后一次修改时间
            }

            // 拿到第一张图片的路径
            if (firstImage == null)
                firstImage = path;
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

                if (parentFile.list() == null) continue;
                int picSize = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".jpeg")
                                || filename.endsWith(".bmp"))
                            return true;
                        return false;
                    }
                }).length;

                imageFloder.setCount(picSize);
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
                imageFloder.getItem().put(date, item);
            }

            item.getImgList().add(path);
        }
        mCursor.close();
        mCursor = null;
        // 扫描完成，辅助的HashSet也就可以释放内存了
        mDirPaths = null;

        handler.sendEmptyMessage(0);

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

        Map<String, AlbumItemInfo> sortMap = new TreeMap<>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            str1 = str1.replace("-", "");
            str2 = str2.replace("-", "");

            /**
             * map 根据相差值排序
             */

            return DataTools.getInteger(str2) - DataTools.getInteger(str1);
        }
    }


    /**
     * 使用 Map按value进行排序
     *
     * @return
     */
    public Map<String, AlbumItemInfo> sortMapByValue(Map<String, AlbumItemInfo> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, AlbumItemInfo> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, AlbumItemInfo>> entryList = new ArrayList<>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, AlbumItemInfo>> iter = entryList.iterator();
        Map.Entry<String, AlbumItemInfo> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }


    class MapValueComparator implements Comparator<Map.Entry<String, AlbumItemInfo>> {

        @Override
        public int compare(Map.Entry<String, AlbumItemInfo> me1, Map.Entry<String, AlbumItemInfo> me2) {

            return DataTools.getInteger(me1.getValue().getDateline()) - DataTools.getInteger(me2.getValue().getDateline());
        }
    }

}
