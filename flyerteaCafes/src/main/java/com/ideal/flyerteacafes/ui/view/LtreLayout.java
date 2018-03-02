package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;


/**
 * Created by fly on 2016/4/7.
 */
public class LtreLayout extends LinearLayout {


    private Context context;

    private int ltColor, rtColor, rtHintColor, ltHintColor;
    private float ltSize, rtSize, ltHintSize, rtHintSize;
    private int ltWidth, rtWidth, ltHeight, rtHeight;
    private int ltPadding, rtPadding;
    private int ltMarginLeft, ltMarginRight, rtMarginLeft, rtMarginRight;
    private String ltText, ltHintText, rtText, rtHintText;
    private int rtMaxLength;
    private int rtInputType;
    private int rtGravity;

    private Drawable reDrawable;


    private TextView leftText;
    private EditText rightEdit;

    private LinearLayout.LayoutParams leftTextParams, rightEditParams;


    public LtreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Resources resources = context.getResources();
        int margin = resources.getDimensionPixelOffset(R.dimen.app_edge_distance);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.LtreLayout);

        ltColor = ta.getColor(R.styleable.LtreLayout_ltColorLtre, resources.getColor(R.color.black));
        ltSize = ta.getDimensionPixelSize(R.styleable.LtreLayout_ltSizeLtre, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ltPadding = (int) ta.getDimension(R.styleable.LtreLayout_ltPaddingLtre, 0);
        ltMarginLeft = (int) ta.getDimension(R.styleable.LtreLayout_ltMarginLeftLtre, margin);
        ltMarginRight = (int) ta.getDimension(R.styleable.LtreLayout_ltMarginRightLtre, 0);
        ltWidth = (int) ta.getDimension(R.styleable.LtreLayout_ltWidthLtre, 0);
        ltHeight = (int) ta.getDimension(R.styleable.LtreLayout_ltHeightLtre, 0);
        ltHintColor = ta.getColor(R.styleable.LtreLayout_ltHintColorLtre, resources.getColor(R.color.app_body_black));
        ltHintSize = ta.getDimensionPixelSize(R.styleable.LtreLayout_ltHintSizeLtre, resources.getDimensionPixelSize(R.dimen.app_body_size));
        ltText = ta.getString(R.styleable.LtreLayout_ltTextLtre);
        ltHintText = ta.getString(R.styleable.LtreLayout_ltHintTextLtre);

        rtColor = ta.getColor(R.styleable.LtreLayout_rtColorLtre, resources.getColor(R.color.black));
        rtSize = ta.getDimensionPixelSize(R.styleable.LtreLayout_rtSizeLtre, resources.getDimensionPixelSize(R.dimen.app_body_size));
        rtPadding = (int) ta.getDimension(R.styleable.LtreLayout_rtPaddingLtre, 0);
        rtMarginLeft = (int) ta.getDimension(R.styleable.LtreLayout_rtMarginLeftLtre, 0);
        rtMarginRight = (int) ta.getDimension(R.styleable.LtreLayout_rtMarginRightLtre, 0);
        rtWidth = (int) ta.getDimension(R.styleable.LtreLayout_rtWidthLtre, 0);
        rtHeight = (int) ta.getDimension(R.styleable.LtreLayout_rtHeightLtre, 0);
        rtHintColor = ta.getColor(R.styleable.LtreLayout_rtHintColorLtre, resources.getColor(R.color.app_body_grey));
        rtHintSize = ta.getDimensionPixelSize(R.styleable.LtreLayout_rtHintSizeLtre, resources.getDimensionPixelSize(R.dimen.app_body_size));
        rtText = ta.getString(R.styleable.LtreLayout_rtTextLtre);
        rtHintText = ta.getString(R.styleable.LtreLayout_rtHintTextLtre);
        rtInputType = ta.getInteger(R.styleable.LtreLayout_rtInputType, InputType.TYPE_CLASS_TEXT);
        rtMaxLength = ta.getInteger(R.styleable.LtreLayout_rtMaxLengthLtre, 0);
        rtGravity = ta.getInteger(R.styleable.LtreLayout_rtGravity, 3);

        reDrawable = ta.getDrawable(R.styleable.LtreLayout_rtBackgroundLtre);

        ta.recycle();


        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);


        initParams();
        initView();

    }


    private void initParams() {

        if (ltWidth == 0) {
            ltWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (ltHeight == 0) {
            ltHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        leftTextParams = new LinearLayout.LayoutParams(ltWidth, ltHeight);
        leftTextParams.leftMargin = ltMarginLeft;

        rightEditParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        rightEditParams.rightMargin = ltMarginLeft;

    }

    private void initView() {
        leftText = new TextView(context);
        rightEdit = new EditText(context);
        this.addView(leftText, leftTextParams);
        this.addView(rightEdit, rightEditParams);

        leftText.setTextSize(DensityUtil.px2dip(context, ltSize));
        leftText.setTextColor(ltColor);
        leftText.setHintTextColor(ltHintColor);
        leftText.setPadding(ltPadding, ltPadding, ltPadding, ltPadding);
        WidgetUtils.setHint(leftText, ltHintText);
        WidgetUtils.setText(leftText, ltText);


        rightEdit.setTextSize(DensityUtil.px2dip(context, rtSize));
        rightEdit.setTextColor(rtColor);
        rightEdit.setHintTextColor(rtHintColor);
        rightEdit.setPadding(rtPadding, rtPadding, rtPadding, rtPadding);
        rightEdit.setBackgroundDrawable(reDrawable);
        WidgetUtils.setHint(rightEdit, rtHintText);
        WidgetUtils.setText(rightEdit, rtText);
        rightEdit.setInputType(rtInputType);
        rightEdit.setSingleLine(true);
        rightEdit.setGravity(rtGravity);
        if (rtMaxLength > 0) {
            rightEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(rtMaxLength)});
        }
    }


    /**
     * 中间文字设置
     *
     * @param text
     */
    public void setTextByRe(String text) {
        if (text != null) {
            rightEdit.setText(text);
        }
    }

    /**
     * 获取中间文字
     *
     * @return
     */
    public String getTextByRe() {
        return rightEdit.getText().toString();
    }

    public void setFocusableByEdit(boolean bol) {
        rightEdit.setFocusable(bol);
    }

}
