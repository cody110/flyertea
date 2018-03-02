package com.ideal.flyerteacafes.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.model.entity.HotelBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 酒店详情
 *
 * @author fly
 */
@ContentView(R.layout.activity_hotel_details)
public class HotelDetailsActivity extends BaseActivity {

    @ViewInject(R.id.include_left_title_text)
    private TextView titleView;
    @ViewInject(R.id.include_left_title_back_layout)
    private View backBtn;
    @ViewInject(R.id.hotel_details_info)
    private TextView infoView;
    @ViewInject(R.id.hotel_details_app_info)
    private TextView appInfoView;
    @ViewInject(R.id.hotel_details_icon_img1)
    private ImageView iconImg1;
    @ViewInject(R.id.hotel_details_icon_img2)
    private ImageView iconImg2;

    private HotelBean hotelBean;

    private int id;

    private boolean flag = true;//未下载为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MobclickAgent.onEvent(this, FinalUtils.EventId.hotel_reservation_2);//友盟统计
        hotelBean = (HotelBean) getIntent().getSerializableExtra("bean");
        id = getIntent().getIntExtra("id", 0);
        String name = hotelBean.getHotel_name();
        if (!DataUtils.isEmpty(name))
            titleView.setText(name);
        if (!DataUtils.isEmpty(hotelBean.getInfo()))
            infoView.setText(hotelBean.getInfo());
        if (!DataUtils.isEmpty(hotelBean.getApp_info()))
            appInfoView.setText(hotelBean.getApp_info());
        if (DataUtils.isPictureMode(this)) {
            DataUtils.downloadPicture(iconImg1,hotelBean.getHotel_icon_url(),R.drawable.icon_def);
            DataUtils.downloadPicture(iconImg2,hotelBean.getHotel_icon_url(),R.drawable.icon_def);
        }
    }

    @Event({R.id.include_left_title_back_layout,
            R.id.hote_details_website_btn, R.id.hote_details_telephone_btn,
            R.id.hote_details_app_download_btn})
    public void onClickAction(View v) {
        switch (v.getId()) {
            case R.id.include_left_title_back_layout:
                finish();
                break;
            case R.id.hote_details_website_btn:
                if (hotelBean == null)
                    return;
                Intent intentWeb = new Intent(HotelDetailsActivity.this,
                        TbsWebActivity.class);
                intentWeb.putExtra("url", hotelBean.getWeb_site());
                startActivity(intentWeb);
                MobclickAgent.onEvent(this, FinalUtils.EventId.hotel_reservation_3);//友盟统计
                break;

            case R.id.hote_details_telephone_btn:
                if (hotelBean == null)
                    return;
                // 拨打客服电话
                String inputStr = hotelBean.getTelephone();
                IntentTools.intentPhone(HotelDetailsActivity.this, inputStr);
                MobclickAgent.onEvent(this, FinalUtils.EventId.hotel_reservation_3);//友盟统计
                break;

            case R.id.hote_details_app_download_btn:
                if (hotelBean == null)
                    return;
                if (!DataUtils.isEmpty(hotelBean.getApp_web_site())) {
                    if (flag) {
                        flag = false;
                        downLoadApk(hotelBean.getApp_web_site());
                    } else {
                        BToast("正在下载中...");
                    }
                }
                break;
        }
    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(final String path) {
        initNotification();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(path);
                    sleep(3000);
                    installApk(file);
                    flag = true;
                } catch (Exception e) {
                    flag = true;
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    Notification notification;
    NotificationManager nm;

    @SuppressWarnings("deprecation")
    private void initNotification() {
        // 建立notification,前面有学习过，不解释了，不懂看搜索以前的文章
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.download4, "开始下载" + hotelBean.getHotel_name() + "啦",
                System.currentTimeMillis());
        notification.contentView = new RemoteViews(getPackageName(),
                R.layout.notification_download);
        // 使用notification.xml文件作VIEW
        notification.contentView.setProgressBar(R.id.notification_download_pb,
                100, 0, false);
        notification.contentView.setTextViewText(
                R.id.notification_download_remind, "正在下载" + hotelBean.getHotel_name());
        // 设置进度条，最大值 为100,当前值为0，最后一个参数为true时显示条纹
        // （就是在Android Market下载软件，点击下载但还没获取到目标大小时的状态）
        PendingIntent contentIntent = PendingIntent.getActivity(this, id,
                new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        nm.notify(id, notification);
    }

    int downloadCount = 0;

    /*
     * 从服务器下载apk
     */
    public File getFileFromServer(String path) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            int totalength = conn.getContentLength() / 100;
            // 获取到文件的大小
            InputStream is = conn.getInputStream();
//			File file = new File(Environment.getExternalStorageDirectory(),"flyertea_down.apk");
            File file = new File(CacheFileManger.getCacheImagePath() + "/" + "flyertea_down_" + id + ".apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int temp = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                temp += len;
                // 获取当前下载量
                if (temp / totalength > downloadCount) {
                    downloadCount = temp / totalength;
                    Message msg = Message.obtain();
                    msg.what = 100;
                    msg.arg1 = downloadCount;
                    handler.sendMessage(msg);
                }

            }

            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            notification.contentView.setProgressBar(
                    R.id.notification_download_pb, 100, msg.arg1,
                    false);
            notification.contentView.setTextViewText(
                    R.id.notification_download_pb_text, msg.arg1 + "%");
            if (msg.arg1 == 100)
                notification.contentView.setTextViewText(R.id.notification_download_remind, "下载" + hotelBean.getHotel_name() + "完成");
            nm.notify(id, notification);
        }

        ;
    };

}
