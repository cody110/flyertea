package com.ideal.flyerteacafes.ui.activity.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by fly on 2016/11/23.
 */

public interface IBase {

    /**
     * 去登陆
     */
    void toLogin();

    void endActivity();

    void jumpActivity(Intent intent);

    void jumpActivity(Class<? extends Activity> activity, Bundle bundle);

    void jumpActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) ;

    void jumpActivitySetResult(Class<? extends Activity> activity, Bundle bundle) ;

    void jumpActivitySetResult(Bundle bundle) ;



}
