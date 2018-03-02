package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ViewpagerBiaoQingAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情viewpager fragment
 *
 * @author fly
 */
public class LookFragment extends Fragment {

    @ViewInject(R.id.replypost_viewpager)
    private ViewPager bqViewPager;
    @ViewInject(R.id.landing_diandian)
    private LinearLayout dian_layout;

    List<Fragment> fmList = new ArrayList<Fragment>();
    private List<SmileyBean> smileyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_look_layout, container, false);
        x.view().inject(this, view);
        // 得到表情数据
        BaseHelper helper = BaseHelper.getInstance();
        smileyList = helper.getListAll(SmileyBean.class);
        // 表情模块初始化
        if (!DataUtils.isEmpty(smileyList)) {
            int bqLength = 23;
            int length = 0;
            if (smileyList.size() % bqLength > 0) {
                length = smileyList.size() / bqLength + 1;
            } else {
                length = smileyList.size() / bqLength;
            }
            for (int i = 1; i <= length; i++) {// 创建几页表情
                List<SmileyBean> list = new ArrayList<SmileyBean>();
                for (int j = (i - 1) * bqLength; j < i * bqLength; j++) {// 得到对应页的表情集合
                    if (j < smileyList.size()) {
                        list.add(smileyList.get(j));
                    } else {
                        list.add(new SmileyBean());
                    }

                }

                if (list.size() == bqLength) {
                    SmileyBean bean = new SmileyBean();
                    bean.setCode("");
                    bean.setImage("close");
                    bean.setIid(R.drawable.smiley_close);
                    list.add(bean);
                }

                BiaoQingFragment fragment = new BiaoQingFragment(list);
                fmList.add(fragment);
            }
            ViewpagerBiaoQingAdapter adapter = new ViewpagerBiaoQingAdapter(getChildFragmentManager(), fmList);
            bqViewPager.setAdapter(adapter);


            final List<ImageView> dots = new ArrayList<ImageView>();
            for (int i = 0; i < length; i++) {
                ImageView dian_img = new ImageView(getActivity());
                dian_img.setImageResource(R.drawable.ic_dot_black);
                dian_img.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(8), DensityUtil.dip2px(8));
                lp.setMargins(DensityUtil.dip2px(3), 0, DensityUtil.dip2px(3), 0);
                dian_layout.addView(dian_img, lp);
                dots.add(dian_img);
            }

            bqViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                private int oldPosition = 0;

                @Override
                public void onPageSelected(int arg0) {
                    bqViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                    if (oldPosition < dots.size())
                        dots.get(oldPosition).setImageResource(R.drawable.ic_dot_black);
                    if (oldPosition < dots.size())
                        dots.get(arg0).setImageResource(R.drawable.black_circle);
                    oldPosition = arg0;
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });

            dots.get(0).setImageResource(R.drawable.black_circle);


        }

        return view;
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class
//                    .getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
