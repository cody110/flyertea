package com.ideal.flyerteacafes.utils.tools;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ideal.flyerteacafes.utils.LogFly;

public class ViewTools {


    public static void editTextDelete(EditText write_thread_content) {
        if (!TextUtils.isEmpty(write_thread_content.getText().toString())) {
            write_thread_content.getText().delete(write_thread_content.getSelectionStart() - 1, write_thread_content.getSelectionStart());
        }
    }

    private int getGridviewItemHeight(GridView gridView) {
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) return 0;
        View listItem = adapter.getView(0, null, gridView);
        try {
            listItem.measure(0, 0);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        int itemHeight = listItem.getMeasuredHeight();
        return itemHeight;
    }

    //获取listview高度
    public static int getListviewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            LogFly.e("item" + i + "  " + listItem.getMeasuredHeight());
//            LogFly.e("height" + i + "" + listItem.getHeight());
        }
        return totalHeight;
    }

    /**
     * 设置listview的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        int totalHeight = 0;

        totalHeight = getListviewHeight(listView);
        if (listView.getHeight() == totalHeight)
            return;

        ListAdapter listAdapter = listView.getAdapter();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (totalHeight == 0) {
            params.height = 0;
        } else {
            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        }
    }


    // 设置视图大小
    public static void setViewSize(int width, int height, float scale, View view) {
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = (int) (width * scale);
        lp.height = (int) (height * scale);
        view.setLayoutParams(lp);
    }

    public static void setViewSize(int height, View view) {
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }


    public static void setViewSize(int width, int height, View view) {
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        lp.width = width;
        view.setLayoutParams(lp);
    }


    /**
     * 检测快读点击,现在判断是两次点击 间隔小于500毫秒
     *
     * @param view
     * @return true如果间隔很短;
     */
    public static boolean checkQuickClick(View view) {

        Object obj = view.getTag(-1);
        long current = System.currentTimeMillis();
        if (obj != null && (obj instanceof Long)) {
            long last = (Long) obj;
            long distance = current - last;
            if (distance < 500) {
                return true;
            } else {
                view.setTag(-1, current);
            }
        } else {
            view.setTag(-1, current);
        }
        return false;
    }

}
