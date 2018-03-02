package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;

import org.xutils.view.annotation.Event;
import org.xutils.x;


public class WriteMajorThreadMenuPopupWindow extends PopupWindow {


    public interface IClick {

        /**
         * 排版
         */
        void sort();

        /**
         * 排序
         */
        void save();

        /**
         * 预览
         */
        void view();

    }

    WriteMajorThreadMenuPopupWindow.IClick iClick;

    public void setIClick(WriteMajorThreadMenuPopupWindow.IClick iClick) {
        this.iClick = iClick;
    }


    private SharedPreferencesString preferences;

    public WriteMajorThreadMenuPopupWindow(final Context context) {
        super(context);
        preferences = SharedPreferencesString.getInstances(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mMenuView = inflater.inflate(R.layout.popupwindow_major_thread_menu_layout, null);
        x.view().inject(this, mMenuView);


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setOutsideTouchable(true);
        this.update();

    }

    @Event({R.id.major_thread_content_save_btn, R.id.major_thread_content_view_btn,R.id.major_thread_sort_view_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.major_thread_content_save_btn:
                if (iClick != null) iClick.save();
                dismiss();
                break;

            case R.id.major_thread_content_view_btn:
                if (iClick != null) iClick.view();
                dismiss();
                break;

            case R.id.major_thread_sort_view_btn:
                if(iClick!=null)iClick.sort();
                break;

        }
    }

}
