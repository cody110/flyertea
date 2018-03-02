package com.ideal.flyerteacafes.ui.activity.threads;

import android.os.Bundle;
import android.os.Handler;

import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticleCommentPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.dialog.ThreadCommentDialog;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by fly on 2016/11/25.
 */

public class ArticleCommentActivity extends MajorCommentActivity {


    ArticleCommentPresenter myPresenter;

    @Override
    protected ThreadPresenter createPresenter() {
        return myPresenter = new ArticleCommentPresenter();
    }

    @Override
    public void settingView(Object obj) {
        ArticleContentBean articleContentBean = (ArticleContentBean) obj;
        toolbar_title.setText("回复（" + articleContentBean.getReplies() + ")");
    }

    @Override
    public void showCommentDialog(Bundle bundle) {

        if (threadCommentDialog == null || threadCommentDialog.getIndex() != bundle.getInt("index")) {
            removeDialogFragment(TAG_COMMENT_DIALOG);
            threadCommentDialog = new ThreadCommentDialog();
            LogFly.e("ThreadCommentDialog");
            threadCommentDialog.setArguments(bundle);
            threadCommentDialog.setCommentListener(mPresenter);
        }
        threadCommentDialog.show(getSupportFragmentManager(), TAG_COMMENT_DIALOG);
        threadCommentDialog.setHintChooseImg();

//        removeDialogFragment(TAG_COMMENT_DIALOG);
//        final ThreadCommentDialog dialog = new ThreadCommentDialog();
//        dialog.setArguments(bundle);
//        dialog.setCommentListener(mPresenter);
//        dialog.show(getSupportFragmentManager(), TAG_COMMENT_DIALOG);
//        dialog.setHintChooseImg();
    }

    @Override
    protected boolean isShowPageAll() {
        return false;
    }

    @Override
    public void umAgentPagingClick() {
        LogFly.e("umAgentPagingClick:"+getClass().getName());
        HashMap<String, String> map = new HashMap<>();
        if (myPresenter.getArticleBean() != null)
            map.put("aid", myPresenter.getArticleBean().getAid() + "");
        MobclickAgent.onEvent(ArticleCommentActivity.this, FinalUtils.EventId.commentlist_paging_click, map);
    }
}
