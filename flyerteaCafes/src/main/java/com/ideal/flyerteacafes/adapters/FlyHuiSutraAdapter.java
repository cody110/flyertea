package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.SpannableString;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.FlyHuiModel;
import com.ideal.flyerteacafes.model.entity.FlyHuiModel.DisList;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class FlyHuiSutraAdapter extends CommonAdapter<ArticleTabBean> {

    private Context context;

    public FlyHuiSutraAdapter(Context context, List<ArticleTabBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, ArticleTabBean t) {
        // TODO Auto-generated method stub
        setRightTuibiao(holder, t, R.id.flyhui_sutra_top);
        holder.setImageUrl(R.id.flyhui_sutra_img, t.getPic(), R.drawable.post_def);
        holder.setText(R.id.flyhui_sutra_title, t.getTitle());
//        holder.setText(R.id.listview_flyhui_type, t.getCate_2_name());
//        holder.setText(R.id.listview_flyhui_preferential_type, t.getShangjianame());
//        String time = DataUtils.conversionTime(DataTools.getLong(t.getDateline()));
        holder.setTextHtml(R.id.listview_flyhui_time, t.getDateline());
        holder.setText(R.id.listview_flyhui_read, t.getViews()+"人查看");

        if (getShowTime(t) == null) {
            holder.setVisible(R.id.flyhui_sutra_hui_time, false);
        } else {
            holder.setVisible(R.id.flyhui_sutra_hui_time, true);
            holder.setText(R.id.flyhui_sutra_hui_time, getShowTime(t));
        }


    }

    protected void setRightTuibiao(ViewHolder holder, ArticleTabBean t, int id) {
//        if (t.getTop().equals("1")) {
//            holder.setImageResource(id, R.drawable.sutra_zhiding);
//            holder.setVisible(id, true);
//        } else if (t.getTuijian().equals("1")) {
//            holder.setImageResource(id, R.drawable.sutra_tuijian);
//            holder.setVisible(id, true);
//        } else if (DateUtil.getDateline() / 1000 > (DataTools.getInteger(t.getEndtime()))) {
//            LogFly.e(Utils.FLYLOGCAT, "DateUtil.getDateline(" + DateUtil.getDateline() + "e.end:" + DataTools.getInteger(t.getEndtime()) * 1000);
//            holder.setImageResource(id, R.drawable.sutra_guoqi);
//            holder.setVisible(id, true);
//        } else {
//            holder.setVisible(id, false);
//        }
    }


    private SpannableString getShowTime(ArticleTabBean disinfo) {
        int between = (int) (DataTools.getInteger(disinfo.getBegindateline()) - DateUtil.getDateline() / 1000);
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;

        if (day1 >= 7 || between < 0)
            return null;

        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode mode1 = new SegmentedStringMode("剩：", R.dimen.app_body_size_2, R.color.app_body_grey);
        modeList.add(mode1);
        if (day1 > 0) {
            SegmentedStringMode mode2 = new SegmentedStringMode(String.valueOf(day1), R.dimen.app_body_size_2, R.color.app_bg_title);
            SegmentedStringMode mode3 = new SegmentedStringMode(" 天 ", R.dimen.app_body_size_2, R.color.app_body_grey);
            SegmentedStringMode mode4 = new SegmentedStringMode(String.valueOf(hour1), R.dimen.app_body_size_2, R.color.app_bg_title);
            SegmentedStringMode mode5 = new SegmentedStringMode(" 小时", R.dimen.app_body_size_2, R.color.app_body_grey);

            modeList.add(mode2);
            modeList.add(mode3);
            modeList.add(mode4);
            modeList.add(mode5);
        } else {
            SegmentedStringMode mode2 = new SegmentedStringMode(String.valueOf(hour1), R.dimen.app_body_size_2, R.color.app_bg_title);
            SegmentedStringMode mode3 = new SegmentedStringMode(" 小时 ", R.dimen.app_body_size_2, R.color.app_body_grey);
            SegmentedStringMode mode4 = new SegmentedStringMode(String.valueOf(minute1), R.dimen.app_body_size_2, R.color.app_bg_title);
            SegmentedStringMode mode5 = new SegmentedStringMode(" 分", R.dimen.app_body_size_2, R.color.app_body_grey);

            modeList.add(mode2);
            modeList.add(mode3);
            modeList.add(mode4);
            modeList.add(mode5);
        }

        return DataUtils.getSegmentedDisplaySs(modeList);
    }

}
