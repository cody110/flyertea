package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SelectItemAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.utils.ToastUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2018/1/21.
 */

public class SearchSortPopupwindow extends PopupWindow {

    protected View mView;
    protected Context mContext;
    @ViewInject(R.id.search_sort_list)
    private ListView mListview;

    public SearchSortPopupwindow(Context context) {
        mContext = context;
        initPop();
        bindData();
    }

    public void initPop() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_search_sort, null);
        x.view().inject(this, mView);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mListview.getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    private void bindData() {
        final List<String> datas = new ArrayList<>();
        datas.add("最新发帖");
        datas.add("回复最多");
        datas.add("收藏最多");


        final SelectItemAdapter<String> adapter = new SelectItemAdapter<String>(mContext, datas, R.layout.item_search_sort) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.text_search_sort, s);
                if (holder.getPosition() == selectIndex) {
                    holder.setTextColorRes(R.id.text_search_sort, R.color.text_blue);
                } else {
                    holder.setTextColorRes(R.id.text_search_sort, R.color.text_black);
                }
            }
        };
        mListview.setAdapter(adapter);
        mListview.setDivider(mContext.getResources().getDrawable(R.drawable.line_1px));

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = null;
                switch (position) {
                    case 0:
                        value = "dateline";
                        break;
                    case 1:
                        value = "reply";
                        break;
                    case 2:
                        value = "favtimes";
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderby", value);
                bundle.putString("name", datas.get(position));
                TagEvent tagEvent = new TagEvent(TagEvent.TAG_SEARCH_SORT);
                tagEvent.setBundle(bundle);
                EventBus.getDefault().post(tagEvent);
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_SEARCH_SORT));
    }

}
