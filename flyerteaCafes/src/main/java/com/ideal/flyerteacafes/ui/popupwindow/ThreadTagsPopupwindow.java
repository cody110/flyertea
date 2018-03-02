package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.ThreadTagActivity;
import com.ideal.flyerteacafes.ui.controls.XCFlowLayout;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by fly on 2017/11/30.
 */

public class ThreadTagsPopupwindow extends BottomPopupwindow {

    @ViewInject(R.id.thread_tags_flowlayout)
    private XCFlowLayout xcFlowLayout;

    public ThreadTagsPopupwindow(Context context) {
        super(context);
    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.pop_thread_tags;
    }

    public void initData(List<ThreadTagBean> threadTagBeanList) {
        initChildViews(threadTagBeanList);
    }

    /**
     * 流标签设置数据
     */
    @SuppressWarnings("deprecation")
    private void initChildViews(final List<ThreadTagBean> threadTagBeanList) {
        if (DataUtils.isEmpty(threadTagBeanList)) return;
        float scale = SharedPreferencesString.getInstances().getFloatToKey("scale");

        for (int i = 0; i < threadTagBeanList.size(); i++) {
            final TextView view = new TextView(mContext);
            final String hotkey = threadTagBeanList.get(i).getTagname();
            view.setText(hotkey);
            view.setTag(i);
            int width = (int) (30 * scale);
            int height = (int) (10 * scale);
            view.setPadding(width, height, width, height);
            view.setTextColor(mContext.getResources().getColor(R.color.app_bg_title));
            view.setBackgroundDrawable(mContext.getResources().getDrawable(
                    R.drawable.thread_tag_bg));
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = (int) (40 * scale);
            lp.topMargin = (int) (30 * scale);
            xcFlowLayout.addView(view, lp);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(mContext, ThreadTagActivity.class);
                    intent.putExtra("data", threadTagBeanList.get(finalI));
                    mContext.startActivity(intent);
                    dismiss();
                }
            });
        }
    }
}
