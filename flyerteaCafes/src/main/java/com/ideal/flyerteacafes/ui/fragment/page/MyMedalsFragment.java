package com.ideal.flyerteacafes.ui.fragment.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.model.entity.MyMedalsBean;
import com.ideal.flyerteacafes.model.entity.MyMedalsSubBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.myinfo.AddRegularCardActivity;
import com.ideal.flyerteacafes.ui.controls.NoScrollGridView;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.popupwindow.MedalsPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.ShareMedalsPopupWindow;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/10/20.
 */

public class MyMedalsFragment extends BaseFragment {


    ListView listView;
    private int REQUEST_ADD_REGULARCARD = 1;

    public static MyMedalsFragment getFragment(List<MyMedalsBean.MyMedalsTypeBean> datas) {
        MyMedalsFragment myMedalsFragment = new MyMedalsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) datas);
        myMedalsFragment.setArguments(bundle);
        return myMedalsFragment;
    }


    List<MyMedalsBean.MyMedalsTypeBean> medalsBeanList;
    CommonAdapter<MyMedalsBean.MyMedalsTypeBean> adapter;
    private String showmedalid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = new ListView(getActivity());
        listView.setDivider(getResources().getDrawable(R.color.transparent));
        listView.setDividerHeight(DensityUtil.dip2px(10));

        medalsBeanList = (List<MyMedalsBean.MyMedalsTypeBean>) getArguments().getSerializable("data");
        adapter = new CommonAdapter<MyMedalsBean.MyMedalsTypeBean>(getActivity(), medalsBeanList, R.layout.item_medals_layout) {
            @Override
            public void convert(final ViewHolder holder, final MyMedalsBean.MyMedalsTypeBean medalsBean) {
                holder.setText(R.id.name_tv, medalsBean.getGroupname());
                holder.setText(R.id.num_tv, "（已获得" + medalsBean.getEraned() + ")");

                final CommonAdapter<MyMedalsSubBean> subAdapter = new CommonAdapter<MyMedalsSubBean>(mContext, medalsBean.getMedals(), R.layout.item_medals_sub_layout) {
                    @Override
                    public void convert(ViewHolder holder, MyMedalsSubBean medalsSubBean) {
                        ImageView igv = holder.getView(R.id.media_igv);
                        x.image().bind(igv, medalsSubBean.getImage(), XutilsHelp.image_FIT_XY);
                        holder.setText(R.id.name_tv, medalsSubBean.getName());


                        holder.setText(R.id.time_tv, medalsSubBean.getActiondesc());
                        holder.setTextColorRes(R.id.time_tv, R.color.app_body_grey);
//                        holder.setBackgroundRes(R.id.time_tv, 0);
                        if (TextUtils.equals(medalsSubBean.getAction(), "0")) {//已拥有
                            holder.setText(R.id.time_tv, DataUtils.getTimeFormat(medalsSubBean.getDateline(), "yyyy.MM.dd"));
                        } else if (TextUtils.equals(medalsSubBean.getAction(), "1")) {//购买
                        } else if (TextUtils.equals(medalsSubBean.getAction(), "2")) {//申请
                            holder.setTextColorRes(R.id.time_tv, R.color.tag_select);
                            holder.setBackgroundRes(R.id.time_tv, R.drawable.tag_select);
                        } else if (TextUtils.equals(medalsSubBean.getAction(), "3")) {//领取
                            holder.setTextColorRes(R.id.time_tv, R.color.tag_select);
                            holder.setBackgroundRes(R.id.time_tv, R.drawable.tag_select);
                        } else if (TextUtils.equals(medalsSubBean.getAction(), "4")) {//已申请
                        }
                    }
                };

                final NoScrollGridView gridview = holder.getView(R.id.gridview);
                gridview.setAdapter(subAdapter);
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        showMedalsPopupWindow(holder.getPosition(), i, listView);
                    }
                });
            }
        };
        listView.setAdapter(adapter);


        return listView;
    }


    //TODO 显示勋章领取页
    public void defShowMedalsPopupWindow(final String showmedalid) {
        if (!TextUtils.isEmpty(showmedalid)) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < medalsBeanList.size(); i++) {
                        for (int j = 0; j < medalsBeanList.get(i).getMedals().size(); j++) {
                            if (TextUtils.equals(medalsBeanList.get(i).getMedals().get(j).getMedalid(), showmedalid)) {
                                showMedalsPopupWindow(i, j, listView);
                                LogFly.e("i:" + i + "j:" + j);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }


    /**
     * 勋章领取
     *
     * @param oneIndex 1级下标
     * @param twoIndex 2级下标
     * @param listView
     */
    private void showMedalsPopupWindow(final int oneIndex, final int twoIndex, final ListView listView) {
        final MedalsPopupWindow popupWindow = new MedalsPopupWindow(getActivity());
        popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);
        popupWindow.bindData(medalsBeanList.get(oneIndex).getMedals().get(twoIndex));
        popupWindow.setiActionListener(new MedalsPopupWindow.IActionListener() {
            @Override
            public void shareMedals(MyMedalsSubBean medalsSubBean) {
                ShareMedalsPopupWindow popupWindow1 = new ShareMedalsPopupWindow(getActivity(), medalsSubBean);
                popupWindow1.showAtLocation(listView, Gravity.CENTER, 0, 0);
            }

            @Override
            public void shengqingMedals(MyMedalsSubBean medalsSubBean) {
                if (TextUtils.equals(medalsSubBean.getType_name(), "airline") || TextUtils.equals(medalsSubBean.getType_name(), "hotel")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("groupname", medalsSubBean.getGroupname());
                    bundle.putString("groupid", medalsSubBean.getGroupid());
                    bundle.putString("mid", medalsSubBean.getMid());
                    bundle.putString("mname", medalsSubBean.getName());
                    bundle.putString("medalid", medalsSubBean.getMedalid());
                    bundle.putString("from", "MyMedalsFragment");
                    jumpActivityForResult(AddRegularCardActivity.class, bundle, REQUEST_ADD_REGULARCARD);
                } else {
                    if (TextUtils.equals(medalsSubBean.getAction(), "2")) {
                        shengqing(oneIndex, twoIndex, medalsSubBean);
                    } else if (TextUtils.equals(medalsSubBean.getAction(), "3")) {
                        lingqu(oneIndex, twoIndex, medalsSubBean);
                    }
                }
            }
        });
    }

    /**
     * 申请接口
     *
     * @param i
     * @param j
     * @param myMedalsSubBean
     */
    private void shengqing(final int i, final int j, final MyMedalsSubBean myMedalsSubBean) {
        mActivity.proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MEDALS_SHENGQING);
        params.addQueryStringParameter("action", "apply");
        params.addQueryStringParameter("medalid", myMedalsSubBean.getMedalid());
        XutilsHttp.Get(params, new DataCallback<MyMedalsSubBean>() {
            @Override
            public void flySuccess(DataBean<MyMedalsSubBean> result) {
                if (result.isSuccessEquals1()) {
                    ToastUtils.showToast("申请已受理");
//                    medalsBeanList.get(i).getMedals().remove(j);
//                    medalsBeanList.get(i).getMedals().add(j, myMedalsSubBean);
                    medalsBeanList.get(i).getMedals().get(j).setAction("4");
                    medalsBeanList.get(i).getMedals().get(j).setActiondesc("已申请");
                    adapter.notifyDataSetChanged();
                } else {
                    showRemindDialog(result.getMessage());
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                mActivity.dialogDismiss();
            }
        });
    }

    /**
     * 领取接口
     *
     * @param i
     * @param j
     * @param myMedalsSubBean
     */
    private void lingqu(final int i, final int j, final MyMedalsSubBean myMedalsSubBean) {
        mActivity.proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MEDALS_SHENGQING);
        params.addQueryStringParameter("action", "confirm");
        params.addQueryStringParameter("medalid", myMedalsSubBean.getMedalid());
        XutilsHttp.Get(params, new DataCallback<MyMedalsSubBean>() {
            @Override
            public void flySuccess(DataBean<MyMedalsSubBean> result) {
                if (result.isSuccessEquals1()) {
                    ToastUtils.showToast("领取成功");
                    medalsBeanList.get(i).getMedals().remove(j);
                    medalsBeanList.get(i).getMedals().add(j, myMedalsSubBean);
//                    medalsBeanList.get(i).getMedals().get(j).setAction("0");
//                    medalsBeanList.get(i).getMedals().get(j).setActiondesc("已领取");
                    adapter.notifyDataSetChanged();
                } else {
                    showRemindDialog(result.getMessage());
                }

            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                mActivity.dialogDismiss();
            }
        });
    }


    private void showRemindDialog(String msg) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(mActivity);
        builder.setTitle(null);
        builder.setIsOneButton(true);
        builder.setMessage(msg);
        builder.setPositiveButton("确定");
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ADD_REGULARCARD) {
                String medalid = data.getStringExtra("medalid");
                for (MyMedalsBean.MyMedalsTypeBean bean : medalsBeanList) {
                    for (MyMedalsSubBean subBean : bean.getMedals()) {
                        if (TextUtils.equals(subBean.getMedalid(), medalid)) {
                            subBean.setAction("4");
                            subBean.setActiondesc("已申请");
                        }
                    }
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}
