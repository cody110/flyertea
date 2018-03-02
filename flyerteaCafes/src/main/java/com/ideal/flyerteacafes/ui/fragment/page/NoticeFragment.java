package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.NoticeAdapter;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.model.entity.NoticeBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 公告消息
 *
 * @author fly
 */
public class NoticeFragment extends Fragment {

    @ViewInject(R.id.fragment_listview)
    private ListView mListview;

    private List<NoticeBean> noticeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        x.view().inject(this, view);
        requestReply();
        return view;
    }

    @Event(value = R.id.fragment_listview, type = AdapterView.OnItemClickListener.class)
    private void toPostDetailsActivity(AdapterView<?> parent, View view,
                                       int position, long id) {
        DataUtils.webViewClickUrlDispose(noticeList.get(position).getMessage(), getActivity());
    }

    /**
     * 我的公告列表
     */
    private void requestReply() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_ANNOUNCEMENT), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                ListObjectBean beanList = JsonAnalysis.jsonToNoticeList(result);
                noticeList = beanList.getDataList();
                if (!DataUtils.isEmpty(noticeList)) {
                    NoticeAdapter adapter = new NoticeAdapter(getActivity(), noticeList);
                    mListview.setAdapter(adapter);
                }
            }
        });
    }

}
