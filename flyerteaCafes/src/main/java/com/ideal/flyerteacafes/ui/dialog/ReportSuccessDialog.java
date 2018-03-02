package com.ideal.flyerteacafes.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;

/**
 * Created by fly on 2015/11/30.
 */
public class ReportSuccessDialog extends DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.dialog_report_success, null);// 得到加载view

        Dialog dialog = new Dialog(getActivity(), R.style.loading_dialog);// 创建自定义样式dialog

        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局

        return dialog;
    }
}
