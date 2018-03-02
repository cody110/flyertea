package com.ideal.flyerteacafes.ui.activity.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.dal.SearchHistoryHelper;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.FragmentMangerUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by fly on 2018/1/19.
 */

public class SearchResultFragment extends BaseFragment {


    public interface ISearchResultFragment {
        void notData();
    }

    @ViewInject(R.id.tab_thread)
    LinearLayout tab_thread;
    @ViewInject(R.id.tab_presentation)
    LinearLayout tab_presentation;
    @ViewInject(R.id.tab_raiders)
    LinearLayout tab_raiders;
    @ViewInject(R.id.tab_discount)
    LinearLayout tab_discount;
    @ViewInject(R.id.tab_user)
    LinearLayout tab_user;

    private String searchKey;
    FragmentMangerUtils fragmentMangerUtils;

    SearchHistoryHelper historyHelper = new SearchHistoryHelper();
    private View selectView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        x.view().inject(this, view);
        initPage();
        fragmentMangerUtils = new FragmentMangerUtils(getChildFragmentManager(), R.id.content_layout);
        click(tab_thread);
        return view;
    }


    @Event({R.id.tab_thread, R.id.tab_presentation, R.id.tab_raiders, R.id.tab_discount, R.id.tab_user})
    private void click(View view) {
        selectView = view;
        setSelectView(view);
        setSelectFragment(view.getId());
        mActivity.showDialog(getString(R.string.search_dialog_tip));
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        saveHistory(searchKey);
        if (selectView != null) {
            click(selectView);
        }
    }

    private void initPage() {
        setTabName(tab_thread, "帖子");
        setTabName(tab_presentation, "报告");
        setTabName(tab_raiders, "攻略");
        setTabName(tab_discount, "优惠");
        setTabName(tab_user, "用户");
    }

    /**
     * 设置tab
     *
     * @param tabView
     * @param name
     */
    private void setTabName(View tabView, String name) {
        TextView tv = (TextView) tabView.findViewById(R.id.search_tab_name);
        WidgetUtils.setText(tv, name);
    }


    /**
     * 设置view为选中
     */
    public void setSelectView(View tabview) {
        clearSelectStatus(tab_thread);
        clearSelectStatus(tab_presentation);
        clearSelectStatus(tab_raiders);
        clearSelectStatus(tab_discount);
        clearSelectStatus(tab_user);
        View line = tabview.findViewById(R.id.search_tab_line);
        WidgetUtils.setVisible(line, true);
        TextView search_tab_name = (TextView) tabview.findViewById(R.id.search_tab_name);
        search_tab_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", search_tab_name.getText().toString());
        MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_navbar_click, map);
    }

    /**
     * 清楚选中状态
     *
     * @param tabview
     */
    private void clearSelectStatus(View tabview) {
        View line = tabview.findViewById(R.id.search_tab_line);
        line.setVisibility(View.INVISIBLE);
        TextView search_tab_name = (TextView) tabview.findViewById(R.id.search_tab_name);
        search_tab_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    private void setSelectFragment(int selectId) {
        switch (selectId) {
            case R.id.tab_thread:
                ThreadSearchResultFragment threadSearchResultFragment = (ThreadSearchResultFragment) fragmentMangerUtils.setShowFragment(ThreadSearchResultFragment.class);
                threadSearchResultFragment.setSearchKey(searchKey, Marks.HttpSearchType.TYPE_THREAD, iSearchResultFragment);
                break;
            case R.id.tab_presentation:
                ReportSearchResultFragment reportSearchResultFragment = (ReportSearchResultFragment) fragmentMangerUtils.setShowFragment(ReportSearchResultFragment.class);
                reportSearchResultFragment.setSearchKey(searchKey, Marks.HttpSearchType.TYPE_REPORT, iSearchResultFragment);
                break;
            case R.id.tab_raiders:
                reportSearchResultFragment = (ReportSearchResultFragment) fragmentMangerUtils.setShowFragment(ReportSearchResultFragment.class);
                reportSearchResultFragment.setSearchKey(searchKey, Marks.HttpSearchType.TYPE_STRATEGY, iSearchResultFragment);
                break;
            case R.id.tab_discount:
                reportSearchResultFragment = (ReportSearchResultFragment) fragmentMangerUtils.setShowFragment(ReportSearchResultFragment.class);
                reportSearchResultFragment.setSearchKey(searchKey, Marks.HttpSearchType.TYPE_PROMOTION, iSearchResultFragment);
                break;
            case R.id.tab_user:
                UserSearchResultFragment userSearchResultFragment = (UserSearchResultFragment) fragmentMangerUtils.setShowFragment(UserSearchResultFragment.class);
                userSearchResultFragment.setSearchKey(searchKey, iSearchResultFragment);
                break;
        }
    }

    ISearchResultFragment iSearchResultFragment = new ISearchResultFragment() {
        @Override
        public void notData() {
            SearchResultNormalFragment fragment = (SearchResultNormalFragment) fragmentMangerUtils.setShowFragment(SearchResultNormalFragment.class);
            fragment.setSearchKey(searchKey);
        }
    };


    /**
     * 保存记录
     *
     * @param text
     */
    public void saveHistory(String text) {
        if (TextUtils.isEmpty(text)) return;
        historyHelper.deleteBySearchName(text);
        SearchHistoryBean saveBean = new SearchHistoryBean();
        saveBean.setSearchName(text);
        historyHelper.saveBean(saveBean);
    }

}
