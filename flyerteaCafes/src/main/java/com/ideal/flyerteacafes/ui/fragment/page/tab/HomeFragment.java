package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.HotKeyBean;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.messagecenter.MessageCenterActivity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.DiscountFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.RaidersFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.RecommendFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/6/27.
 */

public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.migv_remind_message)
    View migv_remind_message;
    @ViewInject(R.id.task_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.task_viewpager)
    ViewPager mViewPager;
    @ViewInject(R.id.thread_search_edittext)
    TextView thread_search_edittext;
    @ViewInject(R.id.btn_search)
    LinearLayout btn_search;
    @ViewInject(R.id.icon_sign)
    ImageView icon_sign;


    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;
    List<HotKeyBean.DefaultWord> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fm_new_home, container, false);
        x.view().inject(this, view);

        initPage();

        BaseDataManger.getInstance().registerIMsgNum(new BaseDataManger.IMsgNum() {
            @Override
            public void msgNum(NumberBean numBean) {
                if (numBean.getNewprompt() > 0 || numBean.getNewpm() > 0) {
                    migv_remind_message.setVisibility(View.VISIBLE);
                } else {
                    migv_remind_message.setVisibility(View.INVISIBLE);
                }
            }

        });
        requestHotkey();
        requestIsSignin();
        return view;
    }


    private void initPage() {
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();


        typeModeList.add(new TypeMode().setName("推荐"));
        typeModeList.add(new TypeMode().setName("优惠"));
        typeModeList.add(new TypeMode().setName("攻略"));
        typeModeList.add(new TypeMode().setName("工具"));


        mListFragments.add(new RecommendFragment());
        mListFragments.add(new DiscountFragment());
        mListFragments.add(new RaidersFragment());
        mListFragments.add(new ServiceFragment());

        mViewPagerAdapter = new PagerIndicatorAdapter(getChildFragmentManager(), mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", typeModeList.get(position).getName());
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.index_tab_click, map);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Event({R.id.icon_sign, R.id.btn_search, R.id.linetool_remind})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.icon_sign:
                if (UserManger.isLogin()) {
                    toSingin();
                } else {
                    DialogUtils.showDialog(mActivity);
                }
                break;
            case R.id.btn_search:
                Bundle bundle = new Bundle();
                bundle.putString("hotWorld", thread_search_edittext.getText().toString());
                jumpActivity(ThreadSearchActivity2.class, bundle);
                break;
            case R.id.linetool_remind:
                if (UserManger.isLogin()) {
                    jumpActivity(MessageCenterActivity.class, null);
                } else {
                    DialogUtils.showDialog(mActivity);
                }
                break;
        }
    }

    /**
     * 显示攻略
     */
    public void showPromotion() {
        if (mViewPager != null)
            mViewPager.setCurrentItem(1);
    }


    /**
     * 显示攻略
     */
    public void showGonglue() {
        if (mViewPager != null)
            mViewPager.setCurrentItem(2);
    }

    /**
     * 热词接口
     */
    private void requestHotkey() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOTKEY), new ListDataCallback(ListDataCallback.LIST_KEY_DEFAULT_WORD, HotKeyBean.DefaultWord.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!DataUtils.isEmpty(result.getDataList())) {
                    datas = result.getDataList();
                    randomHotWorld();
                }
            }
        });
    }

    /**
     * 随机热词
     */
    public void randomHotWorld() {
        if (datas != null) {
            Random random = new Random();
            HotKeyBean.DefaultWord data = datas.get(random.nextInt(datas.size()));
            LogFly.e("data:" + data.getKeyword());
            thread_search_edittext.setText(data.getKeyword());
            thread_search_edittext.invalidate();
            LogFly.e("get:" + thread_search_edittext.getText());

        }
    }


    public boolean isSignin;

    /**
     * 签到
     */
    public void toSingin() {
        if (!isSignin) {
            requestSignin();
        } else {
            ToastUtils.showToast("您已签到");
        }
    }


    /**
     * 签到
     */
    private void requestSignin() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_SIGNIN), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.jsonToSignin(result);
                if (bean.getCode().equals("success")) {
                    isSignin = true;
                    bindSigninIcon();
                    EventBus.getDefault().post(FinalUtils.SIGNINTRUE);
                    MobclickAgent.onEvent(mActivity, FinalUtils.EventId.sign_in);// 签到统计
                }
                if (bean != null)
                    SmartUtil.BToast(mActivity, bean.getMessage());
            }

        });
    }

    /**
     * 是否签到
     */
    public void requestIsSignin() {
        if (UserManger.isLogin())
            XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_IS_SIGNIN), new StringCallback() {
                @Override
                public void flySuccess(String result) {
                    BaseBean bean = JsonAnalysis.jsonToSignin(result);
                    if (bean.getCode().equals("error")) {//已经签到
                        isSignin = true;
                    } else {
                        isSignin = false;
                    }
                    bindSigninIcon();
                }
            });
    }

    private void bindSigninIcon() {
        icon_sign.setImageResource(isSignin ? R.mipmap.icon_sign : R.mipmap.icon_no_sign);
    }


}
