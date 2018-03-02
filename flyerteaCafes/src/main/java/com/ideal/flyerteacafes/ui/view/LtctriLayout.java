package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;


/**
 * Created by fly on 2016/4/7.
 * 左边文字，中间文字，icon
 */
public class LtctriLayout extends LinearLayout {

    private Context context;

    private int ltColor, ctColor, ctHintColor, ltHintColor;
    private float ltSize, ctSize, ltHintSize, ctHintSize;
    private int ltWidth, ctWidth, ltHeight, ctHeight, ltWeight, ctWeight;
    private int ltPadding, ctPadding;
    private int ltMarginLeft, ltMarginRight, ctMarginLeft, ctMarginRight, riMarginLeft, riMarginRight;
    private String ltText, ltHintText, ctText, ctHintText;

    private Drawable riDrawable;


    private TextView leftText, centerText;
    private ImageView rightImg;

    private LinearLayout.LayoutParams leftTextParams, centerTextParams, rightImgParams;

    private int ctGravity;


    public LtctriLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Resources resources = context.getResources();
        int margin = resources.getDimensionPixelOffset(R.dimen.app_edge_distance);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.LtctriLayout);

        ltColor = ta.getColor(R.styleable.LtctriLayout_ltColor, resources.getColor(R.color.black));
        ltSize = ta.getDimensionPixelSize(R.styleable.LtctriLayout_ltSize, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ltPadding = (int) ta.getDimension(R.styleable.LtctriLayout_ltPadding, 0);
        ltMarginLeft = (int) ta.getDimension(R.styleable.LtctriLayout_ltMarginLeft, margin);
        ltMarginRight = (int) ta.getDimension(R.styleable.LtctriLayout_ltMarginRight, 0);
        ltWidth = (int) ta.getDimension(R.styleable.LtctriLayout_ltWidth, 0);
        ltWeight = ta.getInteger(R.styleable.LtctriLayout_ltWeight, 0);
        ltHeight = (int) ta.getDimension(R.styleable.LtctriLayout_ltHeight, 0);
        ltHintColor = ta.getColor(R.styleable.LtctriLayout_ltHintColor, resources.getColor(R.color.app_body_black));
        ltHintSize = ta.getDimensionPixelSize(R.styleable.LtctriLayout_ltHintSize, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ltText = ta.getString(R.styleable.LtctriLayout_ltText);
        ltHintText = ta.getString(R.styleable.LtctriLayout_ltHintText);

        ctColor = ta.getColor(R.styleable.LtctriLayout_ctColor, resources.getColor(R.color.black));
        ctSize = ta.getDimensionPixelSize(R.styleable.LtctriLayout_ctSize, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ctPadding = (int) ta.getDimension(R.styleable.LtctriLayout_ctPadding, 0);
        ctMarginLeft = (int) ta.getDimension(R.styleable.LtctriLayout_ctMarginLeft, 0);
        ctMarginRight = (int) ta.getDimension(R.styleable.LtctriLayout_ctMarginRight, 0);
        ctWidth = (int) ta.getDimension(R.styleable.LtctriLayout_ctWidth, 0);
        ctWeight = ta.getInteger(R.styleable.LtctriLayout_ctWeight, 0);
        ctHeight = (int) ta.getDimension(R.styleable.LtctriLayout_ctHeight, 0);
        ctHintColor = ta.getColor(R.styleable.LtctriLayout_ctHintColor, resources.getColor(R.color.app_body_grey));
        ctHintSize = ta.getDimensionPixelSize(R.styleable.LtctriLayout_ctHintSize, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ctText = ta.getString(R.styleable.LtctriLayout_ctText);
        ctHintText = ta.getString(R.styleable.LtctriLayout_ctHintText);
        ctGravity = ta.getInteger(R.styleable.LtctriLayout_ctGravity, 3);

        riDrawable = ta.getDrawable(R.styleable.LtctriLayout_riSrc);
        riMarginLeft = (int) ta.getDimension(R.styleable.LtctriLayout_riMarginLeft, margin);
        riMarginRight = (int) ta.getDimension(R.styleable.LtctriLayout_riMarginRight, margin);

        ta.recycle();


        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);


        initParams();
        initView();

    }


    private void initParams() {


        if (ltHeight == 0) {
            ltHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (ltWeight == 0) {
            if (ltWidth == 0) {
                ltWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            leftTextParams = new LinearLayout.LayoutParams(ltWidth, ltHeight);
            centerTextParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        } else {
            leftTextParams = new LinearLayout.LayoutParams(0, ltHeight, ltWeight);
            if (ctWeight == 0) {
                if (ctWidth == 0) {
                    ctWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                centerTextParams = new LinearLayout.LayoutParams(ctWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                centerTextParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, ctWeight);
            }

        }

        leftTextParams.leftMargin = ltMarginLeft;


        rightImgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightImgParams.rightMargin = riMarginRight;
        rightImgParams.leftMargin = riMarginLeft;
    }

    private void initView() {
        leftText = new TextView(context);
        centerText = new TextView(context);
        rightImg = new ImageView(context);
        this.addView(leftText, leftTextParams);
        this.addView(centerText, centerTextParams);
        this.addView(rightImg, rightImgParams);

        leftText.setTextSize(DensityUtil.px2dip(context, ltSize));
        leftText.setTextColor(ltColor);
        leftText.setHintTextColor(ltHintColor);
        leftText.setPadding(ltPadding, ltPadding, ltPadding, ltPadding);
        WidgetUtils.setHint(leftText, ltHintText);
        WidgetUtils.setText(leftText, ltText);


        centerText.setTextSize(DensityUtil.px2dip(context, ctSize));
        centerText.setTextColor(ctColor);
        centerText.setHintTextColor(ctHintColor);
        centerText.setPadding(ctPadding, ctPadding, ctPadding, ctPadding);
        WidgetUtils.setHint(centerText, ctHintText);
        WidgetUtils.setText(centerText, ctText);
        centerText.setGravity(ctGravity);
        centerText.setSingleLine(true);
        centerText.setEllipsize(TextUtils.TruncateAt.valueOf("END"));

        rightImg.setImageDrawable(riDrawable);

    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    public void setTextByLt(String text) {
        if (text != null)
            leftText.setText(text);
    }

    /**
     * 中间文字设置
     *
     * @param text
     */
    public void setTextByCt(String text) {
        if (text != null) {
            centerText.setText(text);
        }
    }

    /**
     * 获取中间文字
     *
     * @return
     */
    public String getTextByCt() {
        return centerText.getText().toString();
    }

    /**
     * 右边img设置点击事件
     *
     * @param listener
     */
    public void setRightImgClickListener(OnClickListener listener) {
        rightImg.setOnClickListener(listener);
    }

    /**
     * 设置有图标
     *
     * @param resid
     */
    public void setRightImgResource(int resid) {
        rightImg.setImageResource(resid);
    }


    /**
     * 设置右边按钮显示隐藏
     *
     * @param bol
     */
    public void setVisibleByRi(boolean bol) {
        if (bol)
            rightImg.setVisibility(View.VISIBLE);
        else
            rightImg.setVisibility(View.GONE);
    }

    public void setVisibleByRi(int visible) {
        rightImg.setVisibility(visible);
    }


}
