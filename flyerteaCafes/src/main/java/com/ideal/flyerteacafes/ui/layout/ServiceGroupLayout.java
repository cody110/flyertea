package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.ServiceBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/3/30.
 */

public class ServiceGroupLayout extends LinearLayout {

    @ViewInject(R.id.service_left_icon)
    public ImageView service_left_icon;
    @ViewInject(R.id.service_title)
    public TextView service_title;
    @ViewInject(R.id.service_text)
    public TextView service_text;
    @ViewInject(R.id.service_arrow_icon)
    public ImageView service_arrow_icon;

    public ServiceGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.group_layout_service, this);
        x.view().inject(this, this);
    }

//    public void bindData(int resId, String title, String text, boolean needRightIcon) {
//        service_left_icon.setImageResource(resId);
//        WidgetUtils.setText(service_title, title);
//        WidgetUtils.setText(service_text, text);
//        WidgetUtils.setVisible(service_arrow_icon, needRightIcon);
//    }

    public void bindData(final ServiceBean.SubBean subBean, boolean needRightIcon) {
        DataUtils.downloadPicture(service_left_icon, subBean.getMobilecover(), R.drawable.icon_def);
        WidgetUtils.setText(service_title, subBean.getSubject());
        WidgetUtils.setText(service_text, subBean.getDescription());
        WidgetUtils.setVisible(service_arrow_icon, needRightIcon);
    }

}
