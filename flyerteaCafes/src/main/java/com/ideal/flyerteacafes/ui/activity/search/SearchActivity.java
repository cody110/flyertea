package com.ideal.flyerteacafes.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SearchAdapter;
import com.ideal.flyerteacafes.adapters.SearchHistoryAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.SearchBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.ideal.flyerteacafes.ui.view.SearchHotkeyView;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 搜索页面
 *
 * @author fly
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements
        OnHeaderRefreshListener, OnFooterRefreshListener {

    @ViewInject(R.id.search_edit)
    private EditText searchEdit;
    @ViewInject(R.id.search_list)
    private ListView searchListView;
    @ViewInject(R.id.search_refreshview)
    private PullToRefreshView mPullToRefreshView;
    @ViewInject(R.id.search_history_layout)
    private View searchAssocwordLayout;
    @ViewInject(R.id.search_hotkey_layout)
    private SearchHotkeyView hotkeyView;
    @ViewInject(R.id.search_assocword_list)
    private ListView searchAssocwordList;
    @ViewInject(R.id.include_left_title_text)
    private TextView titleView;
    @ViewInject(R.id.include_left_title_right_btn)
    private View rightBtn;
    @ViewInject(R.id.include_left_title_right_btn_img)
    private View rightImg;

    private List<SearchBean> searchList = new ArrayList<SearchBean>();
    private SearchAdapter adapter;
    private SearchHistoryAdapter assocwordAdapter;// 就用这个装联想词接口数据

    private int page = 1;
    private String searchStr;

    private float scale;

    private List<SearchHistoryBean> assocwordList;

    private boolean textChangedFlag = true;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            searchEdit.setText(searchStr);
            searchEdit.setSelection(searchStr.length());
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        rightImg.setVisibility(View.GONE);
        initLisenner();
        scale = SharedPreferencesString.getInstances(this).getFloatToKey(
                "scale");
        adapter = new SearchAdapter(SearchActivity.this, searchList);
        searchListView.setAdapter(adapter);
        initWeight();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            int pos = data.getIntExtra("position", -1);
            if (pos != -1) {
                this.searchList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initLisenner() {
        EventBus.getDefault().register(this);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        searchEdit.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                hotkeyView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (arg0.toString().equals("")) {
                    hotkeyView.setVisibility(View.VISIBLE);
                    searchAssocwordLayout.setVisibility(View.GONE);
                } else {
                    if (textChangedFlag) {
                        requestAssocword(arg0.toString());
                        hotkeyView.setVisibility(View.GONE);
                    } else {
                        textChangedFlag = true;
                    }
                }
            }
        });
        hotkeyView.setOnClickListener(null);
    }

    private void initWeight() {
        rightBtn.setVisibility(View.VISIBLE);
        titleView.setText(getString(R.string.title_name_search));
    }

    @Event(R.id.include_left_title_back_layout)
    private void closePage(View v) {
        finish();
    }

    @Event(R.id.include_left_title_right_btn)
    private void rightClick(View v) {

    }

    // 帖子详情页
    @Event(value = R.id.search_list, type = AdapterView.OnItemClickListener.class)
    private void toPostDetailsActivity(AdapterView<?> parent, View view,
                                       int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(searchList.get(position).getTid()));
        jumpActivityForResult(ThreadActivity.class, bundle, 0);
    }

    @Event(value = R.id.search_assocword_list, type = AdapterView.OnItemClickListener.class)
    private void toSearch(AdapterView<?> parent, View view, int position, long id) {
        if (assocwordList.size() > 0) {
            textChangedFlag = false;
            searchStr = assocwordList.get(position).getSearchName();
            searchOnClick();
            handler.sendEmptyMessage(12);
        }
    }

    /**
     * 监听右下角确定键
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /* 隐藏软键盘 */
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this
                        .getCurrentFocus().getWindowToken(), 0);
            }
            page = 1;
            searchStr = searchEdit.getText().toString().trim();
            searchOnClick();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub
        page++;
        requestSearch();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub
        page = 1;
        requestSearch();
    }

    /**
     * 联想词接口
     *
     * @param value
     */
    private void requestAssocword(String value) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ASSOCWORD);
        params.addQueryStringParameter("kw", value);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                assocwordList = JsonAnalysis.getSearchAssocwordList(result);

                if (!DataUtils.isEmpty(assocwordList)) {
                    if (assocwordAdapter == null) {
                        assocwordAdapter = new SearchHistoryAdapter(SearchActivity.this,
                                assocwordList, false);
                        searchAssocwordList.setAdapter(assocwordAdapter);
                    } else {
                        assocwordAdapter.refreshData(assocwordList);
                    }
                    searchAssocwordLayout.setVisibility(View.VISIBLE);
                } else {
                    searchAssocwordLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 请求搜索接口
     */
    private void requestSearch() {
        if (!!DataUtils.isEmpty(searchStr)) {
            if (!DataUtils.isEmpty(searchList) && adapter != null) {
                searchList.clear();
                adapter.notifyDataSetChanged();
            }
            return;
        }
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEARCH);
        params.addQueryStringParameter("kw", searchStr);
        params.addQueryStringParameter("perpage", page + "");
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_SEARCH, SearchBean.class) {

            @Override
            public void flySuccess(ListDataBean result) {
                int position = 0;
                List<SearchBean> list = result.getDataList();

                if (page == 1) {
                    searchList.clear();
                    if (list == null || list.isEmpty())
                        BToast("没有搜索到帖子");
                }
                position = searchList.size();

                if (list != null)
                    searchList.addAll(list);
                adapter.notifyDataSetChanged();
                if (page != FinalUtils.START_PAGE)
                    searchListView.setSelectionFromTop(position, (int) (FinalUtils.FROM_TOP * preferences.getScale()));
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        });


        MobclickAgent.onEvent(this, FinalUtils.EventId.search);// 搜索次数
    }

    /**
     * SearchHotkeyView点击了搜索
     *
     * @param event
     */
    public void onEventMainThread(SearchHistoryBean event) {
        textChangedFlag = false;
        searchStr = event.getSearchName();
        searchOnClick();
        handler.sendEmptyMessage(12);
    }

    /**
     * 点击了搜索，执行
     */
    private void searchOnClick() {
        SearchHistoryBean bean = new SearchHistoryBean();
        bean.setSearchName(searchStr);
        hotkeyView.getSearchHistoryBean(bean);
        hotkeyView.setVisibility(View.GONE);
        searchAssocwordLayout.setVisibility(View.GONE);
        requestSearch();
    }

    /**
     * 监听back键
     */
    @Override
    public void onBackPressed() {
        if (searchAssocwordLayout.getVisibility() == View.VISIBLE) {
            searchAssocwordLayout.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

}
