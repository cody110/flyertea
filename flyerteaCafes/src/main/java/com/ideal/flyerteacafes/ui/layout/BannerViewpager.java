package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AdvertAdapter2;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告viewpager 通用页面
 * Created by fly on 2017/12/12.
 */

public class BannerViewpager<T> extends RelativeLayout {


    /**
     * 当前 页面 设置接口
     *
     * @param <T>
     */
    public interface IBannerSetting<T> {
        /**
         * 设置banner imageview
         * 需实现绑定图片，点击方法
         *
         * @param igv
         * @param data
         */
        void bannerIgvSetting(ImageView igv, T data);
    }

    public IBannerSetting iBannerSetting;

    @ViewInject(R.id.banner_viewpager)
    private ViewPager banner_viewpager;
    @ViewInject(R.id.banner_select_layout)
    private LinearLayout banner_select_layout;

    private int bannerSize;
    private int advertPage = 0;// 表示广告集合的位置
    private View[] imgArray;// 装ImageView数组
    private List<ImageView> dots;// ViewPage上显示的点
    private static final int TIME = 3000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (banner_select_layout != null) {
                setTimeViewpagerSelectPage();
                super.handleMessage(msg);
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        public void run() {
            // 每隔多长时间执行一次
            mHandler.postDelayed(mRunnable, TIME);
            advertPage++;
            mHandler.sendEmptyMessage(advertPage);
        }

    };

    public BannerViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.include_banner_viewpager, this);
        x.view().inject(this, this);
    }

    public void bindData(List<T> bannerInfoList, IBannerSetting<T> iSetting) {
        this.iBannerSetting = iSetting;
        bannerSize = bannerInfoList == null ? 0 : bannerInfoList.size();

        if (bannerSize > 0) {
            dots = new ArrayList<>();
            banner_select_layout.removeAllViews();


            if (bannerSize > 1) {
                for (int i = 0; i < bannerSize; i++) {
                    ImageView dian_img = new ImageView(getContext());
                    dian_img.setImageResource(R.drawable.ic_dot_black);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(3, 3, 3, 3);
                    banner_select_layout.addView(dian_img, lp);
                    dots.add(dian_img);
                }
            }

            if (bannerSize == 1) {
                imgArray = new View[bannerInfoList.size()];
                imgArray[0] = createImageview(bannerInfoList.get(0));
            } else if (bannerSize > 1) {
                imgArray = new View[bannerInfoList.size() + 2];
                for (int i = 0; i < bannerSize; i++) {
                    imgArray[i + 1] = createImageview(bannerInfoList.get(i));
                }
                imgArray[0] = createImageview(bannerInfoList.get(bannerSize - 1));
                imgArray[bannerSize + 1] = createImageview(bannerInfoList.get(0));
            }
            AdvertAdapter2 vpIntroAdapter = new AdvertAdapter2(imgArray);
            banner_viewpager.setAdapter(vpIntroAdapter);
            banner_viewpager.setOnPageChangeListener(new PageChange());

            banner_viewpager.setCurrentItem(bannerSize > 1 ? 1 : 0, false);

        } else {
            setVisibility(View.GONE);
        }
    }


    /**
     * 创建广告未的图片
     *
     * @param bannerInfo
     * @return
     */
    private View createImageview(final T bannerInfo) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.advert_layout, null);
        view.findViewById(R.id.advert_mark).setVisibility(View.GONE);
        final ImageView imageView = (ImageView) view.findViewById(R.id.advert_img);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.drawable.post_def);

        if (iBannerSetting != null) {
            iBannerSetting.bannerIgvSetting(imageView, bannerInfo);
        }

        return view;
    }


    /**
     * ViewPage 切换
     */
    class PageChange implements ViewPager.OnPageChangeListener {

        public PageChange() {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (bannerSize == 1)
                return;

            banner_viewpager.getParent().requestDisallowInterceptTouchEvent(
                    true);

            advertPage = arg0;
            setDotsImg();
            setSendAdvTongji();

            /**手动滚动之后，重置定时轮播*/
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, TIME);
        }

        @Override
        public void onPageScrollStateChanged(int status) {
            if (status == 0) {
                setScrollViewpagerSelectPage();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }


    /**
     * 手指滚动
     * viewpage首位多加一页为无线循环滚动 ，
     * 首位之前，跳转到末尾（N）
     * 末位之后，跳转到首位（1）
     */
    private void setScrollViewpagerSelectPage() {
        if (bannerSize > 1) { //多于1，才会循环跳转
            if (advertPage < 1) { //首位之前，跳转到末尾（N）
                advertPage = bannerSize;
                banner_viewpager.setCurrentItem(advertPage, false);
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                banner_viewpager.setCurrentItem(1, false); //false:不显示跳转过程的动画
                advertPage = 1;
            }
        }
    }


    /**
     * 定时器滚动
     * viewpage首位多加一页为无线循环滚动 ，
     * 首位之前，跳转到末尾（N）
     * 末位之后，跳转到首位（1）
     */
    private void setTimeViewpagerSelectPage() {
        if (bannerSize > 1) { //多于1，才会循环跳转
            if (advertPage < 1) { //首位之前，跳转到末尾（N）
                advertPage = bannerSize;
                banner_viewpager.setCurrentItem(bannerSize - 1, false);
                banner_viewpager.setCurrentItem(advertPage, true);
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                banner_viewpager.setCurrentItem(0, false);
                banner_viewpager.setCurrentItem(1, true); //false:不显示跳转过程的动画
                advertPage = 1;
            } else {
                banner_viewpager.setCurrentItem(advertPage, true);
            }
        }
    }


    /**
     * 设置banner位显示位置的标识位点点
     */
    private void setDotsImg() {
        if (bannerSize > 1) {
            for (ImageView igv : dots) {
                igv.setImageResource(R.drawable.ic_dot_black);
            }
            if (advertPage < 1) { //首位之前，跳转到末尾（N）
                dots.get(bannerSize - 1).setImageResource(R.drawable.ic_dot_red);
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                dots.get(0).setImageResource(R.drawable.ic_dot_red);
            } else {
                dots.get(advertPage - 1).setImageResource(R.drawable.ic_dot_red);
            }
        }

    }

    /**
     * 发送统计
     */
    private void setSendAdvTongji() {
//        int index = 0;
//        if (bannerSize > 1) {
//            if (advertPage < 1) { //首位之前，跳转到末尾（N）
//                index = bannerSize - 1;
//            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
//                index = 0;
//            } else {
//                index = advertPage - 1;
//            }
//        }
//        AdvertBean bean = DataTools.getBeanByListPos(mPresenter.advertList, index);
//        if (bean != null) {
//            AdvertStatisticsManger.getInstance().executeCode(FlyerApplication.getContext(), bean.getPvtrackcode());
//        }

    }

}
