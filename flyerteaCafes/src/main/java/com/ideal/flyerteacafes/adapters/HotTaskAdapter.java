package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardAddTask;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardTaskInteract;

import java.util.List;

/**
 * Created by fly on 2017/4/14.
 */

public class HotTaskAdapter extends CommonAdapter<TaskNameBean> {
    public HotTaskAdapter(Context context, List<TaskNameBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final TaskNameBean taskNameBean) {
        holder.setText(R.id.task_title, taskNameBean.getCardMissionTitle());
        holder.setText(R.id.task_des, taskNameBean.getCardMissionDesc());
        holder.setText(R.id.task_taolun, "1321个主题正在讨论...");
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
                if (listener != null) {
                    listener.toAddTask(taskNameBean);
                } else {
                    Intent intent = new Intent(mContext, SwingCardAddTask.class);
                    intent.putExtra("data", taskNameBean);
                    ((BaseActivity) mContext).startActivityForResult(intent, 0);
                }
            }
        });
    }

    private IClickListener listener;

    public HotTaskAdapter setIClickListener(IClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface IClickListener {
        void toAddTask(TaskNameBean taskNameBean);
    }
}
