package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ArticleReplyBean;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fly on 2016/11/25.
 * 文章评论列表
 */

public class ArticleCommentPresenter extends MajorCommentPresenter {


    private boolean isDesc = false;
    private ArticlePresenter presenter;


    @Override
    public void init(Activity activity) {
        getView().loadDataWithBaseURL(imageDir, templateUtils.getTemplateOnceComment(), "text/html", "UTF-8", null);
        presenter = new ArticlePresenter(getView(), getDialog(), activity, this);
        presenter.setIsShowContent(false);
        presenter.requestArticleByContent(isDesc);
//        presenter.requestCommentList(isDesc, page);
    }

    @Override
    public void actionCommentSort() {
        isDesc = !isDesc;
        page = 1;
        requestThreadComment(page);
    }


    /**
     * 文章评论
     *
     * @param page
     */
    @Override
    protected void requestThreadComment(final int page) {
        presenter.requestCommentList(isDesc, page);
    }


    @Override
    public void actionWebviewScrollBottom() {
        super.actionWebviewScrollBottom();
    }

    @Override
    public void actionSendComment(String msg) {
        presenter.requestSendArticleComment(msg);
    }

    @Override
    public void clickCommentLi(String value) {

    }

    @Override
    public void actionToPictureComment(String commentContent) {

    }

    @Override
    public void saveReads() {

    }

    public ArticleContentBean getArticleBean() {
        return presenter.getArticleBean();
    }

}
