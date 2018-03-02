package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.ArticleCommentBean;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ArticleReplyBean;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IDialog;
import com.ideal.flyerteacafes.ui.interfaces.IThread;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TemplateUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/11/25.
 */

public class ArticlePresenter extends BasePresenter<IThread> {

    TemplateUtils templateUtils = new TemplateUtils();

    public static final String BUNDLE_AID = "aid";

    protected String imageDir;
    public String formhash;
    public String aid;
    private ArticleContentBean articleBean;
    private boolean isNeedReplyHeader = false;

    private List<ArticleReplyBean> commentBeanList = new ArrayList<>();
    private ArticleReplyBean newCommentBean;

    private boolean isShowContent = true;


    public ArticleContentBean getArticleBean() {
        return articleBean;
    }

    public List<ArticleReplyBean> getCommentList() {
        return commentBeanList;
    }

    ThreadPresenter mArticleCommentPresenter;

    public ArticlePresenter(IThread iThread, IDialog iDialog, Activity activity, ThreadPresenter mArticleCommentPresenter) {
        this.mArticleCommentPresenter = mArticleCommentPresenter;
        attachView(iThread);
        attachDialog(iDialog);
        attachBase(iThread);
        init(activity);
    }


    @Override
    public void init(Activity activity) {
        initData(activity);
    }


    public void setIsShowContent(boolean isShowContent) {
        this.isShowContent = isShowContent;
    }


    /**
     * 初始化数据
     *
     * @param activity
     */
    private void initData(Activity activity) {
        imageDir = CacheFileManger.getCacheImagePath();
        imageDir = "file:///" + imageDir;

        formhash = SharedPreferencesString.getInstances().getStringToKey("formhash");
        Intent intent = activity.getIntent();
        aid = intent.getStringExtra(BUNDLE_AID);
    }

    /**
     * 请求文章内容
     */
    public void requestArticleByContent(final boolean isDesc) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_DETAIL);
        params.addQueryStringParameter("aid", aid);
        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PDataCallback<ArticleContentBean>() {
            @Override
            public void flySuccess(DataBean<ArticleContentBean> result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    articleBean = result.getDataBean();
                    mArticleCommentPresenter.isLoadContentOk = true;
                    handlerWebViewLoadData(isDesc);
                } else {
                    ToastUtils.showToast(result.getMessage());
                    getBaseView().endActivity();
                }
                requestCommentList(isDesc, 1);
            }
        });
    }


    /**
     * 处理帖子请求结果
     */
    protected void handlerWebViewLoadData(boolean isDesc) {
        if (mArticleCommentPresenter.isLoadContentOk && mArticleCommentPresenter.isLoadCommentOk) {
            String content;
            if (isShowContent) {
                content = templateUtils.getArticleTemplateContentHtml(articleBean);
            } else {
                content = templateUtils.getTemplateOnceComment();
            }
            String comment = getCommentStr(commentBeanList.size(), commentBeanList, isDesc);
            content = StringTools.replace(content, "<!-- FIRST LOAD COMMENT -->", comment);
            getView().loadDataWithBaseURL(imageDir, content, "text/html", "UTF-8", null);
            getView().settingView(articleBean);

        }

    }

    /**
     * 评论字符串
     *
     * @param size
     * @param commentBeanList
     * @return
     */
    protected String getCommentStr(int size, List<ArticleReplyBean> commentBeanList, boolean isDesc) {
        String comment = templateUtils.getArticleTemplateCommentHtml(size, commentBeanList, articleBean.getAuthorid());

        String replyHeaderStr = "";

        if (isNeedReplyHeader() && mArticleCommentPresenter.page == mArticleCommentPresenter.startPage && DataTools.getInteger(articleBean.getReplies()) > 0) {

            replyHeaderStr = templateUtils.getReplyHeaderHTML(articleBean.getReplies(), isDesc);
        }

        comment = replyHeaderStr + comment;

        return comment;
    }


    /**
     * 文章评论列表
     *
     * @param isDesc
     * @param page
     */
    public void requestCommentList(final boolean isDesc, final int page) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_COMMENT);
        params.addQueryStringParameter("aid", aid);
        params.addQueryStringParameter("page", String.valueOf(page));
        if (isDesc) {
            params.addQueryStringParameter("dateline", "desc");
        } else {
            params.addQueryStringParameter("dateline", "asc");
        }
        getDialog().proDialogShow();
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_DATA_POSTS, ArticleReplyBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;

                if (!JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    getBaseView().endActivity();
                }

                /**
                 * 两种情况，
                 * 1. 正常逻辑
                 * 2. 评论列表里有新加的评论，需要移除掉
                 */

                mArticleCommentPresenter.hasNext = result.getHasNext();

                if (page == mArticleCommentPresenter.startPage) {
                    commentBeanList.clear();
                    newCommentBean = null;
                }

                if(!DataUtils.isEmpty(result.getDataList())) {
                    commentBeanList.addAll(result.getDataList());
                }

                //TODO 第一次加载的逻辑
                if (!mArticleCommentPresenter.isLoadCommentOk) {
                    mArticleCommentPresenter.isLoadCommentOk = true;
                    handlerWebViewLoadData(isDesc);
                } else {
                    String comment;
                    if (newCommentBean == null) {
                        comment = getCommentStr(commentBeanList.size(), result.getDataList(), isDesc);
                    } else {
                        commentBeanList.remove(newCommentBean);
                        newCommentBean = null;

                        comment = getCommentStr(commentBeanList.size(), commentBeanList, isDesc);
                    }

                    try {
                        comment = URLEncoder.encode(comment, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if (newCommentBean != null || page == mArticleCommentPresenter.startPage) {
                        getView().webviewLoadUrl("javascript:replaceComment('" + comment + "')()");
                    } else {
                        getView().webviewLoadUrl("javascript:appendComment('" + comment + "')()");
                    }

                }

            }
        });
    }


    /**
     * 发表文章评论内容
     */
    public void requestSendArticleComment(String message) {

        if (articleBean == null) return;

        getDialog().proDialogShow();
        if (DataTools.getInteger(articleBean.getFid()) != 0) {

            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPLYPOST);
            params.addQueryStringParameter("message", message);
            params.addQueryStringParameter("formhash", formhash);
            params.addQueryStringParameter("tid", articleBean.getTid());
            params.addQueryStringParameter("fid", articleBean.getFid());
            params.addQueryStringParameter("version", "6");
            XutilsHttp.Get(params, new PDataCallback<ArticleReplyBean>() {
                @Override
                public void flySuccess(DataBean<ArticleReplyBean> result) {
                    if (!isViewAttached()) return;
                    newCommentBean = result.getDataBean();
                    if (newCommentBean != null) {
                        ToastUtils.showToast("发表评论成功");
                        List<ArticleReplyBean> list = new ArrayList<>();
                        list.add(newCommentBean);
                        commentBeanList.addAll(list);
                        String comment = templateUtils.getArticleTemplateCommentHtml(commentBeanList.size(), list, articleBean.getAuthorid());
                        try {
                            comment = URLEncoder.encode(comment, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        getView().webviewLoadUrl("javascript:appendComment('" + comment + "')()");
                    } else {
                        ToastUtils.showToast(result.getMessage());
                    }

                    getView().closeCommentDialog();
                }
            });
        } else {
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_REPLY);
            params.addBodyParameter("aid", aid + "");
            params.addBodyParameter("message", message);
            params.addQueryStringParameter("version", "6");
            XutilsHttp.Post(params, new PDataCallback<ArticleReplyBean>() {
                @Override
                public void flySuccess(DataBean<ArticleReplyBean> result) {
                    if (!isViewAttached()) return;
                    newCommentBean = result.getDataBean();
                    if (newCommentBean != null) {
                        ToastUtils.showToast("发表评论成功");
                        List<ArticleReplyBean> list = new ArrayList<>();
                        list.add(newCommentBean);
                        commentBeanList.addAll(list);
                        String comment = templateUtils.getArticleTemplateCommentHtml(commentBeanList.size(), list, articleBean.getAuthorid());
                        try {
                            comment = URLEncoder.encode(comment, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        getView().webviewLoadUrl("javascript:appendComment('" + comment + "')()");
                    } else {
                        ToastUtils.showToast(result.getMessage());
                    }
                }
            });
        }

    }


    /**
     * 文章送花
     */
    public void sendFlowerByContent() {

        if (articleBean == null) return;
        if (TextUtils.equals(articleBean.getIsLike(), "1")) return;
        if (UserManger.getUserInfo().getMember_uid().equals(articleBean.getAuthorid())) {
            ToastUtils.showToast("不能自己给自己送花");
            return;
        }
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_SENDFLOWER);
        params.addQueryStringParameter("aid", articleBean.getAid());
        XutilsHttp.Get(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                if (result.getCode().equals("click_success")) {
                    int number = DataTools.getInteger(articleBean.getFlowers()) + 1;
                    articleBean.setFlowers(String.valueOf(number));
                    articleBean.setIsLike("1");
                    getView().settingView(articleBean);
                }
                ToastUtils.showToast(result.getMessage());
            }
        });
    }

    /**
     * 收藏
     */
    private void requestCollect() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_Collect);
        params.addQueryStringParameter("aid", aid);
        params.addQueryStringParameter("formhash", formhash);

        XutilsHttp.Get(params, new PMapCallback() {
            @Override
            public void flySuccess(MapBean result) {
                if (!isViewAttached()) return;
                if (result.getCode().equals("favorite_do_success")) {
                    String favid = result.getData().get("favid");
                    articleBean.setFavid(favid);
                    articleBean.setFavtimes(String.valueOf(DataTools.getInteger(articleBean.getFavtimes()) + 1));
                    getView().settingView(articleBean);
                }
                ToastUtils.showToast(result.getMessage());
            }
        });
    }

    /**
     * 取消收藏
     */
    private void requestCancleCollect() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_CANCLE_Collect);
        params.addQueryStringParameter("aid", aid);
        params.addQueryStringParameter("formhash", formhash);
        params.addQueryStringParameter("favid", articleBean.getFavid());
        XutilsHttp.Get(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                if (TextUtils.equals(result.getCode(), "do_success")) {
                    articleBean.setFavid("");
                    articleBean.setFavtimes(String.valueOf(DataTools.getInteger(articleBean.getFavtimes()) - 1));
                    getView().settingView(articleBean);
                }
                ToastUtils.showToast(result.getMessage());
            }
        });
    }

    /**
     * 点击收藏
     */
    public void actionCollect() {
        if (articleBean != null) {
            if (DataTools.getInteger(articleBean.getFavid()) > 0) {
                requestCancleCollect();
            } else {
                requestCollect();
            }
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        mArticleCommentPresenter = null;
    }

    public boolean isNeedReplyHeader() {
        return isNeedReplyHeader;
    }

    public void setNeedReplyHeader(boolean needReplyHeader) {
        isNeedReplyHeader = needReplyHeader;
    }
}
