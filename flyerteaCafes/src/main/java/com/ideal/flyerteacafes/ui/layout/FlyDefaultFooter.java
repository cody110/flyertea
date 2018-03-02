package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;

/**
 * Created by fly on 2017/9/30.
 */

public class FlyDefaultFooter extends FrameLayout {
    public FlyDefaultFooter(Context context) {
        super(context);
        initViews();
    }

    public FlyDefaultFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.refresh_footer, this);
        View pull_to_load_image = findViewById(R.id.pull_to_load_image);
        View mProgressBar = findViewById(R.id.pull_to_load_progress);
        TextView pull_to_load_text = (TextView) findViewById(R.id.pull_to_load_text);
        pull_to_load_text.setText(R.string.pull_to_refresh_footer_refreshing_label);

        pull_to_load_image.setVisibility(View.GONE);

        Animation progressAnimation = AnimationUtils.loadAnimation(FlyerApplication.getContext(), R.anim.no_stop_rotation);
        LinearInterpolator lin = new LinearInterpolator();
        progressAnimation.setInterpolator(lin);
        mProgressBar.startAnimation(progressAnimation);

    }
}
