package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.calendarlistview.library.CalendarDay;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISwingCardManallyAdd;
import com.ideal.flyerteacafes.ui.activity.presenter.SwingCardManallyAddPresenter;
import com.ideal.flyerteacafes.ui.layout.TaskCycleLayout;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 添加任务：手动添加
 * Created by fly on 2017/4/12.
 */
@ContentView(R.layout.activity_swingcard_manallyadd)
public class SwingCardManallyAdd extends MVPBaseActivity<ISwingCardManallyAdd, SwingCardManallyAddPresenter> implements ISwingCardManallyAdd {

    /**
     * 所属银行
     **/
    @ViewInject(R.id.mltcri_brank_layout)
    LtctriLayout mltcri_brank_layout;
    /**
     * 任务名称
     **/
    @ViewInject(R.id.mltre_task_name_layout)
    LtreLayout mltre_task_name_layout;
    /**
     * 任务描述
     **/
    @ViewInject(R.id.mltre_task_describle_layout)
    LtreLayout mltre_task_describle_layout;
    /**
     * 卡号后4位
     **/
    @ViewInject(R.id.mltre_card_number_layout)
    LtreLayout mltre_card_number_layout;
    /**
     * 编辑
     **/
    @ViewInject(R.id.mtv_edit)
    TextView mtv_edit;

    @ViewInject(R.id.manallyadd_cycle_layout)
    LinearLayout manallyadd_cycle_layout;

    @ViewInject(R.id.mbtn_add_period)
    View mbtn_add_period;

    private static final int flagDate = 1, flagBank = 2;
    private int clickIndex;
    private BankBean bankBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        addTaskCycleLayout();
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.mltcri_brank_layout, R.id.mbtn_add_period, R.id.mtv_edit})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://取消
                finish();
                break;
            case R.id.toolbar_right://完成
                requestAdd();
                break;
            case R.id.mltcri_brank_layout://选择银行
                jumpActivityForResult(BankActivity.class, null, flagBank);
                break;
            case R.id.mbtn_add_period://新建周期
                addTaskCycleLayout();
                break;

            case R.id.mtv_edit:
                if (TextUtils.equals(mtv_edit.getText().toString(), "编辑")) {
                    setVisibleTaskCycleDeleteBtn(true);
                    mtv_edit.setText("取消");
                    mbtn_add_period.setVisibility(View.GONE);
                } else {
                    setVisibleTaskCycleDeleteBtn(false);
                    mtv_edit.setText("编辑");
                    mbtn_add_period.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected SwingCardManallyAddPresenter createPresenter() {
        return new SwingCardManallyAddPresenter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case flagDate:
                    CalendarDay startDay = data.getParcelableExtra(IntentKey.DataChoose.START_TIME);
                    CalendarDay endDay = data.getParcelableExtra(IntentKey.DataChoose.END_TIME);

                    long startTime = startDay.setDayStart().getDate().getTime();
                    long endTime = endDay.setDayEnd().getDate().getTime();


                    boolean isSame = false;
                    for (int i = 0; i < manallyadd_cycle_layout.getChildCount(); i++) {
                        TaskCycleLayout layout = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(i);
                        if (layout.getStartTime() != 0) {
                            if (startTime >= layout.getStartTime() && startTime <= layout.getEndTime()) {
                                isSame = true;
                                break;
                            }
                            if (endTime >= layout.getStartTime() && endTime <= layout.getEndTime()) {
                                isSame = true;
                                break;
                            }
                            if (startTime <= layout.getStartTime() && endTime >= layout.getEndTime()) {
                                isSame = true;
                                break;
                            }
                        }
                    }
                    if (isSame) {
                        DialogUtils.remindDialog(this, "日期已存在，请勿重复选择");
                    } else {
                        TaskCycleLayout taskCycleLayout = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(clickIndex);
                        taskCycleLayout.setTime(startDay, endDay);
                    }
                    break;
                case flagBank:
                    bankBean = (BankBean) data.getSerializableExtra("data");
                    mltcri_brank_layout.setTextByCt(bankBean.getBankName());
                    break;
            }
        }
    }

    /**
     * 添加任务周期
     */
    private void addTaskCycleLayout() {


        TaskCycleLayout taskCycleLayout = new TaskCycleLayout(this);
        taskCycleLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, DensityUtil.dip2px(12));
        taskCycleLayout.setPos(manallyadd_cycle_layout.getChildCount());
        if (manallyadd_cycle_layout.getChildCount() > 0) {
            TaskCycleLayout firstView = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(0);
            taskCycleLayout.setHint(firstView.mltre_expense_layout.getTextByRe(), firstView.mltre_money_layout.getTextByRe(), firstView.mltre_total_money_layout.getTextByRe());
        }
        taskCycleLayout.setIClickListener(new TaskCycleLayout.IClickListener() {
            @Override
            public void onClick(View v, int pos) {
                switch (v.getId()) {
                    case R.id.mltcri_frist_period_layout:
                        clickIndex = pos;
                        jumpActivityForResult(DateChooiceActivity.class, null, flagDate);
                        break;

                    case R.id.delete_btn:
                        deleteTaskCycleLayout(pos);
                        break;
                }
            }
        });
        manallyadd_cycle_layout.addView(taskCycleLayout, params);
    }

    /**
     * 删除任务周期
     *
     * @param pos
     */
    private void deleteTaskCycleLayout(int pos) {
        if (manallyadd_cycle_layout.getChildCount() == 1) {
            ToastUtils.showToast("任务不能没有周期");
            return;
        }
        manallyadd_cycle_layout.removeViewAt(pos);
        for (int i = 0; i < manallyadd_cycle_layout.getChildCount(); i++) {
            TaskCycleLayout taskCycleLayout = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(i);
            taskCycleLayout.setPos(i);
        }
    }

    /**
     * 设置显示隐藏btns
     *
     * @param bol
     */
    private void setVisibleTaskCycleDeleteBtn(boolean bol) {
        for (int i = 0; i < manallyadd_cycle_layout.getChildCount(); i++) {
            TaskCycleLayout taskCycleLayout = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(i);
            WidgetUtils.setVisible(taskCycleLayout.delete_btn, bol);
        }
    }


    /**
     * 添加任务
     */
    private void requestAdd() {

        if (bankBean == null) {
            ToastUtils.showToast("请选择所属银行");
        } else if (TextUtils.isEmpty(mltre_task_name_layout.getTextByRe())) {
            ToastUtils.showToast("请填写任务名称");
        } else if (TextUtils.isEmpty(mltre_task_describle_layout.getTextByRe())) {
            ToastUtils.showToast("请填写描述名称");
        } else if (!TextUtils.isEmpty(mltre_card_number_layout.getTextByRe()) && mltre_card_number_layout.getTextByRe().length() != 4) {
            ToastUtils.showToast("卡号后四位不符合规则");
        } else {

            List periods = new ArrayList();
            for (int i = 0; i < manallyadd_cycle_layout.getChildCount(); i++) {
                TaskCycleLayout taskCycleLayout = (TaskCycleLayout) manallyadd_cycle_layout.getChildAt(i);
                if (!taskCycleLayout.isDataOk()) {
                    ToastUtils.showToast("请填写完整任务周期");
                    return;
                }
                periods.add(taskCycleLayout.getPeriod());
            }


            showDialog();
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ADD_TASK);
            params.addQueryStringParameter("datatype", "mission");
            HashMap<String, Object> map = new HashMap<>();
            map.put("bankId", bankBean.getBankId());
            map.put("cardMissionDesc", mltre_task_describle_layout.getTextByRe());
            map.put("cardMissionTitle", mltre_task_name_layout.getTextByRe());
            map.put("prefix", mltre_card_number_layout.getTextByRe());


            map.put("periods", periods);

            params.setBodyJson(map);
            XutilsHttp.Post(params, new MapCallback() {
                @Override
                public void flySuccess(MapBean result) {
                    if (TextUtils.isEmpty(result.getData().get("missionid"))) {
                        ToastUtils.showToast("添加失败");
                    } else {
                        ToastUtils.showToast("添加成功");
                        UserManger.getInstance().savaMissions(UserManger.getInstance().getMissions() + 1);
                        jumpActivitySetResult(null);
                    }
                }

                @Override
                public void flyFinished() {
                    super.flyFinished();
                    dialogDismiss();
                }
            });

        }
    }


}
