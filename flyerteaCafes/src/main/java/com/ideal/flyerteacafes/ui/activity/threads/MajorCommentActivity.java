package com.ideal.flyerteacafes.ui.activity.threads;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IMajorComment;
import com.ideal.flyerteacafes.ui.activity.presenter.MajorCommentPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.popupwindow.ChoosePagePopupwindow;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * Created by fly on 2016/11/22.
 * 专业帖帖子评论
 */

public class MajorCommentActivity extends ThreadActivity implements IMajorComment {

    TextView post_details_send_flower;
    TextView normal_bottom_et;

    @ViewInject(R.id.show_now_page_tv)
    TextView show_now_page_tv;


    @Override
    protected ThreadPresenter createPresenter() {
        return new MajorCommentPresenter();
    }

    @Override
    public void initViews() {
        super.initViews();
        toolbar_title.setText("回复");
        toolbar_right_img.setImageResource(R.drawable.thread_comment_up);
        toolbar.setTag(R.drawable.thread_comment_up);
        View view = setBottomView(R.layout.include_thread_normal_bottom);
        post_details_send_flower = (TextView) view.findViewById(R.id.post_details_send_flower);
        normal_bottom_et = (TextView) view.findViewById(R.id.normal_bottom_et);
        post_details_send_flower.setVisibility(View.GONE);
        normal_bottom_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.actionShowCommentDialog();
            }
        });
        toolbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataTools.getInteger(toolbar.getTag()) == R.drawable.thread_comment_up) {
                    toolbar_right_img.setImageResource(R.drawable.thread_comment_down);
                    toolbar.setTag(R.drawable.thread_comment_down);
                    ToastUtils.showToast("回复时间降序");
                } else {
                    toolbar_right_img.setImageResource(R.drawable.thread_comment_up);
                    toolbar.setTag(R.drawable.thread_comment_up);
                    ToastUtils.showToast("回复时间升序");
                }
                mPresenter.actionCommentSort();
            }
        });

        show_now_page_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogFly.e("umAgentPagingClick:"+getClass().getName());
                HashMap<String, String> map = new HashMap<>();
                if (mPresenter.getThreadBean() != null)
                    map.put("tid", mPresenter.getThreadBean().getTid() + "");
                MobclickAgent.onEvent(MajorCommentActivity.this, FinalUtils.EventId.commentlist_paging_click, map);

                if (mPresenter.totalpage > 0) {
                    ChoosePagePopupwindow popupwindow = new ChoosePagePopupwindow(MajorCommentActivity.this);
                    popupwindow.showAtLocation(show_now_page_tv, Gravity.BOTTOM, 0, 0);
                    popupwindow.initPage(mPresenter.page, mPresenter.totalpage);
                    popupwindow.setiPageChoose(new ChoosePagePopupwindow.IPageChoose() {
                        @Override
                        public void selectPagePos(int pos) {
                            mPresenter.formStartPageRequestComment(pos + 1);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void settingView(Object obj) {
        ThreadDetailsBean threadDetailsBean = (ThreadDetailsBean) obj;
        toolbar_title.setText("回复(" + threadDetailsBean.getReplies() + ")");
    }

    @Override
    public void showCommentPage(int page, int pageAll) {
        show_now_page_tv.setText(page + "/" + pageAll);
        if (pageAll > 1)
            show_now_page_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean isShowPageAll() {
        return false;
    }

}
