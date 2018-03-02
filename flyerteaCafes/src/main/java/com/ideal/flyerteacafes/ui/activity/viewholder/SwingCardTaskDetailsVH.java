package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;
import com.ideal.flyerteacafes.model.entity.TaskPriodBean;
import com.ideal.flyerteacafes.model.entity.TaskPriodProgressesBean;
import com.ideal.flyerteacafes.ui.layout.SwingCardZhouqiGroupLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DateTimeUtil;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/5/8.
 */

public class SwingCardTaskDetailsVH implements View.OnClickListener {


    @Override
    public void onClick(View view) {
        if (iActionListener != null) {
            iActionListener.actionClick(view);
        }
    }

    IActionListener iActionListener;

    public interface IActionListener {

        int ID_BACK = R.id.toolbar_left;
        int ID_PROGRESS = R.id.toolbar_right;
        int ID_DELETE = R.id.delete_btn;

        void actionClick(View view);
    }


    Context mContext = FlyerApplication.getContext();
    View mView;
    @ViewInject(R.id.task_details_title)
    TextView task_details_title;
    @ViewInject(R.id.task_details_desc)
    TextView task_details_desc;
    @ViewInject(R.id.task_zhouqi)
    TextView task_zhouqi;
    @ViewInject(R.id.task_qingkuang)
    TextView task_qingkuang;
    @ViewInject(R.id.task_progress_layout)
    LinearLayout task_progress_layout;
    @ViewInject(R.id.zhouqi_status_layout)
    LinearLayout zhouqi_status_layout;
    @ViewInject(R.id.zhouqi_status_scroll)
    HorizontalScrollView zhouqi_status_scroll;

    public SwingCardTaskDetailsVH(View view, IActionListener iActionListener) {
        mView = view;
        this.iActionListener = iActionListener;
        x.view().inject(this, view);
        V.f(mView, R.id.toolbar_left).setOnClickListener(this);
        V.f(mView, R.id.toolbar_right).setOnClickListener(this);
        V.f(mView, R.id.delete_btn).setOnClickListener(this);
    }


    public void bindData(final TaskDetailsBean bean) {
        if (bean == null) return;
        zhouqi_status_layout.removeAllViews();
        WidgetUtils.setText(task_details_title, bean.getCardMissionTitle());
        WidgetUtils.setText(task_details_desc, bean.getCardMissionDesc());


        int w_screen = SharedPreferencesString.getInstances().getIntToKey("w_screen");

        if (!DataTools.isEmpty(bean.getPeriods())) {
            if (bean.getPeriods().size() == 1) {
                bindDataZhouqiInfo(bean.getPeriods().get(0));
            } else {
                zhouqi_status_scroll.setVisibility(View.VISIBLE);
                long time = DateUtil.getDateline() / 1000;
                int selectIndex = 0;
                for (int i = 0; i < bean.getPeriods().size(); i++) {
                    final View layout = LayoutInflater.from(mContext).inflate(R.layout.item_task_details_zhouqi, null);
                    ImageView zhouqi_status_icon = (ImageView) layout.findViewById(R.id.zhouqi_status_icon);
                    TextView zhouqi_pos_tv = (TextView) layout.findViewById(R.id.zhouqi_pos_tv);
                    TextView zhouqiTv = (TextView) layout.findViewById(R.id.zhouqi_status);

                    if (bean.getPeriods().get(i).getDoneStatus() == TaskPriodBean.DONESTATUS_COMPLETED) {
                        zhouqi_status_icon.setImageResource(R.mipmap.swing_card_zhouqi_ok);
                    } else if (bean.getPeriods().get(i).getDoneStatus() == TaskPriodBean.DONESTATUS_NOT_COMPLETED) {
                        zhouqi_status_icon.setImageResource(R.mipmap.swing_card_zhouqi_not_ok);
                    } else if (bean.getPeriods().get(i).getDoneStatus() == TaskPriodBean.DONESTATUS_ING) {
                        zhouqi_status_icon.setImageResource(R.drawable.blue_circle);
                        zhouqi_pos_tv.setText(String.valueOf(i + 1));
                        zhouqi_pos_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else if (bean.getPeriods().get(i).getDoneStatus() == TaskPriodBean.DONESTATUS_NOT_START) {
                        zhouqi_status_icon.setImageResource(R.drawable.frames_blue_circle);
                        zhouqi_pos_tv.setText(String.valueOf(i + 1));
                        zhouqi_pos_tv.setTextColor(mContext.getResources().getColor(R.color.app_bg_title));
                    }
                    zhouqiTv.setText(bean.getPeriods().get(i).getDoneDescription());
                    LinearLayout.LayoutParams params;
                    if (bean.getPeriods().size() <= 5) {
                        params = new LinearLayout.LayoutParams(w_screen / 5, w_screen / 5);
                    } else {
                        params = new LinearLayout.LayoutParams((int) (w_screen / 5.5), (int) (w_screen / 5.5));
                    }
                    params.gravity = Gravity.CENTER;
                    zhouqi_status_layout.addView(layout, params);

                    if (DataTools.getInteger(bean.getPeriods().get(i).getEndTime()) > time && DataTools.getInteger(bean.getPeriods().get(i).getStartTime()) < time) {
                        selectIndex = i;
                    }

                    final int finalI = i;
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showSelectIcon(finalI);
                            bindDataZhouqiInfo(bean.getPeriods().get(finalI));
                        }
                    });


                }
                showSelectIcon(selectIndex);
                bindDataZhouqiInfo(bean.getPeriods().get(selectIndex));
                final int finalSelectIndex = selectIndex;
                zhouqi_status_scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        zhouqi_status_scroll.scrollTo(zhouqi_status_layout.getChildAt(finalSelectIndex).getLeft(), 0);
                    }
                });
            }
        }


    }


    /**
     * 设置显示选中标识
     *
     * @param selectIndex
     */
    private void showSelectIcon(final int selectIndex) {
        for (int i = 0; i < zhouqi_status_layout.getChildCount(); i++) {
            View selectIcon = zhouqi_status_layout.getChildAt(i).findViewById(R.id.zhouqi_select_icon);
            WidgetUtils.setVisible(selectIcon, i == selectIndex);
        }
    }

    /**
     * 周期任务详情数据绑定
     *
     * @param bean
     */
    private void bindDataZhouqiInfo(TaskPriodBean bean) {
        if (bean == null) return;
        StringBuffer zhouqi = new StringBuffer();

        zhouqi.append(DataUtils.getTimeFormat(bean.getStartTime(), "yyyy.MM.dd"));
        zhouqi.append(" ~ ");
        zhouqi.append(DataUtils.getTimeFormat(bean.getEndTime(), "yyyy.MM.dd"));

        if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_ING) {
            zhouqi.append("(剩余");
            zhouqi.append((DateTimeUtil.daysBetween(String.valueOf(DateUtil.getDateline()), bean.getEndTime() + "000") + 1));
            zhouqi.append("天）");
        }
        WidgetUtils.setText(task_zhouqi, zhouqi.toString());


        if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_COMPLETED) {//已完成
            task_qingkuang.setText("当期已完成（刷卡" + bean.getTimeDone() + "笔、" + getShowNumber(bean.getValueDone()) + "元）");
        } else if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_NOT_COMPLETED) {//未完成
            task_qingkuang.setText("当期未完成（刷卡" + bean.getTimeDone() + "笔、" + getShowNumber(bean.getValueDone()) + "元）");
        } else {

            float needMoney = bean.getPosValue() - bean.getValueDone();
            if (bean.getPosTime() > 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() == 0) {//   a)任务只有刷卡次数限制，显示文案格式“还需刷卡{2}笔”，{}内为变量
                task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔");
            } else if (bean.getPosTime() == 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() == 0) {// b)任务只有刷卡总金额限制，显示文案格式“还需刷卡{8000}元”，{}内为变量
                task_qingkuang.setText("还需刷卡" + getShowNumber(bean.getPosValue() - bean.getValueDone()) + "元");
            } else if (bean.getPosTime() > 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() == 0) {//  c)任务既有刷卡次数又有刷卡总金额限制，显示文案格式“还需刷卡{2}笔、{8000}元”，{}内为变量
                if (needMoney > 0) {
                    task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + getShowNumber(bean.getPosValue() - bean.getValueDone()) + "元");
                } else {
                    task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔");
                }

            } else if (bean.getPosTime() > 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() > 0) {// d)任务既有刷卡次数又有每次刷卡金额限制，显示文案格式“还需刷卡{2}笔、每笔{399}元”，{}内为变量
                task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
            } else if (bean.getPosTime() > 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() > 0) {// e)任务既有刷卡次数又有刷卡总金额限制还有每次刷卡金额限制，显示文案格式“还需刷卡{2}笔、{8000}元、每笔{399}元”，{}内为变量
                if (needMoney > 0) {
                    task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + getShowNumber(bean.getPosValue() - bean.getValueDone()) + "元、每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
                } else {
                    task_qingkuang.setText("还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + "每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
                }

            } else if (bean.getPosTime() == 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() > 0) {// e)只有单笔金额限制
                task_qingkuang.setText("每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
            } else {
                task_qingkuang.setText("");
            }

        }

        if (!DataTools.isEmpty(bean.getProgresses())) {
            task_progress_layout.setVisibility(View.VISIBLE);

            View titleView = task_progress_layout.getChildAt(0);
            task_progress_layout.removeAllViews();
            task_progress_layout.addView(titleView);

            List<TaskPriodProgressesBean> progressList = bean.getProgresses();
            for (int j = 0; j < progressList.size(); j++) {
                SwingCardZhouqiGroupLayout view = new SwingCardZhouqiGroupLayout(mContext);
                view.bindData(progressList.get(j));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                task_progress_layout.addView(view, p);
            }
        } else {
            task_progress_layout.setVisibility(View.GONE);
        }


    }


    private String getShowNumber(float number) {
        return DataUtils.getShowNumber(number);
    }

}
