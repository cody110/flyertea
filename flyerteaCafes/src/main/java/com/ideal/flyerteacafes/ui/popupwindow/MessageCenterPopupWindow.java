package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.messagecenter.ReplyMineActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;

/**
 * Created by fly on 2016/12/20.
 */

public class MessageCenterPopupWindow extends PopupWindow {

    private Handler handler;

    public MessageCenterPopupWindow(Context context, final Handler handler) {
        super(context);
        this.handler=handler;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View mMenuView = inflater.inflate(R.layout.pop_comment_message, null);

        TextView pop_comment_message_bottom_read = (TextView) mMenuView.findViewById(R.id.pop_comment_message_bottom_read);
        TextView pop_comment_message_bottom_clear = (TextView) mMenuView.findViewById(R.id.pop_comment_message_bottom_clear);


        pop_comment_message_bottom_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(ReplyMineActivity.POP_MARK_ALL_READS);
                dismiss();
            }
        });

        pop_comment_message_bottom_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(ReplyMineActivity.POP_CLEAR_ALL_MESSAGE);
                dismiss();
            }
        });

        mMenuView.findViewById(R.id.pop_comment_message_bottom_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_comment_message_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void dismiss() {
        handler.sendEmptyMessage(100);
        super.dismiss();
    }
}
