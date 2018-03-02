package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.model.entity.MyTaskDetailsBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.SettingActivity;
import com.ideal.flyerteacafes.ui.activity.XunzhangActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.InterlocutionActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.web.TripWebActivity;
import com.ideal.flyerteacafes.ui.activity.UserActivity;
import com.ideal.flyerteacafes.ui.activity.YouzanActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardAddTask;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fly on 2017/5/19.
 */
@ContentView(R.layout.activity_fly_task_details)
public class TaskDetailsActivity extends BaseActivity {

    public static final String BUNDLE_DATA = "data";
    public static final String BUNDLE_ID = "id";


    @ViewInject(R.id.icon)
    private ImageView icon;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.feimi)
    private TextView feimi;
    @ViewInject(R.id.weiwang)
    private TextView weiwang;
    @ViewInject(R.id.other)
    private TextView other;
    @ViewInject(R.id.miaoshu)
    private TextView miaoshu;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.status)
    private TextView status;
    @ViewInject(R.id.task_ok_jilu_title)
    private View task_ok_jilu_title;
    @ViewInject(R.id.log_layout)
    private LinearLayout log_layout;
    @ViewInject(R.id.button_layout)
    private View button_layout;
    @ViewInject(R.id.do_task)
    private TextView do_task;
    @ViewInject(R.id.get_jiangli)
    private TextView get_jiangli;
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    @ViewInject(R.id.zhouqi_time_tv)
    private TextView zhouqi_time_tv;
    @ViewInject(R.id.get_task_time)
    private TextView get_task_time;
    @ViewInject(R.id.xunzhang)
    private TextView xunzhang;

    MyTaskBean myTaskBean;
    MyTaskDetailsBean data;
    String taskId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myTaskBean = (MyTaskBean) getIntent().getSerializableExtra(BUNDLE_DATA);
        if (myTaskBean != null)
            taskId = myTaskBean.getTaskid();
        else
            taskId = getIntent().getStringExtra(BUNDLE_ID);
        setNumber(myTaskBean);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData(taskId);
    }

    private void getData(String id) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MY_TASK_DETAILS);
        params.addQueryStringParameter("taskid", id);
        XutilsHttp.Get(params, new DataCallback<MyTaskDetailsBean>() {
            @Override
            public void flySuccess(DataBean<MyTaskDetailsBean> result) {
                data = result.getDataBean();
                dataBind(result.getDataBean());
            }
        });
    }

    private void setNumber(MyTaskBean bean) {
        if (bean == null) return;
        if (TextUtils.equals(bean.getPrize(), "1")) {//威望
            WidgetUtils.setVisible(weiwang, true);
            if (DataTools.getInteger(bean.getBonus()) != 0) {
                WidgetUtils.setText(weiwang, bean.getBonus() + "威望");
            } else {
                WidgetUtils.setText(weiwang, bean.getRewardother());
            }
        } else if (TextUtils.equals(bean.getPrize(), "6")) {//飞米
            WidgetUtils.setVisible(feimi, true);
            if (DataTools.getInteger(bean.getBonus()) != 0) {
                WidgetUtils.setText(feimi, bean.getBonus() + "飞米");
            } else {
                WidgetUtils.setText(feimi, bean.getRewardother());
            }
        } else if (TextUtils.equals(bean.getPrize(), "0")) {//其他
            WidgetUtils.setVisible(other, true);
            if (DataTools.getInteger(bean.getBonus()) != 0) {
                WidgetUtils.setText(other, bean.getBonus());
            } else {
                WidgetUtils.setText(other, bean.getRewardother());
            }
        }


        if (TextUtils.equals(bean.getPrize2(), "1")) {//威望
            WidgetUtils.setVisible(weiwang, true);
            if (DataTools.getInteger(bean.getBonus2()) != 0) {
                WidgetUtils.setText(weiwang, bean.getBonus2() + "威望");
            } else {
                WidgetUtils.setText(weiwang, bean.getRewardother2());
            }
        } else if (TextUtils.equals(bean.getPrize2(), "6")) {//飞米
            WidgetUtils.setVisible(feimi, true);
            if (DataTools.getInteger(bean.getBonus2()) != 0) {
                WidgetUtils.setText(feimi, bean.getBonus2() + "飞米");
            } else {
                WidgetUtils.setText(feimi, bean.getRewardother2());
            }
        } else if (TextUtils.equals(bean.getPrize2(), "0")) {//其他
            WidgetUtils.setVisible(other, true);
            if (DataTools.getInteger(bean.getBonus2()) != 0) {
                WidgetUtils.setText(other, bean.getBonus2());
            } else {
                WidgetUtils.setText(other, bean.getRewardother2());
            }
        }

        if (TextUtils.equals(bean.getPrize3(), "1")) {//威望
            WidgetUtils.setVisible(weiwang, true);
            if (DataTools.getInteger(bean.getBonus3()) != 0) {
                WidgetUtils.setText(weiwang, bean.getBonus3() + "威望");
            } else {
                WidgetUtils.setText(weiwang, bean.getRewardother3());
            }
        } else if (TextUtils.equals(bean.getPrize3(), "6")) {//飞米
            WidgetUtils.setVisible(feimi, true);
            if (DataTools.getInteger(bean.getBonus3()) != 0) {
                WidgetUtils.setText(feimi, bean.getBonus3() + "飞米");
            } else {
                WidgetUtils.setText(feimi, bean.getRewardother3());
            }
        } else if (TextUtils.equals(bean.getPrize3(), "0")) {//其他
            WidgetUtils.setVisible(other, true);
            if (DataTools.getInteger(bean.getBonus3()) != 0) {
                WidgetUtils.setText(other, bean.getBonus3());
            } else {
                WidgetUtils.setText(other, bean.getRewardother3());
            }
        }


    }

    private void dataBind(MyTaskDetailsBean bean) {
        if (bean == null) return;
        if (TextUtils.equals(bean.getReward(), "medal")) {
            WidgetUtils.setVisible(xunzhang, true);
            WidgetUtils.setText(xunzhang, bean.getRewardtext() + "  有效期" + bean.getRewardother());
        }

        scrollView.setVisibility(View.VISIBLE);
        button_layout.setVisibility(View.VISIBLE);
        DataUtils.downloadPicture(icon, bean.getIcon(), R.drawable.icon_def);
        WidgetUtils.setText(title, bean.getName());

        WidgetUtils.setTextHtml(miaoshu, bean.getDescription());

        if (DataTools.getInteger(bean.getEndtime()) == 0) {
            WidgetUtils.setText(time, "永久");
        } else {
            WidgetUtils.setText(time, DataUtils.getTimeFormat(bean.getStarttime(), "yyyy-MM-dd") + " ~ " + DataUtils.getTimeFormat(bean.getEndtime(), "yyyy-MM-dd"));
        }

        if (TextUtils.equals(bean.getStatus(), "0")) {
            WidgetUtils.setText(status, "进行中");
        } else if (TextUtils.equals(bean.getStatus(), "1")) {
            WidgetUtils.setText(status, "已完成");
            if (isSetTimeTv(bean)) {
                initTimerTask();
            }
        } else if (TextUtils.equals(bean.getStatus(), "2") || TextUtils.equals(bean.getStatus(), "-1")) {
            WidgetUtils.setText(status, "已过期");
        }

        if (!DataTools.isEmpty(bean.getCreditlogs())) {
            log_layout.setVisibility(View.VISIBLE);
            task_ok_jilu_title.setVisibility(View.VISIBLE);
            for (MyTaskDetailsBean.CreditlogBean logBean : bean.getCreditlogs()) {
                View view = LayoutInflater.from(this).inflate(R.layout.include_task_log_item, null);
                View line = LayoutInflater.from(this).inflate(R.layout.include_line_xi_view, null);

                TextView time = V.f(view, R.id.time);
                TextView weiwang = V.f(view, R.id.weiwang);
                TextView feimi = V.f(view, R.id.feimi);
                WidgetUtils.setText(time, DataUtils.getTimeFormatToBiaozhun(logBean.getDateline()));
                WidgetUtils.setText(weiwang, logBean.getExtcredits1());
                WidgetUtils.setText(feimi, logBean.getExtcredits6());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(40));
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                log_layout.addView(view, params);
                log_layout.addView(line, lineParams);
            }
        }

        if (TextUtils.equals(bean.getStatus(), "0") && !TextUtils.equals(bean.getCsc(), "100")) {
            WidgetUtils.setVisible(do_task, true);
        } else {
            WidgetUtils.setVisible(do_task, false);
        }

        if (TextUtils.equals(bean.getStatus(), "0")) {
            if (TextUtils.equals(bean.getCsc(), "100") || TextUtils.equals(bean.getReward(), "manual")) {
                WidgetUtils.setVisible(get_jiangli, true);
            } else {
                WidgetUtils.setVisible(get_jiangli, false);
            }
        } else {
            WidgetUtils.setVisible(get_jiangli, false);
        }


        if (do_task.getVisibility() == View.GONE && get_jiangli.getVisibility() == View.GONE) {
            button_layout.setVisibility(View.GONE);
        }


        String danwei;

        if (TextUtils.equals(bean.getPeriodtype(), "2")) {
            danwei = "周";
        } else if (TextUtils.equals(bean.getPeriodtype(), "3")) {
            danwei = "月";
        } else if (TextUtils.equals(bean.getPeriodtype(), "4")) {
            danwei = "年";
        } else {
            danwei = "日";
        }

        if (TextUtils.isEmpty(bean.getPeriod()) || TextUtils.equals(bean.getPeriod(), "0")) {
            zhouqi_time_tv.setVisibility(View.INVISIBLE);
        } else if (TextUtils.equals(bean.getPeriod(), "1")) {
            String period = "每" + danwei + "任务";
            WidgetUtils.setText(zhouqi_time_tv, period);
            zhouqi_time_tv.setVisibility(View.VISIBLE);
        } else {
            String period = bean.getPeriod() + danwei + "任务";
            WidgetUtils.setText(zhouqi_time_tv, period);
            zhouqi_time_tv.setVisibility(View.VISIBLE);
        }


    }


    @Event({R.id.toolbar_left, R.id.do_task, R.id.get_jiangli})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                onBackPressed();
                break;
            case R.id.do_task:
                if (data == null) return;
                if (!TextUtils.isEmpty(data.getH5_url())) {//TextUtils.equals(data.getApp_url(), "h5_url")
                    Bundle wbundle = new Bundle();
                    wbundle.putString("url", data.getH5_url());
                    jumpActivity(TbsWebActivity.class, wbundle);
                } else if (TextUtils.equals(data.getApp_url(), "personinfo")) {
                    //TODO 跳转个人中心 编辑个人信息
                    jumpActivity(UserActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "article") || TextUtils.equals(data.getApp_url(), "report") || TextUtils.equals(data.getApp_url(), "gonglue")) {
                    //TODO 跳转酒店report

                    if (TextUtils.equals(data.getApp_url_id(), "0")) {
                        Bundle bundle = new Bundle();
                        if (TextUtils.equals(data.getApp_url(), "gonglue")) {
                            bundle.putInt("code", FinalUtils.HOME_GONGLUE);
                        } else {
                            bundle.putInt("code", FinalUtils.HOME_REPORT);
                        }
                        jumpActivity(HomeActivity.class, bundle);

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(ArticlePresenter.BUNDLE_AID, data.getApp_url_id());
                        jumpActivity(ArticleContentActivity.class, bundle);
                    }
                } else if (TextUtils.equals(data.getApp_url(), "setting")) {//设置
                   jumpActivity(SettingActivity.class);
                } else if (TextUtils.equals(data.getApp_url(), "realinfo")) {
                    //TODO -实名信息填写
                    jumpActivity(RealNameActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "ffpinfo")) {
                    //TODO 添加常客卡
                    jumpActivity(AddRegularCardActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "flyertrip")) {
                    //TODO 跳转飞客旅行H5
                    Bundle bundle = new Bundle();
                    String url = data.getApp_url() + "Android_TabBar";
                    bundle.putString("url", url);
                    jumpActivity(TripWebActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "flyerchoice")) {
                    //TODO 跳转优选
                    MobclickAgent.onEvent(this, FinalUtils.EventId.youzan);
                    Intent intent = new Intent(this, YouzanActivity.class);
                    intent.putExtra(YouzanActivity.SIGN_URL, data.getApp_url());
                    startActivity(intent);
                } else if (TextUtils.equals(data.getApp_url(), "creditcardtask")) {
                    //TODO 跳转增加刷卡任务
                    jumpActivity(SwingCardAddTask.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "homepage")) {
                    //TODO 首页'--转首页
                    jumpActivity(HomeActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "homethread")) {
                    //TODO 跳转帖子列表页
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_READ_POST);
                    jumpActivity(HomeActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "homeforum")) {
                    //TODO 跳转首页-版块选择
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_TO_FORUM);
                    jumpActivity(HomeActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "forumdisplay")) {
                    //TODO 跳转某个版块，app_url_id是版块id
                    Bundle bundle = new Bundle();
                    bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, data.getApp_url_id());
                    jumpActivity(CommunitySubActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "flyermiles")) {
                    //TODO 跳转飞米商城
                    Bundle bundle = new Bundle();
                    bundle.putString("url", DataUtils.getFeimiUrl(data.getApp_url()));
                    jumpActivity(TbsWebActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "thread")) {
                    //TODO 跳转某个帖子， app_url_id是帖子id
                    Bundle bundle = new Bundle();
                    bundle.putString(ThreadPresenter.BUNDLE_TID, data.getApp_url_id());
                    jumpActivity(ThreadActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "askhelp")) {//TODO 問答
                    jumpActivity(InterlocutionActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "updatepersoninfo")) {//TODO 完善个人信息
                    jumpActivity(UserActivity.class, null);
                } else if (TextUtils.equals(data.getApp_url(), "homethreadhot")) {//TODO 读帖 热帖
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_THREAD_HOT);
                    jumpActivity(HomeActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "homethreaddigest")) {//TODO 读帖 精华
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_THREAD_DIGEST);
                    jumpActivity(HomeActivity.class, bundle);
                } else if (TextUtils.equals(data.getApp_url(), "medallist")) {//TODO 勋章列表
                    jumpActivity(XunzhangActivity.class);
                } else if (TextUtils.equals(data.getApp_url(), "promotion")) {//TODO 优惠
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", FinalUtils.HOME_PROMOTION);
                    jumpActivity(HomeActivity.class, bundle);
                }

                break;
            case R.id.get_jiangli:
                if (data == null) return;
                if (TextUtils.isEmpty(data.getRewardurl())) {
                    getJangli();
                } else {
                    Bundle wbundle = new Bundle();
                    wbundle.putString("url", data.getRewardurl());
                    jumpActivity(TbsWebActivity.class, wbundle);
                }
                break;
        }
    }


    boolean isRef = false;

    /**
     * 获取奖励
     */
    private void getJangli() {
        proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMTASK_DRAW);
        params.addQueryStringParameter("taskid", taskId);
        XutilsHttp.Get(params, new StringCallback() {

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }

            @Override
            public void flySuccess(String result) {
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    ToastUtils.showToast("领取奖励成功");
                    getData(taskId);
                    isRef = true;
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (isRef) {
            jumpActivitySetResult(null);
        } else {
            super.onBackPressed();
        }
    }


    private Timer timer = new Timer();
    TimerTask task;

    /**
     * 倒计时
     */
    private void initTimerTask() {
        if (task != null)
            task.cancel();
        task = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        if (isSetTimeTv(data)) {

                        } else {
                            task.cancel();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000 * 60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null)
            task.cancel();
    }

    private boolean isSetTimeTv(MyTaskDetailsBean bean) {
        int timeInt = (int) (DataTools.getInteger(bean.getNextapplytime()) - DateUtil.getDateline() / 1000);
        if (timeInt > 0) {

            int minute = timeInt / 60;

            int hour = timeInt / 60 / 60;

            int day = hour / 24;

            hour = hour - day * 24;

            minute = minute - day * 24 * 60 - hour * 60;

            List<SegmentedStringMode> modeList = new ArrayList<>();
            if (day > 0) {
                SegmentedStringMode mode1 = new SegmentedStringMode(String.valueOf(day), R.dimen.app_bg_title_size, R.color.app_bg_title, null);
                SegmentedStringMode mode2 = new SegmentedStringMode("天", R.dimen.app_body_size_1, R.color.app_body_grey, null);
                modeList.add(mode1);
                modeList.add(mode2);
            }
            if (hour > 0) {
                SegmentedStringMode mode1 = new SegmentedStringMode(String.valueOf(hour), R.dimen.app_bg_title_size, R.color.app_bg_title, null);
                SegmentedStringMode mode2 = new SegmentedStringMode("小时", R.dimen.app_body_size_1, R.color.app_body_grey, null);
                modeList.add(mode1);
                modeList.add(mode2);
            }
            if (minute > 0) {
                SegmentedStringMode mode1 = new SegmentedStringMode(String.valueOf(minute), R.dimen.app_bg_title_size, R.color.app_bg_title, null);
                SegmentedStringMode mode2 = new SegmentedStringMode("分", R.dimen.app_body_size_1, R.color.app_body_grey, null);
                modeList.add(mode1);
                modeList.add(mode2);
            }

            SegmentedStringMode mode2 = new SegmentedStringMode("后可再次领取任务", R.dimen.app_body_size_1, R.color.app_body_grey, null);
            modeList.add(mode2);

            WidgetUtils.setText(get_task_time, DataUtils.getSegmentedDisplaySs(modeList));
            get_task_time.setVisibility(View.VISIBLE);
            return true;
        } else {
            get_task_time.setVisibility(View.GONE);
            return false;
        }

    }

}
