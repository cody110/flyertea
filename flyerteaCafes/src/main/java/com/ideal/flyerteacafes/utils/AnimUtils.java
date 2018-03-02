package com.ideal.flyerteacafes.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.RelativeLayout;

import com.ideal.flyerteacafes.ui.view.ViewWrapper;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;


/**
 * Created by fly on 2016/4/13.
 */
public class AnimUtils {


    public static void setWidth(View view, int width, int time) {
        ViewWrapper wrapper = new ViewWrapper(view);
        ObjectAnimator anim = ObjectAnimator.ofInt(wrapper, "width", width).setDuration(time);
        anim.start();
    }

    public static void setHeight(View view, int height, int time) {
        ViewWrapper wrapper = new ViewWrapper(view);
        ObjectAnimator anim = ObjectAnimator.ofInt(wrapper, "height", height).setDuration(time);
        anim.start();
    }


    /**
     * 自由落体
     *
     * @param view
     */
    public static void verticalRun(final View view, int loca, int tranY) {
        ValueAnimator animator = ValueAnimator.ofFloat(loca, tranY);
        animator.setTarget(view);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                view.setTranslationY((Float) animation.getAnimatedValue());
                view.layout(view.getLeft(),view.getTop(),view.getRight(), DataTools.getInteger(animation.getAnimatedValue()));
            }
        });
    }

}
