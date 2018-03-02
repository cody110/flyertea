package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by fly on 2017/5/24.
 */

public abstract class CommonBottomPopupwindow<T> extends BottomPopupwindow {


    public interface ISureOK<T> {
        void selectItem(T t, int pos);
    }

    ISureOK<T> iSureOK;

    public void setISureOK(ISureOK<T> iSureOK) {
        this.iSureOK = iSureOK;
    }

    @ViewInject(R.id.pop_login_wheelview)
    protected WheelView wheelView;
    @ViewInject(R.id.pop_include_title)
    private View pop_include_title;

    private List<T> datas;

    public CommonBottomPopupwindow(Context context, List<T> datas, ISureOK<T> iSureOK) {
        super(context);
        pop_include_title.setVisibility(View.VISIBLE);
        this.datas = datas;
        this.iSureOK = iSureOK;
        bindDatas(datas);
    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.popupwindow_question_layout;
    }

    @Event({R.id.pop_choose_citys_close_btn, R.id.pop_choose_citys_ok_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.pop_choose_citys_close_btn:
                dismiss();
                break;

            case R.id.pop_choose_citys_ok_btn:
                if (iSureOK != null)
                    iSureOK.selectItem(datas.get(wheelView.getCurrentItem()), wheelView.getCurrentItem());
                showEndAnimation();
                break;
        }
    }

    protected abstract void bindDatas(List<T> datas);
}
