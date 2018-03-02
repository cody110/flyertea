package com.ideal.flyerteacafes.utils.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class IntentTools {


    /**
     * 检查app是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 到应用宝的下载当前APP的页面
     *
     * @param context
     */
    public static final void toTencentDownloaderAPP(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        intent.setData(uri);
        //com.qihoo.appstore
        intent.setPackage("com.tencent.android.qqdownloader");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开手机应用市场下载app
     *
     * @param context
     */
    public static final void openAppStore(Context context) {
        String mAddress = "market://details?id=" + context.getPackageName();
        Intent marketIntent = new Intent("android.intent.action.VIEW");
        marketIntent.setData(Uri.parse(mAddress));
        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(marketIntent);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static final void intentPhone(Context context, String phone) {
        Intent phoneIntent = new Intent("android.intent.action.CALL",
                Uri.parse("tel:" + phone));
        // 启动
        context.startActivity(phoneIntent);
    }

}
