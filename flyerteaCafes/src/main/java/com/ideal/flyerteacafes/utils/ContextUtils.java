package com.ideal.flyerteacafes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;

public class ContextUtils {

    private static ContextUtils utils;
    private Context context;

    private ContextUtils() {
        this.context = FlyerApplication.getContext();
    }

    public static ContextUtils getInstance() {
        if (utils == null)
            utils = new ContextUtils();
        return utils;
    }

    public ImageGetter imgGetter = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {

            boolean bol = true;
            int index = source.indexOf("smiley");
            Drawable drawable = null;
            try {
                // url = new URL(source);
                // drawable = Drawable.createFromStream(url.openStream(), "");
                if (DataUtils.isPictureMode(context)) {
                    if (index != -1)
                        bol = false;
                    Bitmap bm = BitmapTools.loadImageFromUrl(source, bol);
                    drawable = new BitmapDrawable(bm);
                } else {
                    drawable = context.getResources().getDrawable(R.drawable.icon_def);
                }
            } catch (Exception e) {
                return null;
            }
            if (drawable != null) {
                if (index != -1) {
                    // drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    // drawable.getIntrinsicHeight());
                    int width = 80;
                    int height = 80;
                    if (SharedPreferencesString.getInstances().getScale() != 0) {
                        height = (int) (SharedPreferencesString.getInstances().getScale() * height);
                        width = (int) (SharedPreferencesString.getInstances().getScale() * width);
                    }
                    drawable.setBounds(0, 0, width, height);
                } else {
                    int width = 1000;
                    int height = 400;
                    if (SharedPreferencesString.getInstances().getScale() != 0) {
                        height = (int) (SharedPreferencesString.getInstances().getScale() * height);
                        width = (int) (SharedPreferencesString.getInstances().getScale() * width);
                    }
                    drawable.setBounds(0, 0, width, height);
                }
            }
            return drawable;
        }
    };

}
