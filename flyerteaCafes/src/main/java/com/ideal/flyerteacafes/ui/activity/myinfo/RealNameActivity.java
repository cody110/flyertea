package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.RealNameBaseDataBean;
import com.ideal.flyerteacafes.model.entity.UserProfileBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.popupwindow.CityPopupWindow4;
import com.ideal.flyerteacafes.ui.popupwindow.HangyePopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.StringPopupWindow;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2017/5/18.
 */
@ContentView(R.layout.activity_realname)
public class RealNameActivity extends BaseActivity {

    @ViewInject(R.id.realname_layout)
    LtreLayout realname_layout;
    @ViewInject(R.id.id_layotu)
    LtreLayout id_layotu;
    @ViewInject(R.id.location_layout)
    LtctriLayout location_layout;
    @ViewInject(R.id.address_layout)
    LtreLayout address_layout;

    @ViewInject(R.id.working_conditions_layout)
    LtctriLayout working_conditions_layout;
    @ViewInject(R.id.work_industry_layout)
    LtctriLayout work_industry_layout;
    @ViewInject(R.id.occupation_category_layout)
    LtctriLayout occupation_category_layout;
    @ViewInject(R.id.position_layout)
    LtreLayout position_layout;
    @ViewInject(R.id.company_layout)
    LtreLayout company_layout;
    @ViewInject(R.id.info_text)
    TextView info_text;

    @ViewInject(R.id.save_btn)
    TextView save_btn;


    StringPopupWindow workingPop;
    StringPopupWindow vocationPop;
    HangyePopupwindow hangyePop;

    String[] working_array = {"已工作", "自由职业", "待业/求学"};
    String[] occupation_array;
    RealNameBaseDataBean baseDataBean;


    StringPopupWindow sexPop;
    CityPopupWindow4 cityPop;
    UserProfileBean userProfileBean;

    public static final int WHAT_CITY = 1, WHAT_SEX = 2, WHAT_WORK = 3, WHAT_VOCATION = 4, WHAT_HANGYE = 5;
    public static final int UPDATE_MOBILE = 12;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case WHAT_CITY:
                    String city = (String) msg.obj;
                    location_layout.setTextByCt(city.trim());
                    break;
                case WHAT_WORK:
                    String work = (String) msg.obj;
                    working_conditions_layout.setTextByCt(work);
                    break;

                case WHAT_VOCATION:
                    String voaction = (String) msg.obj;
                    occupation_category_layout.setTextByCt(voaction);
                    break;

                case WHAT_HANGYE:
                    String hangye = (String) msg.obj;
                    work_industry_layout.setTextByCt(hangye);
                    break;

            }


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        userProfileBean = (UserProfileBean) getIntent().getSerializableExtra("data");
        if (userProfileBean != null) {
            if (userProfileBean.getRealename() != null) {
                if (!TextUtils.isEmpty(userProfileBean.getRealename().getName())) {
                    String name = "*" + userProfileBean.getRealename().getName().substring(1, userProfileBean.getRealename().getName().length());
                    realname_layout.setTextByRe(name);
                }

//                if (!TextUtils.isEmpty(userProfileBean.getRealename().getBirthyear()) && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthmonth()) && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthday())) {
//                    birthday_layout.setTextByCt(userProfileBean.getRealename().getBirthyear() + "-" + userProfileBean.getRealename().getBirthmonth() + "-" + userProfileBean.getRealename().getBirthday());
//                }
                if (!TextUtils.isEmpty(userProfileBean.getRealename().getUserid())) {
                    int idLength = userProfileBean.getRealename().getUserid().length();
                    if (idLength > 7) {
                        String id = userProfileBean.getRealename().getUserid().substring(0, 3) + "***********" + userProfileBean.getRealename().getUserid().substring(idLength - 4, idLength);
                        id_layotu.setTextByRe(id);
                    } else {
                        id_layotu.setTextByRe(userProfileBean.getRealename().getUserid());
                    }
                }

                if (!TextUtils.isEmpty(userProfileBean.getRealename().getBirthprovince())) {
                    String location = userProfileBean.getRealename().getBirthprovince() + "/" + userProfileBean.getRealename().getBirthcity() + "/" + userProfileBean.getRealename().getBirthdist();
                    location_layout.setTextByCt(location);
                }
                address_layout.setTextByRe(userProfileBean.getRealename().getAddress());


                if (TextUtils.equals(userProfileBean.getRealename().getStatus(), "1")) {
                    save_btn.setText(userProfileBean.getRealename().getStatus_desc());

                    location_layout.setEnabled(false);
                    working_conditions_layout.setEnabled(false);
                    work_industry_layout.setEnabled(false);
                    occupation_category_layout.setEnabled(false);

                    realname_layout.setFocusableByEdit(false);
                    id_layotu.setFocusableByEdit(false);
                    address_layout.setFocusableByEdit(false);
                    position_layout.setFocusableByEdit(false);
                    company_layout.setFocusableByEdit(false);
                } else {
                    save_btn.setText("保存");
                }
                if (TextUtils.equals(userProfileBean.getRealename().getStatus(), "2")) {//审核通过后
                    realname_layout.setFocusableByEdit(false);
                    id_layotu.setFocusableByEdit(false);
                }
            }
            if (userProfileBean.getJob() != null) {
                working_conditions_layout.setTextByCt(userProfileBean.getJob().getJob_status());
                if (!TextUtils.isEmpty(userProfileBean.getJob().getProfession()))
                    work_industry_layout.setTextByCt(userProfileBean.getJob().getProfession() + "/" + userProfileBean.getJob().getJob_ctype());
                if (!TextUtils.isEmpty(userProfileBean.getJob().getJob_type()))
                    occupation_category_layout.setTextByCt(userProfileBean.getJob().getJob_type());
                if (!TextUtils.isEmpty(userProfileBean.getJob().getJob_title()))
                    position_layout.setTextByRe(userProfileBean.getJob().getJob_title());
                if (!TextUtils.isEmpty(userProfileBean.getJob().getCompany()))
                    company_layout.setTextByRe(userProfileBean.getJob().getCompany());
            }

        }
        requestBaseData();


        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode mode1 = new SegmentedStringMode("完善实名信息，审核通过将发放", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode2 = new SegmentedStringMode("实名认证标志", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode3 = new SegmentedStringMode("，提高您在众多飞客中的", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode4 = new SegmentedStringMode("威信", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode5 = new SegmentedStringMode("，并获得线下某些活动的", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode6 = new SegmentedStringMode("参与权或者优先权", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode7 = new SegmentedStringMode("，如单身派对、人脉拓展鸡尾酒晚宴等", R.dimen.app_body_size_2, R.color.app_body_grey);

        modeList.add(mode1);
        modeList.add(mode2);
        modeList.add(mode3);
        modeList.add(mode4);
        modeList.add(mode5);
        modeList.add(mode6);
        modeList.add(mode7);

        info_text.setText(DataUtils.getSegmentedDisplaySs(modeList));

    }

    @Event({R.id.toolbar_left, R.id.sex_layout, R.id.location_layout, R.id.working_conditions_layout, R.id.work_industry_layout,
            R.id.occupation_category_layout, R.id.save_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.sex_layout:
                if (sexPop == null) {
                    String[] sex_array = getResources().getStringArray(R.array.sex_array);
                    sexPop = new StringPopupWindow(this, handler, sex_array, WHAT_SEX);
                }
                sexPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.location_layout:
                if (baseDataBean == null) return;
                if (cityPop == null) {
                    cityPop = new CityPopupWindow4(this, handler, WHAT_CITY);
                    cityPop.bindData(baseDataBean.getAreas());
                }
                cityPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.working_conditions_layout:
                if (workingPop == null) {
                    workingPop = new StringPopupWindow(this, handler, working_array, WHAT_WORK);
                }
                workingPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.work_industry_layout:
                if (baseDataBean == null) return;
                if (hangyePop == null) {
                    hangyePop = new HangyePopupwindow(this, handler, WHAT_HANGYE);
                    hangyePop.bindData(baseDataBean.getCareer());
                }
                hangyePop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.occupation_category_layout:
                if (occupation_array == null) return;
                if (vocationPop == null) {
                    vocationPop = new StringPopupWindow(this, handler, occupation_array, WHAT_VOCATION);
                }
                vocationPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.save_btn:
                if (!(userProfileBean != null && userProfileBean.getRealename() != null && TextUtils.equals(userProfileBean.getRealename().getStatus(), "1"))) {
                    commint();
                }
                break;

        }
    }


    /**
     * 基础数据
     */
    private void requestBaseData() {
        final FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MEMBER_DATA_OCCUPATION);
        params.setLoadCache(true);
        XutilsHttp.Get(params, new DataCallback<RealNameBaseDataBean>() {

            @Override
            public FlyRequestParams getRequestParams() {
                return params;
            }

            @Override
            public void flySuccess(DataBean<RealNameBaseDataBean> result) {
                baseDataBean = result.getDataBean();
                List<String> occupation = result.getDataBean().getPosition();
                if (!DataUtils.isEmpty(occupation)) {
                    occupation_array = occupation.toArray(new String[occupation.size()]);
                }
            }
        });
    }


    private void commint() {
        if (TextUtils.isEmpty(realname_layout.getTextByRe())) {
            ToastUtils.showToast("请输入真实姓名");
        }  else if (TextUtils.isEmpty(id_layotu.getTextByRe())) {
            ToastUtils.showToast("请输入身份证号");
        } else {
            showDialog();
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REALNAME_DATA);
            Map<String, String> value = new HashMap<>();


            if (userProfileBean != null && userProfileBean.getRealename() != null && TextUtils.equals(userProfileBean.getRealename().getStatus(), "2")) {//审核通过后
                value.put("name", userProfileBean.getRealename().getName());
                value.put("sex", userProfileBean.getRealename().getSex());
                value.put("userid", userProfileBean.getRealename().getUserid());
            } else {
                value.put("name", realname_layout.getTextByRe());
                value.put("userid", id_layotu.getTextByRe());
            }


//            String[] birth = birthday_layout.getTextByCt().split("-");
//            if (birth.length > 0)
//                value.put("birthyear", birth[0]);
//            if (birth.length > 1)
//                value.put("birthmonth", birth[1]);
//            if (birth.length > 2)
//                value.put("birthday", birth[2]);
            value.put("userid", id_layotu.getTextByRe());
            String[] location = location_layout.getTextByCt().split("/");
            if (location.length > 0)
                value.put("birthprovince", location[0]);
            if (location.length > 1)
                value.put("birthcity", location[1]);
            if (location.length > 2)
                value.put("birthdist", location[2]);
            value.put("address", address_layout.getTextByRe());
            value.put("company", working_conditions_layout.getTextByCt());
            String[] hangye = work_industry_layout.getTextByCt().split("/");
            if (hangye.length > 0)
                value.put("jobtype1", hangye[0]);
            if (hangye.length > 1)
                value.put("jobtype2", hangye[1]);

            value.put("jobtype3", occupation_category_layout.getTextByCt());
            value.put("position", position_layout.getTextByRe());
            value.put("company", company_layout.getTextByRe());
            params.setBodyJson(value);
            XutilsHttp.Post(params, new BaseBeanCallback() {

                @Override
                public void flySuccess(BaseBean result) {
                    if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                        ToastUtils.showToast("实名认证提交成功");
                    } else {
                        ToastUtils.showToast(result.getMessage());
                    }
                    jumpActivitySetResult(null);
                }

                @Override
                public void flyFinished() {
                    super.flyFinished();
                    dialogDismiss();
                }
            });
        }
    }


}
