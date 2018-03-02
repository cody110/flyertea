package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ManagementPlateAllAdapter;
import com.ideal.flyerteacafes.caff.StartUpManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.FollowInfo;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.ManagementPlateActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/12/22.
 * 全部版块，进行关注操作
 */

public class ManagementPlateAllFragment extends BaseFragment implements ManagementPlateAllAdapter.IPlateCancleClick {

    @ViewInject(R.id.fm_management_plate_all_expandable)
    ExpandableListView expandableListView;

    List<CommunityBean> datas;
    List<MyFavoriteBean> favList = new ArrayList<>();

    ManagementPlateAllAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_management_plate_all, container, false);
        x.view().inject(this, view);
        EventBus.getDefault().register(this);

        datas = (List<CommunityBean>) mActivity.getIntent().getSerializableExtra(ManagementPlateActivity.BUNDLE_COMMUNITY);
        Serializable serializable = getActivity().getIntent().getSerializableExtra(ManagementPlateActivity.BUNDLE_MYFAV);
        if (serializable != null)
            favList.addAll((List<MyFavoriteBean>) serializable);

        if (DataUtils.isEmpty(datas)) {
            requestCommunity();
        } else {
            datas.remove(0);
            initExPand();
        }
        return view;
    }

    private void initExPand() {
        if (DataUtils.isEmpty(datas)) return;
        setSelectData();
        adapter = new ManagementPlateAllAdapter(mActivity, datas);
        adapter.setiPlateCancleClick(this);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);

        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }


    private void setSelectData() {
        for (CommunityBean bean : datas) {
            for (CommunitySubBean subBean : bean.getSubforums()) {
                boolean bol = false;
                for (MyFavoriteBean favBean : favList) {
                    if (TextUtils.equals(subBean.getFid(), favBean.getId())) {
                        bol = true;
                    }
                }
                subBean.setIsSelect(bol);
            }
        }
    }


    @Override
    public void plateCancleClick(int i, int i1) {
        CommunitySubBean subBean = datas.get(i).getSubforums().get(i1);
        if (!subBean.isSelect()) {
            requestFollow(subBean);
        }
    }


    /**
     * 关注
     */
    public void requestFollow(final CommunitySubBean subBean) {
        mActivity.showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMACTION);
        params.addQueryStringParameter("action", "follow");
        params.addQueryStringParameter("id", subBean.getFid());
        XutilsHttp.Get(params, new DataCallback<FollowInfo>() {
            @Override
            public void flySuccess(DataBean<FollowInfo> result) {
                if (TextUtils.equals(result.getCode(), "favorite_do_success")) {
                    ToastUtils.showToast("收藏成功");

                    TagEvent event = new TagEvent(TagEvent.TAG_FAV_FOLLOW);
                    Bundle bundle = new Bundle();
                    MyFavoriteBean favBean = new MyFavoriteBean();
                    favBean.setFavid(result.getDataBean().getFavid());
                    favBean.setId(result.getDataBean().getId());
                    favBean.setTitle(subBean.getName());
                    bundle.putSerializable("data", favBean);
                    event.setBundle(bundle);
                    EventBus.getDefault().post(event);

                    favList.add(favBean);
                    setSelectData();
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                mActivity.dialogDismiss();
            }
        });
    }


    //TODO 取消关注成功后
    public void onEventMainThread(TagEvent event) {
        if (event.getTag() == TagEvent.TAG_FAV_CANCLE) {
            Bundle bundle = event.getBundle();
            MyFavoriteBean favBean = (MyFavoriteBean) bundle.getSerializable("data");
            for (MyFavoriteBean bean : favList) {
                if (TextUtils.equals(bean.getFavid(), favBean.getFavid())) {
                    favList.remove(bean);
                    break;
                }
            }
            setSelectData();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * @description: 获取社区版块数据
     * @author: Cindy
     * @date: 2016/11/15 17:20
     * @version V6.6.0
     */
    private void requestCommunity() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_INDEX), new ListDataCallback(ListDataCallback.LIST_KEY_DATA, CommunityBean.class) {
            @Override
            public void flyStart() {
                super.flyStart();
                mActivity.showDialog();
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                if (isAdded())
                    mActivity.dialogDismiss();
            }

            @Override
            public void flySuccess(ListDataBean response) {
                if (response.getDataList() != null && response.getDataList().size() > 0) {
                    datas = response.getDataList();
                    initExPand();
                }

            }
        });
    }
}
