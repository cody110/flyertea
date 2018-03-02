package com.ideal.flyerteacafes.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HisThreadAdapter;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.entity.MyMedalsSubBean;
import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.model.params.DeleteMeEvent;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IUserDatum;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.UserDatumPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToZoomListView;
import com.ideal.flyerteacafes.ui.controls.RoundImageView;
import com.ideal.flyerteacafes.ui.popupwindow.UserDatumMessagePopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.UserdatumDeletePopupwindow;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DateTimeUtil;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 其他用户的个人页面
 */
@SuppressLint("HandlerLeak")
@ContentView(R.layout.activity_my_datum)
public class UserDatumActvity extends MVPBaseActivity<IUserDatum, UserDatumPresenter> implements IUserDatum, View.OnClickListener {

    @ViewInject(R.id.toolbar_right)
    private View toolbar_right;
    @ViewInject(R.id.migv_more_top)
    private View migv_more_top;
    @ViewInject(R.id.migv_more_progress)
    private ImageView topProgress;

    @ViewInject(R.id.mll_back_layout_top)
    private View backLayout;

    @ViewInject(R.id.user_datum_name)
    private TextView user_datum_name;

    /**
     * ListView
     **/
    @ViewInject(R.id.mlv_user_topic)
    private PullToZoomListView mlv_user_topic;

    /**
     * 头像
     **/
    @ViewInject(R.id.my_datum_head)
    private RoundImageView userHead;
    /**
     * 性别
     **/
    @ViewInject(R.id.my_datum_head_sex)
    private ImageView my_datum_head_sex;
    /**
     * 昵称
     **/
    @ViewInject(R.id.mtv_nickname)
    private TextView mtv_nickname;
    /**
     * 认证
     **/
    @ViewInject(R.id.migv_rengzheng)
    private ImageView migv_rengzheng;
    /**
     * 会员等级
     **/
    @ViewInject(R.id.migv_menber_grade)
    private ImageView migv_menber_grade;
    /**
     * 个性签名
     **/
    @ViewInject(R.id.mtv_personality_signature)
    private TextView mtv_personality_signature;
    /**
     * 入住天数
     **/
    @ViewInject(R.id.mtv_check_in_num)
    private TextView mtv_check_in_num;
    /**
     * 鲜花数
     **/
    @ViewInject(R.id.mtv_flower_num)
    private TextView mtv_flower_num;
    /**
     * 加好友
     **/
    @ViewInject(R.id.mtv_add_friends)
    private TextView mtv_add_friends;

    @ViewInject(R.id.my_datum_top)
    private View topTabView;


    private TextView mythread_text, reply_text, mythread_text_top, reply_text_top;
    private View mythread_bottom, reply_bottom, mythread_bottom_top, reply_bottom_top;


    private View tabView, xunzhang_layout;
    private LinearLayout xunzhang_img_layout;

    private View footerView;

    private FragmentManager fm;
    private HisThreadAdapter adapter;

    private String uid;
    private Userinfo info;
    private String hastoken;

    private UserdatumDeletePopupwindow popupwindow;
    private UserDatumMessagePopupWindow userDatumMessagePopupWindow;

    /**
     * 进度圈旋转动画
     */
    private Animation progressAnimation;

    public static final String STATUS_KEY = "activity", STATUS_STRANGER = "SearchMemberActivity", STATUS_FRIENDS = "FriendsFragment";//陌生人，朋友
    public static final int POP_SEND_PRIVATE_MESSAGE = 0, POP_SEND_MESSAGE = 1, POP_DELETE = 2;
    private boolean isShowDeleteView = false;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case POP_SEND_PRIVATE_MESSAGE://发送私信
                    if (info != null) {
                        Bundle chatBundle = new Bundle();
                        chatBundle.putString("uid", uid);
                        chatBundle.putString("name", info.getUsername());
                        jumpActivity(ChatActivity.class, chatBundle);
                    }
                    break;
                case POP_DELETE://删除好友
                    mPresenter.deleteFriend();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        SystemBarUtils.setStatusBarColor(this, R.color.app_bg_title);
        progressAnimation = AnimationUtils.loadAnimation(FlyerApplication.getContext(), R.anim.no_stop_rotation);
        LinearInterpolator lin = new LinearInterpolator();
        progressAnimation.setInterpolator(lin);

        uid = getIntent().getStringExtra("uid");
        fm = getSupportFragmentManager();
        mPresenter.init(this);
        initViews();
    }

    @Override
    public void initViews() {

        mythread_text_top = (TextView) topTabView.findViewById(R.id.mythread_mythread_text);
        reply_text_top = (TextView) topTabView.findViewById(R.id.mythread_reply_text);
        mythread_bottom_top = topTabView.findViewById(R.id.mythread_mythread_bottom);
        reply_bottom_top = topTabView.findViewById(R.id.mythread_reply_bottom);

        //加载header view
        View headerView = mlv_user_topic.getHeaderView();
        x.view().inject(this, headerView);
        tabView = headerView.findViewById(R.id.user_datum_header_tab_layout);
        mythread_text = (TextView) tabView.findViewById(R.id.mythread_mythread_text);
        reply_text = (TextView) tabView.findViewById(R.id.mythread_reply_text);
        mythread_bottom = tabView.findViewById(R.id.mythread_mythread_bottom);
        reply_bottom = tabView.findViewById(R.id.mythread_reply_bottom);


        xunzhang_layout = headerView.findViewById(R.id.xunzhang_layout);
        xunzhang_img_layout = (LinearLayout) headerView.findViewById(R.id.xunzhang_img_layout);


        topTabView.findViewById(R.id.mythread_mythread_layout).setOnClickListener(this);
        topTabView.findViewById(R.id.mythread_reply_layout).setOnClickListener(this);
        tabView.findViewById(R.id.mythread_mythread_layout).setOnClickListener(this);
        tabView.findViewById(R.id.mythread_reply_layout).setOnClickListener(this);

        mlv_user_topic.setmRefreshListener(new PullToZoomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                headerRefresh();
            }
        });
        setScrollListener();

        //根据用户性别显示tab文案
        if (info != null && TextUtils.equals(info.getGender(), Marks.COMMUNITY_GENDER_GIRL)) {
            mythread_text.setText(getResources().getString(R.string.my_datum_ac_girl_thread));
            mythread_text_top.setText(getResources().getString(R.string.my_datum_ac_girl_thread));
            reply_text.setText(getResources().getString(R.string.my_datum_ac_girl_reply));
            reply_text_top.setText(getResources().getString(R.string.my_datum_ac_girl_reply));
        } else {
            mythread_text.setText(getResources().getString(R.string.my_datum_ac_boy_thread));
            mythread_text_top.setText(getResources().getString(R.string.my_datum_ac_boy_thread));
            reply_text.setText(getResources().getString(R.string.my_datum_ac_boy_reply));
            reply_text_top.setText(getResources().getString(R.string.my_datum_ac_boy_reply));
        }
    }

    private void headerRefresh() {
        migv_more_top.setVisibility(View.GONE);
        topProgress.setVisibility(View.VISIBLE);
        mPresenter.headerRefresh();
        topProgress.setAnimation(progressAnimation);
    }

    @Event(value = R.id.mlv_user_topic, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) return;
        Bundle bundle = new Bundle();
        bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(mPresenter.getMyThreadBeanList().get(position - 1).getTid()));
        if (TextUtils.equals(mPresenter.type, mPresenter.TYPE_TOPIC)) {
            bundle.putInt(ThreadPresenter.BUNDLE_POS, position - 1);
            if (mPresenter.getMyThreadBeanList().get(position - 1).isNormal()) {
                jumpActivityForResult(ThreadActivity.class, bundle, 0);
            } else {
                bundle.putInt(ThreadPresenter.BUNDLE_TYPE_KEY, ThreadPresenter.BUNDLE_TYPE_MAJOR);
                jumpActivityForResult(ThreadActivity.class, bundle, 0);
            }
        } else {
            jumpActivity(ThreadActivity.class, bundle);
        }
    }

    @Event({R.id.img_back_top, R.id.toolbar_right, R.id.mtv_add_friends, R.id.my_datum_head, R.id.mythread_mythread_layout, R.id.mythread_reply_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.img_back_top://返回
                finish();
                break;
            case R.id.toolbar_right://发送消息
                if (userDatumMessagePopupWindow == null) {
                    userDatumMessagePopupWindow = new UserDatumMessagePopupWindow(this, handler);
                }
                userDatumMessagePopupWindow.showAsDropDown(v, 0, (int) (10 * preferences.getScale()));
                break;
            case R.id.my_datum_head://头像
                Bundle headbundle = new Bundle();
                headbundle.putSerializable("userinfo", info);
                jumpActivity(PersonalDataActivity.class, headbundle);
                break;
            case R.id.mythread_mythread_layout://他/她的帖子
                chooseTabLayout(v.getId());
                mPresenter.actionSelectStatus(UserDatumPresenter.TYPE_TOPIC);
                break;
            case R.id.mythread_reply_layout://他/她的回复
                chooseTabLayout(v.getId());
                mPresenter.actionSelectStatus(UserDatumPresenter.TYPE_REPLY);
                break;
            case R.id.mtv_add_friends://添加好友
                if (isShowDeleteView) {//解除好友关系
                    if (popupwindow == null)
                        popupwindow = new UserdatumDeletePopupwindow(this, handler);
                    popupwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    addFriends();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加好友
     */
    private void addFriends() {
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        jumpActivity(VerificationActivity.class, bundle);
    }

    @Override
    protected UserDatumPresenter createPresenter() {
        return new UserDatumPresenter();
    }


    @Override
    public void callbackUserInfo(final Userinfo userinfo) {
        info = userinfo;

        WidgetUtils.setText(mythread_text_top, "TA的帖子 " + userinfo.getThreads());
        WidgetUtils.setText(mythread_text, "TA的帖子 " + userinfo.getThreads());
        WidgetUtils.setText(reply_text_top, "TA的回复 " + userinfo.getPosts());
        WidgetUtils.setText(reply_text, "TA的回复 " + userinfo.getPosts());


        WidgetUtils.setText(user_datum_name, userinfo.getUsername());//昵称
        DataUtils.downloadPicture(userHead, info.getFace(), R.drawable.def_face);
        WidgetUtils.setText(mtv_nickname, info.getUsername());//昵称
        String gender = info.getGender();//性别
        if (TextUtils.equals(gender, Marks.COMMUNITY_GENDER_BOY)) {
            WidgetUtils.setVisible(my_datum_head_sex, true);
            my_datum_head_sex.setImageResource(R.drawable.icon_community_boy);
        } else if (TextUtils.equals(gender, Marks.COMMUNITY_GENDER_GIRL)) {
            WidgetUtils.setVisible(my_datum_head_sex, true);
            my_datum_head_sex.setImageResource(R.drawable.icon_community_girl);
        } else {
            WidgetUtils.setVisible(my_datum_head_sex, false);
        }
        if (!TextUtils.isEmpty(info.getSightml())) {//个性签名
            mtv_personality_signature.setText(info.getSightml());
        }
        DataUtils.setGroupName(migv_menber_grade, info.getGroupname());//会员等级图标
        if (TextUtils.equals(info.getHas_sm(), Marks.USERINFO_HAS_SM)) {//是否认证
            WidgetUtils.setVisible(migv_rengzheng, true);
            migv_rengzheng.setImageResource(R.drawable.renzheng);
        } else {
            WidgetUtils.setVisible(migv_rengzheng, false);
        }

        WidgetUtils.setText(mtv_check_in_num, "入住\t" + DateTimeUtil.getDateDays(DateTimeUtil.getTime(), userinfo.getRegistrationtime()));
        WidgetUtils.setText(mtv_flower_num, "鲜花\t" + info.getUser_flower());

        hastoken = info.getHastoken();
        if (info.getIsfriend().equals("0")) {
            isShowDeleteView = false;
        } else if (info.getIsfriend().equals("1")) {
            isShowDeleteView = true;
            mtv_add_friends.setText("已添加");
            if (info.getHastoken().equals("0")) {//没有客户端
                mtv_add_friends.setBackgroundResource(R.drawable.subscribe_gridview_item_bg_not_canceled);
            }
        }

        int xunzhangSize = 0;

        if (userinfo.getShowmedals() != null) {
            xunzhangSize = userinfo.getShowmedals().size();
            addMedalsImg(userinfo.getShowmedals());

        }
        WidgetUtils.setVisible(xunzhang_layout, xunzhangSize > 0);

        xunzhang_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) userinfo.getMedals());
                jumpActivity(TaMedalsActivity.class, bundle);
            }
        });

        mlv_user_topic.resetHeaderHeight();


    }

    private void addMedalsImg(List<MyMedalsSubBean> datas) {
        ImageOptions image_FIT_XY = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.drawable.icon_def)//加载中默认显示图片
                .setFailureDrawableId(R.drawable.icon_def)//加载失败后默认显示图片
                .setUseMemCache(true)
                .build();
        for (int i = 0; i < datas.size(); i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(30), DensityUtil.dip2px(47));
            params.setMargins(DensityUtil.dip2px(20), DensityUtil.dip2px(12), 0, DensityUtil.dip2px(12));
            xunzhang_img_layout.addView(iv, params);

            x.image().bind(iv, datas.get(i).getImage(), image_FIT_XY);

        }
    }


    @Override
    public void callbackDeleteFriend() {
        EventBus.getDefault().post(new DeleteMeEvent(uid));
        Intent intent = new Intent(this, FriendsActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * tab切换，改变ui
     *
     * @param id
     */
    private void chooseTabLayout(int id) {
        if (id == R.id.mythread_mythread_layout) {
            mythread_text.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_bottom.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_text.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            reply_bottom.setBackground(null);
        } else {
            reply_text.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_bottom.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_text.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            mythread_bottom.setBackground(null);
        }
    }


    /**
     * tab切换，改变ui
     *
     * @param id
     */
    private void chooseTabLayoutTop(int id) {
        if (id == R.id.mythread_mythread_layout) {
            mythread_text_top.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_bottom_top.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_text_top.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            reply_bottom_top.setBackground(null);
        } else {
            reply_text_top.setTextColor(getResources().getColor(
                    R.color.app_body_blacks));
            reply_bottom_top.setBackgroundColor(getResources().getColor(
                    R.color.app_body_blacks));
            mythread_text_top.setTextColor(getResources().getColor(
                    R.color.app_body_grey));
            mythread_bottom_top.setBackground(null);
        }
    }


    /**
     * 帖子列表
     *
     * @param myThreadBeanList
     * @param type
     */
    @Override
    public void callbackTopicList(List<MyThreadBean> myThreadBeanList, String type) {
        topProgress.clearAnimation();
        topProgress.setVisibility(View.GONE);
        migv_more_top.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new HisThreadAdapter(this, myThreadBeanList, R.layout.user_datum_thread_item);
            mlv_user_topic.setAdapter(adapter);
        }
        adapter.setThreadType(type);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void addFooterView() {
        if (footerView == null) {
            footerView = LayoutInflater.from(this).inflate(R.layout.refresh_footer, null, false);
            mlv_user_topic.addFooterView(footerView);

            TextView pull_to_load_text = (TextView) footerView.findViewById(R.id.pull_to_load_text);
            pull_to_load_text.setText(R.string.pull_to_refresh_refreshing_label);

            footerView.findViewById(R.id.pull_to_load_image).setVisibility(View.GONE);
            ImageView progress = (ImageView) footerView.findViewById(R.id.pull_to_load_progress);
            progress.setVisibility(View.VISIBLE);
            progress.setAnimation(progressAnimation);

        }
    }

    @Override
    public void removeFooterView() {
        if (footerView != null) {
            mlv_user_topic.removeFooterView(footerView);
            footerView = null;
        }
    }

    Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<>();

    int topHeight;

    /**
     * 滚动监听
     */
    private void setScrollListener() {
        mlv_user_topic.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (topHeight == 0) {
//                    View topBgView = mlv_user_topic.getHeaderView().findViewById(R.id.my_datum_background);
                    View topBgView = mlv_user_topic.getHeaderView();
                    topHeight = topBgView.getHeight();//-backLayout.getHeight();
                }
                View c = view.getChildAt(0); //this is the first visible row
                if (c != null) {
                    int scrollY = -c.getTop();
                    listViewItemHeights.put(view.getFirstVisiblePosition(), c.getHeight());
                    for (int i = 0; i < view.getFirstVisiblePosition(); ++i) {
                        if (listViewItemHeights.get(i) != null) // (this is a sanity check)
                            scrollY += listViewItemHeights.get(i); //add all heights of the views that are gone
                    }


                    int lastVisibalePos = view.getLastVisiblePosition();//最后一项显示的item
                    if (lastVisibalePos == (mPresenter.myThreadBeanList.size() + 1)) {
                        mPresenter.footerRefresh();
                    }

                    int alpha = scrollY / (topHeight / 255);
                    alpha = alpha > 255 ? 255 : alpha;
                    backLayout.getBackground().mutate().setAlpha(alpha);

                    if (alpha == 255) {
                        topTabView.setVisibility(View.VISIBLE);
                        user_datum_name.setVisibility(View.VISIBLE);
                    } else {
                        topTabView.setVisibility(View.GONE);
                        user_datum_name.setVisibility(View.GONE);
                    }


                } else {
                    LogFly.e("null");
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        chooseTabLayout(view.getId());
        chooseTabLayoutTop(view.getId());

        if (view.getId() == R.id.mythread_mythread_layout) {
            if (TextUtils.equals(mPresenter.type, mPresenter.TYPE_REPLY)) {
                mlv_user_topic.smoothScrollToPosition(0);
                mPresenter.actionSelectStatus(mPresenter.TYPE_TOPIC);
                headerRefresh();
            }
        } else {
            if (TextUtils.equals(mPresenter.type, mPresenter.TYPE_TOPIC)) {
                mlv_user_topic.smoothScrollToPosition(0);
                mPresenter.actionSelectStatus(mPresenter.TYPE_REPLY);
                headerRefresh();
            }
        }
    }
}