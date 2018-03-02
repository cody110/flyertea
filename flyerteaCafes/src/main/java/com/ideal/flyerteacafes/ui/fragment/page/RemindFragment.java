package com.ideal.flyerteacafes.ui.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.RemindAdapter;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.model.entity.RemindBean;
import com.ideal.flyerteacafes.ui.activity.RemindDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 提醒
 *
 * @author fly
 */
public class RemindFragment extends Fragment {

    @ViewInject(R.id.fragment_listview)
    private ListView mListview;

    private List<RemindBean> remindList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        x.view().inject(this, view);

        requestRemind();
        return view;
    }

    @Event(value = R.id.fragment_listview, type = AdapterView.OnItemClickListener.class)
    private void toDetailsActivity(AdapterView<?> parent, View view,
                                  int position, long id) {
        RemindBean bean = remindList.get(position);
        if (bean.getType() == null)
            return;
        if (bean.getType().equals("post")) {
            Intent intent = new Intent(getActivity(), ThreadActivity.class);
            intent.putExtra("tid", bean.getFrom_id());
            startActivity(intent);
        } else {
            String msg = bean.getNote();
            Intent intent = new Intent(getActivity(), RemindDetailsActivity.class);
            intent.putExtra("msg", msg);
            startActivity(intent);
        }
    }

    /**
     * 我的提醒列表
     */
    private void requestRemind() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MYNOTELIST);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                ListObjectBean beanList = JsonAnalysis.jsonToRemindList(result);
                remindList = beanList.getDataList();
                if (!DataUtils.isEmpty(remindList)) {
                    if (getActivity() == null)
                        return;
                    RemindAdapter adapter = new RemindAdapter(getActivity(), beanList.getDataList());
                    mListview.setAdapter(adapter);
                }
            }
        });
    }


}
