package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AdvertAdapter;
import com.ideal.flyerteacafes.model.entity.UserCardBean;
import com.ideal.flyerteacafes.ui.controls.FlyRoundImageView;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DeviceUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/6/5.
 */

public class RegularCardInfoVH extends BaseViewHolder {


    private IActionListener iActionListener;

    public interface IActionListener {


        void actionClick(View view);

        void onPageSelected(int position);

    }

    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.change_btn, R.id.delete_btn, R.id.type_layout, R.id.grade_layout, R.id.id_layout, R.id.img_layout})
    private void click(View v) {
        iActionListener.actionClick(v);
    }

    @ViewInject(R.id.viewPagerContainer)
    private RelativeLayout mViewPagerContainer;
    @ViewInject(R.id.viewpager)
    public ViewPager mViewPager;
    @ViewInject(R.id.type_layout)
    public LtctriLayout type_layout;
    @ViewInject(R.id.status_layout)
    public LtctriLayout status_layout;
    @ViewInject(R.id.grade_layout)
    public LtctriLayout grade_layout;
    @ViewInject(R.id.img_layout)
    public View img_layout;
    @ViewInject(R.id.id_layout)
    public LtreLayout id_layout;
    @ViewInject(R.id.rengzhen_img)
    public ImageView rengzhen_img;
    @ViewInject(R.id.change_btn)
    public TextView change_btn;


    public RegularCardInfoVH(View view, IActionListener iActionListener) {
        x.view().inject(this, view);
        status_layout.setVisibleByRi(View.INVISIBLE);
        this.iActionListener = iActionListener;


    }


    public void initViewPager(UserCardBean userCardBean, int selectIndex) {

        ImageView[] imageViews = new ImageView[userCardBean.getVips().size()];

        for (int i = 0; i < userCardBean.getVips().size(); i++) {
            FlyRoundImageView iv1 = new FlyRoundImageView(mContext);
            iv1.setType(FlyRoundImageView.TYPE_ROUND);
            iv1.setBorderRadius(DensityUtil.dip2px(5));
            DataUtils.downloadPicture(iv1, userCardBean.getVips().get(i).getIcon(), R.drawable.post_def);
            imageViews[i] = iv1;
        }

        //设置ViewPager的布局
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                DeviceUtils.getWindowWidth() / 5, DeviceUtils.getWindowWidth() / 5);


        /**** 重要部分  ******/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        mViewPager.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果
        mViewPagerContainer.setClipChildren(false);


        mViewPager.setLayoutParams(params);
        //为ViewPager设置PagerAdapter
        mViewPager.setAdapter(new AdvertAdapter(imageViews));
        //设置ViewPager切换效果，即实现画廊效果
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置预加载数量
        mViewPager.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        mViewPager.setPageMargin(DensityUtil.dip2px(50));

        //将容器的触摸事件反馈给ViewPager
        mViewPagerContainer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return mViewPager.dispatchTouchEvent(event);
            }

        });
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setCurrentItem(selectIndex);
        bindData(userCardBean, userCardBean.getVips().get(selectIndex));

    }


    public void bindData(UserCardBean userCardBean, UserCardBean.CardInfo cardInfo) {
        type_layout.setTextByCt(cardInfo.getMembership_name());
        grade_layout.setTextByCt(cardInfo.getName());
        id_layout.setTextByRe(cardInfo.getCode());
        DataUtils.downloadPicture(rengzhen_img, cardInfo.getImg_url(), R.drawable.icon_def);
        status_layout.setTextByCt(cardInfo.getStatus_desc());


        //TODO status 0 :未填写， 1 审核中  2 通过审核  3 已拒绝
        if (TextUtils.equals(userCardBean.getStatus(), "1")) {
            change_btn.setText("审核中");
        } else {
            change_btn.setText("保存");
        }

    }


    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.2f;
        private static final float MIN_SCALE = 1.0f;//0.85f

        @Override
        public void transformPage(View view, float position) {
            //setScaleY只支持api11以上
            if (position < -1) {
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
//              Log.e("TAG", view + " , " + position + "");
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    view.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    view.setTranslationX(scaleFactor * 2);
                }
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]

                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);

            }
        }

    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            iActionListener.onPageSelected(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPagerContainer != null) {
                mViewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void disableButton() {
        type_layout.setEnabled(false);
        grade_layout.setEnabled(false);
        img_layout.setEnabled(false);
        change_btn.setEnabled(false);
        id_layout.setFocusableByEdit(false);
    }


}
