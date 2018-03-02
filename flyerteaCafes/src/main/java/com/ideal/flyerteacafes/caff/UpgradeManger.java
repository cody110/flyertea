package com.ideal.flyerteacafes.caff;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.ideal.flyerteacafes.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpgradeManger {

    private Context context = FlyerApplication.getContext();
    private static final int id = 100;


    private static UpgradeManger instance;

    private UpgradeManger() {
    }

    public static UpgradeManger getInstance() {
        if (instance == null) {
            synchronized (UpgradeManger.class) {
                instance = new UpgradeManger();
            }
        }
        return instance;
    }

    /*
     * 从服务器中下载APK
     */
    public void downLoadApk(final String path) {
        initNotification();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(path);
                    sleep(3000);
                    installApk(file);
                } catch (Exception e) {
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
        context.startActivity(intent);
    }

    Notification notification;
    NotificationManager nm;

    @SuppressWarnings("deprecation")
    private void initNotification() {
        // 建立notification,前面有学习过，不解释了，不懂看搜索以前的文章
        nm = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.download4, "开始下载飞客茶馆啦",
                System.currentTimeMillis());
        notification.contentView = new RemoteViews(context.getPackageName(),
                R.layout.notification_download);
        // 使用notification.xml文件作VIEW
        notification.contentView.setProgressBar(R.id.notification_download_pb,
                100, 0, false);
        notification.contentView.setTextViewText(
                R.id.notification_download_remind, "正在下载飞客茶馆");
        // 设置进度条，最大值 为100,当前值为0，最后一个参数为true时显示条纹
        // （就是在Android Market下载软件，点击下载但还没获取到目标大小时的状态）
        PendingIntent contentIntent = PendingIntent.getActivity(context, id,
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
            // File file = new File(Environment.getExternalStorageDirectory(),
            // "flyertea_down.apk");
            File file = new File(CacheFileManger.getFlyPath()
                    + "/"
                    + "flyertea_down_"
                    + id
                    + ".apk");
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
                    R.id.notification_download_pb, 100, msg.arg1, false);
            notification.contentView.setTextViewText(
                    R.id.notification_download_pb_text, msg.arg1 + "%");
            if (msg.arg1 == 100)
                notification.contentView.setTextViewText(
                        R.id.notification_download_remind, "飞客茶馆下载完成");
            nm.notify(id, notification);
        }

        ;
    };

}
