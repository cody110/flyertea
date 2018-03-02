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

public class FlightDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_add_tag, container, false);
        TextView description_tv = V.f(view, R.id.description_tv);
        description_tv.setText("请输入航班号");
        final EditText et_tag = V.f(view, R.id.et_tag);
        et_tag.setHint("例如：MU1234");

        V.f(view, R.id.dialog_fly_alert_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagEvent tagEvent = new TagEvent(TagEvent.TAG_FLIGHT);
                EventBus.getDefault().post(tagEvent);
                dismiss();
            }
        });

        V.f(view, R.id.dialog_fly_alert_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_tag.getText().toString().trim())) {

                } else {
                    TagEvent tagEvent = new TagEvent(TagEvent.TAG_FLIGHT);
                    Bundle bundle = new Bundle();
                    bundle.putString("data", et_tag.getText().toString());
                    tagEvent.setBundle(bundle);
                    EventBus.getDefault().post(tagEvent);
                }
            }
        });


        return view;
    }



}
