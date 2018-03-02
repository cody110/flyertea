package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.List;

/**
 * Created by fly on 2017/12/13.
 */

public class NoviceGuidanceAdapter extends CommonAdapter<MyTaskBean> {
    public NoviceGuidanceAdapter(Context context, List<MyTaskBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, MyTaskBean bean) {

        holder.setImageUrl(R.id.icon, bean.getIcon(), R.drawable.icon_def);
        View view = holder.getConvertView();
        TextView title = V.f(view, R.id.title);
        TextView feimi = V.f(view, R.id.feimi);
        TextView weiwang = V.f(view, R.id.weiwang);
        TextView other = V.f(view, R.id.other);
        TextView xunzhang = V.f(view, R.id.xunzhang);
        TextView status = V.f(view, R.id.status_tv);
        WidgetUtils.setText(title, bean.getName());

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

        if (TextUtils.equals(bean.getStatus(), "1")) {
            WidgetUtils.setText(status, "已完成");
            holder.setTextColorRes(R.id.status_tv, R.color.app_bg_title);
        } else {
            WidgetUtils.setText(status, "去做任务");
            holder.setTextColorRes(R.id.status_tv, R.color.tag_select);
        }

    }
}
