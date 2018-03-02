package com.ideal.flyerteacafes.utils;

import com.ideal.flyerteacafes.caff.SharedPreferencesString;

/**
 * Created by fly on 2017/5/22.
 */

public class DeviceUtils {

    public static int getWindowWidth() {
        return SharedPreferencesString.getInstances().getIntToKey("w_screen");
    }

    public static int getWindowHeight() {
        return SharedPreferencesString.getInstances().getIntToKey("h_screen");
    }

}
