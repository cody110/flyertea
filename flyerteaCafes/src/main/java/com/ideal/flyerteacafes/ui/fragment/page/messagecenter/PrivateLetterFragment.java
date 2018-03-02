package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PrivateLetterAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.PersonalLetterBean;
import com.ideal.flyerteacafes.ui.activity.ChatActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.SystemMessageActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PrivateLetterPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.List;

/**
 * Created by Cindy on 2017/3/16.
 */
public class PrivateLetterFragment extends NewPullRefreshFragment<PersonalLetterBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String uid = mPresenter.getDatas().get(position).getTouid();
                String name = mPresenter.getDatas().get(position).getMsgfrom();
                int pmnum = mPresenter.getDatas().get(position).getPmnum();

                if (TextUtils.equals(name, "系统消息")) {
                    jumpActivityForResult(SystemMessageActivity.class, null, FinalUtils.REQUEST_CODE_PRIVATE_LETTER);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", uid);
                    bundle.putString("name", name);
                    bundle.putInt("pmnum", pmnum);
                    jumpActivityForResult(ChatActivity.class, bundle, FinalUtils.REQUEST_CODE_PRIVATE_LETTER);
                }
                //TODO 设为已读，不用再掉接口
                mPresenter.getDatas().get(position).setIsnew("0");
                adapter.notifyDataSetChanged();


            }
        });

        //添加空白layout
        addNormalLayout(R.layout.include_no_message);
    }

    @Override
    protected CommonAdapter<PersonalLetterBean> createAdapter(List<PersonalLetterBean> datas) {
        WidgetUtils.setVisible(normalLayout, DataUtils.isEmpty(datas));
        return new PrivateLetterAdapter(mActivity, datas, R.layout.listview_item_remind_fragment_layout);
    }


    @Override
    protected PullRefreshPresenter<PersonalLetterBean> createPresenter() {
        return new PrivateLetterPresenter();
    }


}

