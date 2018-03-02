package com.ideal.flyerteacafes.ui.activity.threads;

import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticleContentPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;

import java.util.HashMap;

/**
 * Created by fly on 2016/11/25.
 * 文章正文
 */
@ContentView(R.layout.activity_thread)
public class ArticleContentActivity extends ThreadActivity {

    ArticleContentPresenter myPresenter;

    @Override
    protected ThreadPresenter createPresenter() {
        return myPresenter = new ArticleContentPresenter();
    }

    @Override
    public void settingView(Object obj) {
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
        MobclickAgent.onEvent(ArticleContentActivity.this, FinalUtils.EventId.notedetail_paging_click, map);
    }
}
