package com.ideal.flyerteacafes.ui.activity.threads;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.video.PlayVideoActivity;
import com.ideal.flyerteacafes.ui.activity.web.SystemWebActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.controls.PostDetailsWebView;
import com.ideal.flyerteacafes.ui.dialog.FlowDialog;
import com.ideal.flyerteacafes.ui.dialog.ThreadCommentDialog;
import com.ideal.flyerteacafes.ui.dialog.ThreadShareDialog;
import com.ideal.flyerteacafes.ui.interfaces.IThread;
import com.ideal.flyerteacafes.ui.popupwindow.BottomListPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.ChoosePagePopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.PostDetailsPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.ThreadTagsPopupwindow;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.SoftKeyboardStateHelper;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fly on 2016/11/21.
 * 帖子基类
 */
@ContentView(R.layout.activity_thread)
public class ThreadActivity extends MVPBaseActivity<IThread, ThreadPresenter> implements IThread {

    private static final int UPDATE_MOBILE = 11;
    @ViewInject(R.id.thread_toolbar)
    protected View toolbar;
    @ViewInject(R.id.toolbar_title)
    protected TextView toolbar_title;
    @ViewInject(R.id.toolbar_title_icon)
    protected ImageView toolbar_title_icon;
    @ViewInject(R.id.toolbar_right_img)
    protected ImageView toolbar_right_img;
    @ViewInject(R.id.toolbar_right)
    protected View toolbar_right;
    @ViewInject(R.id.thread_webview)
    PostDetailsWebView webView;
    @ViewInject(R.id.thread_bottom_layout)
    protected FrameLayout bottomLayout;
    @ViewInject(R.id.show_now_page_tv)
    protected TextView show_now_page_tv;

    ThreadCommentDialog threadCommentDialog;
    ThreadShareDialog threadShareDialog;
    FlowDialog flowDialog;


    protected String TAG_COMMENT_DIALOG = "tag_alert";
    private String TAG_SHRE_DIALOG = "tag_share";
    private String TAG_FLOW = "tag_flow";

    public static final int request_comment_img = 10;
    private float contentHeight = 0;
    private int h_screen;

    SoftKeyboardStateHelper softKeyboardStateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        h_screen = SharedPreferencesString.getInstances().getIntToKey("h_screen");
        initViews();
        initWebview();
        mPresenter.init(this);

        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.activity_main_layout));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                if (threadCommentDialog != null) {
                    threadCommentDialog.jianIsClose();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    protected View setBottomView(int res) {
        View view = LayoutInflater.from(this).inflate(res, null);
        bottomLayout.removeAllViews();
        bottomLayout.addView(view);
        return view;
    }

    private void initWebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(mPresenter, "android");
        webViewListener();
    }

    private boolean animationIng = false;

    private void webViewListener() {

        final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationIng = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationIng = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        // 滚动监听
        webView.setOnCustomScroolChangeListener(new PostDetailsWebView.ScrollInterface() {
            @Override
            public void onScrollToBottom(boolean flag) {


                if (contentHeight <= h_screen) return;

                if (isShowPageAll()) {
                    if (animationIng) return;
                    if (flag) {
                        if (bottomLayout.getVisibility() == View.VISIBLE) {
                            Animation anim = AnimationUtils.loadAnimation(ThreadActivity.this, R.anim.push_bottom_out);
                            bottomLayout.startAnimation(anim);
                            bottomLayout.setVisibility(View.GONE);
                            anim.setAnimationListener(animationListener);
                        }

                        if (toolbar.getVisibility() == View.VISIBLE) {
                            Animation anim = AnimationUtils.loadAnimation(ThreadActivity.this, R.anim.push_up_down_out);
                            toolbar.startAnimation(anim);
                            toolbar.setVisibility(View.GONE);
                            anim.setAnimationListener(animationListener);
                        }
                    } else {
                        if (bottomLayout.getVisibility() == View.GONE) {
                            Animation anim = AnimationUtils.loadAnimation(ThreadActivity.this, R.anim.push_bottom_in);
                            bottomLayout.startAnimation(anim);
                            bottomLayout.setVisibility(View.VISIBLE);
                            anim.setAnimationListener(animationListener);
                        }

                        if (toolbar.getVisibility() == View.GONE) {
                            Animation anim = AnimationUtils.loadAnimation(ThreadActivity.this, R.anim.push_up_down_in);
                            toolbar.startAnimation(anim);
                            toolbar.setVisibility(View.VISIBLE);
                            anim.setAnimationListener(animationListener);
                        }
                    }
                }

            }

            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {

                // WebView的总高度
                float webViewContentHeight = webView.getContentHeight()
                        * webView.getScale();
                // WebView的现高度
                float webViewCurrentHeight = (webView.getHeight() + webView
                        .getScrollY());

                int marginBottom = (int) (webViewContentHeight - webViewCurrentHeight);
                if (marginBottom < 2) {//计算精度误差，小于2就执行
                    mPresenter.actionWebviewScrollBottom();
                }

            }
        });


        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                DataUtils.webViewClickUrlDispose(url, ThreadActivity.this);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPresenter.actionWebviewLoadFinished();
                contentHeight = view.getContentHeight() * view.getScale();
            }
        });

    }

    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.show_now_page_tv, R.id.biaoqing, R.id.tupian})
    private void click(View v) {
        if (v.getId() == R.id.toolbar_left) {
            finish();
        } else if (v.getId() == R.id.toolbar_right) {
            mPresenter.actionShowShareDialog();
        } else if (v.getId() == R.id.show_now_page_tv) {
            umAgentPagingClick();
            if (mPresenter.totalpage > 0) {
                ChoosePagePopupwindow popupwindow = new ChoosePagePopupwindow(ThreadActivity.this);
                popupwindow.showAtLocation(show_now_page_tv, Gravity.BOTTOM, 0, 0);
                popupwindow.initPage(mPresenter.page, mPresenter.totalpage);
                popupwindow.setiPageChoose(new ChoosePagePopupwindow.IPageChoose() {
                    @Override
                    public void selectPagePos(int pos) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID, mPresenter.tid);
                        bundle.putInt(ThreadPresenter.BUNDLE_STARTPAGE, pos + 1);
                        jumpActivity(MajorCommentActivity.class, bundle);
                    }
                });


            }
        }
    }


    public void umAgentPagingClick() {
        HashMap<String, String> map = new HashMap<>();
        if (mPresenter.getThreadBean() != null)
            map.put("tid", mPresenter.getThreadBean().getTid() + "");
        MobclickAgent.onEvent(ThreadActivity.this, FinalUtils.EventId.notedetail_paging_click, map);
        LogFly.e(FinalUtils.EventId.notedetail_paging_click);

    }


    @Override
    protected ThreadPresenter createPresenter() {
        return new ThreadPresenter();
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void webviewLoadUrl(String str) {
        webView.loadUrl(str);
    }

    @Override
    public void toLogin() {
        DialogUtils.showDialog(this);
    }

    /**
     * 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框。
     */
    @Override
    public void showCommentDialog(final Bundle bundle) {
        if (threadCommentDialog == null || threadCommentDialog.getIndex() != bundle.getInt("index")) {
            removeDialogFragment(TAG_COMMENT_DIALOG);
            threadCommentDialog = new ThreadCommentDialog();
            threadCommentDialog.setArguments(bundle);
            threadCommentDialog.setCommentListener(mPresenter);
        }

        //异常DialogFragment Fragment already added
        //公用一个加载进度框（DialogFragment）,前一个加载框未dismiss，后一个已经开始show
        if (!threadCommentDialog.isAdded() && !threadCommentDialog.isVisible() && !threadCommentDialog.isRemoving()) {
            threadCommentDialog.show(getSupportFragmentManager(), TAG_COMMENT_DIALOG);
        }

    }


    @Override
    public void closeCommentDialog() {
        if (threadCommentDialog != null)
            threadCommentDialog.dismissDialog();
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int height = (int) (webView.getContentHeight() * webView.getScale()) - webView.getHeight();
                webView.scrollTo(0, height - 5);
            }
        }, 100);

    }

    @Override
    public void showShareDialog(Bundle bundle) {
        removeDialogFragment(TAG_SHRE_DIALOG);
        threadShareDialog = new ThreadShareDialog();
        threadShareDialog.setArguments(bundle);
        threadShareDialog.setThreadLinster(mPresenter);
        threadShareDialog.show(getSupportFragmentManager(), TAG_SHRE_DIALOG);
    }

    protected void removeDialogFragment(String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (null != fragment) {
            ft.remove(fragment);
        }
    }

    @Override
    public void isConfirmDeleteThread() {
        showDeletePostDialog(this);
    }

    private void showDeletePostDialog(final Context context) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(context);
        builder.setMessage("您确定要删除本帖吗？");
        builder.setNegativeButton("确定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.confirmDeleteThread();
            }
        });
        builder.setPositiveButton("取消");
        builder.create().show();
    }

    @Override
    public void endActivity() {
        finish();
    }

    PostDetailsPopupWindow popupWindow;
    BottomListPopupWindow sortPopupWindow;
    BottomListPopupWindow morePopupWindow;


    @Override
    public void showDeleteCommentDialog() {
        if (popupWindow == null)
            popupWindow = new PostDetailsPopupWindow(this, mPresenter.handler);
        popupWindow.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == request_comment_img) {
                CommentBean commentBean = (CommentBean) data.getSerializableExtra(ThreadPresenter.BUNDLE_COMMENT_IMG);
                mPresenter.handlerNewComment(commentBean);
            } else if (requestCode == UPDATE_MOBILE) {
                if (threadCommentDialog != null) {
                    threadCommentDialog.sendComment();
                }
            }
        }
    }

    @Override
    public void showCommentSortPop() {
        if (sortPopupWindow == null) {
            sortPopupWindow = new BottomListPopupWindow(this);
            sortPopupWindow.setDatas(new BottomListPopupWindow.IItemClick() {
                @Override
                public void itemClick(int pos, final String data) {
                    TaskUtil.postOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.equals(data, "按时间升序")) {
                                mPresenter.orderByUp();
                            } else if (TextUtils.equals(data, "按时间降序")) {
                                mPresenter.orderByDown();
                            }
                        }
                    });
                }
            }, "按时间升序", "按时间降序");
        }
        sortPopupWindow.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void showCommentPage(int page, int pageAll) {
        show_now_page_tv.setText(page + "/" + pageAll);
        show_now_page_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoreDialog(final int pos, final CommentBean commentBean) {
        if (UserManger.isLogin()) {

            List<String> list = new ArrayList<>();

            if (TextUtils.equals(mPresenter.getThreadBean().getBestable(), "1") && !TextUtils.equals(UserManger.getUserInfo().getMember_uid(), commentBean.getAuthorid())) {
                list.add("设为最佳答案");
            }


            LogFly.e("isbest" + commentBean.getIsbest() + "       " + UserManger.getUserInfo().getMember_uid() + "====" + mPresenter.getThreadBean().getAuthorid());
            if (TextUtils.equals(commentBean.getIsbest(), "1") && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), mPresenter.getThreadBean().getAuthorid())) {
                list.add("取消最佳答案");
            }

//            if (TextUtils.equals(mPresenter.getThreadBean().getExcellentable(), "1")) {
//                list.add("设为精彩回复");
//            }
//
//            if (TextUtils.equals(commentBean.getIsexcellent(), "1") && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), mPresenter.getThreadBean().getAuthorid())) {
//                list.add("撤销精彩回复");
//            }

            if (TextUtils.equals(commentBean.getAuthorid(), UserManger.getUserInfo().getMember_uid())) {
                list.add("删除");
            } else {
                list.add("回复");
                list.add("举报");
            }

            String[] datas = list.toArray(new String[list.size()]);
            morePopupWindow = new BottomListPopupWindow(this);
            morePopupWindow.setDatas(new BottomListPopupWindow.IItemClick() {
                @Override
                public void itemClick(int index, final String data) {
                    String pid = commentBean.getPid();
                    String tid = String.valueOf(mPresenter.getThreadBean().getTid());
                    if (TextUtils.equals(data, "回复")) {
                        mPresenter.actionShowCommentDialog(pos);
                    } else if (TextUtils.equals(data, "设为最佳答案")) {
                        mPresenter.requestAddBestanswer(commentBean, pid, tid);
                    } else if (TextUtils.equals(data, "取消最佳答案")) {
                        mPresenter.requestDelBestanswer(commentBean, pid, tid);
                    }
//                    else if (TextUtils.equals(data, "设为精彩回复")) {
//                        mPresenter.requestAddExcellentanswer(pid, tid);
//                    } else if (TextUtils.equals(data, "撤销精彩回复")) {
//                        mPresenter.requestDelExcellentanswer(pid, tid);
//                    }
                    else if (TextUtils.equals(data, "删除")) {
                        mPresenter.deleteReplyByUser(pos);
                    } else if (TextUtils.equals(data, "举报")) {
                        mPresenter.toJubaoActivity(commentBean.getFid(), commentBean.getPid(), commentBean.getTid());
                    }
                }
            }, datas);
            morePopupWindow.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            toLogin();
        }
    }

    @Override
    public void showTagsMoreDialog(List<ThreadTagBean> threadTagBeanList) {
        ThreadTagsPopupwindow threadTagsPopupwindow = new ThreadTagsPopupwindow(this);
        threadTagsPopupwindow.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        threadTagsPopupwindow.initData(threadTagBeanList);
    }

    @Override
    public void showAuthodDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(ThreadActivity.this);
        builder.setTitle("手机号绑定提示");
        builder.setMessage("依《网络安全法》相关要求回帖之前需先完成手机绑定");
        builder.setNegativeButton("放弃");
        builder.setPositiveButton("去绑定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                Bundle bundle = new Bundle();
                bundle.putString("url", Utils.HtmlUrl.HTML_BIND_PHONE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                jumpActivityForResult(SystemWebActivity.class, bundle, UPDATE_MOBILE);
            }
        });
        builder.create().show();
    }

    @Override
    public void showFlowerThreeDialog(String title, String msg) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(ThreadActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIsOneButton(true);
        builder.create().show();
    }

    @Override
    public void toPlayVideo(String videoUrl) {
        final Bundle bundle = new Bundle();
        bundle.putString(IntentKey.VIDEO_URL, videoUrl);

        //省流模式 wify未连接
        if (SetCommonManger.isFlowbyMode() && !FlyerApplication.wifiIsConnected) {
            if (flowDialog == null) {
                removeDialogFragment(TAG_FLOW);
                flowDialog = new FlowDialog();
                flowDialog.setFlowClickListener(new FlowDialog.IFlowClickListener() {
                    @Override
                    public void oneAllow() {
                        jumpActivity(PlayVideoActivity.class, bundle);
                    }

                    @Override
                    public void permanentAllow() {
                        SetCommonManger.closeFlowbyMode();
                        jumpActivity(PlayVideoActivity.class, bundle);
                    }

                    @Override
                    public void cancle() {

                    }
                });
            }
            flowDialog.show(getSupportFragmentManager(), TAG_FLOW);

        } else {
            jumpActivity(PlayVideoActivity.class, bundle);
        }


    }

    @ViewInject(R.id.major_thread_shoucang_num)
    TextView major_thread_shoucang_num;
    @ViewInject(R.id.major_thread_sendflower_num)
    TextView major_thread_sendflower_num;
    @ViewInject(R.id.major_thread_comment_num)
    TextView major_thread_comment_num;

    @Override
    public void initViews() {
        super.initViews();

        findViewById(R.id.normal_bottom_et).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserManger.isLogin())
                    mPresenter.actionShowCommentDialog();
                else
                    DialogUtils.showDialog(ThreadActivity.this);
            }
        });

        findViewById(R.id.major_thread_shoucang_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserManger.isLogin()) {
                    mPresenter.actionCollect();
                } else {
                    DialogUtils.showDialog(ThreadActivity.this);
                }
            }
        });
        findViewById(R.id.major_thread_sendflower_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserManger.isLogin()) {
                    mPresenter.sendFlowerByContent();
                } else {
                    DialogUtils.showDialog(ThreadActivity.this);
                }
            }
        });
        findViewById(R.id.major_thread_comment_num).setOnClickListener(toCommentListener());


        findViewById(R.id.toolbar_title_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.getThreadBean() == null) return;
                Bundle bundle = new Bundle();
                bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, String.valueOf(mPresenter.getThreadBean().getFid()));
                jumpActivity(CommunitySubActivity.class, bundle);
            }
        });
    }

    @Override
    public void settingView(Object obj) {
        ThreadDetailsBean threadDetailsBean = (ThreadDetailsBean) obj;
        if (DataTools.getInteger(threadDetailsBean.getFavid()) > 0) {//已收藏
            TvDrawbleUtils.chageDrawble(major_thread_shoucang_num, R.drawable.thread_shoucang_has);
            major_thread_shoucang_num.setTextColor(getResources().getColor(R.color.app_black));
        } else {
            TvDrawbleUtils.chageDrawble(major_thread_shoucang_num, R.drawable.thread_shoucang);
            major_thread_shoucang_num.setTextColor(getResources().getColor(R.color.app_body_grey));
        }

        if (TextUtils.equals(threadDetailsBean.getIslike(), "1")) {
            TvDrawbleUtils.chageDrawble(major_thread_sendflower_num, R.drawable.thread_flower_has);
            major_thread_sendflower_num.setTextColor(getResources().getColor(R.color.app_black));
        } else {
            TvDrawbleUtils.chageDrawble(major_thread_sendflower_num, R.drawable.thread_flower);
            major_thread_sendflower_num.setTextColor(getResources().getColor(R.color.app_body_grey));
        }

        WidgetUtils.setText(major_thread_shoucang_num, threadDetailsBean.getFavtimes());
        WidgetUtils.setText(major_thread_sendflower_num, String.valueOf(threadDetailsBean.getFlowers()));
        WidgetUtils.setText(major_thread_comment_num, String.valueOf(threadDetailsBean.getReplies()));

        DataUtils.downloadPicture(toolbar_title_icon, threadDetailsBean.getForumicon(), 0);
        WidgetUtils.setText(toolbar_title, TextUtils.isEmpty(mPresenter.title) ? threadDetailsBean.getForumname() : mPresenter.title);
    }

    /**
     * 点击评论事件
     *
     * @return
     */
    protected View.OnClickListener toCommentListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.toCommentActivity();
            }
        };
    }

    protected boolean isShowPageAll() {
        return true;
    }

}
