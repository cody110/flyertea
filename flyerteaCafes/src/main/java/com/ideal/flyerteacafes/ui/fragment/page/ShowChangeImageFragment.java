package com.ideal.flyerteacafes.ui.fragment.page;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.ui.controls.ZoomableImageView;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.FileUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

public class ShowChangeImageFragment extends Fragment {

    public static final int fNoSetType = -1, fPostHtml = 0, fXiangce = 1;

    @ViewInject(R.id.show_change_imageindex_textview)
    TextView imageTextView;
    @ViewInject(R.id.show_change_imageview_fragment)
    private ZoomableImageView imageView;

    private String url;
    private int index;
    private int length;
    public static int type = fNoSetType;
    private Bitmap bitmap;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            bitmap = (Bitmap) msg.obj;
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
        }

        ;
    };

    public ShowChangeImageFragment(String url, int index, int length) {
        this.url = url;
        this.index = index;
        this.length = length;
    }

    private String xiangceRootPaht;

    public void setXiangceRootPath(String rootPath) {
        this.xiangceRootPaht = rootPath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_show_change_image,
                container, false);
        x.view().inject(this, view);

//		imageTextView.setText(index + "/" + length);

//        downLoadImage();
        return view;
    }

    public void downLoadImage(){
        String path = "";

        if (type == fNoSetType) {
            return ;
        } else if (type == fPostHtml) {
            path = getImagePath(url);
        } else if (type == fXiangce) {
            path = xiangceRootPaht + url;
        }

        if (FileUtil.fileIsExists(path)) {
            bitmap = BitmapTools.getLoacalBitmap(path);
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
        } else {
            Runnable able = new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapTools.loadImageFromUrl(url);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (bitmap!=null) {
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }
                }
            };
            new Thread(able).start();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
    }

    /**
     * 获取图片的本地存储路径。
     *
     * @param imageUrl 图片的URL地址。
     * @return 图片的本地存储路径。
     */
    public String getImagePath(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        String imageName = imageUrl.substring(lastSlashIndex + 1);
        String imageDir = CacheFileManger.getCacheImagePath();
        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = imageDir + "/" + imageName;
        return imagePath;
    }

}
