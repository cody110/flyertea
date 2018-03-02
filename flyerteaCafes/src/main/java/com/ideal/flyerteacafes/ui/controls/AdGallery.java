package com.ideal.flyerteacafes.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class AdGallery extends Gallery {

    public boolean isTouch = false;

    /*	private Timer timer = new Timer();
        private TimerTask task = new TimerTask() {

            @Override
            public void run() {
                   isTouch = false;
                   this.cancel();
            }
        };*/
    public AdGallery(Context context) {
        super(context);
    }

    public AdGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int keyCode;
        /*if(timer != null){
            if(task != null)
				task.cancel();
			task = new TimerTask() {
				
				@Override
				public void run() {
			    	   isTouch = false;
				    	   this.cancel();
				    }
			};
			timer.schedule(task, 3000);
		}*/
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return false;

    }

	/*public void StopTimer(){
        if(timer != null){
			if(task != null){
				task.cancel();
				task = null;
			}
			timer.purge();
			timer.cancel();
			timer = null;
		}
	}*/

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        if (!isTouch) {
            isTouch = true;

			/*if(timer != null){
				if(task != null)
					task.cancel();
				task = new TimerTask() {
					
					@Override
					public void run() {
				    	   isTouch = false;
					    	   this.cancel();
					    }
				};
				timer.schedule(task, 3000);
			}*/
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

}
