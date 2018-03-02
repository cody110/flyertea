package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.ui.activity.ManagementPlateActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
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
 * 关注列表
 */

public class ManagementPlateFollowFragment extends BaseFragment {


    @ViewInject(R.id.fm_management_plate_listview)
    ListView listView;
    @ViewInject(R.id.fm_management_plate_normal_remind)
    TextView normalTv;


    public List<MyFavoriteBean> favList = new ArrayList<>();
    private CommonAdapter<MyFavoriteBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_management_plate_follow, container, false);
        x.view().inject(this, view);
        EventBus.getDefault().register(this);

        Serializable serializable = getActivity().getIntent().getSerializableExtra(ManagementPlateActivity.BUNDLE_MYFAV);
        if (serializable != null)
            favList.addAll((List<MyFavoriteBean>) serializable);

        adapter = new CommonAdapter<MyFavoriteBean>(getActivity(), favList, R.layout.item_plate_all_child) {
            @Override
            public void convert(final ViewHolder holder, MyFavoriteBean myFavoriteBean) {
                holder.setText(R.id.item_plate_all_child_name, myFavoriteBean.getTitle());
                holder.setImageResource(R.id.item_plate_all_child_select_img, R.drawable.plate_subtract);
                holder.setOnClickListener(R.id.item_plate_all_child_select_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestCancleFollow(getItem(holder.getPosition()));
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        setNormalTv();


        return view;
    }


    /**
     * 取消关注
     */
    public void requestCancleFollow(final MyFavoriteBean bean) {
        mActivity.showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMACTION);
        params.addQueryStringParameter("action", "unfollow");
        params.addQueryStringParameter("favid", bean.getFavid());
        XutilsHttp.Get(params, new BaseBeanCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (TextUtils.equals(result.getCode(), "do_success")) {
                    TagEvent event = new TagEvent(TagEvent.TAG_FAV_CANCLE);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", bean);
                    event.setBundle(bundle);
                    EventBus.getDefault().post(event);
                    favList.remove(bean);
                    adapter.notifyDataSetChanged();
                    ToastUtils.showToast("取消收藏成功");
                    setNormalTv();
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

    //TODO 关注成功后
    public void onEventMainThread(TagEvent event) {
        if (event.getTag() == TagEvent.TAG_FAV_FOLLOW) {
            Bundle bundle = event.getBundle();
            MyFavoriteBean favBean = (MyFavoriteBean) bundle.getSerializable("data");
            favList.add(favBean);
            adapter.notifyDataSetChanged();
            setNormalTv();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setNormalTv() {
        WidgetUtils.setVisible(normalTv, favList.size() == 0);
    }

}
