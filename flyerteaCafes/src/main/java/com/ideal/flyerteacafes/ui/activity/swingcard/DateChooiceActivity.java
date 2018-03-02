package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andexert.calendarlistview.library.CalendarDay;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SelectedDays;
import com.baoyz.pg.PG;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.IntentKey;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Cindy on 2017/4/14.
 */
@ContentView(R.layout.activity_date_chooice)
public class DateChooiceActivity extends BaseActivity implements com.andexert.calendarlistview.library.DatePickerController {

    @ViewInject(R.id.pickerView)
    DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initViews();
    }

    @Event(R.id.toolbar_left)
    private void onclick(View v) {
        finish();
    }

    @Override
    public void initViews() {
        super.initViews();
        dayPickerView.setController(this);
        CalendarDay first = getIntent().getParcelableExtra("first");
        CalendarDay last = getIntent().getParcelableExtra("last");
        dayPickerView.setDefDay(first, last);
        smoothMoveToPosition(dayPickerView, 12);
        dayPickerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(dayPickerView, mToPosition);
                }
            }
        });
    }


    @Override
    public int getMaxYear() {
        return 2048;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }

    @Override
    public void onDateRangeSelected(SelectedDays<CalendarDay> selectedDays) {
        if (selectedDays == null || selectedDays.getFirst() == null || selectedDays.getLast() == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentKey.DataChoose.START_TIME, PG.convertParcelable(selectedDays.getFirst()));
        bundle.putParcelable(IntentKey.DataChoose.END_TIME, PG.convertParcelable(selectedDays.getLast()));
        jumpActivitySetResult(bundle);
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
// 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
// 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
// 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
// 跳转位置在第一个可见项之后，最后一个可见项之前
// smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
// 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
// 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
