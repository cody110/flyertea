package com.ideal.flyerteacafes.ui.activity.threads;

import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticleContentPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by fly on 2017/9/7.
 */

public class DiscountArticleContentActivity extends ThreadActivity {

    ArticleContentPresenter myPresenter;

    @Override
    protected ThreadPresenter createPresenter() {
        return myPresenter = new ArticleContentPresenter();
    }

    @Override
    public void settingView(Object obj) {

        View view = setBottomView(R.layout.include_discount_article_layout);

        major_thread_shoucang_num = V.f(view, R.id.major_thread_shoucang_num);
        major_thread_comment_num = V.f(view, R.id.major_thread_comment_num);
        major_thread_sendflower_num = V.f(view, R.id.major_thread_sendflower_num);
        View go_to = V.f(view, R.id.normal_bottom_et);


        ArticleContentBean articleContentBean = (ArticleContentBean) obj;
        if (DataTools.getInteger(articleContentBean.getFavid()) > 0) {//已收藏
            TvDrawbleUtils.chageDrawble(major_thread_shoucang_num, R.drawable.thread_shoucang_has);
            major_thread_shoucang_num.setTextColor(getResources().getColor(R.color.app_black));
        } else {
            TvDrawbleUtils.chageDrawble(major_thread_shoucang_num, R.drawable.thread_shoucang);
            major_thread_shoucang_num.setTextColor(getResources().getColor(R.color.app_body_grey));
        }

        if (TextUtils.equals(articleContentBean.getIsLike(), "1")) {
            TvDrawbleUtils.chageDrawble(major_thread_sendflower_num, R.drawable.thread_flower_has);
            major_thread_sendflower_num.setTextColor(getResources().getColor(R.color.app_black));
        } else {
            TvDrawbleUtils.chageDrawble(major_thread_sendflower_num, R.drawable.thread_flower);
            major_thread_sendflower_num.setTextColor(getResources().getColor(R.color.app_body_grey));
        }

        WidgetUtils.setText(major_thread_shoucang_num, articleContentBean.getFavtimes());
        WidgetUtils.setText(major_thread_sendflower_num, String.valueOf(articleContentBean.getFlowers()));
        WidgetUtils.setText(major_thread_comment_num, String.valueOf(articleContentBean.getReplies()));

        go_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast("立即参加");
            }
        });

        major_thread_shoucang_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserManger.isLogin()) {
                    mPresenter.actionCollect();
                } else {
                    DialogUtils.showDialog(DiscountArticleContentActivity.this);
                }
            }
        });
        major_thread_sendflower_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserManger.isLogin()) {
                    mPresenter.sendFlowerByContent();
                } else {
                    DialogUtils.showDialog(DiscountArticleContentActivity.this);
                }
            }
        });
        major_thread_comment_num.setOnClickListener(toCommentListener());

        WidgetUtils.setText(toolbar_title, TextUtils.isEmpty(myPresenter.title) ? articleContentBean.getForumname() : myPresenter.title);
        WidgetUtils.setVisible(toolbar_title_icon, false);

    }

    @Override
    protected View.OnClickListener toCommentListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPresenter.toCommentActivity();
            }
        };
    }

    @Override
    public void umAgentPagingClick() {
        HashMap<String, String> map = new HashMap<>();
        if (myPresenter.getArticleBean() != null)
            map.put("aid", myPresenter.getArticleBean().getAid() + "");
        MobclickAgent.onEvent(DiscountArticleContentActivity.this, FinalUtils.EventId.notedetail_paging_click, map);
    }
}
