package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.NoviceGuidanceAdapter;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.ui.activity.myinfo.TaskDetailsActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/12/13.
 */

public class NoviceGuidanceFragment extends BaseFragment {

    public static final String SILVER = "silver", BLUE = "blue";

    private View view;

    @ViewInject(R.id.fragment_listview)
    private ListView listView;
    @ViewInject(R.id.get_btn)
    private TextView get_btn;
    @ViewInject(R.id.get_btn_desc)
    private TextView get_btn_desc;

    NoviceGuidanceAdapter adapter;
    private String type;
    private List<MyTaskBean> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview_novice_guidance, container, false);
        x.view().inject(this, view);
        view.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(TaskDetailsActivity.BUNDLE_ID, adapter.getItem(i).getTaskid());
                jumpActivityForResult(TaskDetailsActivity.class, bundle, 0);
            }
        });
        return view;
    }

    @Event(R.id.get_btn)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.get_btn:
                if (TextUtils.equals(type, SILVER)) {
                    //领取银卡会员
                    if (UserManger.getUserInfo().getGroupid() == 11 && isOkList(datas)) {
                        requestGetCarl(SILVER);
                    }
                } else if (TextUtils.equals(type, BLUE)) {
                    //领取蓝卡会员
                    if (UserManger.getUserInfo().getGroupid() == 10 && isOkList(datas)) {
                        requestGetCarl(BLUE);
                    }
                }
                break;
        }

    }

    /**
     * 绑定数据
     *
     * @param datas
     * @param type
     */
    public void bindData(List<MyTaskBean> datas, String type) {
        this.type = type;

        this.datas.clear();
        this.datas.addAll(datas);
        if (adapter == null) {
            adapter = new NoviceGuidanceAdapter(mActivity, this.datas, R.layout.item_novice_guidance);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        initGetCarlBtn();
        view.setVisibility(View.VISIBLE);
    }


    /**
     * 设置btn
     */
    private void initGetCarlBtn() {
        if (TextUtils.equals(type, BLUE)) {
            if (UserManger.getUserInfo().getGroupid() >= 11) {
                get_btn.setText("已领取蓝卡会员");
            } else {
                get_btn.setText("领取蓝卡会员");
            }
            get_btn_desc.setText("完成上述任务，赢取蓝卡会员");
        } else {
            if (UserManger.getUserInfo().getGroupid() >= 12) {
                get_btn.setText("已领取银卡会员");
            } else {
                get_btn.setText("领取银卡会员");
            }
            get_btn_desc.setText("完成上述任务，赢取银卡会员");
        }

        setBtnTextColor(datas);
    }


    /**
     * 获取列表已完成数量
     *
     * @param datas
     * @return
     */
    private boolean isOkList(List<MyTaskBean> datas) {
        int num = 0;
        for (MyTaskBean bean : datas) {
            if (TextUtils.equals(bean.getStatus(), "0") && !TextUtils.equals(bean.getCsc(), "100")) {
            } else { //已完成
                num++;
            }
        }
        return num == 4;
    }

    /**
     * 设置底部btn颜色
     *
     * @param datas
     */
    private void setBtnTextColor(List<MyTaskBean> datas) {
        if (isOkList(datas)) {
            get_btn.setBackgroundColor(getResources().getColor(R.color.tag_select));
        } else {
            get_btn.setBackgroundColor(getResources().getColor(R.color.tag_unselect));
        }
    }

    /**
     * 获取银卡蓝卡
     *
     * @param value
     */
    private void requestGetCarl(final String value) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_GET_CARL);
        params.addQueryStringParameter("membership", value);
        XutilsHttp.Get(params, new BaseBeanCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (result.isSuccessEquals1()) {
                    if (TextUtils.equals(value, BLUE)) {
                        UserManger.getUserInfo().setGroupid(11);
                    } else if (TextUtils.equals(value, SILVER)) {
                        UserManger.getUserInfo().setGroupid(12);
                    }
                    initGetCarlBtn();
                }
                ToastUtils.showToast(result.getMessage());
            }
        });
    }


}
