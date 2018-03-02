package com.ideal.flyerteacafes.ui.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SearchKeyAdapter;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearch;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadSearchPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.ThreadSearchFragment;
import com.ideal.flyerteacafes.ui.view.SerachEdittext;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2016/12/16.
 */
@ContentView(R.layout.activity_thread_search)
public class ThreadSearchActivity extends MVPBaseActivity<IThreadSearch, ThreadSearchPresenter> implements IThreadSearch {

    @ViewInject(R.id.thread_search_edittext)
    SerachEdittext thread_search_edittext;
    @ViewInject(R.id.thread_search_history_lv)
    ListView thread_search_history_lv;
    @ViewInject(R.id.thread_search_result_layout)
    FrameLayout thread_search_result_layout;
    @ViewInject(R.id.thread_search_result_null_layout)
    View thread_search_result_null_layout;

    private FragmentManager fm;
    private ThreadSearchFragment threadSearchFragment;
    private SearchKeyAdapter adapter;

    public static final String BUNDLE_FID_KEY = "fid";


    public static void toSearchReport(Context context) {
        Intent intent = new Intent(context, ThreadSearchActivity.class);
        intent.putExtra(IntentKey.ThreadSearch.BUNDLE_KEY, IntentKey.ThreadSearch.TYPE_REPORT);
        context.startActivity(intent);
    }

    public static void toSearchrAiders(Context context) {
        Intent intent = new Intent(context, ThreadSearchActivity.class);
        intent.putExtra(IntentKey.ThreadSearch.BUNDLE_KEY, IntentKey.ThreadSearch.TYPE_RAIDERS);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MobclickAgent.onEvent(this, FinalUtils.EventId.search);
        showContent(thread_search_history_lv);
        fm = getSupportFragmentManager();
        setSearchFragment();

        thread_search_edittext.setHint("输入搜索字词");


        thread_search_edittext.setISearchClick(new SerachEdittext.ISearchClick() {
            @Override
            public void onSearchClick(String text) {
                threadSearchFragment.setSearchKw(text);
                showContent(thread_search_result_layout);
                mPresenter.saveHistory(text);
            }
        });


        thread_search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showContent(thread_search_history_lv);
                if (i + i2 == 0) {
                    mPresenter.getHistoryList();
                } else {
                    mPresenter.requestAssocword(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        thread_search_edittext.setIClickSearch(new SerachEdittext.IClickSearch() {
            @Override
            public void onClickSearch(String content) {
                if (!TextUtils.isEmpty(content))
                    threadSearchFragment.setSearchKw(content);
            }
        });

        mPresenter.init(this);

    }

    @Override
    protected ThreadSearchPresenter createPresenter() {
        return new ThreadSearchPresenter();
    }


    /**
     * 设置显示的内容view
     *
     * @param view
     */
    private void showContent(View view) {
        thread_search_history_lv.setVisibility(View.GONE);
        thread_search_result_layout.setVisibility(View.GONE);
        thread_search_result_null_layout.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 搜索的页面
     */
    private void setSearchFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        threadSearchFragment = (ThreadSearchFragment) fm.findFragmentByTag("threadSearchFragment");
        if (threadSearchFragment == null) {
            threadSearchFragment = new ThreadSearchFragment();
            transaction.add(R.id.thread_search_result_layout, threadSearchFragment, "threadSearchFragment");
        } else {
            transaction.show(threadSearchFragment);
        }
        transaction.commit();
    }

    /**
     * 搜索后，是否有结果
     *
     * @param bol
     */
    public void searchResultIsNull(boolean bol) {
        if (bol) {
            showContent(thread_search_result_null_layout);
        } else {
            showContent(thread_search_result_layout);
        }
    }


    /**
     * 历史记录或联想关键词
     *
     * @param datas
     */
    @Override
    public void callBackListData(List<SearchHistoryBean> datas, final String searchKey) {
        if (adapter == null) {
            adapter = new SearchKeyAdapter(this, datas, R.layout.listview_search_history_layout);
            thread_search_history_lv.setAdapter(adapter);
        }
        adapter.setSearchKey(searchKey);
    }

    @Override
    public void setEtText(String text) {
        if (text == null) return;
        thread_search_edittext.setText(text);
        thread_search_edittext.setSelection(text.length());
        threadSearchFragment.setSearchKw(text);
        showContent(thread_search_result_layout);
    }

    @Event(R.id.hotel_serach_cancle)
    private void click(View v) {
        finish();
    }

    @Event(value = R.id.thread_search_history_lv, type = AdapterView.OnItemClickListener.class)
    private void gridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.itemClick(position);
    }
}
