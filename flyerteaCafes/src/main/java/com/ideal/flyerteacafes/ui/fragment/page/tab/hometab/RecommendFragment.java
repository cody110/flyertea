package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AdvertAdapter2;
import com.ideal.flyerteacafes.adapters.HotInterlocutionAdapter;
import com.ideal.flyerteacafes.adapters.PostAdapter;
import com.ideal.flyerteacafes.caff.AdvertStatisticsManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.AdvertBean;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.InterlocutionBean;
import com.ideal.flyerteacafes.model.entity.UpgradeBean;
import com.ideal.flyerteacafes.ui.activity.ExerciseActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.YouzanActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.dialog.UpgradeFragment;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IHomeFm;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.HomeFmPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/5/31.
 */
public class RecommendFragment extends MVPBaseFragment<IHomeFm, HomeFmPresenter> implements IHomeFm, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {


    @ViewInject(R.id.fragment_refreshview)
    private PullToRefreshView pullToRefreshView;
    @ViewInject(R.id.fragment_refreshview_listview)
    private ListView postListview;
    @ViewInject(R.id.advert_root)
    private View advert_root;
    @ViewInject(R.id.advertisement_dian_layout)
    private LinearLayout advertisement_dian_layout;// 显示点的布局
    @ViewInject(R.id.advert_viewPager)
    private ViewPager advert_viewPager;// 广告viewpager
    @ViewInject(R.id.advertisement_bottom_layout)
    private View bottomLayout;
    @ViewInject(R.id.home_fm_popular_interaction_listview)
    private ListView home_fm_popular_interaction_listview;
    @ViewInject(R.id.home_fm_intreaction_layout)
    private View home_fm_intreaction_layout;

    private PostAdapter postAdapter;
    private HotInterlocutionAdapter hotInterlocutionAdapter;

    private int listviewHeight = 0;


    private int advertPage = 0;// 表示广告集合的位置
    private View[] imgArray;// 装ImageView数组
    private List<ImageView> dots;// ViewPage上显示的点
    private static final int TIME = 3000;

    /**
     * 是否调用onSaveInstanceState函数
     */
    private boolean isSaveInstanceState = false;
    private UpgradeBean upgradeBean;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (advert_viewPager != null) {
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

    View view;

    ImageOptions image_FIT_XY = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.fm_home, container, false);
        x.view().inject(this, view);
        initViews();
        initVariables();
        mPresenter.init(mActivity);
        pullToRefreshView.headerRefreshing();
        return view;
    }

    @Override
    public void initVariables() {
        super.initVariables();
        postListview.post(new Runnable() {
            @Override
            public void run() {
                listviewHeight = postListview.getHeight();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        isSaveInstanceState = false;
        if (upgradeBean != null) {
            showUpgradeDialog(upgradeBean);
        }
    }


    @Override
    public void initViews() {
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        postListview.setDivider(null);
        postListview.setBackgroundColor(getResources().getColor(R.color.white));
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.home_fm_header, null, false);
        x.view().inject(this, headerView);
        postListview.addHeaderView(headerView);

    }


    @Override
    protected HomeFmPresenter createPresenter() {
        return new HomeFmPresenter();
    }


    @Event({R.id.home_fm_intreaction_title_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.home_fm_intreaction_title_layout:
                jumpActivity(ExerciseActivity.class, null);
                break;
        }
    }


    @Event(value = R.id.home_fm_popular_interaction_listview, type = AdapterView.OnItemClickListener.class)
    private void interactionItemClick(AdapterView<?> parent, View view, int position, long id) {

        InterlocutionBean bean = hotInterlocutionAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(ThreadPresenter.BUNDLE_TID, bean.getTid());
        jumpActivity(ThreadActivity.class, bundle);

        Map<String, String> map = new HashMap<>();
        map.put("tid", bean.getTid());
        map.put("position", String.valueOf(position + 1));
        MobclickAgent.onEvent(mActivity, FinalUtils.EventId.index_hot_click, map);
    }

    @Event(value = R.id.fragment_refreshview_listview, type = AdapterView.OnItemClickListener.class)
    private void postItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position > 0) {
            ArticleTabBean bean = postAdapter.getItem(position - 1);
            Bundle bundle = new Bundle();
            bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(bean.getAid()));
            jumpActivity(ArticleContentActivity.class, bundle);

            Map<String, String> map = new HashMap<>();
            map.put("aid", bean.getAid() + "");
            MobclickAgent.onEvent(mActivity, FinalUtils.EventId.index_recommend_click, map);
        }
    }


    @Override
    public void callbackAdvertList(final List<AdvertBean> bannerInfoList) {
        bannerSize = bannerInfoList == null ? 0 : bannerInfoList.size();

        if (bannerSize > 0) {
            advert_root.setVisibility(View.VISIBLE);
            bottomLayout.getBackground().setAlpha(100);

            dots = new ArrayList<>();
            advertisement_dian_layout.removeAllViews();


            if (bannerSize > 1) {
                for (int i = 0; i < bannerSize; i++) {
                    ImageView dian_img = new ImageView(getActivity());
                    dian_img.setImageResource(R.drawable.ic_dot_black);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(3, 3, 3, 3);
                    advertisement_dian_layout.addView(dian_img, lp);
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
            advert_viewPager.setAdapter(vpIntroAdapter);
            advert_viewPager.setOnPageChangeListener(new PageChange());

            advert_viewPager.setCurrentItem(bannerSize > 1 ? 1 : 0, false);

        } else {
            advert_root.setVisibility(View.GONE);
        }
    }

    private int bannerSize;

    /**
     * 创建广告未的图片
     *
     * @param bannerInfo
     * @return
     */
    private View createImageview(final AdvertBean bannerInfo) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.advert_layout, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.advert_img);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.drawable.post_def);

        x.image().bind(imageView, bannerInfo.getImg_path(), image_FIT_XY);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("bid", bannerInfo.getId() + "");
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.index_banner_click, map);
                String url;
                if (TextUtils.equals(bannerInfo.getAdtype(), "code")) {
                    url = Utils.HtmlUrl.HTML_ADV + bannerInfo.getId();
                } else {
                    url = bannerInfo.getUrl();
                }
                if (DataUtils.isYouZanUrl(url)) {
                    Intent intent = new Intent(getActivity(), YouzanActivity.class);
                    intent.putExtra(YouzanActivity.SIGN_URL, url);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TbsWebActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }
        });

        return view;
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
                advert_viewPager.setCurrentItem(advertPage, false);
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                advert_viewPager.setCurrentItem(1, false); //false:不显示跳转过程的动画
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
                advert_viewPager.setCurrentItem(bannerSize - 1, false);
                advert_viewPager.setCurrentItem(advertPage, true);
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                advert_viewPager.setCurrentItem(0, false);
                advert_viewPager.setCurrentItem(1, true); //false:不显示跳转过程的动画
                advertPage = 1;
            } else {
                advert_viewPager.setCurrentItem(advertPage, true);
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

    private void setSendAdvTongji() {
        int index = 0;
        if (bannerSize > 1) {
            if (advertPage < 1) { //首位之前，跳转到末尾（N）
                index = bannerSize - 1;
            } else if (advertPage > bannerSize) { //末位之后，跳转到首位（1）
                index = 0;
            } else {
                index = advertPage - 1;
            }
        }
        AdvertBean bean = DataTools.getBeanByListPos(mPresenter.advertList, index);
        if (bean != null) {
            AdvertStatisticsManger.getInstance().executeCode(FlyerApplication.getContext(), bean.getPvtrackcode());
        }

    }


    @Override
    public void callbackInterlocution(List<InterlocutionBean> interlocutionBeanList) {
        if (hotInterlocutionAdapter == null) {
            hotInterlocutionAdapter = new HotInterlocutionAdapter(mActivity, interlocutionBeanList, R.layout.item_hot_interlocution_layout);
            home_fm_popular_interaction_listview.setAdapter(hotInterlocutionAdapter);
        } else {
            hotInterlocutionAdapter.notifyDataSetChanged();
        }
        ViewTools.setListViewHeightBasedOnChildren(home_fm_popular_interaction_listview);
        home_fm_intreaction_layout.setVisibility(View.VISIBLE);

    }

    @Override
    public void callbackPost(List<ArticleTabBean> postBeanList) {
        if (postAdapter == null) {
            postAdapter = new PostAdapter(mActivity, postBeanList, R.layout.item_home_post_layout);
            postListview.setAdapter(postAdapter);
        } else {
            postAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void pullToRefreshViewComplete() {
        pullToRefreshView.onFooterRefreshComplete();
        pullToRefreshView.onHeaderRefreshComplete();
    }

    @Override
    public void hintInterlocutionView() {
        home_fm_intreaction_layout.setVisibility(View.GONE);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        TaskUtil.postOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.onFooterRefreshComplete();
                EventBus.getDefault().post(new TagEvent(TagEvent.TAG_TAB_CHOOSE_REPORT));
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPresenter.onHeaderRefresh();
    }

    @Override
    public void refreshInterlocutionAdapter() {
        hotInterlocutionAdapter.notifyDataSetChanged();
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

            advert_viewPager.getParent().requestDisallowInterceptTouchEvent(
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


    public void footerRefreshSetListviewScrollLocation(int oldSize) {
        postListview.setSelectionFromTop(oldSize, listviewHeight - pullToRefreshView.mFooterViewHeight);
    }

    @Override
    public void showUpgradeDialog(UpgradeBean upgradeBean) {
        if (!isSaveInstanceState) {
            removeDialogFragment("UpgradeFragment");
            Bundle bundle = new Bundle();
            bundle.putSerializable(UpgradeFragment.BUNDLE_DATA, upgradeBean);
            UpgradeFragment dialog = new UpgradeFragment();
            dialog.setArguments(bundle);
            dialog.show(getChildFragmentManager(), "UpgradeFragment");
            if (this.upgradeBean != null) {
                this.upgradeBean = null;
            }
        } else {
            this.upgradeBean = upgradeBean;
        }
    }

    protected void removeDialogFragment(String tag) {
        android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        android.support.v4.app.Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (null != fragment) {
            ft.remove(fragment);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isSaveInstanceState = true;
    }
}
