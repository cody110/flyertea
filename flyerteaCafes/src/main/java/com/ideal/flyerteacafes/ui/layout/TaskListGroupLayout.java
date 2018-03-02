package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.List;

/**
 * Created by fly on 2017/5/17.
 */

public class TaskListGroupLayout extends LinearLayout {

    public TaskListGroupLayout(Context context) {
        super(context);
    }

    public TaskListGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindData(List<MyTaskBean> datas, boolean isStatus) {
        removeAllViews();
        if (datas == null) return;
        for (int i = 0; i < datas.size(); i++) {
            MyTaskBean bean = datas.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_mytask, null);
            ImageView icon = V.f(view, R.id.icon);
            TextView title = V.f(view, R.id.title);
            TextView feimi = V.f(view, R.id.feimi);
            TextView weiwang = V.f(view, R.id.weiwang);
            TextView other = V.f(view, R.id.other);
            TextView xunzhang = V.f(view, R.id.xunzhang);
            TextView zhouqi_time_tv = V.f(view, R.id.zhouqi_time_tv);
            TextView status = V.f(view, R.id.status_tv);
            View line = V.f(view, R.id.line);
            DataUtils.downloadPicture(icon, bean.getIcon(), R.drawable.icon_def);
            WidgetUtils.setText(title, bean.getName());


            if (isStatus) {
                if (TextUtils.equals(bean.getStatus(), "1")) {
                    WidgetUtils.setText(status, "已完成");
                    status.setTextColor(getResources().getColor(R.color.app_bg_title));
                } else {
                    WidgetUtils.setText(status, "去做任务");
                    status.setTextColor(getResources().getColor(R.color.tag_select));
                }
            }

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

            if (TextUtils.equals(bean.getReward(), "medal")) {
                WidgetUtils.setVisible(xunzhang, true);
                WidgetUtils.setText(xunzhang, bean.getRewardtext());
            } else {
                WidgetUtils.setVisible(xunzhang, false);
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
                WidgetUtils.setVisible(zhouqi_time_tv, false);
            } else if (TextUtils.equals(bean.getPeriod(), "1")) {
                String period = "每" + danwei + "任务";
                WidgetUtils.setText(zhouqi_time_tv, period);
            } else {
                String period = bean.getPeriod() + danwei + "任务";
                WidgetUtils.setText(zhouqi_time_tv, period);
            }


            if (i == datas.size() - 1) {
                line.setVisibility(View.GONE);
            }
            addView(view);
        }
    }

    public void bindData(List<MyTaskBean> datas) {
        bindData(datas, false);
    }
}
