package com.ideal.flyerteacafes.utils.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpTools {


    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */

    public static boolean isNetworkAvailable(Context activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    /**
//     * 判断是否有网络连接
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager
//                    .getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//
//
//    /**
//     * 判断wifi是否可用
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isWifiConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (mWiFiNetworkInfo != null) {
//                return mWiFiNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断MOBILE网络是否可用
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isMobileConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mMobileNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            if (mMobileNetworkInfo != null) {
//                return mMobileNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//    /**
//     * @author sky Email vipa1888@163.com QQ:840950105 获取当前的网络状态 -1：没有网络
//     * 1：WIFI网络2：wap网络3：net网络
//     * @param context
//     * @return
//     */
//    private static login_bg int WIFI = 1, CMWAP = 2, CMNET = 3;
//
//    public static int getAPNType(Context context) {
//        int netType = -1;
//        ConnectivityManager connMgr = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo == null) {
//            return netType;
//        }
//        int nType = networkInfo.getType();
//        if (nType == ConnectivityManager.TYPE_MOBILE) {
//            LogFly.e("networkInfo.getExtraInfo()",
//                    "networkInfo.getExtraInfo() is "
//                            + networkInfo.getExtraInfo());
//            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
//                netType = CMNET;
//            } else {
//                netType = CMWAP;
//            }
//        } else if (nType == ConnectivityManager.TYPE_WIFI) {
//            netType = WIFI;
//        }
//        return netType;
//    }

}
