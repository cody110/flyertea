package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.view.View;

import com.ideal.flyerteacafes.R;

import org.xutils.view.annotation.Event;

public class FlyHuiOneListPopupwindow extends TopPopupwindow {


    public FlyHuiOneListPopupwindow(Context context) {
        super(context);
    }

    @Override
    protected int setTopViewLayoutId() {
        return R.layout.pop_top_sort;
    }

    @Event({R.id.new_tv, R.id.hot_tv})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.new_tv:
                if (iSort != null)
                    iSort.sortType("dateline");
                dismiss();
                break;

            case R.id.hot_tv:
                if (iSort != null)
                    iSort.sortType("hot");
                dismiss();
                break;
        }
    }


    private ISort iSort;

    public void setISort(ISort iSort) {
        this.iSort = iSort;
    }

    public interface ISort {
        void sortType(String sortType);
    }

}
