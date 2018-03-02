package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.BankAdapter;
import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.entity.CardGroupBean;
import com.ideal.flyerteacafes.ui.sortlistview.SideBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by fly on 2017/4/13.
 */

public class BankPopupwindow extends TopPopupwindow {

    public interface IItemClick {

        void itemClick(BankBean bean);
    }

    IItemClick iItemClick;

    public void setiItemClick(IItemClick iItemClick) {
        this.iItemClick = iItemClick;
    }


    List<BankBean> mDatas;

    @ViewInject(R.id.listview)
    ListView listView;
    @ViewInject(R.id.sidrbar)
    SideBar sidrbar;

    public BankPopupwindow(Context context) {
        super(context);
        ViewTools.setViewSize(DensityUtil.dip2px(400), mTopView);

        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                for (int i = 0; i < mDatas.size(); i++) {
                    if (TextUtils.equals(s, mDatas.get(i).getIndex())) {
                        listView.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected int setTopViewLayoutId() {
        return R.layout.pop_bank;
    }

    public void bindData(List<BankBean> datas) {
        mDatas = datas;
        BankAdapter adapter = new BankAdapter(mContext, datas, R.layout.item_bank);
        listView.setAdapter(adapter);
    }

    @Event(value = R.id.listview, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        if (iItemClick != null) {
            iItemClick.itemClick(mDatas.get(position));
        }
        showEndAnimation();
    }
}
