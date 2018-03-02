package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Cindy on 2016/11/23.
 */
public class TopicTypeTitle extends FrameLayout{

    @ViewInject(R.id.hotel_pop_screening_type_title_name)
    TextView titleName;

    public TopicTypeTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TopicTypeTitle);
        String titleString= ta.getString(R.styleable.TopicTypeTitle_titleString);

        initView(context);
        WidgetUtils.setText(titleName,titleString);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.popupwindow_topic_type_title, this);
        x.view().inject(this);
    }
}
