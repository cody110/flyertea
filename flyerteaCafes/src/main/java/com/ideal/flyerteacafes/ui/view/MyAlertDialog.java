package com.ideal.flyerteacafes.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.WidgetUtils;


/**
 * @version V1.1
 * @description: 自定义对话框
 * @author: Cindy
 * @date: 2016/3/21 20:11
 */
public class MyAlertDialog extends Dialog {


    public MyAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * @author Cindy
     *         <p>
     *         点击事件的接口
     */
    public interface OnAlertViewClickListener {
        void OnAlertViewClick();
    }

    public static class Builder {
        Context context;
        String title;
        String message;
        String positiveButtonText;
        String negativeButtonText;
        OnAlertViewClickListener negativeButtonListener, positiveButtonListener;
        boolean focusboolean;
        boolean isOneButton = false;


        public Builder(Context context) {
            this.context = context;
            title = context.getString(R.string.warm_tips);
            positiveButtonText = context.getString(R.string.ensure);
            negativeButtonText = context.getString(R.string.cancel);
        }

        public Builder(Context context, boolean isOneButton) {
            this.isOneButton = isOneButton;
            this.context = context;
            title = context.getString(R.string.warm_tips);
            positiveButtonText = context.getString(R.string.ensure);
            negativeButtonText = context.getString(R.string.cancel);
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public void setIsOneButton(boolean isOneButton) {
            this.isOneButton = isOneButton;
        }

        public Builder setPositiveButton(@StringRes int textId, final OnAlertViewClickListener listener) {
            positiveButtonText = context.getString(textId);
            positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textId, final OnAlertViewClickListener listener) {
            negativeButtonText = context.getString(textId);
            negativeButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, final OnAlertViewClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, final OnAlertViewClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            negativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder setPositiveButton(final OnAlertViewClickListener listener) {
            positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(final OnAlertViewClickListener listener) {
            negativeButtonListener = listener;
            return this;
        }

        public Dialog create() {
            final MyAlertDialog dialog = new MyAlertDialog(context, R.style.alertView);

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.alertdialog_view, null);
            // 标题和内容
            TextView titleTextView = (TextView) view
                    .findViewById(R.id.mtv_titlte_alert);
            TextView messageTextView = (TextView) view
                    .findViewById(R.id.mtv_message_alert);
            titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            if (!TextUtils.isEmpty(title)) {
                titleTextView.setText(title);
            } else {
                titleTextView.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
//				messageTextView.setText(message);
                WidgetUtils.setTextHtml(messageTextView, message);
            } else {
                messageTextView.setVisibility(View.GONE);
            }

            // 动态添加按钮
            LinearLayout buttonLayout = (LinearLayout) view
                    .findViewById(R.id.buttonLayout);
            TextView negativeButton = (TextView) view.findViewById(R.id.mdialog_button_negative);
            TextView positiveButton = (TextView) view.findViewById(R.id.mdialog_button_positive);

            if (isOneButton) {
                positiveButton.setBackgroundResource(R.drawable.alert_bottom_button);
                negativeButton.setVisibility(View.GONE);
            } else {
                positiveButton.setBackgroundResource(R.drawable.alert_right_button);

                negativeButton.setText(negativeButtonText);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (negativeButtonListener != null) {
                            negativeButtonListener.OnAlertViewClick();
                        }
                        dialog.dismiss();
                    }
                });
            }

            positiveButton.setText(positiveButtonText);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != positiveButtonListener) {
                        positiveButtonListener.OnAlertViewClick();
                    }
                    dialog.dismiss();
                }
            });


            Window mWindow = dialog.getWindow();
            // mWindow.setWindowAnimations(anim); // 添加动画
            mWindow.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
            dialog.setCanceledOnTouchOutside(focusboolean);
            dialog.setCancelable(focusboolean);
            dialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (!focusboolean) {
                        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        } else {
                            dialog.dismiss();
                            return false;
                        }
                    } else {
                        dialog.dismiss();
                        return false;
                    }

                }
            });
            dialog.setContentView(view);

            return dialog;
        }

    }

}
