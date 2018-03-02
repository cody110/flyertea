package com.ideal.flyerteacafes.caff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ideal.flyerteacafes.utils.FinalUtils;

import de.greenrobot.event.EventBus;

/**
 * 监听日期变化的广播
 *
 * @author fly
 */
public class DataChange extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        /**
         * 日期变化了,重置签到状态
         */
        if (arg1.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
            EventBus.getDefault().post(FinalUtils.ISSIGNIN);

        }
    }

}
