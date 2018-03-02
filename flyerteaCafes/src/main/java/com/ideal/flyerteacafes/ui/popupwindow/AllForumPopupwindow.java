package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SelectItemAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.ThreadSearchForumthreads;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2018/1/19.
 */

public class AllForumPopupwindow extends PopupWindow {

    protected View mView;
    protected Context mContext;


    @ViewInject(R.id.all_forum_gv)
    GridView mGridview;

    public AllForumPopupwindow(Context context) {
        mContext = context;
        initPop();
    }

    public void initPop() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_all_forum, null);
        x.view().inject(this, mView);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
                int height = mGridview.getBottom();
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


    public void bindData(List<ThreadSearchForumthreads> forumthreads) {
        final SelectItemAdapter<ThreadSearchForumthreads> adapter = new SelectItemAdapter<ThreadSearchForumthreads>(mContext, forumthreads, R.layout.item_search_forum) {
            @Override
            public void convert(ViewHolder holder, ThreadSearchForumthreads threadSearchForumthreads) {
                holder.setBackgroundRes(R.id.text_search_sort, R.drawable.grey_frame_1px);
                holder.setText(R.id.text_search_sort, threadSearchForumthreads.getFname() + "(" + threadSearchForumthreads.getThreads() + ")");
                if (holder.getPosition() == selectIndex) {
                    holder.setTextColorRes(R.id.text_search_sort, R.color.text_blue);
                } else {
                    holder.setTextColorRes(R.id.text_search_sort, R.color.text_black);
                }

            }
        };
        mGridview.setAdapter(adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectIndex(position);
                ThreadSearchForumthreads data = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                TagEvent tagEvent = new TagEvent(TagEvent.TAG_SEARCH_FORUM);
                tagEvent.setBundle(bundle);
                EventBus.getDefault().post(tagEvent);
                dismiss();
            }
        });



    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_SEARCH_FORUM));
    }
}
