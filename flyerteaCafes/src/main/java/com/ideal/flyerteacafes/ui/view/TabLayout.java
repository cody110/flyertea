package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by fly on 2016/12/27.
 */

public class TabLayout extends HorizontalScrollView {


    LinearLayout mTabLayout;
    List<String> dataList = new ArrayList<>();
    int selectIndex;

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTabLayout = new LinearLayout(context);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    /**
     * 设置数据源
     *
     * @param dataList
     */
    public void setData(List<String> dataList) {
        this.dataList.clear();
        if (dataList != null)
            this.dataList.addAll(dataList);
        addListView();
    }



    private void addListView() {
        mTabLayout.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            createTextView(i);
        }
    }

    private void createTextView(final int index) {
        final TextView tv = new TextView(getContext());
        tv.setText(dataList.get(index));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(getResources().getDimensionPixelOffset(R.dimen.app_commen_margin), 0, getResources().getDimensionPixelOffset(R.dimen.app_commen_margin), 0);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectIndex(index);
                scrollTo(tv.getLeft(), 0);
            }
        });
        mTabLayout.addView(tv, params);
    }

    public void setSelectIndex(int index) {
        int size = mTabLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            TextView tv = (TextView) mTabLayout.getChildAt(i);
            if (i == index) {
                tv.setTextColor(getResources().getColor(R.color.app_bg_title));
                tv.setBackground(getResources().getDrawable(R.drawable.text_bottom_line_bg));
            } else {
                tv.setTextColor(getResources().getColor(R.color.app_body_grey));
                tv.setBackground(null);
            }
        }
    }

}
