package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.UserGroupsBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.NoviceGuidanceActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.TaskDetailsActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 我的任务页面header
 * <p>
 * Created by fly on 2017/5/17.
 */
public class TaskHeaderLayout extends LinearLayout {

    @ViewInject(R.id.face_img)
    private ImageView face_img;
    @ViewInject(R.id.seekbar_text)
    private TextView seekbar_text;
    @ViewInject(R.id.seekbar)
    private SeekBar seekbar;
    @ViewInject(R.id.weiwang_tv)
    private TextView weiwang_tv;
    @ViewInject(R.id.user_grade_layout)
    private LinearLayout user_grade_layout;
    @ViewInject(R.id.banner_layout)
    private BannerViewpager<MyTaskAllBean.BannerBean> banner_layout;


    public TaskHeaderLayout(Context context) {
        super(context);
        init();
    }

    public TaskHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_mymember, this);
        x.view().inject(this, this);
        bindUser();
        requestUsergroup();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }

    @Event(R.id.shengji_gonglue_btn)
    private void click(View view) {
        Bundle b = new Bundle();
        b.putString("url", Utils.HtmlUrl.HTML_HOT_SCORE);
        Intent intent = new Intent(getContext(), TbsWebActivity.class);
        intent.putExtras(b);
        getContext().startActivity(intent);
    }


    /**
     * 获取用户等级列表
     */
    private void requestUsergroup() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_USERGROUPS);
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.DATA, UserGroupsBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                bindUserGroups(result.getDataList());
            }
        });
    }


    private void bindUser() {
        seekbar.setMax(100);
        WidgetUtils.setText(weiwang_tv, "威望 " + UserManger.getUserInfo().getCredits());
        DataUtils.downloadPicture(face_img, UserManger.getUserInfo().getFace(), R.drawable.def_face_2);
    }


    public void bindUserGroups(List<UserGroupsBean> datas) {
        if (DataUtils.isEmpty(datas)) return;
        for (int i = 0; i < datas.size(); i++) {
            TextView tv = new TextView(getContext());
            String groupname = datas.get(i).getGroupname();
            if (!TextUtils.isEmpty(groupname) && groupname.length() > 2) {
                groupname = groupname.substring(0, 2);
            }
            WidgetUtils.setText(tv, groupname);
            tv.setGravity(Gravity.CENTER);
            if (datas.get(i).getGroupid() == UserManger.getUserInfo().getGroupid()) {
                tv.setTextColor(getContext().getResources().getColor(R.color.app_body_goldenrod));
                WidgetUtils.setText(seekbar_text, UserManger.getUserInfo().getCredits() + "/" + datas.get(i).getCreditslower());
                if (i == datas.size() - 1) {
                    seekbar.setProgress(100);
                } else {
                    int scale = 100 / (datas.size() - 1);
                    int Offset = (DataTools.getInteger(UserManger.getUserInfo().getCredits()) - datas.get(i + 1).getCreditshigher()) / (datas.get(i + 1).getCreditslower() - datas.get(i + 1).getCreditshigher()) * scale;
                    int progress = i * scale + Offset;
                    seekbar.setProgress(progress);


                    int seekbarWidth = seekbar.getWidth();
                    int textWidth = seekbar_text.getWidth();
                    float marginLeft = seekbarWidth / 100 * progress - textWidth / 2;
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekbar_text.getLayoutParams();
                    params.setMargins((int) marginLeft, 0, 0, 0);

                }


            } else {
                tv.setTextColor(getContext().getResources().getColor(R.color.white));
            }

            tv.setTextSize(12);
            LinearLayout.LayoutParams params;
            if (i == 0 || i == datas.size() - 1) {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            }

            user_grade_layout.addView(tv, params);
        }
    }

    /**
     * 设置banner
     *
     * @param bannerBeanList
     */
    public void bindBanner(List<MyTaskAllBean.BannerBean> bannerBeanList) {
        WidgetUtils.setVisible(banner_layout, !DataUtils.isEmpty(bannerBeanList));

        banner_layout.bindData(bannerBeanList, new BannerViewpager.IBannerSetting<MyTaskAllBean.BannerBean>() {
            @Override
            public void bannerIgvSetting(ImageView igv, final MyTaskAllBean.BannerBean data) {
                x.image().bind(igv, data.getImage_url(), XutilsHelp.image_FIT_XY);
                igv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.equals(data.getType(), "birthday")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(TaskDetailsActivity.BUNDLE_ID, data.getTaskid());
                            Intent intent = new Intent(getContext(), TaskDetailsActivity.class);
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        } else if (TextUtils.equals(data.getType(), "freshman")) {
                            Intent intent = new Intent(getContext(), NoviceGuidanceActivity.class);
                            getContext().startActivity(intent);
                        }
                    }
                });
            }
        });

    }


}
