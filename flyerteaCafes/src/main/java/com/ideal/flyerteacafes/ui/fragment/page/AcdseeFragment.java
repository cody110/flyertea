package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PhotoPagerAdapter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.List;

/**
 * Created by fly on 2015/11/23.
 */
public class AcdseeFragment extends BaseFragment {

    public interface vpListener {
        void onPageSelected(int index);
    }

    private vpListener mListener;
    protected ViewPager mViewPager;
    private PhotoPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.root_viewpager, container, false);
        mViewPager = V.f(view, R.id.root_viewpager);
        return view;
    }

    public void setvpListener(vpListener listener) {
        mListener = listener;
    }

    public void setAdapterImageClickI(PhotoPagerAdapter.imageClick imageClick ){
        if(adapter!=null){
            adapter.setIimageClick(imageClick);
        }
    }

    public void setUrlDatas(List<String> urlList,int index) {
        adapter=new PhotoPagerAdapter(getActivity(),urlList);
        mViewPager.setAdapter(adapter);
        if (urlList.size() > index) {
            mViewPager.setCurrentItem(index);
            if (mListener != null) {
                mListener.onPageSelected(index);
            }
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    mListener.onPageSelected(arg0);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
