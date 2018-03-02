package com.ideal.flyerteacafes.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 广告
 *
 * @author fly
 */
public class AdvertAdapter extends PagerAdapter {

    private ImageView[] arrayImg;

    public AdvertAdapter(ImageView[] arrayImg) {
        this.arrayImg = arrayImg;
    }

    @Override
    public int getCount() {
        return arrayImg.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        // TODO Auto-generated method stub
        ((ViewPager) arg0).removeView(arrayImg[arg1]);
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        // TODO Auto-generated method stub
        try {
            ((ViewPager) arg0).addView(arrayImg[arg1]);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arrayImg[arg1];

    }

}
