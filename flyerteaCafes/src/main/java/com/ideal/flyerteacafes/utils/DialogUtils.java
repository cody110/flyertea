package com.ideal.flyerteacafes.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.LoginVideoActivity;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;

public class DialogUtils {

    public static void remindDialog(final Context context, String msg) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(context);
        builder.setIsOneButton(true);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.warm_tips));
        builder.setPositiveButton("确定");
        builder.create().show();
    }


    public static void showDialog(final Context context) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(context);
        builder.setMessage("您还未登录，请登录后进行操作");
        builder.setTitle(context.getString(R.string.warm_tips));
        builder.setPositiveButton("登录", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                Intent intent = new Intent(context, LoginVideoActivity.class);
                intent.putExtra("dialog", true);
                context.startActivity(intent);
            }
        });
        builder.create().show();
    }

    public static void showDialog(final Context context, MyAlertDialog.OnAlertViewClickListener loginListener) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(context);
        builder.setMessage("您还未登录，请登录后进行操作");
        builder.setTitle(context.getString(R.string.warm_tips));
        builder.setPositiveButton("登录", loginListener);
        builder.create().show();
    }


    public static void showDialog(final Context context, MyAlertDialog.OnAlertViewClickListener loginListener, MyAlertDialog.OnAlertViewClickListener cancleListener) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(context);
        builder.setMessage("您还未登录，请登录后进行操作");
        builder.setTitle(context.getString(R.string.warm_tips));
        builder.setPositiveButton("登录", loginListener);
        builder.setNegativeButton(cancleListener);
        builder.create().show();
    }


    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public Dialog loadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  

        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        tipTextView.setText(msg);// 设置加载信息  

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
        return loadingDialog;

    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public Dialog loadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
        return loadingDialog;

    }

}
