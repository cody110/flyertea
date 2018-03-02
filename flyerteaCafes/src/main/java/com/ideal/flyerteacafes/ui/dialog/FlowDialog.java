package com.ideal.flyerteacafes.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.utils.tools.V;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/12.
 */

public class FlowDialog extends DialogFragment {

    IFlowClickListener iFlowClickListener;

    public interface IFlowClickListener {
        void oneAllow();

        void permanentAllow();

        void cancle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_flow, container, false);
        view.findViewById(R.id.one_allow_btn).setOnClickListener(onClickListener);
        view.findViewById(R.id.all_allow_btn).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancle_btn).setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.one_allow_btn:
                    if (iFlowClickListener != null) iFlowClickListener.oneAllow();
                    break;
                case R.id.all_allow_btn:
                    if (iFlowClickListener != null) iFlowClickListener.permanentAllow();
                    break;
                case R.id.cancle_btn:
                    if (iFlowClickListener != null) iFlowClickListener.cancle();
                    break;
            }
            dismiss();
        }
    };


    public void setFlowClickListener(IFlowClickListener iFlowClickListener) {
        this.iFlowClickListener = iFlowClickListener;
    }


}
