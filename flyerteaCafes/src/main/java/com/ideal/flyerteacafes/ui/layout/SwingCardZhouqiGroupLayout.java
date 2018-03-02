package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.ServiceBean;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;
import com.ideal.flyerteacafes.model.entity.TaskPriodProgressesBean;
import com.ideal.flyerteacafes.ui.activity.viewholder.SwingCardTaskDetailsVH;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/3/30.
 * 刷卡任务 详情 周期
 */

public class SwingCardZhouqiGroupLayout extends LinearLayout {

    @ViewInject(R.id.swingcard_time)
    public TextView swingcard_time;
    @ViewInject(R.id.swingcard_money)
    public TextView swingcard_money;
    @ViewInject(R.id.swingcard_merchant)
    public TextView swingcard_merchant;
    @ViewInject(R.id.swingcard_mcc)
    public TextView swingcard_mcc;
    @ViewInject(R.id.swingcard_pic)
    public ImageView swingcard_pic;

    public SwingCardZhouqiGroupLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_task_zhouqi, this);
        x.view().inject(this, this);
    }

    public SwingCardZhouqiGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_task_zhouqi, this);
        x.view().inject(this, this);
    }


    public void bindData(TaskPriodProgressesBean bean) {
        WidgetUtils.setText(swingcard_time, DataUtils.getTimeFormat(bean.getPosTime(), "yy-MM-dd"));
        WidgetUtils.setText(swingcard_money, DataUtils.getShowNumber(bean.getValue()));
        if (TextUtils.isEmpty(bean.getShop())) {
            WidgetUtils.setText(swingcard_merchant, "/");
        } else {
            WidgetUtils.setText(swingcard_merchant, bean.getShop());
        }
        if (TextUtils.isEmpty(bean.getMCC())) {
            WidgetUtils.setText(swingcard_mcc, "/");
        } else {
            WidgetUtils.setText(swingcard_mcc, bean.getMCC());
        }
        if (!TextUtils.isEmpty(bean.getBillPic())) {
            DataUtils.downloadPicture(swingcard_pic, bean.getBillPic(), R.drawable.icon_def);
            swingcard_pic.setVisibility(View.VISIBLE);
        } else {
            swingcard_pic.setVisibility(View.GONE);
        }
    }

}
