package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.CommunitySubTypeAdapter;
import com.ideal.flyerteacafes.adapters.RcForumTypeAdapter;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.ui.controls.MyHorizontalScrollView;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by fly on 2016/11/29.
 * 论坛 分类
 */

public class CommunitySubTypeLayout extends HorizontalScrollView {

    IGridViewItemClick iGridViewItemClick;
    LinearLayout mTabLayout;
    int margin;
    int selectIndex;

    private List<CommunitySubTypeBean> dataList = new ArrayList<>();
    private String forumName;


    public CommunitySubTypeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        margin = getResources().getDimensionPixelOffset(R.dimen.app_commen_margin);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        mTabLayout = new LinearLayout(getContext());
        mTabLayout.setGravity(Gravity.CENTER_VERTICAL);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    /**
     * 设置数据源
     *
     * @param dataList
     */
    public void setData(List<CommunitySubTypeBean> dataList, String forumName) {
        this.forumName = forumName;
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
        setSelectIndex(0);
    }

    private void createTextView(final int index) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_tab, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, 0, margin, 0);
        TextView tv = (TextView) tabView.findViewById(R.id.search_tab_name);
        tv.setText(dataList.get(index).getName());
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("tabname", dataList.get(index).getName());
                map.put("name", forumName);
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.notelist_tab_click, map);
                if (iGridViewItemClick != null)
                    iGridViewItemClick.gridViewItemClick(index);
            }
        });
        mTabLayout.addView(tabView, params);
    }

    public void setSelectIndex(int index) {
        selectIndex = index;
        int size = mTabLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            View tabView = mTabLayout.getChildAt(i);
            TextView tv = (TextView) tabView.findViewById(R.id.search_tab_name);
            View line = tabView.findViewById(R.id.search_tab_line);
            if (i == index) {
                line.setVisibility(View.VISIBLE);
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                scrollTo(tv.getLeft() - margin, 0);
            } else {
                line.setVisibility(View.INVISIBLE);
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }

    /**
     * 设置点击
     */
    public void setItemClick(final IGridViewItemClick iGridViewItemClick) {
        this.iGridViewItemClick = iGridViewItemClick;
    }

    /**
     * 设置滚动监听
     *
     * @param listener
     */
    public void setScrollviewLinster(MyHorizontalScrollView.ScrollViewListener listener) {
        setOnScrollStateChangedListener(listener);
        scrollViewListener = listener;
    }

    /**
     * 滚动到指定位置
     *
     * @param x
     * @param y
     */
    public void setMyHorizontalScrollViewScrollTo(final int x, final int y) {
        scrollTo(x, y);
    }


    public interface ScrollViewListener {

        void onScrollChanged(MyHorizontalScrollView.ScrollType scrollType, int currentX);

    }

    private Handler mHandler = new Handler();
    private MyHorizontalScrollView.ScrollViewListener scrollViewListener;

    /**
     * 滚动状态   IDLE 滚动停止  TOUCH_SCROLL 手指拖动滚动         FLING滚动
     *
     * @author DZC
     * @version XHorizontalScrollViewgallery
     * @Time 2014-12-7 上午11:06:52
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }

    ;

    /**
     * 记录当前滚动的距离
     */
    private int currentX = 0;
    /**
     * 当前滚动状态
     */
    private MyHorizontalScrollView.ScrollType scrollType = MyHorizontalScrollView.ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (getScrollX() == currentX) {
                currentX = getScrollX();
                //滚动停止  取消监听线程
                LogFly.d("", "停止滚动");
                scrollType = MyHorizontalScrollView.ScrollType.IDLE;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType, currentX);
                }
                mHandler.removeCallbacks(this);
                return;
            } else {
                currentX = getScrollX();
                //手指离开屏幕    view还在滚动的时候
                LogFly.d("", "Fling。。。。。");
                scrollType = MyHorizontalScrollView.ScrollType.FLING;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType, currentX);
                }
            }
            mHandler.postDelayed(this, scrollDealy);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float downX = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                int moveX = (int) Math.abs(downX - ev.getX());
                if (moveX < 20) {
                    return false;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = MyHorizontalScrollView.ScrollType.TOUCH_SCROLL;
                if (scrollViewListener != null)
                    scrollViewListener.onScrollChanged(scrollType, currentX);
                //手指在上面移动的时候   取消滚动监听线程
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                //手指移动的时候
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置滚动监听
     * 2014-12-7 下午3:59:51
     *
     * @param listener
     * @return void
     * @author DZC
     * @TODO
     */
    public void setOnScrollStateChangedListener(MyHorizontalScrollView.ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }

}
