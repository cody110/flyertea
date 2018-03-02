package com.ideal.flyerteacafes.model.loca;

import android.text.style.ClickableSpan;

/**
 * Created by fly on 2016/5/13.
 * 分段展示字符串的属性
 */
public class SegmentedStringMode {

    private String showText;
    private int size;
    private int color;
    private ClickableSpan clickableSpan;

    public SegmentedStringMode(String showText, int size, int color) {
        this.showText = showText;
        this.size = size;
        this.color = color;
    }

    public SegmentedStringMode(String showText, int size, int color,ClickableSpan clickableSpan) {
        this.showText = showText;
        this.size = size;
        this.color = color;
        this.clickableSpan=clickableSpan;
    }

    public ClickableSpan getClickableSpan() {
        return clickableSpan;
    }

    public void setClickableSpan(ClickableSpan clickableSpan) {
        this.clickableSpan = clickableSpan;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getShowText() {
        return showText;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

}
