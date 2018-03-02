package com.ideal.flyerteacafes.caff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.widget.Toast;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;

import de.greenrobot.event.EventBus;

/**
 * wify状态监听广播
 *
 * @author fly
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:// WIFI网卡不可用（1）
                    FlyerApplication.wifiIsConnected = false;
                    break;
                case WifiManager.WIFI_STATE_ENABLED: // WIFI网卡可用（3）
                    break;
            }
        }

        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
                if (isConnected) {
                    FlyerApplication.wifiIsConnected = true;
                    EventBus.getDefault().post(FinalUtils.ADVERT);// wifi模式下广告加载图片
                } else {
                    FlyerApplication.wifiIsConnected = false;
                }
            }

        }

        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo gprs = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo info = intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {

                if (NetworkInfo.State.CONNECTED == info.getState()) {
//                    if (info.getType() == 1) {
//                        Toast.makeText(context,
//                                context.getString(R.string.wifi_remind),
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(context,
//                                context.getString(R.string.yidong_remind),
//                                Toast.LENGTH_LONG).show();
//                    }
                } else {
                    if (gprs != null && wifi != null)
                        if (!wifi.isConnected() && !gprs.isConnected())
                            Toast.makeText(context, context.getString(R.string.network_no_connection), Toast.LENGTH_LONG).show();
                }
            }

        }

    }

}
