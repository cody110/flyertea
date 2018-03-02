package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.NoviceGuidanceFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DataUtils;
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
 * Created by fly on 2017/12/13.
 */
@ContentView(R.layout.activity_novice_guidance)
public class NoviceGuidanceActivity extends BaseActivity {


    @ViewInject(R.id.tabstrip)
    private PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.tabstrip_vp)
    private ViewPager viewPager;


    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();
    List<MyTaskBean> myTaskBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initPage();
        getTaskData();
    }


    @Event(R.id.toolbar_left)
    private void click(View view) {
        finish();
    }

    private void initPage() {
        typeList.clear();

        typeList.add(new TypeMode("初识飞客", Utils.HttpRequest.HTTP_REPORT_HOTEL));
        typeList.add(new TypeMode("融入飞客", Utils.HttpRequest.HTTP_REPORT_AVIATION));


        NoviceGuidanceFragment noviceGuidanceFragment1 = new NoviceGuidanceFragment();
        NoviceGuidanceFragment noviceGuidanceFragment2 = new NoviceGuidanceFragment();


        fmList.add(noviceGuidanceFragment1);
        fmList.add(noviceGuidanceFragment2);


        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);


        viewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(viewPager);


    }

    /**
     * 获取任务
     */
    public void getTaskData() {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MY_TASK_LIST);
        params.addQueryStringParameter("progress", "doing");
        XutilsHttp.Get(params, new DataCallback<MyTaskAllBean>() {

            @Override
            public void flySuccess(DataBean<MyTaskAllBean> result) {
                if (result.getDataBean() == null) return;
                if (!DataUtils.isEmpty(result.getDataBean().getAllfreshman_tasks()) && result.getDataBean().getAllfreshman_tasks().size() >= 8) {
                    myTaskBeanList = result.getDataBean().getAllfreshman_tasks();
                    List<MyTaskBean> datas1 = myTaskBeanList.subList(0, 4);
                    ((NoviceGuidanceFragment) fmList.get(0)).bindData(datas1, NoviceGuidanceFragment.BLUE);
                    List<MyTaskBean> datas2 = myTaskBeanList.subList(4, 8);
                    ((NoviceGuidanceFragment) fmList.get(1)).bindData(datas2, NoviceGuidanceFragment.SILVER);
                }

            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getTaskData();
        }
    }
}
