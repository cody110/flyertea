package com.ideal.flyerteacafes.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.tools.V;

/**
 * Created by fly on 2015/11/26.
 */
public class FlyAlertDialog extends DialogFragment implements View.OnTouchListener{

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(v.getId()== R.id.dialog_fly_alert_cancle) {
                    v.setBackgroundResource(R.drawable.fly_dialog_leftbtn_select_bg);
                }else{
                    v.setBackgroundResource(R.drawable.fly_dialog_rightbtn_select_bg);
                }
                break;

            case MotionEvent.ACTION_UP:
                if(v.getId()== R.id.dialog_fly_alert_cancle) {
                    v.setBackgroundResource(R.drawable.fly_dialog_leftbtn_unselect_bg);
                    dismiss();
                }else{
                    v.setBackgroundResource(R.drawable.fly_dialog_rightbtn_unselect_bg);
                    if(IClick!=null){
                        IClick.onDialogDone();
                        dismiss();
                    }
                }
                break;
        }


        return true;
    }

    public interface flyAlertDialogClick{
        void onDialogDone();
    }

    private flyAlertDialogClick IClick;

//    public void setFlyAlertDialogClick(flyAlertDialogClick IClick){
//        this.IClick=IClick;
//    }


    @Override
    public void onAttach(Activity activity) {
        try{
            flyAlertDialogClick act= (flyAlertDialogClick) activity;
            IClick=act;
        }catch (Exception e){

        }

        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_fly_alert, container, false);
        TextView cancle_tv= V.f(view, R.id.dialog_fly_alert_cancle);
        TextView sure_tv=V.f(view, R.id.dialog_fly_alert_sure);
        cancle_tv.setOnTouchListener(this);
        sure_tv.setOnTouchListener(this);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
