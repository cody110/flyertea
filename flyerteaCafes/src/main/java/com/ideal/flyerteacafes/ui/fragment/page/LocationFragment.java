package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.ui.activity.RatingActivity;
import com.ideal.flyerteacafes.ui.dialog.FlightDialog;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/17.
 */

public class LocationFragment extends BaseFragment {


    public static LocationFragment getFragment(List<LocationListBean.LocationBean> data, List<TagBean> tags) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) data);
        bundle.putSerializable("tags", (Serializable) tags);
        LocationFragment fm = new LocationFragment();
        fm.setArguments(bundle);
        return fm;
    }


    List<LocationListBean.LocationBean> datas;
    List<TagBean> tags;
    int selectIndex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fm_location_list_data, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.listview);
        datas = (List<LocationListBean.LocationBean>) getArguments().get("data");
        tags = (List<TagBean>) getArguments().get("tags");

        CommonAdapter adapter = new CommonAdapter<LocationListBean.LocationBean>(mActivity, datas, R.layout.listview_selection_item) {

            @Override
            public void convert(ViewHolder holder, LocationListBean.LocationBean loca) {
                holder.setText(R.id.selection_text, loca.getName());
                holder.setText(R.id.location_jiedao, loca.getAddress());
            }
        };
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectIndex = i;
                if (TextUtils.equals(datas.get(i).getType(), "airport")) {
                    showShareDialog();
                } else {
                    toRatingActivity();
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void toRatingActivity(String flightid, String flight) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas.get(selectIndex));
        bundle.putSerializable("tags", (Serializable) tags);
        bundle.putString("flight", flight);
        bundle.putString("flightid", flightid);
        jumpActivityForResult(RatingActivity.class, bundle, 0);
    }

    private void toRatingActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas.get(selectIndex));
        bundle.putSerializable("tags", (Serializable) tags);
        jumpActivityForResult(RatingActivity.class, bundle, 0);
    }


    public void onEventMainThread(TagEvent event) {
        if (event.getTag() == TagEvent.TAG_FLIGHT) {
            if (TextUtils.equals(datas.get(selectIndex).getType(), "airport")) {
                if (event.getBundle() != null) {
                    checkFlight(event.getBundle().getString("data"));
                } else {
                    mActivity.finish();
                }
            }
        }
    }

    static String TAG_SHRE_DIALOG = "FlightDialog";
    private FlightDialog threadShareDialog;

    public void showShareDialog() {
        removeDialogFragment(TAG_SHRE_DIALOG);
        threadShareDialog = new FlightDialog();
        threadShareDialog.show(getChildFragmentManager(), TAG_SHRE_DIALOG);
    }

    protected void removeDialogFragment(String tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (null != fragment) {
            ft.remove(fragment);
        }
    }


    private void checkFlight(final String flight) {
        mActivity.showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CHECK_FLIGHT);
        params.addQueryStringParameter("flight", flight);
        XutilsHttp.Get(params, new BaseBeanCallback() {

            @Override
            public void flySuccess(BaseBean result) {
                if (result.isSuccessEquals1()) {
                    if (threadShareDialog != null) {
                        threadShareDialog.dismiss();
                    }
                    if (result.getData() != null) {
                        toRatingActivity(flight, flight);
                    }

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


}
