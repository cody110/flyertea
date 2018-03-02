package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PhotoPagerAdapter;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.AcdseeFragment;
import com.ideal.flyerteacafes.ui.popupwindow.SavaPicturePopupWindow;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.SmartUtil;

import org.xutils.view.annotation.ContentView;

import java.io.File;
import java.util.ArrayList;

/**
 * 图片浏览页面
 *
 * @author fly
 */
@ContentView(R.layout.activity_picturebrowse)
public class PictureBrowseActivity extends BaseActivity implements AcdseeFragment.vpListener,PhotoPagerAdapter.imageClick {

    TextView showIndexText;

    private int allLength;
    private int index;

    private ArrayList<String> urlList;
    private SavaPicturePopupWindow popupWindow;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturebrowse);
        showIndexText = V.f(this, R.id.picturebrowse_text);
        AcdseeFragment acdseeFragment = (AcdseeFragment) getSupportFragmentManager().findFragmentById(R.id.picturebrowse_fragment);
        int index = getIntent().getIntExtra("pos", 0);
        acdseeFragment.setvpListener(this);
        urlList = (ArrayList<String>) getIntent().getSerializableExtra("urlList");
        allLength = urlList.size();
        acdseeFragment.setUrlDatas(urlList, index);
        acdseeFragment.setAdapterImageClickI(this);
    }

    @Override
    public void onPageSelected(int index) {
        this.index=index;
        showIndexText.setText((index + 1) + "/" + allLength);
    }

    @Override
    public void isHideTopAndBottom(boolean bol) {
        finish();

    }

    @Override
    public void longClick() {
        if (popupWindow == null) {
            popupWindow = new SavaPicturePopupWindow(PictureBrowseActivity.this, handler);
        }
        popupWindow.showAtLocation(showIndexText, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    save();
                }
            }).start();
        }
    };
    public void save(){
        String url=urlList.get(index);
        final Bitmap bitmap = BitmapTools.loadImageFromUrl(url);
        if(bitmap==null){
            TaskUtil.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("图片下载失败！");
                }
            });
        }else{
            String name=url.substring(url.lastIndexOf("/"), url.length());
            if(name.endsWith("!k")){
                name=name.replace("!k","");
            }


            final File file=new File(CacheFileManger.getSavePath()+name);
            final boolean bol= BitmapTools.saveJPGE_After(bitmap,file);
            final String finalName = name;
            handler.post(new Runnable() {

                @Override
                public void run() {
                    if (bol) {
                        Toast.makeText(PictureBrowseActivity.this, "保存成功！\n"+CacheFileManger.getSavePath()+ finalName, 8000).show();
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(file);
                        intent.setData(uri);
                        FlyerApplication.getContext().sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
                    } else {
                        SmartUtil.BToast(PictureBrowseActivity.this, "保存失败！");
                    }
                    if (bitmap != null)
                        bitmap.recycle();
                }
            });

        }
    }

}
