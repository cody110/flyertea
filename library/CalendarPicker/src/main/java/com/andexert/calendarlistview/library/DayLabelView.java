package com.andexert.calendarlistview.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by fly on 2016/3/22.
 */
public class DayLabelView extends View{

    private Drawable bg;

    protected static final int OFFSET_TOP=5;
    protected static int MONTH_HEADER_SIZE;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE;
    protected int mWidth;
    protected int mPadding = 0;
    protected int mNumDays = 7;
    protected int mWeekStart = 1;
    private Calendar mDayLabelCalendar;
    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    protected int mDayTextColor;
    private String mDayOfWeekTypeface;

    protected Paint mMonthDayLabelPaint;

    public DayLabelView(Context context) {
        super(context);
    }

    public DayLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.DayPickerView);
        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance();
        mDayOfWeekTypeface = resources.getString(R.string.sans_serif);
        bg=typedArray.getDrawable(R.styleable.DayPickerView_bgDayLabelView);
        MONTH_HEADER_SIZE = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_headerMonthHeight, resources.getDimensionPixelOffset(R.dimen.day_label_height));
        MONTH_DAY_LABEL_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDayName, resources.getDimensionPixelSize(R.dimen.text_size_day_name));

        mDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(R.color.normal_day));

        typedArray.recycle();
        initView();
    }

    public DayLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),  MONTH_HEADER_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMonthDayLabels(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    protected void initView() {
        setBackground(bg);
        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setStyle(Paint.Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Paint.Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = (MONTH_HEADER_SIZE+MONTH_DAY_LABEL_TEXT_SIZE)/2;
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            String weekDays=mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)];
            weekDays=weekDays.replace("周","");
            weekDays=weekDays.replace("星期","");
            canvas.drawText(weekDays, x, y, mMonthDayLabelPaint);
//            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()).substring(1), x, y, mMonthDayLabelPaint);
        }
    }
}
