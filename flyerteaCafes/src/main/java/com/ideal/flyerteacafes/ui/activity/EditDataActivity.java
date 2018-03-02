package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.HobbiesBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.popupwindow.CityPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.StringPopupWindow;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 编辑资料
 *
 * @author fly
 */
@ContentView(R.layout.activity_editdata)
public class EditDataActivity extends BaseActivity {

    @ViewInject(R.id.include_title_right_btn)
    View rightView;
    @ViewInject(R.id.include_title_text)
    TextView titleView;
    @ViewInject(R.id.include_title_right_img)
    ImageView rightImg;
    @ViewInject(R.id.include_title_right_text)
    TextView rightText;

    @ViewInject(R.id.editdata_username)
    View userNameView;
    @ViewInject(R.id.editdata_postbox)
    View postboxView;
    @ViewInject(R.id.editdata_sex)
    View sexView;
    @ViewInject(R.id.editdata_location)
    View locationView;
    @ViewInject(R.id.editdata_vocation)
    View vocationView;
    @ViewInject(R.id.editdata_hangye)
    View hangyeView;
    @ViewInject(R.id.editdata_hobbies)
    View hobbiesView;
    @ViewInject(R.id.editdata_qianming)
    View qianmingView;

    TextView userNameTitle;
    TextView userNameName;
    ImageView userNameInto;

    TextView postboxTitle;
    EditText postboxName;
    ImageView postboxInto;
    View postboxLine;

    TextView sexTitle;
    TextView sexName;

    TextView locationTitle;
    TextView locationName;

    TextView hangyeTitle;
    TextView hangyeName;

    TextView vocationTitle;
    TextView vocationName;
    View vocationLine;

    TextView hobbiesTitle;
    TextView hobbiesName;
    View hobbiesLine;

    TextView qianmingTitle;
    TextView qianmingName;
    View qianmingLine;

    UserBean userBean;

    CityPopupWindow cityPop;
    StringPopupWindow sexPop;
    StringPopupWindow vocationPop;
    StringPopupWindow hangyePop;

    String[] occupation_array;
    String[] hangye_array;
    private String hobbies;


    public static final int WHAT_CITY = 1, WHAT_SEX = 2, WHAT_VOCATION = 3, WHAT_HANGYE = 4, REQUEST_HOBBIES = 1, REQUEST_QIANMING = 2;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case WHAT_CITY:
                    String city = (String) msg.obj;
                    locationName.setText(city.trim());
                    break;
                case WHAT_SEX:
                    String sex = (String) msg.obj;
                    sexName.setText(sex);
                    break;

                case WHAT_VOCATION:
                    String voaction = (String) msg.obj;
                    vocationName.setText(voaction);
                    break;

                case WHAT_HANGYE:
                    String hangye = (String) msg.obj;
                    hangyeName.setText(hangye);
                    break;

                default:
                    break;
            }


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        userBean = UserManger.getUserInfo();
        initView();
        initWidget();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_HOBBIES:
                    if (data.getSerializableExtra("hobbies") == null)
                        return;
                    @SuppressWarnings("unchecked")
                    List<HobbiesBean> hobbiesList = (List<HobbiesBean>) data.getSerializableExtra("hobbies");

                    StringBuffer sb = new StringBuffer();
                    for (HobbiesBean hobbiesBean : hobbiesList) {
                        if (hobbiesBean.getMark() == 1)
                            sb.append(hobbiesBean.getHobbies() + "\t\t");
                    }
                    hobbies = sb.toString();
                    hobbiesName.setText(hobbies);

                    break;

                case REQUEST_QIANMING:
                    String qianming = data.getStringExtra("qianming");
                    qianmingName.setText(qianming);
                    break;

            }
        }
    }

    private void initView() {
        /**标题栏*/
        titleView.setText(getString(R.string.editdata_title));
        rightView.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        rightText.setText(getString(R.string.accomplish));

        /**include*/
        userNameTitle = (TextView) userNameView.findViewById(R.id.include_edit_data_title);
        userNameName = (TextView) userNameView.findViewById(R.id.include_edit_data_name);
        userNameInto = (ImageView) userNameView.findViewById(R.id.include_edit_data_into);

        postboxTitle = (TextView) postboxView.findViewById(R.id.include_edit_edit_title);
        postboxName = (EditText) postboxView.findViewById(R.id.include_edit_edit_name);
        postboxInto = (ImageView) postboxView.findViewById(R.id.include_edit_edit_into);
        postboxLine = postboxView.findViewById(R.id.include_edit_edit_line);

        sexTitle = (TextView) sexView.findViewById(R.id.include_edit_data_title);
        sexName = (TextView) sexView.findViewById(R.id.include_edit_data_name);

        locationTitle = (TextView) locationView.findViewById(R.id.include_edit_data_title);
        locationName = (TextView) locationView.findViewById(R.id.include_edit_data_name);

        hangyeTitle = (TextView) hangyeView.findViewById(R.id.include_edit_data_title);
        hangyeName = (TextView) hangyeView.findViewById(R.id.include_edit_data_name);

        vocationTitle = (TextView) vocationView.findViewById(R.id.include_edit_data_title);
        vocationName = (TextView) vocationView.findViewById(R.id.include_edit_data_name);
        vocationLine = vocationView.findViewById(R.id.include_edit_data_line);


        hobbiesTitle = (TextView) hobbiesView.findViewById(R.id.include_edit_data_title);
        hobbiesName = (TextView) hobbiesView.findViewById(R.id.include_edit_data_name);
        hobbiesLine = hobbiesView.findViewById(R.id.include_edit_data_line);

        qianmingTitle = (TextView) qianmingView.findViewById(R.id.include_edit_data_title);
        qianmingName = (TextView) qianmingView.findViewById(R.id.include_edit_data_name);
        qianmingLine = qianmingView.findViewById(R.id.include_edit_data_line);
    }

    private void initWidget() {
        userNameTitle.setText(getString(R.string.userinfo_name));
        postboxTitle.setText(getString(R.string.postbox));
        sexTitle.setText(getString(R.string.edit_sex));
        locationTitle.setText(getString(R.string.location));
        hangyeTitle.setText(getString(R.string.hangye));
        vocationTitle.setText(getString(R.string.vocation));
        hobbiesTitle.setText(getString(R.string.hobbies));
        qianmingTitle.setText(getString(R.string.qianming));

        if (userBean != null) {
            userNameName.setText(userBean.getMember_username());
            postboxName.setText(userBean.getEmail());
            if (userBean.getGender() == 1)
                sexName.setText("男");
            else if (userBean.getGender() == 2)
                sexName.setText("女");
            else
                sexName.setText("保密");
            locationName.setText(userBean.getResideprovince() + "  " + userBean.getResidecity());
            hangyeName.setText(userBean.getField7());
            vocationName.setText(userBean.getOccupation());
            hobbies = StringTools.replace(userBean.getInterest(), "-", "\t\t");
            hobbiesName.setText(hobbies);
            qianmingName.setText(userBean.getSightml());
        }

        userNameInto.setVisibility(View.GONE);
        postboxInto.setVisibility(View.GONE);
        vocationLine.setVisibility(View.GONE);

        postboxLine.setVisibility(View.GONE);
        hobbiesLine.setVisibility(View.GONE);
        qianmingLine.setVisibility(View.GONE);

    }

    @Event({R.id.editdata_hangye, R.id.editdata_qianming, R.id.editdata_hobbies, R.id.editdata_vocation, R.id.editdata_sex, R.id.include_title_menu_btn, R.id.include_title_right_btn, R.id.editdata_location})
    private void onActionClick(View v) {
        switch (v.getId()) {
            case R.id.include_title_menu_btn:
                finish();
                break;

            case R.id.include_title_right_btn:
                requestUpdate();
                break;

            case R.id.editdata_location:
                if (cityPop == null)
                    cityPop = new CityPopupWindow(this, handler);
                cityPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;

            case R.id.editdata_sex:
                if (sexPop == null) {
                    String[] sex_array = getResources().getStringArray(R.array.sex_array);
                    sexPop = new StringPopupWindow(this, handler, sex_array, WHAT_SEX);
                }
                sexPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.editdata_vocation:
                if (occupation_array == null) {
                    requestOccupation();
                    return;
                }
                if (vocationPop == null) {
                    vocationPop = new StringPopupWindow(this, handler, occupation_array, WHAT_VOCATION);
                }
                vocationPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.editdata_hobbies:
                Bundle bundle = new Bundle();
                bundle.putString("hobbies", hobbies);
                jumpActivityForResult(HobbiesActivity.class, bundle, REQUEST_HOBBIES);
                break;

            case R.id.editdata_qianming:
                qianmingStr = qianmingName.getText().toString().trim();
                Bundle bundleQM = new Bundle();
                bundleQM.putString("qianming", qianmingStr);
                jumpActivityForResult(QianmingActivity.class, bundleQM, REQUEST_QIANMING);
                break;

            case R.id.editdata_hangye:
                if (hangye_array == null) {
                    requestHangye();
                    return;
                }
                if (hangyePop == null) {
                    hangyePop = new StringPopupWindow(this, handler, hangye_array, WHAT_VOCATION);
                }
                hangyePop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            default:
                break;
        }
    }

    /**
     * 行业
     */
    private void requestHangye() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HANGYE), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                List<String> hangye = JsonAnalysis.getStringList(result, "hangye");
                if (!DataUtils.isEmpty(hangye)) {
                    hangye_array = hangye.toArray(new String[hangye.size()]);
                    if (hangyePop == null) {
                        hangyePop = new StringPopupWindow(EditDataActivity.this, handler, hangye_array, WHAT_HANGYE);
                    }
                    hangyePop.showAtLocation(titleView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
    }

    /**
     * 职位
     */
    private void requestOccupation() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_OCCUPATION), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                List<String> occupation = JsonAnalysis.getStringList(result, "occupation");
                if (!DataUtils.isEmpty(occupation)) {
                    occupation_array = occupation.toArray(new String[occupation.size()]);
                    if (vocationPop == null) {
                        vocationPop = new StringPopupWindow(EditDataActivity.this, handler, occupation_array, WHAT_VOCATION);
                    }
                    vocationPop.showAtLocation(titleView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
    }

    String sexStr;
    String locationStr;
    String hangyeStr;
    String vocationStr;
    String hobbiesStr;
    String qianmingStr;

    private void requestUpdate() {

        sexStr = sexName.getText().toString().trim();
        locationStr = locationName.getText().toString().trim();
        hangyeStr = hangyeName.getText().toString().trim();
        vocationStr = vocationName.getText().toString().trim();

        hobbiesStr = hobbiesName.getText().toString().trim();
        qianmingStr = qianmingName.getText().toString().trim();

        String formhash = SharedPreferencesString.getInstances(this).getStringToKey("formhash");

        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMIT);
        params.addBodyParameter("formhash", formhash);
        int sex_num;
        if (sexStr.equals("男"))
            sex_num = 1;
        else if (sexStr.equals("女"))
            sex_num = 2;
        else
            sex_num = 0;
        params.addBodyParameter("gender", sex_num + "");

        params.addBodyParameter("profilesubmit", "true");
        params.addBodyParameter("profilesubmitbtn", "true");


        String[] location = locationStr.split("\t\t");
        if (location != null && location.length > 0)
            params.addBodyParameter("resideprovince", location[0]);
        if (location != null && location.length > 1)
            params.addBodyParameter("residecity", location[1]);

        params.addBodyParameter("field7", hangyeStr);
        params.addBodyParameter("occupation", vocationStr);
        String[] interest = hobbiesStr.split("\t\t");
        if (interest != null && interest.length > 0) {
            String appinterest = "";
            for (int i = 0; i < interest.length; i++) {
                appinterest = appinterest + interest[i];
                if (i < interest.length - 1)
                    appinterest = appinterest + "-";
            }
            params.addBodyParameter("appinterest", appinterest);
        }
        params.addBodyParameter("sightml", qianmingStr);
        params.addBodyParameter("timeoffset","8");

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean != null) {
                    if (bean.getCode() != null && bean.getCode().equals("update_profile_done")) {
                        BToast("资料修改成功！");

                        if (userBean != null) {
                            int sex_num;
                            if (sexStr.equals("男"))
                                sex_num = 1;
                            else if (sexStr.equals("女"))
                                sex_num = 2;
                            else
                                sex_num = 0;
                            userBean.setGender(sex_num);

                            if (locationStr != null) {
                                String[] location = locationStr.split("\t\t");
                                if (location != null && location.length > 0)
                                    userBean.setResideprovince(location[0]);
                                if (location != null && location.length > 1)
                                    userBean.setResidecity(location[1]);
                            }

                            userBean.setField7(hangyeStr);
                            userBean.setOccupation(vocationStr);
                            userBean.setInterest(hobbiesStr);
                            userBean.setSightml(qianmingStr);

                        }
                        EventBus.getDefault().post(userBean);
                        finish();
                        return;
                    }
                }
                BToast("资料修改失败！");
            }
        });

    }

}
