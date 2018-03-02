package com.ideal.flyerteacafes.utils;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;

import java.util.ArrayList;
import java.util.List;

public class WidgetUtils {

    /**
     * 设置edittext不能编辑
     *
     * @param editText
     */
    public static void setEditTextCanNot(EditText editText) {
        if (editText != null)
            editText.setKeyListener(null);
    }


    public static void notifyDataSetChanged(BaseAdapter adapter) {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * Popupwindow dismiss
     *
     * @param popupWindow
     */
    public static void dismiss(PopupWindow popupWindow) {
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    /**
     * 设置控件的显示，隐藏
     *
     * @param view
     * @param bol
     */
    public static void setVisible(View view, boolean bol) {
        if (view != null) {
            if (bol)
                view.setVisibility(View.VISIBLE);
            else
                view.setVisibility(View.GONE);
        }
    }

    /**
     * textview 赋值
     *
     * @param tv
     * @param text
     */
    public static void setText(TextView tv, CharSequence text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setText(text);
        }
    }

    public static void setHint(TextView tv, CharSequence text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setHint(text);
        }
    }

    public static void setText(TextView tv, String text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setText(text);
        }
    }

    public static void setText(TextView tv, Spanned text) {
        if (tv != null) {
            if (text == null) {
                tv.setText("");
            } else {
                tv.setText(text);
            }
        }
        if (tv != null && text != null) {
            tv.setText(text);
        }
    }

    public static void setTextHtml(TextView tv, String text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setText(Html.fromHtml(text));
        }
    }

    public static void setTextSegmentedDisplay(TextView tv, SpannableString ss) {
        if (tv != null && ss != null) {
            tv.setText(ss);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static void setHint(TextView tv, String text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setHint(text);
        }
    }

    public static void setHint(EditText tv, String text) {
        if (tv != null) {
            if (text == null)
                text = "";
            tv.setHint(text);
        }
    }

    /**
     * 设置view 的宽
     *
     * @param view
     * @param width
     */
    public static void setWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public static void setWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
    }

    /**
     * 高亮显示key
     *
     * @param tv
     * @param subject
     * @param searchKey
     * @param isDisSize 是否区分大小写  ture=区分
     */
    public static void setHighLightKey(TextView tv, String subject, String searchKey, int defColorRes, int selectColorRes, boolean isDisSize) {
        tv.setTextColor(FlyerApplication.getContext().getColor(defColorRes));
        if (!TextUtils.isEmpty(subject) && !TextUtils.isEmpty(searchKey)) {

            int index = subject.indexOf(searchKey);

            if (!isDisSize) {
                String content = subject.toUpperCase();
                String key = searchKey.toUpperCase();
                index = content.indexOf(key);
            }

            if (index == -1) {
                WidgetUtils.setText(tv, subject);
            } else {
                String str1 = subject.substring(0, index);
                String str2 = subject.substring(index, index + searchKey.length());
                String str3 = subject.substring(index + searchKey.length(), subject.length());

                List<SegmentedStringMode> modeList = new ArrayList<>();
                if (!TextUtils.isEmpty(str1)) {
                    SegmentedStringMode mode1 = new SegmentedStringMode(str1, R.dimen.font_size_16, defColorRes, null);
                    modeList.add(mode1);
                }
                SegmentedStringMode mode2 = new SegmentedStringMode(str2, R.dimen.app_body_size_1, selectColorRes, null);
                modeList.add(mode2);
                if (!TextUtils.isEmpty(str3)) {
                    SegmentedStringMode mode3 = new SegmentedStringMode(str3, R.dimen.app_body_size_1, defColorRes, null);
                    modeList.add(mode3);
                }
                tv.setText(DataUtils.getSegmentedDisplaySs(modeList));
            }
        } else {
            WidgetUtils.setText(tv, subject);
        }
    }

    /**
     * 高亮 不区分大小写
     *
     * @param tv
     * @param subject
     * @param searchKey
     */
    public static void setHighLightKey(TextView tv, String subject, String searchKey) {
        setHighLightKey(tv, subject, searchKey, R.color.text_black, R.color.text_orange, false);
    }


}
