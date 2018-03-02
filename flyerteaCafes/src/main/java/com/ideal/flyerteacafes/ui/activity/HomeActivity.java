package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.JPushReceiver;
import com.ideal.flyerteacafes.caff.NetworkConnectChangedReceiver;
import com.ideal.flyerteacafes.caff.ReadsManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.dal.RoughDraftHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.MessageCenterActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.fragment.page.tab.HomeFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.MyFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.ReportFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.ChooseCommunityFm;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.CommunityPlateFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.ReadThreadFragment;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {


    @ViewInject(R.id.home_tab_home)
    View homeView;
    @ViewInject(R.id.home_tab_forum)
    View forumView;
    @ViewInject(R.id.home_tab_me)
    View meView;
    @ViewInject(R.id.home_tab_thread)
    View threadView;
    @ViewInject(R.id.home_tab_report)
    View reportView;

    private ImageView reportImg;
    private TextView reportText;
    private ImageView homeImg;
    private TextView homeText;
    private ImageView forumImg;
    private TextView forumText;
    private ImageView meImg;
    private TextView meText;
    private ImageView meRedImg;

    private HomeFragment homeFragment;
    private CommunityPlateFragment forumFragment;
    private ChooseCommunityFm chooseCommunityFm;
    private MyFragment myFragment;
    private ReportFragment reportFragment;
    private ReadThreadFragment raidersFragment;

    private long exitTime = 0;
    private UserBean userBean;
    private ImageView threadImg;
    private TextView threadText;


    private int BOOK_TAG = R.id.home_tab_report;
    private int FORUM_AG = R.id.home_tab_forum;
    private int choose_tag = R.id.home_tab_home;

    /**
     * 默认显示社区首页
     */
    private boolean isCommunityFragment = true;

    /**
     * 选择关注版块 是否是第一次
     */
    private boolean isFirstChooseForum = false;


    /**
     * 社区版块按钮显示论坛首页
     */
    public void setCommunityFragment() {
        isCommunityFragment = true;
        if (choose_tag == FORUM_AG) {
            chooseTabView(R.id.home_tab_forum);
            ChooseTabFragment(R.id.home_tab_forum);
        }
    }

    /**
     * 社区版块按钮显示关注版块
     */
    public void setChooseForumFragment(boolean isFirstChoose) {
        isCommunityFragment = false;
        isFirstChooseForum = isFirstChoose;
        if (choose_tag == FORUM_AG) {
            chooseTabView(R.id.home_tab_forum);
            ChooseTabFragment(R.id.home_tab_forum);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        if (savedInstanceState != null) {
            choose_tag = savedInstanceState.getInt("choose_tag");
        }
        chooseTabView(choose_tag);
        ChooseTabFragment(choose_tag);
        registerMyReceiver();

        disposeJPush();

        AmapLocation.getInstance().init();
        ReadsManger.getInstance().upload();
        BaseDataManger.getInstance().registerIMsgNum(new BaseDataManger.IMsgNum() {
            @Override
            public void msgNum(NumberBean numBean) {
                WidgetUtils.setVisible(meRedImg, numBean.getTasktodraw() > 0);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("choose_tag", choose_tag);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        int code = intent.getIntExtra("code", 0);
        switch (code) {
            case FinalUtils.HOME_LOGIN:
                userBean = UserManger.getUserInfo();
                if (myFragment != null)
                    myFragment.loginUser(userBean);
                break;

            case FinalUtils.HOME_GONGLUE:
                showGonelue();
                break;

            case FinalUtils.HOME_READ_POST:
                chooseTabView(R.id.home_tab_thread);
                ChooseTabFragment(R.id.home_tab_thread);
                break;

            case FinalUtils.HOME_THREAD_HOT:
                showReadThreadHot();
                break;


            case FinalUtils.HOME_THREAD_DIGEST:
                showReadThreadDigest();
                break;


            case FinalUtils.HOME_TO_FORUM:
                chooseTabView(R.id.home_tab_forum);
                ChooseTabFragment(R.id.home_tab_forum);
                break;

            case FinalUtils.HOME_REPORT:
                chooseTabView(R.id.home_tab_report);
                ChooseTabFragment(R.id.home_tab_report);
                break;

            case FinalUtils.HOME_TO_MYTHREAD:
                chooseTabView(R.id.home_tab_me);
                ChooseTabFragment(R.id.home_tab_me);
                jumpActivity(MyThreadActivity.class, null);
                break;

            case FinalUtils.HOME_PROMOTION:
                showPromotion();
                break;

            default:
                break;
        }
    }

    private void initView() {
        // SystemBarUtils.initSystemBar(this);
        homeImg = (ImageView) homeView.findViewById(R.id.home_bottom_tab_img);
        homeText = (TextView) homeView.findViewById(R.id.home_bottom_tab_text);
        forumImg = (ImageView) forumView.findViewById(R.id.home_bottom_tab_img);
        forumText = (TextView) forumView.findViewById(R.id.home_bottom_tab_text);
        meImg = (ImageView) meView.findViewById(R.id.home_bottom_tab_img);
        meRedImg = (ImageView) meView.findViewById(R.id.home_is_red_img);
        threadImg = V.f(threadView, R.id.home_bottom_tab_img);
        threadText = V.f(threadView, R.id.home_bottom_tab_text);
        meText = (TextView) meView.findViewById(R.id.home_bottom_tab_text);
        homeText.setText(getString(R.string.drawer_home_page));
        forumText.setText(getString(R.string.drawer_forum));
        meText.setText(getString(R.string.home_tab_me));
        threadText.setText("读帖");

        reportImg = V.f(reportView, R.id.home_bottom_tab_img);
        reportText = V.f(reportView, R.id.home_bottom_tab_text);
        reportText.setText("报告");

    }

    @SuppressWarnings("unchecked")
    private void initData() {
        int count = preferences.getIntToKey("count");
        count++;
        preferences.savaToInt("count", count);
        preferences.commit();
    }

    @Event({R.id.home_tab_home, R.id.home_tab_forum, R.id.home_tab_thread, R.id.home_tab_report, R.id.home_tab_me})
    private void tabChange(View v) {

        chooseTabView(v.getId());
        ChooseTabFragment(v.getId());
    }


    /**
     * 提供给homeFragment 跳转到直播Fragment
     */
    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    chooseTabView(R.id.home_tab_thread);
                    ChooseTabFragment(R.id.home_tab_thread);
                    break;
            }
        }
    };

    private void chooseTabView(int id) {
        homeImg.setImageResource(R.drawable.tabbar_home);
        forumImg.setImageResource(R.drawable.tabbar_forum);
        reportImg.setImageResource(R.drawable.tabbar_report);
        meImg.setImageResource(R.drawable.tabbar_me);
        threadImg.setImageResource(R.drawable.tabbar_thread);
        homeText.setTextColor(getResources().getColor(R.color.home_not_selected));
        forumText.setTextColor(getResources().getColor(R.color.home_not_selected));
        meText.setTextColor(getResources().getColor(R.color.home_not_selected));
        reportText.setTextColor(getResources().getColor(R.color.home_not_selected));
        threadText.setTextColor(getResources().getColor(R.color.home_not_selected));

        HashMap<String, String> map = new HashMap<>();

        if (id == R.id.home_tab_home) {
            map.put("name", homeText.getText().toString());
            homeImg.setImageResource(R.drawable.tabbar_home_selected);
            homeText.setTextColor(getResources().getColor(R.color.home_selected));
        } else if (id == R.id.home_tab_forum) {
            map.put("name", forumText.getText().toString());
            forumImg.setImageResource(R.drawable.tabbar_forum_select);
            forumText.setTextColor(getResources().getColor(R.color.home_selected));
        } else if (id == R.id.home_tab_thread) {
            map.put("name", threadText.getText().toString());
            threadImg.setImageResource(R.mipmap.tabbar_refresh);
            threadText.setTextColor(getResources().getColor(R.color.home_selected));
        } else if (id == R.id.home_tab_report) {
            map.put("name", reportText.getText().toString());
            reportImg.setImageResource(R.drawable.tabbar_report_select);
            reportText.setTextColor(getResources().getColor(R.color.home_selected));
        } else if (id == R.id.home_tab_me) {
            map.put("name", meText.getText().toString());
            meImg.setImageResource(R.drawable.tabbar_me_selected);
            meText.setTextColor(getResources().getColor(R.color.home_selected));
        }

        MobclickAgent.onEvent(this, FinalUtils.EventId.tabbar_click, map);
    }

    private void ChooseTabFragment(int id) {
        MobclickAgent.onEvent(this, FinalUtils.EventId.tabbar_click);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
        forumFragment = (CommunityPlateFragment) getSupportFragmentManager().findFragmentByTag("forumFragment");
        raidersFragment = (ReadThreadFragment) getSupportFragmentManager().findFragmentByTag("raidersFragment");
        reportFragment = (ReportFragment) getSupportFragmentManager().findFragmentByTag("reportFragment");
        myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("myFragment");
        chooseCommunityFm = (ChooseCommunityFm) getSupportFragmentManager().findFragmentByTag("chooseCommunityFm");

        hideFragments(transaction);


        if (id == R.id.home_tab_home) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                transaction.add(R.id.home_fragment_layout, homeFragment,
                        "homeFragment");
            } else {
                transaction.show(homeFragment);
                homeFragment.randomHotWorld();
            }
        } else if (id == R.id.home_tab_forum) {
            if (isCommunityFragment) {
                if (forumFragment == null) {
                    forumFragment = new CommunityPlateFragment();
                    transaction.add(R.id.home_fragment_layout, forumFragment,
                            "forumFragment");
                } else {
                    transaction.show(forumFragment);
                }
            } else {
                if (chooseCommunityFm == null) {
                    chooseCommunityFm = new ChooseCommunityFm();
                    transaction.add(R.id.home_fragment_layout, chooseCommunityFm, "chooseCommunityFm");
                } else {
                    transaction.show(chooseCommunityFm);
                }

                chooseCommunityFm.setIsFirst(isFirstChooseForum);

            }
        } else if (id == R.id.home_tab_thread) {
            if (raidersFragment == null) {
                raidersFragment = new ReadThreadFragment();
                transaction.add(R.id.home_fragment_layout, raidersFragment, "raidersFragment");
            } else {
                transaction.show(raidersFragment);

                if (choose_tag == R.id.home_tab_thread) {
                    raidersFragment.refreshList();
                } else {
                    raidersFragment.refreshTab();
                }


            }
        } else if (id == R.id.home_tab_report) {
            if (reportFragment == null) {
                reportFragment = new ReportFragment();
                transaction.add(R.id.home_fragment_layout, reportFragment,
                        "reportFragment");
            } else {
                transaction.show(reportFragment);
            }
        } else if (id == R.id.home_tab_me) {
            if (myFragment == null) {
                myFragment = new MyFragment();
                transaction.add(R.id.home_fragment_layout, myFragment,
                        "myFragment");
            } else {
                transaction.show(myFragment);
            }
        }
        transaction.commitAllowingStateLoss();

        choose_tag = id;

        if (id == R.id.home_tab_me) {
            SystemBarUtils.setStatusBarColor(this, R.color.app_bg_title);
        } else {
            SystemBarUtils.setStatusBarColor(this, R.color.bg_toolbar);
        }

    }

    /**
     * 显示优惠
     */
    public void showPromotion() {
        chooseTabView(R.id.home_tab_home);
        ChooseTabFragment(R.id.home_tab_home);
        homeFragment.showPromotion();
    }

    /**
     * 显示攻略
     */
    public void showGonelue() {
        chooseTabView(R.id.home_tab_home);
        ChooseTabFragment(R.id.home_tab_home);
        homeFragment.showGonglue();
    }

    /**
     * 读帖，热帖
     */
    public void showReadThreadHot() {
        chooseTabView(R.id.home_tab_thread);
        ChooseTabFragment(R.id.home_tab_thread);
        raidersFragment.showReadThreadHot();
    }

    /**
     * 读帖，热帖
     */
    public void showReadThreadDigest() {
        chooseTabView(R.id.home_tab_thread);
        ChooseTabFragment(R.id.home_tab_thread);
        raidersFragment.showReadThreadDigest();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (forumFragment != null)
            transaction.hide(forumFragment);
        if (myFragment != null)
            transaction.hide(myFragment);
        if (reportFragment != null)
            transaction.hide(reportFragment);
        if (raidersFragment != null)
            transaction.hide(raidersFragment);
        if (chooseCommunityFm != null)
            transaction.hide(chooseCommunityFm);
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }


    /**
     * eventbus ,传值
     *
     * @param msg
     */
    public void onEventMainThread(String msg) {
        if (msg.equals(FinalUtils.OUTLOGIN)) {// 退出登录了
            this.userBean = null;
            if (myFragment != null)
                myFragment.loginOut();
        }
    }

    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_TAB_CHOOSE_REPORT) {
            chooseTabView(R.id.home_tab_report);
            ChooseTabFragment(R.id.home_tab_report);
        }
    }

    public void onEventMainThread(UserBean bean) {
        this.userBean = bean;
        if (myFragment != null)
            myFragment.loginUser(bean);
    }

    public UserBean getUserBean() {
        return userBean;
    }

    /**
     * 点击两次，退出程序
     */
    public void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            BToast(getString(R.string.press_one_more_time_exitapp));
            exitTime = System.currentTimeMillis();
        } else {
            YueManger.getInstance().saveReadIdsByLoca();
            preferences.savaToString("rong_connect");
            preferences.savaToString("uid");
            preferences.commit();
            MobclickAgent.onKillProcess(this);// 保存统计数据
            RoughDraftHelper helper = new RoughDraftHelper();
            helper.deteleAll();
            AmapLocation.onPause();
            finish();
            System.exit(0);
        }
    }

    /**
     * 处理推送
     */
    public void disposeJPush() {
        String type = getIntent().getStringExtra("type");
        String value = getIntent().getStringExtra("key");
        if (type == null)
            type = "";
        if (value == null)
            value = "";
        if (!type.equals("")) {
            if (TextUtils.equals(type, IntentKey.TYPE_ADVERT)) {
                Bundle bundle = new Bundle();
                bundle.putString("url", value);
                jumpActivity(TbsWebActivity.class, bundle);
            } else {
                final Intent intent = new Intent();
                intent.putExtra("type", type);
                if (type.equals(JPushReceiver.PM)) {
                    intent.setClass(this, MessageCenterActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.announcement)) {
                    intent.setClass(this, RemindActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.remind)) {
                    intent.setClass(this, RemindActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.AD)) {
                    intent.putExtra("url", value);
                    intent.setClass(HomeActivity.this, TbsWebActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.thread)) {
                    intent.putExtra("tid", value);
                    intent.setClass(this, ThreadActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.newsInfo)) {
                    intent.putExtra("aid", value);
                    intent.setClass(this, ThreadActivity.class);
                    startActivity(intent);
                } else if (type.equals(JPushReceiver.collection)) {
                    intent.putExtra("ctid", value);
                    intent.setClass(this, TopicDetailsActivity.class);
                    startActivity(intent);
                }

            }
        }
    }

    /**
     * 注册广播
     */
    private void registerMyReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }


}
