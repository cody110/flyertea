package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ForumOneLevelAdapter;
import com.ideal.flyerteacafes.adapters.ForumTwoLevelAdapter;
import com.ideal.flyerteacafes.caff.ForumDataManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_choose_forum)
public class ChooseForumActivity extends BaseActivity {

    @ViewInject(R.id.fragment_forum_one_level_listview)
    ListView oneLevelListview;
    @ViewInject(R.id.fragment_forum_two_level_listview)
    ListView twoLevelListview;

    private ForumOneLevelAdapter adapter;
    private ForumTwoLevelAdapter twoAdapter;

    private List<CommunitySubBean> communitySubBeanList = new ArrayList<>();

    List<CommunityBean> communityBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        List<CommunityBean> list = ForumDataManger.getInstance().getData();
        if (list != null) {
            this.communityBeanList.addAll(list);
            if (UserManger.isLogin()) {
                List<CommunitySubBean> subList = ForumDataManger.getInstance().getMyFavToCommunitySubList();
                if (subList != null && !subList.isEmpty()) {
                    CommunityBean bean = new CommunityBean();
                    bean.setName("我的关注");
                    bean.setSubforums(subList);
                    this.communityBeanList.add(0, bean);
                }
            }
            adapter = new ForumOneLevelAdapter(this, this.communityBeanList, R.layout.listview_forum_one_level_layout);
            oneLevelListview.setAdapter(adapter);
            adapter.setSelectIndex(0);
            setTwoAdapter(0);

        }
    }

    @Event(value = R.id.fragment_forum_one_level_listview, type = AdapterView.OnItemClickListener.class)
    private void oneLevelClickItem(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectIndex(position);
        setTwoAdapter(position);
    }

    @Event(value = R.id.fragment_forum_two_level_listview, type = AdapterView.OnItemClickListener.class)
    private void toSubForumDetails(AdapterView<?> parent, View view, int position, long id) {

        CommunitySubBean subBean = communitySubBeanList.get(position);
        String fid1 = null;
        String fname1 = null;

        for (CommunityBean bean1 : communityBeanList) {
            for (CommunitySubBean bean2 : bean1.getSubforums()) {
                if (TextUtils.equals(subBean.getFid(), bean2.getFid())) {
                    fid1 = bean1.getFid();
                    fname1 = bean1.getName();
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, fid1);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_1, fname1);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, communitySubBeanList.get(position).getFid());
        jumpActivity(CommunitySubActivity.class, bundle);
        finish();
    }

    /**
     * 设置二级adapter
     */
    public void setTwoAdapter(int position) {

        communitySubBeanList.clear();
        communitySubBeanList.addAll(communityBeanList.get(position).getSubforums());

        if (twoAdapter == null) {
            twoAdapter = new ForumTwoLevelAdapter(this, communitySubBeanList, R.layout.listview_forum_two_level_layout);
            twoLevelListview.setAdapter(twoAdapter);
        } else {
            twoAdapter.notifyDataSetChanged();
        }

    }


}
