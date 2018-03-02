package com.ideal.flyerteacafes.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.RoundImageView;
import com.ideal.flyerteacafes.ui.popupwindow.BirthdayPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.StringPopupWindow;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by young on 2015/12/25.
 * 完善资料
 */
@ContentView(R.layout.activity_perfect_datum)
public class PerfectDatumActivity extends BaseActivity {


    @ViewInject(R.id.my_datum_head)
    private RoundImageView faceImg;
    @ViewInject(R.id.et_username)
    EditText etName;
    @ViewInject(R.id.et_password)
    EditText etPsd;
    @ViewInject(R.id.et_email)
    EditText etEmail;
    @ViewInject(R.id.tv_sex)
    TextView tv_sex;
    @ViewInject(R.id.tv_birthday)
    TextView tv_birthday;

    private String mobile;
    private String code;

    private int type;
    private String bindtype, openid, accesstoken, nickname, faceUrl, unionid;


    StringPopupWindow sexPop;
    BirthdayPopupwindow birthdayPopupwindow;
    public static final int WHAT_SEX = 1;


    Handler handler = new Handler() {


        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT_SEX:
                    String sex = (String) msg.obj;
                    WidgetUtils.setText(tv_sex, sex);
                    break;
            }
        }

    };

    public void onEventMainThread(TagEvent tagEvent) {
        //TODO 选择生日返回
        if (tagEvent.getTag() == TagEvent.TAG_BirthDay) {
            if (tagEvent.getBundle() == null) return;
            if (TextUtils.isEmpty(tagEvent.getBundle().getString("year"))) return;
            if (TextUtils.isEmpty(tagEvent.getBundle().getString("month"))) return;
            if (TextUtils.isEmpty(tagEvent.getBundle().getString("day"))) return;
            int year = DataTools.getInteger(tagEvent.getBundle().getString("year"));
            int month = DataTools.getInteger(tagEvent.getBundle().getString("month"));
            int day = DataTools.getInteger(tagEvent.getBundle().getString("day"));
            tv_birthday.setText(year + "-" + month + "-" + day);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        x.view().inject(this);
        initVariables();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initVariables() {
        super.initVariables();
        type = getIntent().getIntExtra(FinalUtils.REGIST_TYPE, 0);
        if (type == FinalUtils.REGIST_PHONE) {
            mobile = getIntent().getStringExtra("mobile");
            code = getIntent().getStringExtra("code");
        } else if (type == FinalUtils.REGIST_THRID) {
            bindtype = getIntent().getStringExtra("bindtype");
            openid = getIntent().getStringExtra("openid");
            accesstoken = getIntent().getStringExtra("accesstoken");
            nickname = getIntent().getStringExtra("nickname");
            faceUrl = getIntent().getStringExtra("faceurl");
            unionid = getIntent().getStringExtra("unionid");
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        DataUtils.downloadPicture(faceImg, faceUrl, R.drawable.def_face);
    }


    String uname, pwd1, email;


    @Event({R.id.toolbar_left, R.id.type_finish_btn, R.id.layout_sex, R.id.layout_birthday})
    private void click(View v) {

        switch (v.getId()) {
            case R.id.toolbar_left:
                jumpActivitySetResult(null);
                break;
            case R.id.type_finish_btn:
                uname = etName.getText().toString().trim();
                pwd1 = etPsd.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                if (uname.equals("")) {
                    BToast("请输入用户名");
                } else if (pwd1.equals("")) {
                    BToast("请输入密码");
                } else {
                    MobclickAgent.onEvent(PerfectDatumActivity.this, FinalUtils.EventId.perfectInfo_doneBtn_click);
                    if (type == FinalUtils.REGIST_PHONE) {
                        regPhone();
                    } else if (type == FinalUtils.REGIST_THRID) {
                        registBindThrid();
                    }

                    if (!TextUtils.isEmpty(tv_sex.getText())) {
                        requestUpdateSex(tv_sex.getText().toString());
                    }

                    if (!TextUtils.isEmpty(tv_birthday.getText())) {
                        String[] array = tv_birthday.getText().toString().split("-");
                        if (array.length == 3) {
                            updateBirthday(array[0], array[1], array[2]);
                        }
                    }


                }
                break;
            case R.id.layout_sex:
                if (sexPop == null) {
                    String[] sex_array = getResources().getStringArray(R.array.sex_array);
                    sexPop = new StringPopupWindow(this, handler, sex_array, WHAT_SEX);
                }
                sexPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.layout_birthday:
                if (birthdayPopupwindow == null)
                    birthdayPopupwindow = new BirthdayPopupwindow(this);
                birthdayPopupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
        }


    }


    /**
     * 完善资料，完成
     */
    public void regPhone() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REGISTER);
        params.addQueryStringParameter("uname", StringTools.encodeBase64(uname));
        params.addQueryStringParameter("pwd1", StringTools.encodeBase64(pwd1));
        params.addQueryStringParameter("pwd2", StringTools.encodeBase64(pwd1));
        if (email != null)
            params.addQueryStringParameter("email", email);
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("code", code);
        params.addQueryStringParameter("regtype", "1");
        String registrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addQueryStringParameter("Registrationid", registrationID);

        showDialog();

        XutilsHttp.Get(params, new StringCallback() {

            @Override
            public void flySuccess(String result) {
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    if (loginBase == null) return;
                    bean = loginBase.getVariables();
                    loginSuccessHandle();
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                    dialogDismiss();
                }
            }

        });
    }

    /**
     * 第三方
     */
    private void registBindThrid() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REGIST_BIND_THRID_NEW);
        params.addQueryStringParameter("uname", StringTools.encodeBase64(uname));
        params.addQueryStringParameter("pwd1", StringTools.encodeBase64(pwd1));
        if (email != null)
            params.addQueryStringParameter("email", email);
        params.addQueryStringParameter("bindtype", bindtype);
        params.addQueryStringParameter("openid", openid);
        params.addQueryStringParameter("accesstoken", accesstoken);
        params.addQueryStringParameter("nickname", nickname);
        params.addQueryStringParameter("unionid", unionid);
        params.addQueryStringParameter("avatar", faceUrl);
        String RegistrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addQueryStringParameter("Registrationid", RegistrationID);
        params.addQueryStringParameter("version", "6");
        showDialog();
        XutilsHttp.Get(params, new StringCallback() {

            @Override
            public void flySuccess(String result) {
                loginInit(result);
            }
        });

    }

    private String token1;
    UserBean bean;

    public void loginInit(String result) {
        LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
        if (loginBase == null) return;
        bean = loginBase.getVariables();
        if (DataUtils.loginIsOK(loginBase.getMessage().getCode())) {
            loginSuccessHandle();
        } else {
            BToast(loginBase.getMessage().getMessage());
            dialogDismiss();
        }

    }

    private void loginSuccessHandle() {
        SharedPreferencesString.getInstances().saveUserinfo(bean);
        XutilsHelp.saveCookie();
        token1 = bean.getToken();
        face(faceUrl, UserManger.getUserInfo().getMember_uid());
        loginBack();
    }


    private void loginBack() {
        EventBus.getDefault().post(bean);
        jumpActivitySetResult(null);
        dialogDismiss();
    }

    private void face(final String url, final String uid) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap faceBitmap = null;
                try {
                    faceBitmap = BitmapTools.loadImageFromUrl(url, false);
                    if (faceBitmap != null)
                        for (int i = 1; i < 4; i++) {
                            uploadThreeToUpy(PerfectDatumActivity.this, faceBitmap,
                                    uid, "face", i);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }


    private void uploadThreeToUpy(Context context, Bitmap bitmap, String uid,
                                  String name, int index) {
        String sdFile = CacheFileManger.getCacheImagePath();
        UploadTask task = new UploadTask();
        task.uploadFace(uid, index);
        switch (index) {
            case 1:
                // big
                Bitmap big = BitmapTools.zoomImage(bitmap, 200, 133);
                String bigPath = sdFile + "/" + name + "_big.jpg";
                File bigFile = new File(bigPath);
                boolean bigBol = BitmapTools.saveJPGE_After(big, bigFile);
                if (bigBol) {
                    task.execute(bigPath);
                }
                break;
            case 2:
                // middle
                Bitmap middle = BitmapTools.zoomImage(bitmap, 120, 120);
                String middlePath = sdFile + "/" + name + "_middle.jpg";
                File middleFile = new File(middlePath);
                boolean middleBol = BitmapTools.saveJPGE_After(middle, middleFile);
                if (middleBol) {
                    task.execute(middlePath);
                }
                break;
            case 3:
                // small
                Bitmap small = BitmapTools.zoomImage(bitmap, 48, 48);
                String smallPath = sdFile + "/" + name + "_small.jpg";
                File smallFile = new File(smallPath);
                boolean smallBol = BitmapTools.saveJPGE_After(small, smallFile);
                if (smallBol) {
                    task.execute(smallPath);
                }
                break;

            default:
                break;
        }

    }


    /**
     * 修改性别
     *
     * @param sexStr
     */
    private void requestUpdateSex(final String sexStr) {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMIT);
        params.addBodyParameter("formhash", UserManger.getFormhash());
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
        params.addBodyParameter("timeoffset", "8");

        XutilsHttp.Post(params, null);


    }


    /**
     * 修改生日
     *
     * @param year
     * @param mounth
     * @param day
     */
    private void updateBirthday(String year, String mounth, String day) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_UPDATE_BIRTHDAY);
        params.addBodyParameter("birthyear", String.valueOf(year));
        params.addBodyParameter("birthmonth", String.valueOf(mounth));
        params.addBodyParameter("birthday", String.valueOf(day));

        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("profilesubmit", "true");
        params.addBodyParameter("profilesubmitbtn", "true");
        params.addBodyParameter("timeoffset", "8");

        XutilsHttp.Post(params, null);
    }


}
