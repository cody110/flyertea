package com.ideal.flyerteacafes.ui.activity.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.HotKeyBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.popupwindow.SearchTipsPopupwindow;
import com.ideal.flyerteacafes.ui.view.SerachEdittext;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.FragmentMangerUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Random;

/**
 * Created by fly on 2018/1/19.
 */
@ContentView(R.layout.activity_thread_search_2)
public class ThreadSearchActivity2 extends BaseActivity implements IThreadSearchActivity {


    List<HotKeyBean.DefaultWord> hotWordBeanList;

    @Override
    public void toSearchResult(String value) {
        handlerToSearch(value);
    }

    @Override
    public void callbackHotWordDatas(List<HotKeyBean.DefaultWord> datas) {
        hotWordBeanList = datas;
        if (!DataUtils.isEmpty(datas) && TextUtils.equals(thread_search_edittext.getHint(), getString(R.string.search_hint))) {
            Random random = new Random();
            HotKeyBean.DefaultWord data = datas.get(random.nextInt(datas.size()));
            thread_search_edittext.setHint(data.getKeyword());
        }
    }


    @ViewInject(R.id.thread_search_edittext)
    SerachEdittext thread_search_edittext;

    SearchInitFragment searchInitFragment;
    AssociativeSearchFragment associativeSearchFragment;
    SearchResultFragment searchResultFragment;

    FragmentMangerUtils fmUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MobclickAgent.onEvent(this, FinalUtils.EventId.search);
        bindEditText();
        fmUtils = new FragmentMangerUtils(getSupportFragmentManager(), R.id.content_layout);
        fmUtils.setShowFragment(SearchInitFragment.class);


        //第一次进入
        if (SharedPreferencesString.getInstances().isFirstToSearch()) {
            thread_search_edittext.post(new Runnable() {
                @Override
                public void run() {
                    SearchTipsPopupwindow tipsPopupwindow = new SearchTipsPopupwindow(ThreadSearchActivity2.this);
                    tipsPopupwindow.showAtLocation(thread_search_edittext, Gravity.NO_GRAVITY, 0, 0);
                }
            });
        }

    }


    @Event(R.id.hotel_serach_cancle)
    private void click(View v) {
        finish();
    }

    /**
     * 搜素事件监听
     */
    private void bindEditText() {
        String hotWorld = getIntent().getStringExtra("hotWorld");
        thread_search_edittext.setHint(TextUtils.isEmpty(hotWorld) ? getString(R.string.search_hint) : hotWorld);

        thread_search_edittext.setISearchClick(new SerachEdittext.ISearchClick() {
            @Override
            public void onSearchClick(String text) {
                if (TextUtils.isEmpty(text) && TextUtils.equals(thread_search_edittext.getHint(), getString(R.string.search_hint))) {
                    searchInitFragment = (SearchInitFragment) fmUtils.setShowFragment(SearchInitFragment.class);
                    searchInitFragment.refreshHistory();
                } else {
                    text = TextUtils.isEmpty(text) ? thread_search_edittext.getHint() : text;
                    handlerToSearch(text);
                }
            }
        });


        thread_search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LogFly.e("onTextChanged:" + charSequence);
                if (TextUtils.isEmpty(charSequence)) {
                    searchInitFragment = (SearchInitFragment) fmUtils.setShowFragment(SearchInitFragment.class);
                    searchInitFragment.refreshHistory();
                } else {
                    associativeSearchFragment = (AssociativeSearchFragment) fmUtils.setShowFragment(AssociativeSearchFragment.class);
                    associativeSearchFragment.setSearchKey(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    /**
     * 搜索关键词 匹配热门词逻辑
     *
     * @param searchKey
     */
    private void handlerToSearch(String searchKey) {
        if (!DataUtils.isEmpty(hotWordBeanList)) {
            LogFly.e("searchKey:" + searchKey);
            for (HotKeyBean.DefaultWord data : hotWordBeanList) {
                LogFly.e("keyword:" + data.getKeyword() + "bol?" + TextUtils.equals(data.getKeyword(), searchKey));
                if (TextUtils.equals(data.getKeyword(), searchKey)) {
                    LogFly.e("type:" + data.getType());
                    if (TextUtils.equals(data.getType(), Marks.HotWordType.h5)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", data.getUrl_id());
                        jumpActivity(TbsWebActivity.class, bundle);
                        return;
                    }
                    if (TextUtils.equals(data.getType(), Marks.HotWordType.thread)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("tid", data.getUrl_id());
                        jumpActivity(ThreadActivity.class, bundle);
                        return;
                    }
                    if (TextUtils.equals(data.getType(), Marks.HotWordType.report)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("aid", data.getUrl_id());
                        jumpActivity(ArticleContentActivity.class, bundle);
                        return;
                    }
                }
            }
        }
        thread_search_edittext.setText(searchKey);
        searchResultFragment = (SearchResultFragment) fmUtils.setShowFragment(SearchResultFragment.class);
        searchResultFragment.setSearchKey(searchKey);
    }


}
