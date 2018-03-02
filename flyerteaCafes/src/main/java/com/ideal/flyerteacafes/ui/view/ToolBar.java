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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;


/**
 * Created by fly on 2016/2/24.
 */
public class ToolBar extends RelativeLayout {

    /**
     * 标题栏背景
     */
    private Drawable background_bar;

    /**
     * 标题栏的三个控件
     */
    private ImageView leftBtn, rightBtn;
    private TextView titleTextview, rightTextview, leftTextview;
    private RelativeLayout leftView, rightView;

    /**
     * 左边按钮的属性
     */
    private int leftTextColor;
    private Drawable leftBackground, leftDrawable;
    private String leftText;

    /**
     * 右边按钮的属性
     */
    private int rightTextColor;
    private Drawable rightBackground, rightDrawable;
    private String rightText;
    private float rightTextSize;

    /**
     * 中间TextView的属性
     */
    private int titleTextColor;
    private String titleText;
    private float titleTextSize;

    /**
     * 三个控件的布局参数
     */
    private LayoutParams leftParams, leftImgParams, rightParams, rightImgParams, titleParams, leftViewParams, rightViewParams;

    private int toolbar_height;

    private Context context;

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ToolBar);
        Resources resources = context.getResources();

        toolbar_height = resources.getDimensionPixelOffset(R.dimen.app_bg_title_height);

        background_bar = ta.getDrawable(R.styleable.ToolBar_background_bar);
        if (background_bar == null)
            background_bar = resources.getDrawable(R.color.bg_toolbar);

        leftTextColor = ta.getColor(R.styleable.ToolBar_leftTextColor, resources.getColor(R.color.text_black));
        leftBackground = ta.getDrawable(R.styleable.ToolBar_leftBackground);
        leftDrawable = ta.getDrawable(R.styleable.ToolBar_leftImgSrc);
        leftText = ta.getString(R.styleable.ToolBar_leftText);

        rightTextColor = ta.getColor(R.styleable.ToolBar_rightTextColor, resources.getColor(R.color.text_black));
        rightBackground = ta.getDrawable(R.styleable.ToolBar_rightBackground);
        rightDrawable = ta.getDrawable(R.styleable.ToolBar_rightImgSrc);
        rightText = ta.getString(R.styleable.ToolBar_rightText);
        rightTextSize = ta.getDimension(R.styleable.ToolBar_rightTextSize, resources.getDimension(R.dimen.app_bg_title_size));

        titleText = ta.getString(R.styleable.ToolBar_title_bar);
        titleTextColor = ta.getColor(R.styleable.ToolBar_titleTextColor_bar, resources.getColor(R.color.text_black));
        titleTextSize = ta.getDimension(R.styleable.ToolBar_titleTextSize, resources.getDimension(R.dimen.app_bg_title_size));


        //对ta进行回收
        ta.recycle();

        leftView = new RelativeLayout(context);
        rightView = new RelativeLayout(context);
        leftBtn = new ImageView(context);
        leftTextview = new TextView(context);
        rightTextview = new TextView(context);
        rightBtn = new ImageView(context);
        titleTextview = new TextView(context);

        /**
         * 给view设置id
         */
        leftView.setId(R.id.toolbar_left);
        rightView.setId(R.id.toolbar_right);
        leftBtn.setId(R.id.toolbar_left_img);

        /**
         * 设置属性
         */
        leftBtn.setImageDrawable(leftDrawable);
        leftTextview.setText(leftText);
        leftTextview.setTextColor(leftTextColor);

        rightTextview.setText(rightText);
        rightTextview.setTextColor(rightTextColor);
        if (rightTextSize == 0) {
//            rightTextview.setTextSize(18);
        } else {
            rightTextview.setTextSize(DensityUtil.px2dip(context, rightTextSize));
        }

        rightBtn.setImageDrawable(rightDrawable);

        titleTextview.setText(titleText);
        titleTextview.setTextColor(titleTextColor);
        if (titleTextSize == 0) {
            titleTextview.setTextSize(18);
        } else {
            titleTextview.setTextSize(DensityUtil.px2dip(context, titleTextSize));
        }

        titleTextview.setGravity(Gravity.CENTER);

        //设置整体背景颜色
        setBackground(background_bar);

        setLayoutParams();


    }


    private void setLayoutParams() {
        leftViewParams = new LayoutParams(
                DensityUtil.dip2px(context, 60),
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(leftView, leftViewParams);

        rightViewParams = new LayoutParams(
                DensityUtil.dip2px(context, 60),
                ViewGroup.LayoutParams.MATCH_PARENT);
        rightViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightView, rightViewParams);


        leftImgParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftImgParams.leftMargin = DensityUtil.dip2px(context, 10);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        //添加控件
        leftView.addView(leftBtn, leftImgParams);


        leftParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        leftParams.addRule(RelativeLayout.RIGHT_OF, R.id.toolbar_left_img);

        //添加控件
        leftView.addView(leftTextview, leftParams);

        rightParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rightParams.rightMargin = DensityUtil.dip2px(context, 10);
        rightView.addView(rightTextview, rightParams);

        rightImgParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightImgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rightImgParams.rightMargin = DensityUtil.dip2px(context, 10);
        rightView.addView(rightBtn, rightImgParams);

        titleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(titleTextview, titleParams);
    }

    /**
     * 设置标题
     *
     * @param titleString
     */
    public void setTitle(String titleString) {
        if (!TextUtils.isEmpty(titleString))
            titleTextview.setText(titleString);
    }


    public void setTitleMaxEmsEnd(int length) {
        titleTextview.setMaxEms(length);
        titleTextview.setEllipsize(TextUtils.TruncateAt.END);
        titleTextview.setMaxLines(1);
    }

    public void setTitleTextColorRes(int coloRes) {
        titleTextview.setTextColor(getResources().getColor(coloRes));
    }

    public void setLeftImage(int id) {
        leftBtn.setImageResource(id);
    }

    /**
     * 设置右边布局是否显示
     *
     * @param bol
     */
    public void setRightViewIsShow(boolean bol) {
        if (bol)
            rightView.setVisibility(View.VISIBLE);
        else
            rightView.setVisibility(View.GONE);
    }

    /**
     * 设置右边文字是否显示
     *
     * @param bol
     */
    public void setRightTextIsShow(boolean bol) {
        if (bol)
            rightTextview.setVisibility(View.VISIBLE);
        else
            rightTextview.setVisibility(View.GONE);
    }

    /**
     * 设置右边图标是否显示
     *
     * @param bol
     */
    public void setRightImgIsShow(boolean bol) {
        if (bol)
            rightBtn.setVisibility(View.VISIBLE);
        else
            rightBtn.setVisibility(View.GONE);
    }

    /**
     * 设置右边文字
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        if (rightText == null)
            rightText = "";
        this.rightText = rightText;
        rightTextview.setText(rightText);
    }

    /**
     * 设置右边图标
     *
     * @param resource
     */
    public void setRightImgResource(int resource) {
        rightBtn.setImageResource(resource);
    }

    public void setRightClickListener(OnClickListener clickListener) {
        rightView.setOnClickListener(clickListener);
    }

    public void setLeftClickListener(OnClickListener clickListener) {
        leftView.setOnClickListener(clickListener);
    }


}
