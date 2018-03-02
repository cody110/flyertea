package com.ideal.flyerteacafes.ui.controls;

import de.greenrobot.event.EventBus;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class PostDetailsListView extends ListView {

    public PostDetailsListView(Context context) {
        super(context);
    }

    public PostDetailsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PostDetailsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Main", "down");
                // 手指按下时，记录按下时的横坐标
                xDown = event.getRawX();
                yDown = event.getRawY();
                EventBus.getDefault().post(true);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Main", "move");
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
                xMove = event.getRawX();
                yMove = event.getRawY();
                int distanceX = (int) (xMove - xDown);
                int distanceY = (int) (yMove - yDown);
                if (distanceX > 10 || distanceY > 10) {
                    EventBus.getDefault().post(false);
                }
                Log.i("Main:", "distanceX;" + distanceX);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Main", "up");
                // 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
                xUp = event.getRawX();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 记录手指按下时的横坐标。
     */
    private float xDown;
    private float yDown;
    /**
     * 记录手指移动时的横坐标。
     */
    private float xMove;
    private float yMove;

    /**
     * 记录手机抬起时的横坐标。
     */
    private float xUp;

}
