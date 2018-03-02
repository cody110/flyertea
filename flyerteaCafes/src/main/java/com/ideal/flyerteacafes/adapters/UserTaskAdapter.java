package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.TaskPriodBean;
import com.ideal.flyerteacafes.model.entity.UserTaskBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardTaskInteract;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardUpdateTaskProgress;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DateTimeUtil;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2017/4/14.
 */

public class UserTaskAdapter extends CommonAdapter<UserTaskBean> {
    public UserTaskAdapter(Context context, List<UserTaskBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final UserTaskBean taskNameBean) {


        TaskPriodBean bean = taskNameBean.getCurrentperiod();
        TextView task_qingkuang = holder.getView(R.id.task_time);


        if (isComplete) {
            holder.setBackgroundRes(R.id.task_time, R.color.grey);
        } else {
            holder.setBackgroundRes(R.id.task_time, R.color.app_bg_title);
        }

        if (!isComplete) {//TODO待完成

            if (bean == null) {
                StringBuffer sb = new StringBuffer();
                if (taskNameBean.getNextperiod() != null) {
                    sb.append(daysBetween(System.currentTimeMillis() + "", taskNameBean.getNextperiod().getStartTime() + "000"));
                    sb.append("天后开始");
                }
                task_qingkuang.setText(sb.toString());
            } else {
                if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_ING) {
                    float needMoney = bean.getPosValue() - bean.getValueDone();
                    String remainingTime = "剩余" + (daysBetween(System.currentTimeMillis() + "", (bean.getEndTime() + "000")) + "天，");
                    if (bean.getPosTime() > 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() == 0) {//   a)任务只有刷卡次数限制，显示文案格式“还需刷卡{2}笔”，{}内为变量
                        task_qingkuang.setText(remainingTime + "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔");
                    } else if (bean.getPosTime() == 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() == 0) {// b)任务只有刷卡总金额限制，显示文案格式“还需刷卡{8000}元”，{}内为变量
                        task_qingkuang.setText(remainingTime + "还需刷卡" + getShowNumber(needMoney) + "元");
                    } else if (bean.getPosTime() > 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() == 0) {//  c)任务既有刷卡次数又有刷卡总金额限制，显示文案格式“还需刷卡{2}笔、{8000}元”，{}内为变量
                        if (needMoney > 0) {
                            remainingTime += "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + getShowNumber(needMoney) + "元";
                        } else {
                            remainingTime += "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔";
                        }
                        task_qingkuang.setText(remainingTime);
                    } else if (bean.getPosTime() > 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() > 0) {// d)任务既有刷卡次数又有每次刷卡金额限制，显示文案格式“还需刷卡{2}笔、每笔{399}元”，{}内为变量
                        task_qingkuang.setText(remainingTime + "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
                    } else if (bean.getPosTime() > 0 && bean.getPosValue() > 0 && bean.getPertimeLimit() > 0) {// e)任务既有刷卡次数又有刷卡总金额限制还有每次刷卡金额限制，显示文案格式“还需刷卡{2}笔、{8000}元、每笔{399}元”，{}内为变量
                        if (needMoney > 0) {
                            remainingTime += "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + getShowNumber(needMoney) + "元、每笔" + getShowNumber(bean.getPertimeLimit()) + "元";
                        } else {
                            remainingTime += "还需刷卡" + (bean.getPosTime() - bean.getTimeDone()) + "笔、" + "每笔" + getShowNumber(bean.getPertimeLimit()) + "元";
                        }
                        task_qingkuang.setText(remainingTime);
                    } else if (bean.getPosTime() == 0 && bean.getPosValue() == 0 && bean.getPertimeLimit() > 0) {// e)只有单笔金额限制
                        task_qingkuang.setText(remainingTime + "每笔" + getShowNumber(bean.getPertimeLimit()) + "元");
                    } else {
                        task_qingkuang.setText(remainingTime.substring(0, remainingTime.length() - 1));
                    }
                } else if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_COMPLETED) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("当期已完成");
                    if (taskNameBean.getNextperiod() != null) {
                        sb.append(",下一期");
                        sb.append(daysBetween(System.currentTimeMillis() + "", taskNameBean.getNextperiod().getStartTime() + "000"));
                        sb.append("天后开始");
                    }
                    task_qingkuang.setText(sb.toString());
                } else if (bean.getDoneStatus() == TaskPriodBean.DONESTATUS_NOT_COMPLETED) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("当期未完成");
                    if (taskNameBean.getNextperiod() != null) {
                        sb.append(",下一期");
                        sb.append(daysBetween(System.currentTimeMillis() + "", taskNameBean.getNextperiod().getStartTime() + "000"));
                        sb.append("天后开始");
                    }
                    task_qingkuang.setText(sb.toString());
                } else {
                    task_qingkuang.setText("您还没有已结束的任务哦");
                }
            }
        } else {//TODO 已结束
            task_qingkuang.setText("完成" + taskNameBean.getPeriodDone() + "期，共" + taskNameBean.getPeriodnum() + "期");
        }


        if (TextUtils.isEmpty(taskNameBean.getPrefix()) || DataTools.getInteger(taskNameBean.getPrefix()) == 0) {
            holder.setText(R.id.task_title, taskNameBean.getCardMissionTitle());
        } else {
            holder.setText(R.id.task_title, "[" + taskNameBean.getPrefix() + "]" + taskNameBean.getCardMissionTitle());
        }

        holder.setText(R.id.task_des, taskNameBean.getCardMissionDesc());
        holder.setText(R.id.task_taolun, "1321个主题正在讨论...");
        holder.setText(R.id.btn_2, "更新进度");

        holder.setOnClickListener(R.id.btn_1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SwingCardTaskInteract.class);
                mContext.startActivity(intent);
            }
        });
        holder.setOnClickListener(R.id.btn_2, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SwingCardUpdateTaskProgress.class);
                intent.putExtra(SwingCardUpdateTaskProgress.BUNDLE_MISSIONID, taskNameBean.getMyCardMissionId());
                ((BaseActivity) mContext).startActivityForResult(intent, 0);
            }
        });

    }

    private boolean isComplete = false;

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
        notifyDataSetChanged();
    }

    public int daysBetween(String smdate, String bdate) {
        return DateTimeUtil.daysBetween(smdate, bdate) + 1;
    }


    private String getShowNumber(float number) {
        return DataUtils.getShowNumber(number);
    }

}
