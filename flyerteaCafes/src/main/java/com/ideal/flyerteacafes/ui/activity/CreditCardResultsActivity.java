package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.CreditCardResultAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.CreditCardBackBean;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡积分查询结果
 *
 * @author fly
 */
@ContentView(R.layout.activity_creditcard_query_results)
public class CreditCardResultsActivity extends BaseActivity implements OnHeaderRefreshListener, OnFooterRefreshListener {

    @ViewInject(R.id.include_left_title_text)
    private TextView titleView;
    @ViewInject(R.id.creditcard_query_results_number)
    private TextView showNumView;
    @ViewInject(R.id.creditcard_query_results_listview)
    private ListView resultListView;
    @ViewInject(R.id.creditcard_result_refreshview)
    private PullToRefreshView mPullToRefreshView;

    List<CreditCardBackBean> backList = new ArrayList<CreditCardBackBean>();

    private int page = FinalUtils.START_PAGE;
    private int count = 1;
    private int pageCount = 0;

    private int type_id;
    private int scores;
    private int code;
    CreditCardResultAdapter adapter;

    @SuppressWarnings({"unchecked", "unused"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.onHeaderRefreshComplete();
        type_id = getIntent().getIntExtra("type_id", 0);
        scores = getIntent().getIntExtra("scores", 0);
        code = getIntent().getIntExtra("mcc", 0);
        titleView.setText(getString(R.string.title_name_creditcardresults));
        adapter = new CreditCardResultAdapter(
                CreditCardResultsActivity.this,
                backList);
        resultListView.setAdapter(adapter);
        requestCredit();
    }

    @Event(R.id.include_left_title_back_layout)
    private void back(View v) {
        finish();
    }

    /**
     * 查询
     */
    @SuppressWarnings("unused")
    private void requestCredit() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CREDIT);
        if (type_id != 0)
            params.addQueryStringParameter("type_id", type_id + "");
        if (scores != 0)
            params.addQueryStringParameter("scores", scores + "");
        if (code != 0)
            params.addQueryStringParameter("code", code + "");

        params.addQueryStringParameter("p", page + "");
        XutilsHttp.Get(params, new StringCallback() {

            @Override
            public void flyFinished() {
                super.flyFinished();
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
            }

            @Override
            public void flySuccess(String result) {
                int position = 0;
                ListObjectBean beanList = JsonAnalysis
                        .jsonToCreditCardBack(result);
                if (DataUtils.loginIsOK(beanList.getCode())) {
                    count = beanList.getCount();

                    if (page == FinalUtils.START_PAGE) {
                        backList.clear();
                        showNumView.setText("查询到" + beanList.getCount() + "条结果");
                    } else {
                        position = backList.size();
                    }
                    if (!DataUtils.isEmpty(beanList.getDataList()))
                        backList.addAll(beanList.getDataList());
                    adapter.notifyDataSetChanged();
                    if (page != 1)
                        resultListView.setSelectionFromTop(position, (int) (FinalUtils.FROM_TOP * SharedPreferencesString.getInstances().getScale()));
                } else {
                    BToast(beanList.getMessage());
                }
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub

        int temp = count % 10;

        if (temp == 0) {
            pageCount = count / 10;
        } else if (temp != 0) {
            pageCount = count / 10 + 1;
        }
        if (page < pageCount) {
            page++;
            requestCredit();
        } else {
            mPullToRefreshView.onFooterRefreshComplete();
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        // TODO Auto-generated method stub
        page = FinalUtils.START_PAGE;
        requestCredit();
    }


}
