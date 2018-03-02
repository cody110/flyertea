package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.viewholder.TopicDetailsVH;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by fly on 2017/10/12.
 */
@ContentView(R.layout.activity_topic_details)
public class TopicDetailsActivity extends BaseActivity {

    TopicDetailsVH vh;
    TopicBean topicBean;
    String ctid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        topicBean = (TopicBean) getIntent().getSerializableExtra("data");
        ctid = getIntent().getStringExtra("ctid");

        View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        vh = new TopicDetailsVH(view);
        vh.initPage(getSupportFragmentManager(), topicBean);

        if (topicBean != null) {
            ctid = topicBean.getCtid();
            view.post(new Runnable() {
                @Override
                public void run() {
                    vh.bindHeader(topicBean);
                }
            });
        }

        requestDetails();

    }

    @Event({R.id.left_back, R.id.fm_community_home_write_thread_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.fm_community_home_write_thread_btn:
                if (UserManger.isLogin()) {
                    MobclickAgent.onEvent(this, FinalUtils.EventId.forum_post);//友盟统计
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("topicBean", topicBean);
                    jumpActivity(WriteMajorThreadContentActivity.class, bundle);
                } else {
                    DialogUtils.showDialog(this);
                }

                break;
        }
    }


    @Override
    public boolean isSystemBarTransparent() {
        return true;
    }

    @Override
    public boolean isSetSystemBar() {
        return false;
    }


    private void requestDetails() {
        if (topicBean == null) {
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TOPIC_DETAILS);
            params.addQueryStringParameter("ctid", ctid);
            XutilsHttp.Get(params, new DataCallback<TopicBean>() {
                @Override
                public void flySuccess(DataBean<TopicBean> result) {
                    vh.bindHeader(result.getDataBean());
                }
            });
        }
    }
}
