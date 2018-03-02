package com.ideal.flyerteacafes.ui.controls;

import com.ideal.flyerteacafes.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageBtnWithText extends LinearLayout {

    private TextView mTv;
    private View mRoot;

    public ImageBtnWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        View view = LayoutInflater.from(context).inflate(R.layout.imagebtn_with_text, this, true);
        mTv = (TextView) view.findViewById(R.id.tvImageBtnWithText);
        mRoot = view.findViewById(R.id.rootImageBtnWithText);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageBtnWithText);
        CharSequence text = a.getText(R.styleable.ImageBtnWithText_android_text);
        if (text != null)
            mTv.setText(text);
        Drawable drawable = a.getDrawable(R.styleable.ImageBtnWithText_android_drawable);
        if (mRoot != null && drawable != null)
            mRoot.setBackgroundDrawable(drawable);
        a.recycle();

    }

    public void SetBackground(int id) {
        if (mRoot != null)
            mRoot.setBackgroundResource(id);
    }

    public void setText(String text) {
        if (text != null && mTv != null)
            mTv.setText(text);
    }

    public void setTextColor(int id) {
        if (mTv != null)
            mTv.setTextColor(id);
    }

}
