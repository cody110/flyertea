package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SearchHistoryAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.SearchHistoryHelper;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.entity.TakeNotesBean;
import com.ideal.flyerteacafes.ui.controls.XCFlowLayout;
import com.ideal.flyerteacafes.ui.interfaces.IFlyCallBack;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchHotkeyView extends RelativeLayout {

    private Context context;
    private XCFlowLayout hotkeyView;
    private ListView searchHistoryList;
    private SearchHistoryHelper historyHelper;
    private List<SearchHistoryBean> historyList;
    private SearchHistoryAdapter historyAdapter;

    private boolean isSameFlag = true;// 搜索字符串，是否相同


    public SearchHotkeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchHotkeyView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initView(context);
        initListener();
        initData();
        initWidget();
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_search_hotkey, this);
        this.setBackgroundColor(getResources().getColor(R.color.app_bg_grey));
        hotkeyView = (XCFlowLayout) findViewById(R.id.view_search_hotkey_flowlayout);
        searchHistoryList = (ListView) findViewById(R.id.view_search_hotkey_history_listview);
        requestHotkey();
    }

    private void initData() {
        historyHelper = new SearchHistoryHelper();
        historyList = historyHelper.getSearchHistoryList();
        if (historyList == null)
            historyList = new ArrayList<SearchHistoryBean>();
        if (historyList.size() > 0) {
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setSearchName(getResources().getString(R.string.search_clear_history));
            historyList.add(bean);
        }

    }

    private void initWidget() {
        historyAdapter = new SearchHistoryAdapter(context, historyList, true);
        searchHistoryList.setAdapter(historyAdapter);
    }

    private void initListener() {
        searchHistoryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if (historyList.size() == arg2 + 1) {
                    historyHelper.deleteSearchHistory();
                    historyList.clear();
                    historyAdapter.notifyDataSetChanged();
                } else {
                    SearchHistoryBean bean = (SearchHistoryBean) historyAdapter
                            .getItem(arg2);
                    EventBus.getDefault().post(bean);
                }
            }

        });
    }

    /**
     * 传递搜索记录
     */
    public void getSearchHistoryBean(SearchHistoryBean event) {
        if (historyList.size() == 0) {
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setSearchName(getResources().getString(R.string.search_clear_history));
            historyList.add(bean);
        }
        String searchStr = event.getSearchName();
        for (int i = 0; i < historyList.size(); i++) {
            if (historyList.get(i).getSearchName().equals(searchStr)) {
                isSameFlag = false;
                break;
            }
        }
        if (isSameFlag) {
            historyHelper.insertSearchHistory(event);
            historyList.add(0, event);
            if (historyList.size() > 11) {
                historyList.remove(10);
            }
            historyAdapter.notifyDataSetChanged();
        } else {
            isSameFlag = true;
        }

    }

    /**
     * 热词接口
     */
    private void requestHotkey() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOTKEY), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                String[] hotkeyArray = JsonAnalysis.getHotkey(result);
                if(hotkeyArray!=null&&hotkeyArray.length>0)
                    initChildViews(hotkeyArray);
            }
        });
    }

    /**
     * 流标签设置数据
     *
     * @param hotkeyArray
     */
    @SuppressWarnings("deprecation")
    private void initChildViews(final String[] hotkeyArray) {
        // TODO Auto-generated method stub
        float scale = SharedPreferencesString.getInstances(context).getFloatToKey("scale");
        MarginLayoutParams lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = (int) (20 * scale);
        lp.rightMargin = (int) (20 * scale);
        lp.topMargin = (int) (5 * scale);
        lp.bottomMargin = (int) (5 * scale);
        for (int i = 0; i < hotkeyArray.length; i++) {
            final TextView view = new TextView(context);
            String hotkey = hotkeyArray[i];
            view.setText(hotkey);
            view.setTag(i);
            int width = (int) (30 * scale);
            int height = (int) (15 * scale);
            view.setPadding(width, height, width, height);
            view.setTextColor(getResources().getColor(R.color.app_body_black));
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.subscribe_gridview_item_bg_to));
            hotkeyView.addView(view, lp);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    int index = (int) view.getTag();
                    SearchHistoryBean bean = new SearchHistoryBean();
                    bean.setSearchName(hotkeyArray[index]);
                    EventBus.getDefault().post(bean);
                }
            });
        }
    }

}
