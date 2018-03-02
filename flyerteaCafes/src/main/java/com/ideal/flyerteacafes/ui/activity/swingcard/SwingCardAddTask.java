package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by fly on 2017/4/12.
 */
@ContentView(R.layout.activity_swingcard_addtask)
public class SwingCardAddTask extends BaseActivity {

    private static final int KEY_REQUEST_TASKNAME = 1, KEY_REQUEST_MANALLYADD = 2;
    private TaskNameBean taskNameBean;

    /**
     * 任务名称
     **/
    @ViewInject(R.id.mltcri_task_name_layout)
    LtctriLayout mltcri_task_name_layout;
    /**
     * 卡号后4位
     **/
    @ViewInject(R.id.mltre_card_number_layout)
    LtreLayout mltre_card_number_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getData(getIntent());
    }

    @Event({R.id.toolbar_left, R.id.mll_manually_add, R.id.mltcri_task_name_layout, R.id.mbtn_add})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left://取消
                finish();
                break;
            case R.id.mltcri_task_name_layout://任务名称
                jumpActivityForResult(SwingCardTaskName.class, null, KEY_REQUEST_TASKNAME);
                break;
            case R.id.mll_manually_add://手动添加
                jumpActivityForResult(SwingCardManallyAdd.class, null, KEY_REQUEST_MANALLYADD);
                break;
            case R.id.mbtn_add://添加任务
                requestAdd();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == KEY_REQUEST_TASKNAME) {
                getData(data);
            } else if (requestCode == KEY_REQUEST_MANALLYADD) {
                jumpActivitySetResult(null);
            }
        }
    }


    private void getData(Intent data) {
        taskNameBean = (TaskNameBean) data.getSerializableExtra("data");
        if (taskNameBean != null)
            mltcri_task_name_layout.setTextByCt(taskNameBean.getCardMissionTitle());
    }


    /**
     * 添加任务
     */
    private void requestAdd() {
        if (taskNameBean == null) {
            ToastUtils.showToast("请先选择任务名称");
            return;
        }

        if (!TextUtils.isEmpty(mltre_card_number_layout.getTextByRe())) {
            if (mltre_card_number_layout.getTextByRe().length() != 4) {
                ToastUtils.showToast("卡号后四位不符合规则");
                return;
            }
        }

        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ADD_TASK);
        params.addQueryStringParameter("datatype", "sysmission");
        HashMap<String, Object> map = new HashMap<>();
        map.put("bankId", taskNameBean.getBankId());
        map.put("cardMissionDesc", taskNameBean.getCardMissionDesc());
        map.put("missionId", taskNameBean.getCardMissionId());
        map.put("cardMissionTitle", taskNameBean.getCardMissionTitle());
        map.put("channelId", taskNameBean.getChannelId());
        map.put("prefix", mltre_card_number_layout.getTextByRe());
        map.put("startTime", taskNameBean.getStartTime());
        map.put("endTime", taskNameBean.getEndTime());
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
