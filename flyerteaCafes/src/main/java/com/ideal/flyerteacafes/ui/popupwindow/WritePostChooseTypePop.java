package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.Calendar;

/**
 * Created by fly on 2017/3/9.
 */

public class WritePostChooseTypePop extends PopupWindow {

    public interface IToWriteThread {

        void toNormalWriteThread();

        void toMajorWriteThread();
    }

    private IToWriteThread iToWriteThread;
    private Context mContext;

    public void setiToWriteThread(IToWriteThread iToWriteThread) {
        this.iToWriteThread = iToWriteThread;
    }

    public WritePostChooseTypePop(Context context) {
        super(context);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.pop_writepost_choosetype, null);
        TextView days_text = V.f(mView, R.id.days_text);
        TextView week_text = V.f(mView, R.id.week_text);
        TextView year_month_text = V.f(mView, R.id.year_month_text);


        Calendar now = Calendar.getInstance();
        WidgetUtils.setText(days_text, String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
        WidgetUtils.setText(year_month_text, String.format("%0" + 2 + "d", (now.get(Calendar.MONTH) + 1)) + "/" + now.get(Calendar.YEAR));

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = now.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        week_text.setText(weekDays[w]);


        x.view().inject(this, mView);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }


    @Event({R.id.btn_send_pic, R.id.btn_send_text, R.id.root})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.root:
                dismiss();
                break;
            case R.id.btn_send_text:
                MobclickAgent.onEvent(mContext, FinalUtils.EventId.click_write_normal);//友盟统计
                if (iToWriteThread == null) {
                    mContext.startActivity(new Intent(mContext, WriteThreadActivity.class));
                } else {
                    iToWriteThread.toNormalWriteThread();
                }
                dismiss();
                break;
            case R.id.btn_send_pic:
                MobclickAgent.onEvent(mContext, FinalUtils.EventId.click_write_major);//友盟统计
                if (iToWriteThread == null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE, AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD_FIRST);
                    bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 30);
                    Intent intent = new Intent(mContext, AlbumActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else {
                    iToWriteThread.toMajorWriteThread();
                }

                dismiss();
                break;
        }
    }
}
