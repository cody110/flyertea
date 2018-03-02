package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2018/1/22.
 */

public class SearchScreenLayout extends LinearLayout {

    @ViewInject(R.id.tv_all_forum)
    TextView tv_all_forum;
    @ViewInject(R.id.igv_all_forum)
    ImageView igv_all_forum;
    @ViewInject(R.id.tv_sort)
    TextView tv_sort;
    @ViewInject(R.id.igv_sort)
    ImageView igv_sort;

    public SearchScreenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_search_screen, this);
        x.view().inject(this);
    }


    /**
     * 设置版块点击状态
     *
     * @param isSelect
     */
    public void setSelectForum(boolean isSelect) {
        if (isSelect) {
            tv_all_forum.setTextColor(getContext().getResources().getColor(R.color.text_blue));
            igv_all_forum.setImageResource(R.drawable.down_blue);
            igv_all_forum.setRotation(180);
        } else {
            tv_all_forum.setTextColor(getContext().getResources().getColor(R.color.text_light_grey));
            igv_all_forum.setImageResource(R.drawable.down_grey);
            igv_all_forum.setRotation(0);
        }
    }

    /**
     * 排序点击状态
     *
     * @param isSelect
     */
    public void setSelectSort(boolean isSelect) {
        if (isSelect) {
            tv_sort.setTextColor(getContext().getResources().getColor(R.color.text_blue));
            igv_sort.setImageResource(R.drawable.down_blue);
            igv_sort.setRotation(180);
        } else {
            tv_sort.setTextColor(getContext().getResources().getColor(R.color.text_light_grey));
            igv_sort.setImageResource(R.drawable.down_grey);
            igv_sort.setRotation(0);
        }
    }

    /**
     * 指定版块
     */
    public void setForumAppoint() {
        setForumName("指定版块");
        igv_all_forum.setVisibility(View.GONE);
    }

    public void setForumName(String value) {
        WidgetUtils.setText(tv_all_forum, value);
    }

    public void setSortName(String value) {
        WidgetUtils.setText(tv_sort, value);
    }

}
