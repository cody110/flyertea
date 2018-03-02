package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.NotificationAdapter;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2.OnFooterRefreshListener2;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2.OnHeaderRefreshListener2;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子成就
 *
 * @author fly
 */
@ContentView(R.layout.activity_collect)
public class PostAchievementActivity extends BaseActivity implements OnHeaderRefreshListener2, OnFooterRefreshListener2 {

    @ViewInject(R.id.toolbar)
    private ToolBar toolBar;
    @ViewInject(R.id.collect_refreshview_listview)
    private SwipeMenuListView mListview;
    @ViewInject(R.id.collect_refreshview)
    private PullToRefreshView2 mPullToRefreshView;
    @ViewInject(R.id.no_message_root)
    private View no_message_root;

    private int page = FinalUtils.START_PAGE;
    List<NotificationBean> collectList = new ArrayList<>();

    private NotificationAdapter adapter;

    private int pos = -1;// 记录，点击删除项的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        toolBar.setTitle(getString(R.string.message_center_ac_post_achievement));
        bindAdapter();
        requestMyfavthread();
        BaseDataManger.getInstance().requestMarkTypeReads(Marks.MarkType.REWARD);
    }

    @Event(R.id.include_left_title_back_layout)
    private void closePage(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }

    @Event(value = R.id.collect_refreshview_listview, type = AdapterView.OnItemClickListener.class)
    private void toPostDetails(AdapterView<?> parent, View view, int position,
                               long id) {
        NotificationBean bean = collectList.get(position);
        if (TextUtils.equals(bean.getType(), "post")) {
            Intent intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("tid", bean.getFrom_id());
            startActivity(intent);
        } else {
            String msg = bean.getNote();
            Intent intent = new Intent(this, RemindDetailsActivity.class);
            intent.putExtra("msg", msg);
            startActivity(intent);
        }

        if (TextUtils.equals(bean.getIsnew(), Marks.MESSAGE_UNREAD)) {
            requestMarkPositionReads(position);
        }
    }

    /**
     * 初始化listview
     */
    private void bindAdapter() {
        WidgetUtils.setVisible(no_message_root, DataUtils.isEmpty(collectList));
        if (adapter == null) {
            adapter = new NotificationAdapter(this, collectList, R.layout.item_message_center_other_message);
            mListview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 帖子成就
     */
    private void requestMyfavthread() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_POST_ACHIEVEMENT);
        params.addQueryStringParameter("page", String.valueOf(page));

        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                int position = 0;
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
                if (page == FinalUtils.START_PAGE)
                    collectList.clear();
                else
                    position = collectList.size();
                if (!DataUtils.isEmpty(result.getDataList()))
                    collectList.addAll(result.getDataList());
                bindAdapter();
                if (page != FinalUtils.START_PAGE)
                    mListview.setSelectionFromTop(position, (int) (FinalUtils.FROM_TOP * SharedPreferencesString.getInstances().getScale()));
            }
        });
    }


    /**
     * 标记某条消息为已读
     */
    public void requestMarkPositionReads(int index) {
        collectList.get(index).setIsnew(Marks.MESSAGE_READ);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onFooterRefresh(PullToRefreshView2 view) {
        // TODO Auto-generated method stub
        page++;
        requestMyfavthread();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView2 view) {
        page = FinalUtils.START_PAGE;
        requestMyfavthread();
    }

}
