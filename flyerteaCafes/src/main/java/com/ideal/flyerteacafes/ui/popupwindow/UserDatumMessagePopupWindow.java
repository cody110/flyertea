package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.utils.tools.V;


public class UserDatumMessagePopupWindow extends PopupWindow {

    private SharedPreferencesString preferences;
    private TextView btnSendPrivate;

    public UserDatumMessagePopupWindow(final Context context, final Handler handler) {
        super(context);
        preferences = SharedPreferencesString.getInstances(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mMenuView = inflater.inflate(R.layout.popupwindow_user_datum_message_layout, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        int density = (int) preferences.getFloatToKey("density");
        this.setWidth(100 * density);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(50 * density);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setOutsideTouchable(true);
        this.update();
        btnSendPrivate = V.f(mMenuView, R.id.btn_send_private_message);
        btnSendPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(UserDatumActvity.POP_SEND_PRIVATE_MESSAGE);
                dismiss();
            }
        });


    }

}