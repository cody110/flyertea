package com.ideal.flyerteacafes.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.entity.UserCardBean;
import com.ideal.flyerteacafes.model.entity.UserProfileBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.RealNameActivity;
import com.ideal.flyerteacafes.ui.activity.myinfo.RegularCardInfoActivity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMyFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.MyFragmentPresenter;
import com.ideal.flyerteacafes.ui.popupwindow.BirthdayPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.StringPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.UpdateFacePopupWindow;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/18.
 */
@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity {

    private Uri photoUri;
    static public final int PHOTO = 1, ALBUM = 2, WHAT_SEX = 3;
    private static final int requestImageCode = 1, requestCameraCode = 2, REQUEST_NORMAL = 3, REQUEST_REALNAME = 4, REQUEST_CARDLIST = 5, REQUEST_QIANMING = 6;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PHOTO:
                    // 没有储存卡要挂掉，暂未找到解决方法
                    if (ApplicationTools.sdCardExist()) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                                "yyyy_MM_dd_HH_mm_ss");
                        String filename = timeStampFormat.format(new Date());
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Audio.Media.TITLE, filename);
                        photoUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, requestCameraCode);
                    }
                    break;

                case ALBUM:
                    Intent gainImage = new Intent(Intent.ACTION_GET_CONTENT);
                    gainImage.addCategory(Intent.CATEGORY_OPENABLE);
                    gainImage.setType("image/*");
                    startActivityForResult(gainImage, requestImageCode);
                    break;
                case WHAT_SEX:
                    String sex = (String) msg.obj;
                    requestUpdate(sex);
                    break;
            }
        }

    };


    UserCardBean userCardBean;
    MyFragmentPresenter mPresenter = new MyFragmentPresenter();
    UpdateFacePopupWindow popupWindow;
    StringPopupWindow sexPop;
    BirthdayPopupwindow birthdayPopupwindow;


    @ViewInject(R.id.face_img)
    ImageView face_img;
    @ViewInject(R.id.username_layout)
    LtctriLayout username_layout;
    @ViewInject(R.id.sex_layout)
    LtctriLayout sex_layout;
    @ViewInject(R.id.qianming_layout)
    LtctriLayout qianming_layout;
    @ViewInject(R.id.realname_layout)
    LtctriLayout realname_layout;
    @ViewInject(R.id.info_text)
    TextView info_text;
    @ViewInject(R.id.birthday_layout)
    LtctriLayout birthday_layout;

    private UserProfileBean userProfileBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        username_layout.setVisibleByRi(View.INVISIBLE);
        requestUserProfile();
        bindData();


        mPresenter.attachView(new IMyFragment() {
            @Override
            public void callbackFaceLocaPath(String locaPath) {
                DataUtils.downloadPicture(face_img, locaPath, R.drawable.def_face_2);
            }

            @Override
            public void callbackTaskAll(MyTaskAllBean bean) {

            }

            @Override
            public void setViewByIsSigin(boolean isSigin) {

            }

            @Override
            public void isSetBirthday(boolean bol) {

            }
        });

        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode mode1 = new SegmentedStringMode("1、完善实名信息，审核通过将发放", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode2 = new SegmentedStringMode("实名认证标志", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode3 = new SegmentedStringMode("，提高您在众多飞客中的", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode4 = new SegmentedStringMode("威信", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode5 = new SegmentedStringMode("，并获得线下某些活动的", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode6 = new SegmentedStringMode("参与权或者优先权", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode7 = new SegmentedStringMode("，如单身派对、人脉拓展鸡尾酒晚宴等\n", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode8 = new SegmentedStringMode("2、完善酒店、航空、信用卡的常客信息，认证通过后可获得", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode9 = new SegmentedStringMode("常客会员认证", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode10 = new SegmentedStringMode("，未来还可能有", R.dimen.app_body_size_2, R.color.app_body_grey);
        SegmentedStringMode mode11 = new SegmentedStringMode("商家神秘大礼", R.dimen.app_body_size_2, R.color.app_body_black);
        SegmentedStringMode mode12 = new SegmentedStringMode("赠送哦", R.dimen.app_body_size_2, R.color.app_body_grey);

        modeList.add(mode1);
        modeList.add(mode2);
        modeList.add(mode3);
        modeList.add(mode4);
        modeList.add(mode5);
        modeList.add(mode6);
        modeList.add(mode7);
        modeList.add(mode8);
        modeList.add(mode9);
        modeList.add(mode10);
        modeList.add(mode11);
        modeList.add(mode12);

        info_text.setText(DataUtils.getSegmentedDisplaySs(modeList));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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
            updateBirthday(year, month, day);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == requestImageCode
                    || requestCode == requestCameraCode) {
                Uri originalUri = null;
                if (requestCode == requestImageCode) {// 选择相册照片
                    originalUri = intent.getData();

                } else if (requestCode == requestCameraCode) {// 拍照
                    if (intent != null && intent.getData() != null) {
                        originalUri = intent.getData();
                    }
                    if (originalUri == null)
                        originalUri = photoUri;
                }
                if (originalUri == null)
                    return;
                mPresenter.uploadFace(originalUri);
            } else if (requestCode == REQUEST_NORMAL) {
                UserCardBean.CardInfo cardinfo = (UserCardBean.CardInfo) intent.getSerializableExtra("data");
                if (cardinfo == null) return;
                List<UserCardBean.CardInfo> list = new ArrayList<>();
                list.add(cardinfo);
                userCardBean.setVips(list);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", userCardBean);
                jumpActivity(RegularCardInfoActivity.class, bundle);
            } else if (requestCode == REQUEST_REALNAME) {
                requestUserProfile();
            } else if (requestCode == REQUEST_QIANMING) {
                String qianming = intent.getStringExtra("qianming");
                qianming_layout.setTextByCt(qianming);
            }
        }
    }

    @Event({R.id.toolbar_left, R.id.realname_layout, R.id.face_layout, R.id.username_layout, R.id.sex_layout, R.id.qianming_layout, R.id.birthday_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.face_layout:
                if (popupWindow == null)
                    popupWindow = new UpdateFacePopupWindow(this, handler);
                popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.username_layout:

                break;

            case R.id.sex_layout:
                if (isUpdateGender()) {
                    if (sexPop == null) {
                        String[] sex_array = getResources().getStringArray(R.array.sex_array);
                        sexPop = new StringPopupWindow(this, handler, sex_array, WHAT_SEX);
                    }
                    sexPop.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;

            case R.id.qianming_layout:
                Bundle bundleQM = new Bundle();
                bundleQM.putString("qianming", UserManger.getUserInfo().getSightml());
                jumpActivityForResult(QianmingActivity.class, bundleQM, REQUEST_QIANMING);
                break;


            case R.id.realname_layout:
                if (userProfileBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", userProfileBean);
                    jumpActivityForResult(RealNameActivity.class, bundle, REQUEST_REALNAME);
                }
                break;

            case R.id.birthday_layout:
                if (isUpdateBirthday()) {
                    if (birthdayPopupwindow == null)
                        birthdayPopupwindow = new BirthdayPopupwindow(this);
                    birthdayPopupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
                break;
        }
    }

    private void bindData() {
        UserBean userBean = UserManger.getUserInfo();
        if (userBean != null) {
            DataUtils.downloadPicture(face_img, userBean.getFace(), R.drawable.def_face);
            username_layout.setTextByCt(userBean.getMember_username());
            if (userBean.getGender() == 1)
                sex_layout.setTextByCt("男");
            else if (userBean.getGender() == 2)
                sex_layout.setTextByCt("女");
            else
                sex_layout.setTextByCt("保密");
            sex_layout.setVisibleByRi(isUpdateGender() ? View.VISIBLE : View.INVISIBLE);
            qianming_layout.setTextByCt(userBean.getSightml());

        }
    }


    /**
     * true=可以修改性别
     *
     * @return
     */
    private boolean isUpdateGender() {
        if (UserManger.getUserInfo().getGender() == 1 || UserManger.getUserInfo().getGender() == 2) {
            return false;
        }
        return true;
    }

    /**
     * true=可以修改生日
     * true=还没有没有设置过生日
     *
     * @return
     */
    private boolean isUpdateBirthday() {
        if (userProfileBean == null) return false;
        if (userProfileBean.getRealename() == null) return false;
        if (!TextUtils.isEmpty(userProfileBean.getRealename().getBirthyear()) && !TextUtils.equals(userProfileBean.getRealename().getBirthyear(), "0")
                && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthmonth()) && !TextUtils.equals(userProfileBean.getRealename().getBirthmonth(), "0")
                && !TextUtils.isEmpty(userProfileBean.getRealename().getBirthday()) && !TextUtils.equals(userProfileBean.getRealename().getBirthday(), "0")) {
            return false;
        }
        return true;
    }

    /**
     * 修改性别
     *
     * @param sexStr
     */
    private void requestUpdate(final String sexStr) {
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

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean != null) {
                    if (bean.getCode() != null && bean.getCode().equals("update_profile_done")) {
                        BToast("性别修改成功！");
                        sex_layout.setTextByCt(sexStr);
                    } else {
                        BToast("性别修改失败！");
                    }
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });


    }


    /**
     * 请求个人相关信息
     * 个性签名，实名信息
     */
    private void requestUserProfile() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_USERPROFILE);
        XutilsHttp.Get(params, new DataCallback<UserProfileBean>() {
            @Override
            public void flySuccess(DataBean<UserProfileBean> result) {
                if (result == null) return;
                if (result.getDataBean() == null) return;
                userProfileBean = result.getDataBean();
                if (result.getDataBean().getRealename() == null) return;
                realname_layout.setTextByCt(result.getDataBean().getRealename().getStatus_desc());
                if (isUpdateBirthday()) {
                    birthday_layout.setTextByCt("设置生日，领取生日礼包");
                } else {
                    birthday_layout.setTextByCt(userProfileBean.getRealename().getBirthyear() + "-" + userProfileBean.getRealename().getBirthmonth() + "-" + userProfileBean.getRealename().getBirthday());
                    birthday_layout.setVisibleByRi(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 修改生日
     *
     * @param year
     * @param mounth
     * @param day
     */
    private void updateBirthday(final int year, final int mounth, final int day) {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_UPDATE_BIRTHDAY);
        params.addBodyParameter("birthyear", String.valueOf(year));
        params.addBodyParameter("birthmonth", String.valueOf(mounth));
        params.addBodyParameter("birthday", String.valueOf(day));

        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("profilesubmit", "true");
        params.addBodyParameter("profilesubmitbtn", "true");
        params.addBodyParameter("timeoffset", "8");

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean != null) {
                    if (bean.getCode() != null && bean.getCode().equals("update_profile_done")) {
                        birthday_layout.setTextByCt(year + "-" + mounth + "-" + day);
                        birthday_layout.setVisibleByRi(View.INVISIBLE);
                        BToast("生日修改成功！");
                        birthdayPopupwindow.dismiss();

                        userProfileBean.getRealename().setBirthyear(String.valueOf(year));
                        userProfileBean.getRealename().setBirthmonth(String.valueOf(mounth));
                        userProfileBean.getRealename().setBirthday(String.valueOf(day));
                        //通知首页-我的 更新ui
                        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_BIRTHDAY_SUCCESS));
                    } else {
                        BToast("生日修改失败！");
                    }
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }


}
