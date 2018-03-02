package com.ideal.flyerteacafes.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;

/*
 * 进度框类
 */
public class CustomDialog {

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
     * @param
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
