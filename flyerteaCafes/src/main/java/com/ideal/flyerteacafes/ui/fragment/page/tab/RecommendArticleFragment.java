package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.model.entity.PersonalMode;
import com.ideal.flyerteacafes.ui.activity.RemindDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;

import java.util.List;

/**
 * 好文推荐
 * Created by fly on 2017/12/14.
 */

public class RecommendArticleFragment extends NewPullRefreshFragment<PersonalMode> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PersonalMode bean = mPresenter.getDatas().get(position);
                if (TextUtils.equals(bean.getType(), "post")) {
                    Intent intent = new Intent(mActivity, ThreadActivity.class);
                    intent.putExtra("tid", bean.getTid());
                    startActivity(intent);
                } else {
                    String msg = bean.getNote();
                    Intent intent = new Intent(mActivity, RemindDetailsActivity.class);
                    intent.putExtra("msg", msg);
                    startActivity(intent);
                }

                /**
                 * 未读消息
                 * 标记为已读
                 */
                if (TextUtils.equals(bean.getIsnew(), Marks.MESSAGE_UNREAD)) {
                    mPresenter.getDatas().get(position).setIsnew(Marks.MESSAGE_READ);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected CommonAdapter<PersonalMode> createAdapter(List<PersonalMode> datas) {
        return new CommonAdapter<PersonalMode>(mActivity, datas, R.layout.item_message_center_other_message) {
            @Override
            public void convert(ViewHolder holder, PersonalMode personalMode) {
                holder.setTextHtml(R.id.item_my_reply_title, personalMode.getSubject());
                String dbdateline = personalMode.getDateline();
                if (!TextUtils.isEmpty(dbdateline)) {
                    holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(dbdateline));
                } else {
                    holder.setText(R.id.item_my_reply_time, "");
                }
                holder.setVisible(R.id.icon_remind, TextUtils.equals(personalMode.getIsnew(), "1"));
            }
        };
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshViewComplete();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshViewComplete();
    }

    @Override
    protected PullRefreshPresenter<PersonalMode> createPresenter() {
        return new PullRefreshPresenter<PersonalMode>() {

            String marktype;

            @Override
            public void init(Activity activity) {
                super.init(activity);
                BaseDataManger.getInstance().requestMarkTypeReads(marktype);
            }

            @Override
            public boolean isFirstLoadNeedHeader() {
                return false;
            }

            @Override
            protected void getIntentDatas(Intent intent) {
                super.getIntentDatas(intent);
                datas = (List<PersonalMode>) intent.getSerializableExtra("data");
                marktype = intent.getStringExtra(Marks.MarkType.MARKTYPE);
            }

            @Override
            public void requestDatas() {
                getView().callbackData(datas);
            }
        };
    }
}
