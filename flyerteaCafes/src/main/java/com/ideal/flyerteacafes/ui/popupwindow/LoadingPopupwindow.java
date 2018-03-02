package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

/**
 * Created by fly on 2017/5/9.
 */

public class LoadingPopupwindow extends PopupWindow {

    TextView tipTextView;

    public LoadingPopupwindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mMenuView = inflater
                .inflate(R.layout.loading, null);
        tipTextView = (TextView) mMenuView.findViewById(R.id.tipTextView);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setTipTextView(String tipString) {
        WidgetUtils.setVisible(tipTextView, !TextUtils.isEmpty(tipString));
        WidgetUtils.setText(tipTextView, tipString);
    }


}
