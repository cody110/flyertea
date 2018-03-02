package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.ReadsManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.Attachments;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.PictureBrowseActivity;
import com.ideal.flyerteacafes.ui.activity.ReplyPostsActivity;
import com.ideal.flyerteacafes.ui.activity.ThreadJubaoActivity;
import com.ideal.flyerteacafes.ui.activity.ThreadTagActivity;
import com.ideal.flyerteacafes.ui.activity.TopicDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.VerificationActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IShareDialogGexing;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadCommentDialog;
import com.ideal.flyerteacafes.ui.activity.threads.MajorCommentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.activity.video.PlayVideoActivity;
import com.ideal.flyerteacafes.ui.dialog.ThreadShareDialog;
import com.ideal.flyerteacafes.ui.interfaces.IThread;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.TemplateUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.codec.Encoder;
import org.lasque.tusdk.core.http.URLEncodedUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fly on 2016/11/21.
 * 文章帖子基类
 */

public class ThreadPresenter extends BasePresenter<IThread> implements IThreadCommentDialog, IShareDialogGexing, Handler.Callback {


    public String img = "<img id='imageid' class='contentImage' style = 'width:100%;height:100' src='file:///android_asset/post_not_image_def.png' index='<!--  CONTENTIMAGE INDEX  -->'/>";
    public String imgComment = "<img id='imageid' class='commentImage' src='file:///android_asset/post_not_image_def.png' index='<!--  CONTENTIMAGE INDEX  -->'/>";

    ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private Context context = FlyerApplication.getContext();
    protected TemplateUtils templateUtils = new TemplateUtils();
    protected ThreadDetailsBean threadBean;
    protected List<CommentBean> commentBeanList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    public String tid;
    public int page = 1;
    protected String friend = "1";//朋友
    protected boolean hasNext = true;
    protected String imageDir;
    public static final String BUNDLE_FID = "fid", BUNDLE_TID = "tid";
    public static final String BUNDLE_POS = "index";
    public static final String BUNDLE_COMMENT_IMG = "comment_img";//图片评论返回
    private String fid;
    private int commentThread = -1;
    private int commentIndex = commentThread;
    private int index;

    private boolean isDesc = false;
    private CommentBean newCommentBean;


    public static final int HANDLER_WHAT_DELETE = 1, HANDLER_WHAT_UP = 2, HANDLER_WHAT_DOWN = 3;

    public Handler handler = new Handler(this);

    //TODO 短帖 专业帖标识
    public static final String BUNDLE_TYPE_KEY = "type";
    public static final int BUNDLE_TYPE_MAJOR = 1;
    public static final int BUNDLE_TYPE_NORMAL = 2;
    private int thread_type = BUNDLE_TYPE_NORMAL;


    public static final String BUNDLE_TITLE = "title";
    public static final String BUNDLE_STARTPAGE = "bundle_startpage";
    public static final String BUNDLE_SCROLL_POS = "bundle_scroll_pos";

    public int totalpage = 0;
    //TODO 默认开始请求的页码
    public int startPage = 1;
    //TODO 定位到的楼层
    private int location_pos = 1;
    //TODO 是否加载了正文 、评论
    protected boolean isLoadContentOk, isLoadCommentOk;

    public String title;


    public ThreadDetailsBean getThreadBean() {
        return threadBean;
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        initData(activity);
        requestThread();

    }

    /**
     * 是否是正常帖
     *
     * @return
     */
    public boolean isNormal() {
        return thread_type == BUNDLE_TYPE_NORMAL;
    }


    /**
     * 初始化数据
     *
     * @param activity
     */
    protected void initData(Activity activity) {
        imageDir = CacheFileManger.getCacheImagePath();
        imageDir = "file:///" + imageDir;

        Intent intent = activity.getIntent();
        tid = intent.getStringExtra("tid");
//        tid = "1869475";
        index = intent.getIntExtra(BUNDLE_POS, -1);
        thread_type = intent.getIntExtra(BUNDLE_TYPE_KEY, BUNDLE_TYPE_NORMAL);
        page = startPage = intent.getIntExtra(BUNDLE_STARTPAGE, 1);
        location_pos = intent.getIntExtra(BUNDLE_SCROLL_POS, 1);
        title = intent.getStringExtra(BUNDLE_TITLE);

    }

    @Override
    public void detachView() {
        super.detachView();
        threadPool.shutdownNow();
        saveReads();
    }


    public void actionShowCommentDialog() {
        commentIndex = commentThread;
        Bundle bundle = new Bundle();
        bundle.putString("hint", "回复:");
        bundle.putInt("index", commentIndex);
        getView().showCommentDialog(bundle);
    }

    public void actionShowCommentDialog(int pos) {
        commentIndex = pos;
        CommentBean commentBean = commentBeanList.get(pos);

        Bundle bundle = new Bundle();
        bundle.putString("hint", "回复" + commentBean.getAuthor() + ":");
        bundle.putInt("index", commentIndex);
        getView().showCommentDialog(bundle);
    }

    /**
     * 点击右上角
     */
    public void actionShowShareDialog() {
        if (threadBean != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(ThreadShareDialog.BUNDLE_TYPE, ThreadShareDialog.TYPE_THREAD);
            bundle.putInt(ThreadShareDialog.BUNDLE_THREAD_TYPE, ThreadShareDialog.TYPE_THREAD_MAJOR);
            bundle.putSerializable(ThreadShareDialog.BUNDLE_CONTENT, threadBean);
            getView().showShareDialog(bundle);
        }
    }


    /**
     * webview加载完成
     */
    public void actionWebviewLoadFinished() {
        if (isViewAttached()) {
            if (location_pos > 1)
                getView().webviewLoadUrl("javascript:scrollToSpecifiedFloor('#comment-item-" + (location_pos - 2) + "')()");
            startLoadImage();
        }
    }

    /**
     * 开始下载图片
     */
    public void startLoadImage() {
        if (DataUtils.isPictureMode(context)) {
            for (String url : urlList) {
                Thread thread = new DownLoadImage(url);
                threadPool.execute(thread);
            }
        }
    }


    /**
     * 帖子
     */
    protected void requestThread() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_DETAIL);
        params.addQueryStringParameter("tid", tid);
        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PDataCallback<ThreadDetailsBean>() {
            @Override
            public void flySuccess(DataBean<ThreadDetailsBean> result) {
                if (!isViewAttached()) return;
                if (result.getJsonType() == result.type_error || result.getDataBean() == null) {
                    ToastUtils.showToast("您没有权限或帖子不存在");
                    getBaseView().endActivity();
                } else {
                    threadBean = result.getDataBean();
                    isLoadContentOk = true;
                    handlerWebViewLoadData();
                    requestThreadComment(page);
                }

            }
        });
    }


    /**
     * 处理帖子请求结果
     */
    protected void handlerWebViewLoadData() {
        if (isLoadContentOk && isLoadCommentOk) {
            String content = templateUtils.getTemplateContentHtml(threadBean, true, commentBeanList.size());
            String comment = getCommentStr(commentBeanList.size(), commentBeanList);
            content = StringTools.replace(content, "<!-- FIRST LOAD COMMENT -->", comment);
            getView().loadDataWithBaseURL(imageDir, content, "text/html", "UTF-8", null);
            getView().settingView(threadBean);

        }

    }

    /**
     * 阅读记录
     */

    public void saveReads() {
        if (threadBean != null) {
            ReadsManger.getInstance().save(threadBean);
            YueManger.getInstance().save(String.valueOf(threadBean.getTid()));
        }
    }


    /**
     * webview滚动到底部
     */
    public void actionWebviewScrollBottom() {
        if (hasNext) {
            page++;
            requestThreadComment(page);
        }
    }

    /**
     * 评论列表升序降序切换
     */
    public void actionCommentSort() {
        isDesc = !isDesc;
        startPage = 1;
        page = startPage;
        requestThreadComment(page);
    }


    public void confirmDeleteThread() {
//        if (TextUtils.equals(threadBean.getGroupid(), "1") || TextUtils.equals(threadBean.getGroupid(), "2")) {
//            deletePostByAdmin();
//        } else {
        deletePostByUser();
//        }
    }

    /**
     * 回复
     */
    @SuppressWarnings("unused")
    private void requestComment(String msg) {
        if (UserManger.isLogin()) {
            if (threadBean == null) return;
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPLYPOST);
            params.addQueryStringParameter("tid", tid + "");
            params.addQueryStringParameter("fid", String.valueOf(threadBean.getFid()));

            if (commentIndex != commentThread) {
                params.addQueryStringParameter("pid", commentBeanList.get(commentIndex).getPid());
                params.addQueryStringParameter("noticetrimstr", commentBeanList.get(commentIndex).getQuotemsg());
                params.addQueryStringParameter("replytouid", commentBeanList.get(commentIndex).getAuthorid());
            } else {
                params.addQueryStringParameter("replytouid", threadBean.getAuthorid());
            }
            params.addQueryStringParameter("message", msg);
            params.addQueryStringParameter("formhash", UserManger.getFormhash());
            params.addQueryStringParameter("version", "6");
            getDialog().proDialogShow();
            XutilsHttp.Get(params, new PDataCallback<CommentBean>() {

                @Override
                public void flyStart() {
                    super.flyStart();
                    getDialog().proDialogShow();
                }

                @Override
                public void flySuccess(DataBean<CommentBean> result) {
                    if (!isViewAttached()) return;
                    handlerNewComment(result.getDataBean());
                    if (result.isSuccessEquals1()) {
                        ToastUtils.showToast("发表评论成功");
                    } else {
                        ToastUtils.showToast(result.getMessage());
                    }
                }

                @Override
                public void flyError() {
                    super.flyError();
                    ToastUtils.showToast("发表评论失败！");
                }

            });
        } else {
            getBaseView().toLogin();
        }
    }

    /**
     * 评论完的处理操作
     *
     * @param commentBean
     */
    public void handlerNewComment(CommentBean commentBean) {
        newCommentBean = commentBean;
        if (newCommentBean != null) {
            List<CommentBean> list = new ArrayList<>();
            list.add(newCommentBean);
            commentBeanList.addAll(list);


            String comment = templateUtils.getTemplateCommentHtml(commentBeanList.size(), list, threadBean.getAuthorid());

            //为第一条数据需要头部
            if (commentBeanList.size() == 1) {
                String replyHeaderStr = templateUtils.getReplyHeaderHTML(threadBean.getReplies(), isDesc);
                comment = replyHeaderStr + comment;
            }

            try {
                comment = URLEncoder.encode(comment, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (commentBeanList.size() == 1) {
                getView().webviewLoadUrl("javascript:replaceComment('" + comment + "')()");
            } else {
                getView().webviewLoadUrl("javascript:appendComment('" + comment + "')()");
            }
        }
        getView().closeCommentDialog();
    }

    /**
     * 评论列表
     */
    protected void requestThreadComment(final int page) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_COMMENTLIST);
        params.addQueryStringParameter("tid", tid);
        params.addQueryStringParameter("page", String.valueOf(page));
        if (isDesc) {
            params.addQueryStringParameter("dateline", "desc");
        } else {
            params.addQueryStringParameter("dateline", "asc");
        }
        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_DATA_POSTS, CommentBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;

                /**
                 * 两种情况，
                 * 1. 正常逻辑
                 * 2. 评论列表里有新加的评论，需要移除掉
                 */

                hasNext = result.getHasNext();

                if (page == startPage) {
                    commentBeanList.clear();
                    newCommentBean = null;
                }

                if (!DataUtils.isEmpty(result.getDataList())) {
                    commentBeanList.addAll(result.getDataList());
                }

                //TODO 第一次加载的逻辑
                if (!isLoadCommentOk) {
                    isLoadCommentOk = true;
                    handlerWebViewLoadData();
                } else {
                    String comment;
                    if (newCommentBean == null) {
                        comment = getCommentStr(commentBeanList.size(), result.getDataList());
                    } else {
                        commentBeanList.remove(newCommentBean);
                        newCommentBean = null;

                        comment = getCommentStr(commentBeanList.size(), commentBeanList);
                    }

                    try {
                        comment = URLEncoder.encode(comment, "UTF-8");
                        comment = comment.replace("%", "%25");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if (newCommentBean != null || page == startPage) {
                        getView().webviewLoadUrl("javascript:replaceComment('" + comment + "')()");
                    } else {
                        getView().webviewLoadUrl("javascript:appendComment('" + comment + "')()");
                    }


                }

                totalpage = result.getTotalpage();
                getView().showCommentPage(page, result.getTotalpage());

                if (page == startPage) {
                    if (location_pos != 1) {
                        getView().webviewLoadUrl("javascript:scollToIndex('" + (location_pos - 2) + "')()");
                    }
                }
            }

        });
    }


    /**
     * 评论字符串
     *
     * @param size
     * @param commentBeanList
     * @return
     */
    protected String getCommentStr(int size, List<CommentBean> commentBeanList) {
        String comment = templateUtils.getTemplateCommentHtml(size, commentBeanList, threadBean.getAuthorid());

        String replyHeaderStr = "";

        if (isNeedReplyHeader() && page == startPage && commentBeanList.size() > 0) {
            replyHeaderStr = templateUtils.getReplyHeaderHTML(threadBean.getReplies(), isDesc);
        }

        comment = replyHeaderStr + comment;

        return comment;
    }


    /**
     * 设置初始化请求页码
     *
     * @param startPage
     */
    public void formStartPageRequestComment(int startPage) {
        this.startPage = startPage;
        page = startPage;
        requestThreadComment(page);
    }

    /**
     * 是否需要评论条数
     *
     * @return
     */
    protected boolean isNeedReplyHeader() {
        return true;
    }


    /**
     * 给主贴送鲜花
     */
    public void sendFlowerByContent() {
        if (UserManger.isLogin()) {
            if (threadBean == null) return;
            if (UserManger.getUserInfo().getMember_uid().equals(threadBean.getAuthorid())) {
                ToastUtils.showToast("不能自己给自己送花");
                return;
            }
            if (TextUtils.equals(threadBean.getIslike(), "1")) {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CANCLE_FLOWER);
                params.addBodyParameter("tid", tid + "");
                params.addBodyParameter("touid", threadBean.getAuthorid());
                params.addBodyParameter("pid", threadBean.getPid() + "");

                getDialog().proDialogShow();
                XutilsHttp.Post(params, new PBaseCallback() {
                    @Override
                    public void flySuccess(BaseBean baseBean) {
                        if (!isViewAttached()) return;
                        if (baseBean.isSuccessEquals1()) {
                            int number = DataTools.getInteger(threadBean.getFlowers()) - 1;
                            threadBean.setFlowers(number);
                            threadBean.setIslike("0");
                            getView().settingView(threadBean);
                            ToastUtils.showToast("取消送花成功");
                        } else {
                            ToastUtils.showToast(baseBean.getMessage());
                        }
                    }
                });


            } else {
                FlyRequestParams params1 = new FlyRequestParams(Utils.HttpRequest.HTTP_FLOWER);
                params1.addBodyParameter("tid", tid + "");
                params1.addBodyParameter("uid", threadBean.getAuthorid());
                params1.addBodyParameter("pid", threadBean.getPid() + "");
                params1.addBodyParameter("formhash", UserManger.getFormhash());

                getDialog().proDialogShow();
                XutilsHttp.Post(params1, new PStringCallback() {
                    @Override
                    public void flySuccess(String result) {
                        if (!isViewAttached()) return;
                        BaseBean baseBean = JsonAnalysis.jsonToSendFlower(result);
                        if (baseBean.getCode().equals("success")) {
                            int number = DataTools.getInteger(threadBean.getFlowers()) + 1;
                            threadBean.setFlowers(number);
                            threadBean.setIslike("1");
                            getView().settingView(threadBean);
                            if (!isSendFlowerThreeHander(result)) {
                                ToastUtils.showToast("送花成功");
                            }

                        } else {
                            ToastUtils.showToast(baseBean.getMessage());
                        }
                    }

                });
            }


        } else {
            getBaseView().toLogin();
        }

    }


    /**
     * 点击收藏
     */
    @Override
    public void actionCollect() {
        if (threadBean != null) {
            if (DataTools.getInteger(threadBean.getFavid()) > 0) {
                requestCancleCollect(threadBean.getFavid());
            } else {
                requestCollect(String.valueOf(threadBean.getTid()));
            }
        }
    }

    @Override
    public void deleteThread() {
        getView().isConfirmDeleteThread();
    }

    @Override
    public void actionJubao() {
        if (UserManger.isLogin()) {
            if (threadBean == null) return;
            toJubaoActivity(String.valueOf(threadBean.getFid()), String.valueOf(threadBean.getPid()), String.valueOf(threadBean.getTid()));
        } else {
            getBaseView().toLogin();
        }
    }


    public void toJubaoActivity(String fid, String pid, String tid) {
        Bundle bundle = new Bundle();
        bundle.putString(ThreadJubaoPresenter.BUNDLE_FID, fid);
        bundle.putString(ThreadJubaoPresenter.BUNDLE_PID, pid);
        bundle.putString(ThreadJubaoPresenter.BUNDLE_TID, tid);
        getBaseView().jumpActivity(ThreadJubaoActivity.class, bundle);
    }

    /**
     * 取消收藏
     *
     * @param id
     */
    private void requestCancleCollect(String id) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CANCLE_COLLECT);
        params.addQueryStringParameter("favid", id + "");
        params.addQueryStringParameter("formhash", UserManger.getFormhash());
        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                BaseBean bean = JsonAnalysis.getToMessage(result);
                if (bean.getCode().equals("do_success")) {
                    threadBean.setFavid("0");
                    threadBean.setFavtimes(String.valueOf(DataTools.getInteger(threadBean.getFavtimes()) - 1));
                    getView().settingView(threadBean);
                }
                ToastUtils.showToast(bean.getMessage());
            }

        });
    }

    /**
     * 收藏
     *
     * @param id
     */
    private void requestCollect(String id) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COLLECT);
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("formhash", UserManger.getFormhash());
        XutilsHttp.Get(params, new PStringCallback() {

            @Override
            public void flyStart() {
                super.flyStart();
                getDialog().proDialogShow();
            }

            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                BaseBean bean = JsonAnalysis.getToMessage(result);
                if (bean.getCode().equals("favorite_do_success")) {
                    int favid = JsonAnalysis.getPostCollectId(result);
                    threadBean.setFavid(String.valueOf(favid));
                    threadBean.setFavtimes(String.valueOf(DataTools.getInteger(threadBean.getFavtimes()) + 1));
                    getView().settingView(threadBean);
                }
                ToastUtils.showToast(bean.getMessage());
            }

        });
    }

    /**
     * 管理员删除主贴
     */
    public void deletePostByAdmin() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DELETE_POST);
        params.addBodyParameter("frommodcp", "");
        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("handlekey", "mods");
        params.addBodyParameter("reason", "通过app删除");
        params.addBodyParameter("listextra", "listextra");
        params.addBodyParameter("redirect", "");
        params.addBodyParameter("operations[]", "delete");
        params.addBodyParameter("fid", threadBean.getFid() + "");
        params.addBodyParameter("moderate[]", tid + "");//tid
        XutilsHttp.Post(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                postDeleteDispose(result);
            }
        });
    }

    /**
     * 用户删除帖子
     */
    public void deletePostByUser() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DELETE_REPLY_USER);
        params.addBodyParameter("fid", threadBean.getFid() + "");
        params.addBodyParameter("tid", tid + "");
        params.addBodyParameter("pid", threadBean.getPid() + "");
        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("delete", "1");
        params.addBodyParameter("reason", "用户自己删除");
        XutilsHttp.Post(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                postDeleteDispose(result);
            }
        });
    }


    /**
     * 删除帖子，处理
     */
    private void postDeleteDispose(BaseBean bean) {
        if (bean.getCode().equals("post_edit_delete_succeed") || bean.getCode().equals("admin_succeed")) {
            if (index != -1) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", index);
                getBaseView().jumpActivitySetResult(bundle);
            } else {
                getBaseView().endActivity();
            }
            ToastUtils.showToast("帖子删除成功");
        } else {
            ToastUtils.showToast(bean.getMessage());
        }
    }

    /**
     * 用户删除回复
     */
    public void deleteReplyByUser(final int commentIndex) {
        CommentBean bean = commentBeanList.get(commentIndex);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DELETE_REPLY_USER);
        params.addBodyParameter("fid", bean.getFid());
        params.addBodyParameter("tid", String.valueOf(threadBean.getTid()));
        params.addBodyParameter("pid", bean.getPid());
        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("delete", "1");
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                BaseBean bean = JsonAnalysis.getToMessage(result);
                if (bean.getCode().equals("post_edit_delete_succeed") || bean.getCode().equals("post_reply_succeed")) {
                    commentBeanList.remove(commentIndex);
                    String comment = templateUtils.getTemplateCommentHtml(commentBeanList.size(), commentBeanList, threadBean.getAuthorid());
                    try {
                        comment = URLEncoder.encode(comment, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getView().webviewLoadUrl("javascript:replaceComment('" + comment + "')()");
                    ToastUtils.showToast("回复删除成功");
                } else {
                    ToastUtils.showToast(bean.getMessage());
                }
            }
        });
    }


    public void requestAddExcellentanswer(String pid, String tid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_COMMENT_ACTION);
        params.addQueryStringParameter("action", "excellentanswer");
        params.addQueryStringParameter("op", "add");
        params.addQueryStringParameter("pid", pid);
        params.addQueryStringParameter("tid", tid);

        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    getView().webviewLoadUrl("javascript:showHuifu(" + commentIndex + ")()");
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                }
            }
        });


    }

    public void requestDelExcellentanswer(String pid, String tid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_COMMENT_ACTION);
        params.addQueryStringParameter("action", "excellentanswer");
        params.addQueryStringParameter("op", "del");
        params.addQueryStringParameter("pid", pid);
        params.addQueryStringParameter("tid", tid);

        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    getView().webviewLoadUrl("javascript:hintHuifu(" + commentIndex + ")()");
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                }
            }
        });


    }

    /**
     * 添加最佳答案
     *
     * @param commentBean
     * @param pid
     * @param tid
     */
    public void requestAddBestanswer(final CommentBean commentBean, String pid, String tid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_COMMENT_ACTION);
        params.addQueryStringParameter("action", "bestanswer");
        params.addQueryStringParameter("op", "add");
        params.addQueryStringParameter("pid", pid);
        params.addQueryStringParameter("tid", tid);

        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    getThreadBean().setBestable("0");
                    commentBean.setIsbest("1");
                    getView().webviewLoadUrl("javascript:showDaan(" + commentIndex + ")()");
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                }
            }
        });


    }

    /**
     * 撤销最佳答案
     *
     * @param commentBean
     * @param pid
     * @param tid
     */
    public void requestDelBestanswer(final CommentBean commentBean, String pid, String tid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_COMMENT_ACTION);
        params.addQueryStringParameter("action", "bestanswer");
        params.addQueryStringParameter("op", "del");
        params.addQueryStringParameter("pid", pid);
        params.addQueryStringParameter("tid", tid);

        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    getThreadBean().setBestable("1");
                    commentBean.setIsbest("0");
                    getView().webviewLoadUrl("javascript:hintDaan(" + commentIndex + ")()");
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                }

            }
        });


    }


    public void toCommentActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(ThreadPresenter.BUNDLE_TID, tid);
        getBaseView().jumpActivity(MajorCommentActivity.class, bundle);

        HashMap<String, String> map = new HashMap<>();
        map.put("tid", tid);
        MobclickAgent.onEvent(FlyerApplication.getContext(), FinalUtils.EventId.notedetail_comment_click, map);
    }


    @Override
    public void actionToPictureComment(String commentContent) {
        if (threadBean == null) return;
        Bundle bundle = new Bundle();
        bundle.putString("authorid", threadBean.getAuthorid());
        if (commentIndex == commentThread) {
            bundle.putInt("tid", DataTools.getInteger(tid));
            bundle.putInt("fid", threadBean.getFid());
            bundle.putInt("pid", threadBean.getPid());
        } else {
            bundle.putInt("tid", DataTools.getInteger(commentBeanList.get(commentIndex).getTid()));
            bundle.putInt("fid", DataTools.getInteger(commentBeanList.get(commentIndex).getFid()));
            bundle.putInt("pid", DataTools.getInteger(commentBeanList.get(commentIndex).getPid()));
            bundle.putString("commit", commentBeanList.get(commentIndex).getQuotemsg());
        }
        bundle.putInt("posttype", FinalUtils.POST_COMMENT);
        bundle.putString("commentContent", commentContent);
        getBaseView().jumpActivityForResult(ReplyPostsActivity.class, bundle, ThreadActivity.request_comment_img);
    }

    @Override
    public void actionSendComment(String msg) {
        if (TextUtils.equals(UserManger.getUserInfo().getAuthed(), "1")) {
            requestComment(msg);
        } else {
            getView().showAuthodDialog();
        }
    }

    @Override
    public boolean handleMessage(Message message) {

        return false;
    }


    public void orderByUp() {
        if (!isDesc) return;
        page = 1;
        isDesc = false;
        requestThreadComment(page);
    }

    public void orderByDown() {
        if (isDesc) return;
        page = 1;
        isDesc = true;
        requestThreadComment(page);
    }

    /**
     * 下载图片的线程
     */
    private class DownLoadImage extends Thread {

        private String url;

        public DownLoadImage(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String path = DataUtils.getImagePath(context, url);
            File file = new File(path);
            Bitmap bitmap = BitmapTools.loadImageFromUrl(url);
            Boolean bol = false;
            if (bitmap != null)
                bol = BitmapTools.saveJPGE_After(bitmap, file);
            //         先判断是否已经回收
            if (bitmap != null && !bitmap.isRecycled()) {
                // 回收并且置为null
                bitmap.recycle();
            }

            if (!isViewAttached()) return;
            if (bol) {
                getView().webviewLoadUrl("javascript:(function(){"
                        + "document.getElementById(\"" + DataUtils.getImageId(url)
                        + "\").src=\"" + "file:///"
                        + DataUtils.getImagePath(context, url) + "\"" + "})()");
            } else {
                getView().webviewLoadUrl("javascript:(function(){"
                        + "document.getElementById(\"" + DataUtils.getImageId(url)
                        + "\").src=\"" + url + "\"" + "})()");
            }

        }

    }

    /**
     * 处理送花三次提醒
     *
     * @param jsonString
     */
    private boolean isSendFlowerThreeHander(String jsonString) {
        Map<String, String> map = GsonTools.getMap(jsonString);
        if (map != null) {
            if (TextUtils.equals(map.get("code"), "0")) {
                getView().showFlowerThreeDialog(map.get("msg"), map.get("appcanstr2"));
                return true;
            }

        }
        return false;
    }


    /**webview js start*/
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 图片点击
     *
     * @param index
     */
    @JavascriptInterface
    public void clickContentImage(String index) {
        int num = index.indexOf(",");
        int pos = Integer.parseInt(index.substring(0, num)); // 楼层，-1为正文，其他代表评论
        index = index.substring(num + 1);// 标识图片位置

        Bundle bundle = new Bundle();

        ArrayList<String> urlList = new ArrayList<>();
        List<Attachments> attachmentsList = null;

        if (pos == -1)
            attachmentsList = threadBean.getAttachList();
        else
            attachmentsList = commentBeanList.get(pos).getAttachments();

        for (Attachments bean : attachmentsList) {
            urlList.add(bean.getImageurl());
        }

        bundle.putStringArrayList("urlList", urlList);
        bundle.putInt("pos", Integer.parseInt(index));

        getBaseView().jumpActivity(PictureBrowseActivity.class, bundle);
    }

    /**
     * 加好友
     */
    @JavascriptInterface
    public void clickAddFriend() {
        if (UserManger.isLogin()) {
            if (!TextUtils.equals(threadBean.getIsfriend(), friend)) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", threadBean.getAuthorid());
                getBaseView().jumpActivity(VerificationActivity.class, bundle);
            }
        } else {
            getBaseView().toLogin();
        }
    }

    /**
     * 作者点击
     */
    @JavascriptInterface
    public void clickOnAvatar(String value) {
        String uid = "";
        if (value.equals("undefined")) {
            uid = threadBean.getAuthorid();
        } else {
            value = StringTools.replaceFirst(value, "comment", "");
            int pos = Integer.parseInt(value);
            uid = commentBeanList.get(pos).getAuthorid();
        }
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        bundle.putString("activity", "SearchMemberActivity");
        getBaseView().jumpActivity(UserDatumActvity.class, bundle);
    }

    /**
     * 评论点击
     */
    @JavascriptInterface
    public void clickCommentLi(final String value) {
        commentIndex = DataTools.getInteger(value);
        if (UserManger.isLogin()) {
            if (TextUtils.equals(commentBeanList.get(DataTools.getInteger(value)).getAuthorid(), UserManger.getUserInfo().getMember_uid())) {
//                getView().showDeleteCommentDialog();
            } else {
                actionShowCommentDialog(DataTools.getInteger(value));

            }
        } else {
            getBaseView().toLogin();
        }

    }

    /**
     * 送花
     */
    @JavascriptInterface
    public void clickSendFLower(final String value) {

        LogFly.e("clickSendFLower:" + value);


        if (!UserManger.isLogin()) return;
        final CommentBean commentbean = commentBeanList.get(DataTools.getInteger(value));
        if (TextUtils.equals(UserManger.getUserInfo().getMember_uid(), commentbean.getAuthorid())) {
            ToastUtils.showToast("不能自己给自己送花");
            return;
        }
        if (TextUtils.equals(commentbean.getIslike(), Marks.THREAD_SEND_FLOWER_ED)) {
            ToastUtils.showToast(context.getString(R.string.thread_send_flower_remind));
            return;
        }

        FlyRequestParams params1 = new FlyRequestParams(Utils.HttpRequest.HTTP_FLOWER);
        params1.addBodyParameter("tid", commentbean.getTid());
        params1.addBodyParameter("uid", commentbean.getAuthorid());
        params1.addBodyParameter("pid", commentbean.getPid() + "");
        params1.addBodyParameter("formhash", UserManger.getFormhash());
        XutilsHttp.Post(params1, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;

                BaseBean baseBean = JsonAnalysis.jsonToSendFlower(result);
                if (baseBean.getCode().equals("success")) {
                    int number = DataTools.getInteger(commentbean.getFlowers()) + 1;
                    commentbean.setFlowers(String.valueOf(number));
                    commentbean.setIslike(Marks.THREAD_SEND_FLOWER_ED);
                    getView().webviewLoadUrl("javascript:updateFlower('" + value + "','" + number + "')()");
                }

                if (!isSendFlowerThreeHander(result)) {
                    ToastUtils.showToast(baseBean.getMessage());
                }
            }
        });
    }


    /**
     * 选择论坛版块
     */
    @JavascriptInterface
    public void clickToForum() {
        Bundle bundle = new Bundle();
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, String.valueOf(threadBean.getFid()));
        getBaseView().jumpActivity(CommunitySubActivity.class, bundle);
    }

    /**
     * 滚动到指定位置
     *
     * @param location
     */
    @JavascriptInterface
    public void scollToLocation(String location) {

    }


    /**
     * 按时间升序
     */
    @JavascriptInterface
    public void clickCommentUp() {
        orderByUp();
    }

    /**
     * 按时间降序
     */
    @JavascriptInterface
    public void clickCommentDown() {
        orderByDown();
    }

    /**
     * more
     */
    @JavascriptInterface
    public void clickMore(String value) {
        commentIndex = DataTools.getInteger(value);
        getView().showMoreDialog(commentIndex, commentBeanList.get(commentIndex));
    }

    @JavascriptInterface
    public void clickTag() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", threadBean.getTags().get(0));
        getBaseView().jumpActivity(ThreadTagActivity.class, bundle);
    }


    @JavascriptInterface
    public void clickTagMore() {
        getView().showTagsMoreDialog(threadBean.getTags());
    }

    @JavascriptInterface
    public void clickTopic() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", threadBean.getCollection());
        getBaseView().jumpActivity(TopicDetailsActivity.class, bundle);

        if (TextUtils.equals(threadBean.getCollection().getType(), "fid")) {//熱帖
            HashMap<String, String> map = new HashMap<>();
            map.put("name", threadBean.getForumname());
            MobclickAgent.onEvent(context, FinalUtils.EventId.notedetail_hotcollection_click, map);
        }
    }

    @JavascriptInterface
    public void clickPlay(String id) {
        if (!TextUtils.isEmpty(id)) {
            if (id.contains("-")) {
                id = id.substring(id.indexOf("-") + 1);
                for (ThreadDetailsBean.VideoInfo info : threadBean.getVideos()) {
                    if (TextUtils.equals(id, info.getVid())) {
                        getView().toPlayVideo(info.getVideourl());
                        break;
                    }
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**webview js end*/


}
