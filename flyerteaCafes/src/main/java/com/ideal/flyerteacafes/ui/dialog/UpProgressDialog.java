package com.ideal.flyerteacafes.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.flyer.tusdk.R;
import com.flyer.tusdk.views.MySeekBar;
import com.ideal.flyerteacafes.model.TagEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2018/1/18.
 */

public class UpProgressDialog extends DialogFragment {


    MySeekBar mySeekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_up_press, container, false);
        mySeekBar = (MySeekBar) view.findViewById(R.id.seekbar);
        mySeekBar.setMax(100);
        mySeekBar.setProgress(0);

        view.findViewById(R.id.cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new TagEvent(TagEvent.TAG_CANCLE_SEND_VIDEO));
                dismiss();
            }
        });

        return view;
    }

    public void updateViewProgress(int progress) {
        if (mySeekBar != null && isAdded()) {
            mySeekBar.setProgress(progress);
        }
    }
}
