package com.ideal.flyerteacafes.ui.activity.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.HotKeyBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISearchInitFm;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.SearchInitFmPresenter;
import com.ideal.flyerteacafes.ui.controls.XCFlowLayout;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2018/1/18.
 * 搜索初始状态
 */

public class SearchInitFragment extends MVPBaseFragment<ISearchInitFm, SearchInitFmPresenter> {


    @ViewInject(R.id.view_history_flowlayout)
    XCFlowLayout xcFlowLayout;
    @ViewInject(R.id.hot_key_listview)
    ListView hot_key_listview;
    @ViewInject(R.id.delete_title)
    View delete_title;

    CommonAdapter<HotKeyBean.Hotword> hotKeyAdapter;
    IThreadSearchActivity iThreadSearchActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iThreadSearchActivity = (IThreadSearchActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(iSearchInitFm);
        View view = inflater.inflate(R.layout.fragment_thread_search_init, container, false);
        x.view().inject(this, view);
        initPage();
        mPresenter.init(mActivity);
        return view;
    }

    private void initPage() {
        hot_key_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.search_hot_click);
                mPresenter.saveHistory(hotKeyAdapter.getItem(position).getKeyword());
                iThreadSearchActivity.toSearchResult(hotKeyAdapter.getItem(position).getKeyword());
            }
        });
    }

    @Event(R.id.btn_delete)
    private void click(View v) {
        MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.search_record_delete);
        mPresenter.deleteAllHistory();
    }

    /**
     * 刷新历史
     */
    public void refreshHistory() {
        mPresenter.getHistoryList();
    }

    @Override
    protected SearchInitFmPresenter createPresenter() {
        return new SearchInitFmPresenter();
    }

    /**
     * 数据返回
     */
    ISearchInitFm iSearchInitFm = new ISearchInitFm() {
        @Override
        public void callbackSearchHistoryData(List<SearchHistoryBean> datas) {
            WidgetUtils.setVisible(delete_title, !DataUtils.isEmpty(datas));
            WidgetUtils.setVisible(xcFlowLayout, !DataUtils.isEmpty(datas));
            if (!DataUtils.isEmpty(datas)) {
                addSearchHistoryViews(datas);
            }
        }

        @Override
        public void callbackHotKeyData(List<HotKeyBean.Hotword> datas) {
            if (!DataUtils.isEmpty(datas)) {
                addHotKeyViews(datas);
            }
        }

        @Override
        public void callbackDefaultWordData(List<HotKeyBean.DefaultWord> datas) {
            if (!DataUtils.isEmpty(datas)) {
                if (iThreadSearchActivity != null)
                    iThreadSearchActivity.callbackHotWordDatas(datas);
            }
        }


    };


    /**
     * 热词
     */
    private void addHotKeyViews(List<HotKeyBean.Hotword> datas) {

        hotKeyAdapter = new CommonAdapter<HotKeyBean.Hotword>(mActivity, datas, R.layout.item_search_init_hot_key) {
            @Override
            public void convert(ViewHolder holder, HotKeyBean.Hotword data) {
                holder.setText(R.id.hot_key_index, String.valueOf(holder.getPosition() + 1));
                holder.setText(R.id.hot_key_name, data.getKeyword());
                if (holder.getPosition() == 0) {
                    holder.setBackgroundRes(R.id.hot_key_index, R.drawable.search_list_pos_bg_yellow);
                } else if (holder.getPosition() == 1) {
                    holder.setBackgroundRes(R.id.hot_key_index, R.drawable.search_list_pos_bg_light_blue);
                } else if (holder.getPosition() == 2) {
                    holder.setBackgroundRes(R.id.hot_key_index, R.drawable.search_list_pos_bg_orange);
                } else {
                    holder.setBackgroundRes(R.id.hot_key_index, R.drawable.search_list_pos_bg_grey);
                }
            }
        };
        hot_key_listview.setAdapter(hotKeyAdapter);
        hot_key_listview.setDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
    }

    /**
     * 历史 流标签设置数据
     */
    private void addSearchHistoryViews(final List<SearchHistoryBean> datas) {
        xcFlowLayout.removeAllViews();
        float scale = SharedPreferencesString.getInstances().getFloatToKey("scale");
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = (int) (20 * scale);
        lp.rightMargin = (int) (20 * scale);
        lp.topMargin = (int) (5 * scale);
        lp.bottomMargin = (int) (5 * scale);
        for (int i = 0; i < datas.size(); i++) {
            final TextView view = new TextView(mActivity);
            String hotkey = datas.get(i).getSearchName();
            view.setText(hotkey);
            view.setTag(i);
            int width = (int) (30 * scale);
            int height = (int) (15 * scale);
            view.setPadding(width, height, width, height);
            view.setTextColor(getResources().getColor(R.color.app_body_black));
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.light_grey_bg));
            xcFlowLayout.addView(view, lp);
            final int finalI1 = i;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.search_record_click);
                    iThreadSearchActivity.toSearchResult(datas.get(finalI1).getSearchName());
                }
            });
        }
    }
}
