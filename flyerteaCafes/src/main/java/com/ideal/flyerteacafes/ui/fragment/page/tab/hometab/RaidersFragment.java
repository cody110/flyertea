package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.RaidersRootBean;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.layout.RaidersGroupLayout;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/8/30.
 */

public class RaidersFragment extends BaseFragment {

    @ViewInject(R.id.hot_group_layout)
    RaidersGroupLayout hot_group_layout;
    @ViewInject(R.id.hotel_raiders_group_layout)
    RaidersGroupLayout hotel_raiders_group_layout;
    @ViewInject(R.id.flyer_raiders_group_layout)
    RaidersGroupLayout flyer_raiders_group_layout;
    @ViewInject(R.id.carl_raiders_group_layout)
    RaidersGroupLayout carl_raiders_group_layout;


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.raiders_fm, container, false);
        x.view().inject(this, view);

        requestDatas();

        return view;
    }


    private void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_RAIDERS);
        params.setLoadCache(true);
        XutilsHttp.Get(params, new DataCallback<RaidersRootBean>() {
            @Override
            public void flySuccess(DataBean<RaidersRootBean> result) {
                if (result.getDataBean() == null) return;
                hot_group_layout.bindGroup(result.getDataBean().getHot(), true);
                hotel_raiders_group_layout.bindGroup(result.getDataBean().getHotel());
                flyer_raiders_group_layout.bindGroup(result.getDataBean().getAirline());
                carl_raiders_group_layout.bindGroup(result.getDataBean().getCredit());
            }
        });
    }

}
