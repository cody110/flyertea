package com.ideal.flyerteacafes.ui.fragment.page.tab.community;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ForumOneLevelAdapter;
import com.ideal.flyerteacafes.adapters.ForumTwoLevelAdapter;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.ManagementPlateActivity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunityPlate;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.CommunityPlatePresenter;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2016/12/21.
 * 社区版块
 */

public class CommunityPlateFragment extends MVPBaseFragment<ICommunityPlate, CommunityPlatePresenter> implements ICommunityPlate {


    @ViewInject(R.id.fragment_forum_one_level_listview)
    ListView oneLevelListview;
    @ViewInject(R.id.fragment_forum_two_level_listview)
    ListView twoLevelListview;
    @ViewInject(R.id.community_normal_add_follow_viewstub)
    ViewStub community_normal_add_follow_viewstub;
    @ViewInject(R.id.fragment_forum_management_plate_layout)
    View fragment_forum_management_plate_layout;

    View community_normal_add_follow_layout;

    private ForumOneLevelAdapter adapter;
    private ForumTwoLevelAdapter twoAdapter;

    private HomeActivity homeActivity;
    /**
     * 是否调用onSaveInstanceState函数
     */
    private boolean isSaveInstanceState = false;
    /**
     * 是否需要执行未执行的actionToChooseFav
     */
    private boolean isSetChooseForumFragment = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeActivity = (HomeActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_forum, container, false);
        x.view().inject(this, view);
        mPresenter.init(homeActivity);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isSaveInstanceState = false;
        if (isSetChooseForumFragment) {
            actionToChooseFav();

        }
    }

    @Event({R.id.fragment_forum_management_plate_layout, R.id.fm_community_home_write_thread_btn, R.id.toolbar_right})
    private void click(View v) {
        if (v.getId() == R.id.fragment_forum_management_plate_layout) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ManagementPlateActivity.BUNDLE_COMMUNITY, (Serializable) mPresenter.communityBeanList);
            bundle.putSerializable(ManagementPlateActivity.BUNDLE_MYFAV, (Serializable) mPresenter.favList);
            jumpActivity(ManagementPlateActivity.class, bundle);
        } else if (v.getId() == R.id.fm_community_home_write_thread_btn) {
            ToastUtils.showToast("?");
            if (UserManger.isLogin()) {
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.forum_post);//友盟统计
                jumpActivity(WriteMajorThreadContentActivity.class, null);
            } else {
                DialogUtils.showDialog(mActivity);
            }
        } else if (v.getId() == R.id.toolbar_right) {
            jumpActivity(ThreadSearchActivity2.class);
        }
    }


    @Event(value = R.id.fragment_forum_one_level_listview, type = AdapterView.OnItemClickListener.class)
    private void oneLevelClickItem(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectIndex(position);
        mPresenter.actionClickOneLevel(position);
    }

    @Event(value = R.id.fragment_forum_two_level_listview, type = AdapterView.OnItemClickListener.class)
    private void toSubForumDetails(AdapterView<?> parent, View view, int position, long id) {
        position = position - twoLevelListview.getHeaderViewsCount();
        mPresenter.actionClickTwoLevel(position);
    }


    @Override
    protected CommunityPlatePresenter createPresenter() {
        return new CommunityPlatePresenter();
    }

    @Override
    public void actionSetOneLevelAdapter(List<CommunityBean> communityBeanList) {
        if (adapter == null) {
            adapter = new ForumOneLevelAdapter(mActivity, communityBeanList, R.layout.listview_forum_one_level_layout);
            oneLevelListview.setAdapter(adapter);
            adapter.setSelectIndex(0);
        } else {
            adapter.notifyDataSetChanged();
        }
        mPresenter.actionClickOneLevel(adapter.getSelectIndex());
    }


    View advView;
    ImageView advImg;

    @Override
    public void actionSetTwoLevelAdapter(List<CommunitySubBean> communitySubBeanList, final Map<String, String> adv) {
        WidgetUtils.setVisible(community_normal_add_follow_layout, false);
        if (twoAdapter == null) {
            twoAdapter = new ForumTwoLevelAdapter(mActivity, communitySubBeanList, R.layout.listview_forum_two_level_layout);
            twoLevelListview.setAdapter(twoAdapter);
        } else {
            twoAdapter.notifyDataSetChanged();
        }


        if (advView == null) {
            advView = LayoutInflater.from(mActivity).inflate(R.layout.plate_adv_img, null, false);
            advImg = (ImageView) advView.findViewById(R.id.adv_img);
        }


        if (adv == null) {
            twoLevelListview.removeHeaderView(advView);
            return;
        }

        String imgUrl = adv.get("img_path");
        final String htmlUrl = adv.get("url");
        final String apptemplateid = adv.get("apptemplateid");
        final String apptemplatetype = adv.get("apptemplatetype");
        if (TextUtils.isEmpty(imgUrl)) {
            twoLevelListview.removeHeaderView(advView);
        } else {
            advImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("aid", adv.get("id"));
                    map.put("name", adv.get("name"));
                    MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_sidebarAd_click, map);
                    if (TextUtils.equals(apptemplatetype, "tid")) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(apptemplateid));
                        jumpActivity(ThreadActivity.class, bundle);
                    } else if (TextUtils.equals(apptemplatetype, "fid")) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, apptemplateid);
                        jumpActivity(CommunitySubActivity.class, bundle);
                    } else if (TextUtils.equals(apptemplatetype, "aid")) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(apptemplateid));
                        jumpActivity(ArticleContentActivity.class, bundle);
                    } else {
                        if (!TextUtils.isEmpty(htmlUrl)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("url", htmlUrl);
                            jumpActivity(TbsWebActivity.class, bundle);
                        }
                    }


                }
            });
            x.image().bind(advImg, imgUrl, XutilsHelp.image_FIT_XY);
            if (twoLevelListview.getHeaderViewsCount() == 0) {
                twoLevelListview.addHeaderView(advView);
            }
        }


    }

    @Override
    public void actionShowNormalAddFollow() {
        if (community_normal_add_follow_layout == null) {
            community_normal_add_follow_layout = community_normal_add_follow_viewstub.inflate();
            View community_normal_add_follow_btn = community_normal_add_follow_layout.findViewById(R.id.community_normal_add_follow_btn);
            community_normal_add_follow_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UserManger.isLogin()) {
                        homeActivity.setChooseForumFragment(false);
                    } else {
                        DialogUtils.showDialog(mActivity);
                    }
                }
            });
        }
        community_normal_add_follow_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void actionIsShowManagementPlate(boolean bol) {
        WidgetUtils.setVisible(fragment_forum_management_plate_layout, bol);
    }

    @Override
    public void actionToChooseFav() {
        if (!isSaveInstanceState) {
            homeActivity.setChooseForumFragment(true);
            isSetChooseForumFragment = false;
        } else {
            isSetChooseForumFragment = true;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isSaveInstanceState = true;
    }
}
