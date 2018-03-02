package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AdvertAdapter;
import com.ideal.flyerteacafes.adapters.LandingAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 *
 * @author fly
 */
@ContentView(R.layout.activity_landing)
public class LandingActivity extends FragmentActivity {

    @ViewInject(R.id.landing_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.landing_diandian)
    private LinearLayout dian_layout;
    @ViewInject(R.id.landing_to_main)
    View landing_to_main;

    int[] res_ids = {R.drawable.landing_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageView[] imageViews = new ImageView[res_ids.length];
        for (int i = 0; i < res_ids.length; i++) {
            imageViews[i] = new ImageView(this);
            imageViews[i].setImageResource(res_ids[i]);
            imageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
        }

        viewPager.setAdapter(new AdvertAdapter(imageViews));

        final List<ImageView> dots = new ArrayList<ImageView>();
        for (int i = 0; i < res_ids.length; i++) {
            ImageView dian_img = new ImageView(this);
            dian_img.setImageResource(R.drawable.icon_dot_normal);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            int num = 30;
            float scale = SharedPreferencesString.getInstances(this).getFloatToKey("scale");
            num = (int) (num * scale);
            lp.setMargins(num, 40, num, 40);
            dian_layout.addView(dian_img, lp);
            dots.add(dian_img);
        }

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            private int oldPosition = 0;

            @Override
            public void onPageSelected(int arg0) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                if (oldPosition < dots.size())
                    dots.get(oldPosition).setImageResource(R.drawable.icon_dot_normal);
                if (oldPosition < dots.size())
                    dots.get(arg0).setImageResource(R.drawable.icon_dot_highlight);
                oldPosition = arg0;

                WidgetUtils.setVisible(landing_to_main, arg0 == imageViews.length - 1);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        dots.get(0).setImageResource(R.drawable.icon_dot_highlight);

    }

    @Event(R.id.landing_to_main)
    private void click(View view) {
//        if (SharedPreferencesString.getInstances().isFirstInstall()) {
//            startActivity(new Intent(this, BootVideoActivity.class));
//        } else {
            startActivity(new Intent(this, HomeActivity.class));
//        }
        finish();
    }


}
