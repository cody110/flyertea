package com.ideal.flyerteacafes.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AdvertAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fly on 2017/3/28.
 */

public class WriteMajorGuideDialog extends DialogFragment {

    @ViewInject(R.id.landing_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.landing_diandian)
    private LinearLayout dian_layout;
    @ViewInject(R.id.btn_ok)
    View btn_ok;


    public interface IWriteMajorGuide {
        void toWrite();
    }

    public IWriteMajorGuide iWriteMajorGuide;

    public void setIWriteMajorGuide(IWriteMajorGuide iWriteMajorGuide) {
        this.iWriteMajorGuide = iWriteMajorGuide;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_write_major_guide, container, false);
        x.view().inject(this, view);
        init();
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Event({R.id.btn_ok})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                dismiss();
                if (iWriteMajorGuide != null) {
                    iWriteMajorGuide.toWrite();
                }
                break;
        }
    }

    int[] res_ids = {R.drawable.guide_thread_1, R.drawable.guide_thread_2, R.drawable.guide_thread_3};

    private void init() {

        final ImageView[] imageViews = new ImageView[res_ids.length];
        for (int i = 0; i < res_ids.length; i++) {
            imageViews[i] = new ImageView(getActivity());
            imageViews[i].setImageResource(res_ids[i]);
            imageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
        }

        viewPager.setAdapter(new AdvertAdapter(imageViews));

        final List<ImageView> dots = new ArrayList<>();
        for (int i = 0; i < imageViews.length; i++) {
            ImageView dian_img = new ImageView(getActivity());
            dian_img.setImageResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int num = 30;
            float scale = SharedPreferencesString.getInstances(getActivity()).getFloatToKey("scale");
            num = (int) (num * scale);
            lp.setMargins(num, 40, num, 40);
            dian_layout.addView(dian_img, lp);
            dots.add(dian_img);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int oldPosition = 0;

            @Override
            public void onPageSelected(int arg0) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                if (oldPosition < dots.size())
                    dots.get(oldPosition).setImageResource(R.drawable.dot_normal);
                if (oldPosition < dots.size())
                    dots.get(arg0).setImageResource(R.drawable.dot_highlight);
                oldPosition = arg0;
                WidgetUtils.setVisible(btn_ok, arg0 == imageViews.length - 1);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        dots.get(0).setImageResource(R.drawable.dot_highlight);
    }

}
