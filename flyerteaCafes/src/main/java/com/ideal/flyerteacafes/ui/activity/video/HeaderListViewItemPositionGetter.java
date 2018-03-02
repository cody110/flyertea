package com.ideal.flyerteacafes.ui.activity.video;

import android.view.View;
import android.widget.ListView;

import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;

/**
 * Created by fly on 2018/2/4.
 */

public class HeaderListViewItemPositionGetter extends ListViewItemPositionGetter {

    private ListView mListView;
    private int headerCount = 0;

    public HeaderListViewItemPositionGetter(ListView listView) {
        super(listView);
        this.mListView = listView;
        headerCount = listView.getHeaderViewsCount();
    }

    @Override
    public View getChildAt(int position) {
        return mListView.getChildAt(position + headerCount);
    }

    @Override
    public int indexOfChild(View view) {
        return mListView.indexOfChild(view) - headerCount;
    }

    @Override
    public int getChildCount() {
        return mListView.getChildCount() - headerCount;
    }

    @Override
    public int getLastVisiblePosition() {
        return mListView.getLastVisiblePosition() - headerCount;
    }

    @Override
    public int getFirstVisiblePosition() {
        return mListView.getFirstVisiblePosition() - headerCount;
    }
}
