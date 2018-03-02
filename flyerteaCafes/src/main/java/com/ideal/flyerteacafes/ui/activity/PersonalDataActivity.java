package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.utils.tools.StringTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 个人资料
 * Created by Cindy on 2016/12/19.
 */
@ContentView(R.layout.activity_personal_data)
public class PersonalDataActivity extends BaseActivity {

    /** 所在地 **/
    @ViewInject(R.id.mltci_city_layout)
    LtctriLayout mltci_city_layout;
    /** 行业 **/
    @ViewInject(R.id.mltci_hangye_layout)
    LtctriLayout mltci_hangye_layout;
    /** 职业 **/
    @ViewInject(R.id.mltci_vocation_layout)
    LtctriLayout mltci_vocation_layout;
//    /** 兴趣爱好 **/
//    @ViewInject(R.id.mltci_hobbies_layout)
//    LtctriLayout mltci_hobbies_layout;
    /** 兴趣爱好 **/
    @ViewInject(R.id.mtv_content_hobbies)
    TextView mtv_content_hobbies;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getIntentData();
        initViews();
    }

    /**
     * init data
     */
    private void getIntentData() {
        userinfo = (Userinfo) getIntent().getSerializableExtra("userinfo");
    }

    @Override
    public void initViews() {
        if (userinfo != null) {
            mltci_city_layout.setTextByCt(userinfo.getResideprovince() + "\t" + userinfo.getResidecity());
            mltci_hangye_layout.setTextByCt(userinfo.getField7());
            mltci_vocation_layout.setTextByCt(userinfo.getOccupation());
            if (!TextUtils.isEmpty(userinfo.getInterest())){
                String hobbies = StringTools.replace(userinfo.getInterest(), "-", "\t\t");
                mtv_content_hobbies.setText(hobbies);
            }
        }
    }

    @Event({R.id.toolbar_left})
    private void click(View v) {
        switch (v.getId()){
            case R.id.toolbar_left://返回
                finish();
                break;
        }
    }
}
