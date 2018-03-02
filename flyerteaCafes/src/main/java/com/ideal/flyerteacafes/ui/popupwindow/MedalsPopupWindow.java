package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.MyMedalsSubBean;
import com.ideal.flyerteacafes.ui.activity.viewholder.XunzhangVH;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/19.
 */

public class MedalsPopupWindow extends PopupWindow {


    public interface IActionListener {
        void shareMedals(MyMedalsSubBean medalsSubBean);

        void shengqingMedals(MyMedalsSubBean medalsSubBean);
    }

    public MedalsPopupWindow.IActionListener iActionListener;

    public void setiActionListener(MedalsPopupWindow.IActionListener iActionListener) {
        this.iActionListener = iActionListener;
    }

    @ViewInject(R.id.medals_igv)
    ImageView medals_igv;
    @ViewInject(R.id.name_tv)
    TextView name_tv;
    @ViewInject(R.id.feimi_tv)
    TextView feimi_tv;
    @ViewInject(R.id.xianhua_tv)
    TextView xianhua_tv;
    @ViewInject(R.id.weiwang_tv)
    TextView weiwang_tv;
    @ViewInject(R.id.description_tv)
    TextView description_tv;
    @ViewInject(R.id.get_num_tv)
    TextView get_num_tv;
    @ViewInject(R.id.share_btn)
    TextView share_btn;


    private Context context;
    private MyMedalsSubBean medalsSubBean;

    public MedalsPopupWindow(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mMenuView = inflater.inflate(R.layout.pop_medals_layout, null);
        x.view().inject(this, mMenuView);


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }

    @Event({R.id.share_btn, R.id.root_view, R.id.center_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.share_btn:
                if (TextUtils.equals(medalsSubBean.getAction(), "2") || TextUtils.equals(medalsSubBean.getAction(), "3")) {//申请
                    if (iActionListener != null)
                        iActionListener.shengqingMedals(medalsSubBean);
                } else if (TextUtils.equals(medalsSubBean.getAction(), "0")) {//已拥有
                    if (iActionListener != null)
                        iActionListener.shareMedals(medalsSubBean);
                }
                dismiss();
                break;

            case R.id.root_view:
                dismiss();
                break;

        }

    }


    public void bindData(MyMedalsSubBean medalsSubBean) {
        this.medalsSubBean = medalsSubBean;
        x.image().bind(medals_igv, medalsSubBean.getImage(), XutilsHelp.image_FIT_XY);
        WidgetUtils.setText(name_tv, medalsSubBean.getName());


        if (TextUtils.equals(medalsSubBean.getCredit(), "1")) {//威望
            WidgetUtils.setText(weiwang_tv, " +" + medalsSubBean.getBonus());
            WidgetUtils.setVisible(weiwang_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit(), "6")) {//飞米
            WidgetUtils.setText(feimi_tv, " +" + medalsSubBean.getBonus());
            WidgetUtils.setVisible(feimi_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit(), "2")) {//鲜花
            WidgetUtils.setText(xianhua_tv, " +" + medalsSubBean.getBonus());
            WidgetUtils.setVisible(xianhua_tv, true);
        }

        if (TextUtils.equals(medalsSubBean.getCredit1(), "1")) {//威望
            WidgetUtils.setText(weiwang_tv, " +" + medalsSubBean.getBonus1());
            WidgetUtils.setVisible(weiwang_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit1(), "6")) {//飞米
            WidgetUtils.setText(feimi_tv, " +" + medalsSubBean.getBonus1());
            WidgetUtils.setVisible(feimi_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit1(), "2")) {//鲜花
            WidgetUtils.setText(xianhua_tv, " +" + medalsSubBean.getBonus1());
            WidgetUtils.setVisible(xianhua_tv, true);
        }

        if (TextUtils.equals(medalsSubBean.getCredit2(), "1")) {//威望
            WidgetUtils.setText(weiwang_tv, " +" + medalsSubBean.getBonus2());
            WidgetUtils.setVisible(weiwang_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit2(), "6")) {//飞米
            WidgetUtils.setText(feimi_tv, " +" + medalsSubBean.getBonus2());
            WidgetUtils.setVisible(feimi_tv, true);
        } else if (TextUtils.equals(medalsSubBean.getCredit2(), "2")) {//鲜花
            WidgetUtils.setText(xianhua_tv, " +" + medalsSubBean.getBonus2());
            WidgetUtils.setVisible(xianhua_tv, true);
        }


        description_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        WidgetUtils.setTextHtml(description_tv, medalsSubBean.getDescription());
        WidgetUtils.setText(get_num_tv, medalsSubBean.getDisplayorder() + "人已获得");


        if (TextUtils.equals(medalsSubBean.getAction(), "2") || TextUtils.equals(medalsSubBean.getAction(), "3")) {//申请
            WidgetUtils.setVisible(share_btn, true);
            WidgetUtils.setText(share_btn, medalsSubBean.getActiondesc());
        } else if (TextUtils.equals(medalsSubBean.getAction(), "0")) {//已拥有
            WidgetUtils.setVisible(share_btn, true);
            WidgetUtils.setText(share_btn, "分享");
        } else {
            WidgetUtils.setVisible(share_btn, false);
        }

    }

}
