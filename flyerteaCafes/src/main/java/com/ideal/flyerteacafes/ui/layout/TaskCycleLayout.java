package com.ideal.flyerteacafes.ui.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.andexert.calendarlistview.library.CalendarDay;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by fly on 2017/4/21.
 * 任务周期
 */

public class TaskCycleLayout extends LinearLayout {

    public interface IClickListener {
        void onClick(View v, int pos);
    }

    private IClickListener iClickListener;

    public void setIClickListener(IClickListener listener) {
        iClickListener = listener;
    }

    private int pos;
    private CalendarDay startTime, endTime;

    @ViewInject(R.id.mltcri_frist_period_layout)
    LtctriLayout mltcri_frist_period_layout;
    @ViewInject(R.id.mltre_expense_layout)
    public LtreLayout mltre_expense_layout;

    @ViewInject(R.id.mltre_money_layout)
    public LtreLayout mltre_money_layout;

    @ViewInject(R.id.mltre_total_money_layout)
    public LtreLayout mltre_total_money_layout;

    @ViewInject(R.id.delete_btn)
    public View delete_btn;


    public TaskCycleLayout(Context context) {
        super(context);
        init();
    }

    public TaskCycleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_task_cycle, this);
        x.view().inject(this, this);
    }

    public void setPos(int pos) {
        this.pos = pos;
        mltcri_frist_period_layout.setTextByLt("第" + DataTools.ToCH(pos + 1) + "期");
    }

    public void setHint(String bishu, String jine, String allJine) {
        mltre_expense_layout.setTextByRe(bishu);
        mltre_money_layout.setTextByRe(jine);
        mltre_total_money_layout.setTextByRe(allJine);
    }

    @Event({R.id.mltcri_frist_period_layout, R.id.delete_btn})
    private void onclck(View v) {
        if (iClickListener != null) iClickListener.onClick(v, pos);
    }


    public void setTime(CalendarDay startTime, CalendarDay endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        mltcri_frist_period_layout.setTextByCt(startTime.getYear() + "." + startTime.getMonth() + "." + startTime.getDay()
                + "-" + endTime.getYear() + "." + endTime.getMonth() + "." + endTime.getDay());
    }

    public long getStartTime() {
        if (startTime == null)
            return 0;
        return startTime.setDayStart().getDate().getTime();
    }

    public long getEndTime() {
        if (endTime == null)
            return 0;
        return endTime.setDayEnd().getDate().getTime();
    }


    /**
     * 获取数据
     *
     * @return
     */
    public HashMap<String, Object> getPeriod() {
        HashMap<String, Object> period = new HashMap<>();
        period.put("startTime", startTime.setDayStart().getDate().getTime() / 1000);
        period.put("endTime", endTime.setDayEnd().getDate().getTime() / 1000);
        period.put("posValue", mltre_total_money_layout.getTextByRe());
        period.put("posTime", mltre_expense_layout.getTextByRe());
        period.put("pertimeLimit", mltre_money_layout.getTextByRe());
        period.put("cardMissionPeroidId", (pos + 1));
        return period;
    }

    public boolean isDataOk() {
        if (startTime == null || endTime == null)
            return false;
        if (TextUtils.isEmpty(mltre_total_money_layout.getTextByRe().trim())
                && TextUtils.isEmpty(mltre_expense_layout.getTextByRe().trim())
                && TextUtils.isEmpty(mltre_money_layout.getTextByRe().trim()))
            return false;
        return true;
    }
}
