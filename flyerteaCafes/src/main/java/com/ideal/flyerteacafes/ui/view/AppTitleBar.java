package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.tools.V;

/**
 * Created by fly on 2015/11/14.
 */
public class AppTitleBar extends RelativeLayout {

    final public static int fRightViewId = R.id.include_title_right_btn, fLeftViewId = R.id.include_title_menu_btn;
    public View rootView;
    public View leftView;
    public RelativeLayout centerView;
    public ImageView leftImg;
    public TextView titleTxt;
    public View rightView;
    public ImageView rightImg;
    public TextView rightTxt,leftTxt;
    private Context context;


    public AppTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化
     */
    private void initView(Context context) {
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.include_centre_title_bar, this);
        rootView = V.f(this, R.id.include_title_menu_root);
        leftView = V.f(this, R.id.include_title_menu_btn);
        leftImg = V.f(this, R.id.include_title_menu_img);
        centerView = V.f(this, R.id.include_title_view);
        titleTxt = V.f(this, R.id.include_title_text);
        rightView = V.f(this, R.id.include_title_right_btn);
        rightImg = V.f(this, R.id.include_title_right_img);
        rightTxt = V.f(this, R.id.include_title_right_text);
        leftTxt= V.f(this, R.id.include_title_menu_left_text);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setTitleBackgroundColor(int color) {
        rootView.setBackgroundColor(color);
    }

    /**
     * 设置右边图片
     *
     * @param resId
     */
    public void setRightImageResource(int resId) {
        rightImg.setImageResource(resId);
    }

    /**
     * 设置右文字
     *
     * @param str
     */
    public void setRightTxt(String str) {
        if (str != null)
            rightTxt.setText(str);
    }

    /**
     * 设置左文字
     *
     * @param str
     */
    public void setLeftTxt(String str){
        if (str != null) {
            leftTxt.setText(str);
            leftImg.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param str
     */
    public void setTitleTxt(String str) {
        if (str != null)
            titleTxt.setText(str);
    }

    public void setCenterView(int layoutId){
        if(centerView.getChildCount()>0){
            centerView.removeAllViews();
        }
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(layoutId, null);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerView.addView(view,params);
    }

    /**
     * 设置标题字体大小
     *
     * @param size
     */
    public void setTitleTxtSize(float size) {
        titleTxt.setTextSize(size);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleTxtColor(int color) {
        titleTxt.setTextColor(color);
    }

    public void setLeftOnClickListener(OnClickListener l) {
        leftView.setOnClickListener(l);
    }

    public void setCenterOnClickListener(OnClickListener l) {
        titleTxt.setOnClickListener(l);
    }

    public void setRightOnClickListener(OnClickListener l) {
        rightView.setOnClickListener(l);
    }
}
