package com.ideal.flyerteacafes.ui.activity.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SearchKeyAdapter;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.SearchHistoryHelper;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2018/1/19.
 * 输入状态，联想搜索
 */

public class AssociativeSearchFragment extends BaseFragment {


    @ViewInject(R.id.thread_search_history_lv)
    ListView thread_search_history_lv;


    SearchKeyAdapter adapter;
    List<SearchHistoryBean> datas = new ArrayList<>();
    IThreadSearchActivity iAssociativeSearch;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iAssociativeSearch = (IThreadSearchActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associative_search, container, false);
        x.view().inject(this, view);
        initPage();
        return view;
    }

    private void initPage() {
        thread_search_history_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (iAssociativeSearch != null)
                    iAssociativeSearch.toSearchResult(adapter.getItem(position).getSearchName());
            }
        });
    }


    /**
     * 联想搜索关键词
     *
     * @param key
     */
    public void setSearchKey(String key) {
        requestAssocword(key);
    }

    /**
     * 联想词接口
     *
     * @param value
     */
    public void requestAssocword(final String value) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ASSOCWORD);
        params.addQueryStringParameter("kw", value);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                List<SearchHistoryBean> assocwordList = JsonAnalysis.getSearchAssocwordList(result);
                datas.clear();
                datas.addAll(assocwordList);
                bindAdapter(datas, value);
            }
        });
    }


    /**
     * 绑定
     *
     * @param searchKey
     */
    private void bindAdapter(List<SearchHistoryBean> datas, String searchKey) {
        if (adapter == null) {
            adapter = new SearchKeyAdapter(mActivity, datas, R.layout.listview_search_history_layout);
            thread_search_history_lv.setAdapter(adapter);
        }
        adapter.setSearchKey(searchKey);
    }

}
