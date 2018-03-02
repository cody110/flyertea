package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.ReadsManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.Attachments;
import com.ideal.flyerteacafes.ui.activity.PictureBrowseActivity;
import com.ideal.flyerteacafes.ui.activity.VerificationActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleCommentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.dialog.ThreadShareDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fly on 2016/11/25.
 * 文章正文
 */

public class ArticleContentPresenter extends ThreadPresenter {

    private boolean isDesc = false;
    private ArticlePresenter presenter;

    @Override
    public void init(Activity activity) {
        title = activity.getIntent().getStringExtra(ThreadPresenter.BUNDLE_TITLE);
        presenter = new ArticlePresenter(getView(), getDialog(), activity, this);
        presenter.setNeedReplyHeader(isNeedReplyHeader());
        presenter.requestArticleByContent(isDesc);
//        presenter.requestCommentList(isDesc, page);
    }



    /**
     * 阅读记录
     */
    @Override
    public void saveReads() {
        if (presenter.getArticleBean() != null) {
            ReadsManger.getInstance().save(presenter.getArticleBean());
        }
    }

    /**
     * 收藏
     */
    @Override
    public void actionCollect() {
        presenter.actionCollect();
    }

    /**
     * 选择排序处理
     *
     * @param message
     * @return
     */
    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case HANDLER_WHAT_UP:
                isDesc = false;
                page = 1;
                presenter.requestCommentList(false, 1);
                break;
            case HANDLER_WHAT_DOWN:
                isDesc = true;
                page = 1;
                presenter.requestCommentList(true, 1);
                break;
        }
        return false;
    }

    /**
     * 正文送花
     */
    @Override
    public void sendFlowerByContent() {
        presenter.sendFlowerByContent();
    }

    /**
     * 滚动到底部
     */
    @Override
    public void actionWebviewScrollBottom() {
        startLoadImage();
        if (hasNext) {
            page++;
            presenter.requestCommentList(isDesc, page);
        }
    }

    /**
     * 分享弹出框
     */
    @Override
    public void actionShowShareDialog() {
        if (presenter.getArticleBean() != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(ThreadShareDialog.BUNDLE_TYPE, ThreadShareDialog.TYPE_ARTICLE);
            bundle.putSerializable(ThreadShareDialog.BUNDLE_CONTENT, presenter.getArticleBean());
            bundle.putInt(ThreadShareDialog.BUNDLE_THREAD_TYPE, ThreadShareDialog.TYPE_THREAD_MAJOR);
            getView().showShareDialog(bundle);
        }
    }

    /**
     * 加好友
     */
    @Override
    public void clickAddFriend() {
        if (UserManger.isLogin()) {
            if (!TextUtils.equals(presenter.getArticleBean().getIsfriend(), friend)) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", presenter.getArticleBean().getAuthorid());
                getBaseView().jumpActivity(VerificationActivity.class, bundle);
            }
        } else {
            getBaseView().toLogin();
        }
    }

    /**
     * 去评论页面
     */
    @Override
    public void toCommentActivity() {
        if (presenter.getArticleBean() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ArticlePresenter.BUNDLE_AID, presenter.getArticleBean().getAid());
            getBaseView().jumpActivity(ArticleCommentActivity.class, bundle);

            HashMap<String, String> map = new HashMap<>();
            map.put("aid", presenter.getArticleBean().getAid());
            MobclickAgent.onEvent(FlyerApplication.getContext(), FinalUtils.EventId.notedetail_comment_click, map);
        }
    }

    /**
     * 图片点击
     *
     * @param index
     */
    @Override
    public void clickContentImage(String index) {
        int num = index.indexOf(",");
        int pos = Integer.parseInt(index.substring(0, num)); // 楼层，-1为正文，其他代表评论
        index = index.substring(num + 1);// 标识图片位置

        Bundle bundle = new Bundle();

        ArrayList<String> urlList = new ArrayList<>();
        List<Attachments> attachmentsList = null;

        if (pos == -1)
            attachmentsList = presenter.getArticleBean().getAttachments();
        else
            attachmentsList = presenter.getCommentList().get(pos).getAttachments();

        for (Attachments bean : attachmentsList) {
            urlList.add(bean.getImageurl());
        }

        bundle.putStringArrayList("urlList", urlList);
        bundle.putInt("pos", Integer.parseInt(index));

        getBaseView().jumpActivity(PictureBrowseActivity.class, bundle);
    }


    /**
     * 评论是否需要头部
     *
     * @return
     */
    @Override
    protected boolean isNeedReplyHeader() {
        return true;
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


    /**
     * 发表评论
     *
     * @param msg
     */
    @Override
    public void actionSendComment(String msg) {
        if (TextUtils.equals(UserManger.getUserInfo().getAuthed(), "1")) {
            presenter.requestSendArticleComment(msg);
        } else {
            getView().showAuthodDialog();
        }
    }

    @Override
    public void clickCommentLi(String value) {

    }

    @Override
    public void actionToPictureComment(String commentContent) {

    }

    public ArticleContentBean getArticleBean() {
        return presenter.getArticleBean();
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

}
