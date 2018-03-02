package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.StartUpManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.CollectActivity;
import com.ideal.flyerteacafes.ui.activity.FriendsActivity;
import com.ideal.flyerteacafes.ui.activity.LoginVideoActivity;
import com.ideal.flyerteacafes.ui.activity.MyReadsActivity;
import com.ideal.flyerteacafes.ui.activity.MyThreadActivity;
import com.ideal.flyerteacafes.ui.activity.NoviceGuidanceActivity;
import com.ideal.flyerteacafes.ui.activity.ReplyPostsActivity;
import com.ideal.flyerteacafes.ui.activity.SettingActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.UserActivity;
import com.ideal.flyerteacafes.ui.activity.XunzhangActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.MessageCenterActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.MyTaskActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.TaskDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.WeiWangActivity;
import com.ideal.flyerteacafes.ui.dialog.AppShareDialog;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMyFragment;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.MyFragmentPresenter;
import com.ideal.flyerteacafes.ui.fragment.viewholder.MyFragmentVH;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import de.greenrobot.event.EventBus;

/**
 * 我的
 */
public class MyFragment extends MVPBaseFragment<IMyFragment, MyFragmentPresenter> {

    private View mView;

    public static final int loginRequestCod = 100;

    MyFragmentVH vh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mPresenter.attachView(iMyFragment);
        mView = inflater.inflate(R.layout.layout_my, null);
        vh = new MyFragmentVH(mView, iActionListener);
        StartUpManger.getInstance().registerIAutomaticLogin(new StartUpManger.IAutomaticLogin() {
            @Override
            public void loginSuccess(UserBean userBean) {
                vh.bindUserInfo(userBean);
                mPresenter.getTaskData();
            }
        });
        BaseDataManger.getInstance().registerIMsgNum(new BaseDataManger.IMsgNum() {
            @Override
            public void msgNum(NumberBean numBean) {
                vh.bindNumber(numBean);
            }
        });
        BaseDataManger.getInstance().requestGetNum();

        if (UserManger.isLogin()) {
            vh.bindUserInfo(UserManger.getUserInfo());
            YueManger.getInstance().initUserReadIds();
            mPresenter.getTaskData();
        } else {
            loginOut();
        }
        mPresenter.init(mActivity);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        BaseDataManger.getInstance().requestGetNum();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 用户登录了
     *
     * @param userBean
     */
    public void loginUser(UserBean userBean) {
        if (userBean != null) {
            vh.bindUserInfo(userBean);
            YueManger.getInstance().initUserReadIds();
            mPresenter.getTaskData();
            mPresenter.requestIsSignin();
        }
    }


    /**
     * 退出登录了
     */
    public void loginOut() {
        mPresenter.isSignin = false;
        vh.loginOut();
    }


    /**
     * 跳转跟用户信息相关的activity
     */
    private <T> void intentActivity(Class<T> activity, boolean flag) {
        if (UserManger.isLogin() || flag) {
            Intent intent = new Intent(getActivity(), activity);
            intent.putExtra("userBean", UserManger.getUserInfo());
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    /**
     * 登录提示框
     */
    private void showDialog() {
        DialogUtils.showDialog(getActivity(), new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                startActivityForResult(new Intent(getActivity(),
                        LoginVideoActivity.class), loginRequestCod);
            }
        });
    }


    MyFragmentVH.IActionListener iActionListener = new MyFragmentVH.IActionListener() {
        @Override
        public void actionClick(View view) {
            Bundle bundle = new Bundle();
            switch (view.getId()) {

                //设置
                case MyFragmentVH.IActionListener.ID_TO_SETTING:
                    jumpActivity(SettingActivity.class, null);
                    break;
                //消息中心
                case MyFragmentVH.IActionListener.ID_TO_MESSAGE:
                    intentActivity(MessageCenterActivity.class, false);
                    MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.message);
                    break;
                //登录
                case MyFragmentVH.IActionListener.ID_TO_LOGIN:
                    Intent intentLogin = new Intent();
                    intentLogin.setClass(getActivity(), LoginVideoActivity.class);
                    startActivity(intentLogin);
                    break;
                //头像 用户名 完善个人资料
                case MyFragmentVH.IActionListener.ID_TO_PERFECT_INFORMATION:
                case MyFragmentVH.IActionListener.ID_TO_USER:
                case MyFragmentVH.IActionListener.ID_TO_FACE_CHANGE:
                    intentActivity(UserActivity.class, false);
                    break;

                //威望
                case MyFragmentVH.IActionListener.ID_TO_WEIWANG:
                    if (UserManger.isLogin()) {
                        jumpActivity(WeiWangActivity.class, null);
                    }
                    break;
                //飞米
                case MyFragmentVH.IActionListener.ID_TO_FEIMI:
                    if (UserManger.getUserInfo() != null && UserManger.getUserInfo().getPs_token() != null) {
                        bundle.putString("url", Utils.HtmlUrl.HTML_MY_FEIMI + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                        jumpActivity(TbsWebActivity.class, bundle);
                    }
                    break;
                //余额
                case MyFragmentVH.IActionListener.ID_TO_MONEY:
                    if (UserManger.getUserInfo() != null && UserManger.getUserInfo().getPs_token() != null) {
                        bundle.putString("url", Utils.HtmlUrl.HTML_MY_YUE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                        jumpActivity(TbsWebActivity.class, bundle);
                    }
                    break;
                //勋章
                case MyFragmentVH.IActionListener.ID_TO_XUNZHANG:
                    jumpActivity(XunzhangActivity.class, null);
                    break;
                //好友
                case MyFragmentVH.IActionListener.ID_TO_friends:
                    intentActivity(FriendsActivity.class, false);
                    break;
                //帖子
                case MyFragmentVH.IActionListener.ID_TO_POST:
                    intentActivity(MyThreadActivity.class, false);
                    break;
                //收藏
                case MyFragmentVH.IActionListener.ID_TO_COLLECTION:
                    intentActivity(CollectActivity.class, false);
                    break;
                //我的任务
                case MyFragmentVH.IActionListener.ID_TO_USER_RIGHT:
                case MyFragmentVH.IActionListener.ID_SEE_MORE_TASK:
                case MyFragmentVH.IActionListener.ID_TO_TASK:
                    if (UserManger.isLogin()) {
                        if (mPresenter.getMyTaskAllBean() != null) {
                            bundle = new Bundle();
                            bundle.putSerializable("data", (Serializable) mPresenter.getMyTaskAllBean().getBanner());
                            jumpActivity(MyTaskActivity.class, bundle);
                        }
                    } else {
                        DialogUtils.showDialog(mActivity);
                    }
                    break;

                //浏览记录
                case MyFragmentVH.IActionListener.ID_TO_SEE_JILU:
                    jumpActivity(MyReadsActivity.class, null);
                    break;
                //推荐好友
                case MyFragmentVH.IActionListener.ID_TO_TUIJIAN_FRIEND:
                    showShareDialog();
                    break;
                //意见反馈
                case MyFragmentVH.IActionListener.ID_TO_FANKUI:
                    if (UserManger.isLogin()) {
                        Intent commentUsIntent = new Intent(getActivity(), ReplyPostsActivity.class);
                        commentUsIntent.putExtra("posttype", FinalUtils.FEED_BACK);
                        commentUsIntent.putExtra("fid", 73);
                        startActivity(commentUsIntent);
                    } else {
                        DialogUtils.showDialog(mActivity);
                    }
                    break;
                //飞米商城
                case MyFragmentVH.IActionListener.ID_TO_FLYING_MALL:
//                    if (UserManger.isLogin()) {
//                        mPresenter.toSingin();
//                    } else {
//                        DialogUtils.showDialog(mActivity);
//                    }

                    bundle = new Bundle();
                    bundle.putString("url", Utils.HtmlUrl.HTML_FLASHSALE);
                    jumpActivity(TbsWebActivity.class, bundle);

                    break;
                //飞客攻略电子书
                case MyFragmentVH.IActionListener.ID_TO_RAIDERS_BOOK:
                    bundle = new Bundle();
                    bundle.putString("url", Utils.HtmlUrl.HTML_RAIDERS_BOOK);
                    jumpActivity(TbsWebActivity.class, bundle);
                    break;

            }
        }

        @Override
        public void taskClick(MyTaskBean bean) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TaskDetailsActivity.BUNDLE_DATA, bean);
            jumpActivity(TaskDetailsActivity.class, bundle);
        }

        @Override
        public void bannerClick(MyTaskAllBean.BannerBean bean) {
            if (TextUtils.equals(bean.getType(), "birthday")) {
                Bundle bundle = new Bundle();
                bundle.putString(TaskDetailsActivity.BUNDLE_ID, bean.getTaskid());
                jumpActivity(TaskDetailsActivity.class, bundle);
            } else if (TextUtils.equals(bean.getType(), "freshman")) {
                jumpActivity(NoviceGuidanceActivity.class);
            }
        }
    };


    IMyFragment iMyFragment = new IMyFragment() {
        @Override
        public void callbackFaceLocaPath(String locaPath) {
            vh.updateFace(locaPath);
        }

        @Override
        public void callbackTaskAll(MyTaskAllBean allBean) {
            vh.bindTaskData(allBean);
        }

        @Override
        public void setViewByIsSigin(boolean isSigin) {
            vh.bindIsSigin(isSigin);
        }

        @Override
        public void isSetBirthday(boolean bol) {
            vh.bindBirthdayText(bol);
        }
    };

    @Override
    protected MyFragmentPresenter createPresenter() {
        return new MyFragmentPresenter();
    }

    /**
     * eventbus ,传值
     */
    public void onEventMainThread(TagEvent tag) {
        if (tag.getTag() == TagEvent.TAG_FACE_CHANGE) {//头像变了
            String loca = tag.getBundle().getString("data");
            vh.updateFace(loca);
        } else if (tag.getTag() == TagEvent.TAG_BIRTHDAY_SUCCESS) {//设置生日成功
            vh.bindBirthdayText(false);
        } else if (tag.getTag() == TagEvent.TAG_TASK_BACK) {//任务页面返回，刷新任务数据
            //任务页，加载改变了图片大小需重新加载
            if (mPresenter.getMyTaskAllBean() != null) {
                vh.bindBanner(mPresenter.getMyTaskAllBean().getBanner());
            }
            mPresenter.getTaskData();
        }
    }


    /**
     * 创建分享
     */
    public void showShareDialog() {
        removeDialogFragment("tag_share_friend");
        AppShareDialog appShareDialog = new AppShareDialog();
        appShareDialog.show(getChildFragmentManager(), "tag_share_friend");
    }

    /**
     * 移除dialog fragment
     *
     * @param tag
     */
    protected void removeDialogFragment(String tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (null != fragment) {
            ft.remove(fragment);
        }
    }


}
