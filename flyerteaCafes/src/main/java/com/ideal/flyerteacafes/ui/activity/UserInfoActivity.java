package com.ideal.flyerteacafes.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.RoundImageView;
import com.ideal.flyerteacafes.ui.popupwindow.UpdateFacePopupWindow;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * 用户资料信息
 *
 * @author fly
 */
@ContentView(R.layout.activity_userinfo)
public class UserInfoActivity extends BaseActivity implements UploadTask.IUploadStatus {

    @ViewInject(R.id.user_face_img)
    private RoundImageView headImg;

    @ViewInject(R.id.person_user_name)
    private TextView userName;

    @ViewInject(R.id.personal_signature)
    private TextView singature;

    @ViewInject(R.id.my_grade)
    private ImageView userGradle;
    @ViewInject(R.id.my_rengzheng)
    private ImageView rengzhengView;
    @ViewInject(R.id.mrl_loginout_btn)
    View loginBackBtn;

    private UserBean userBean;

    private int w_screen;
    private Uri photoUri;// 有时候返回拍照返回Uri为null,需要拍照之前手动指定Uri、
    private static final int requestImageCode = 1, requestChooseType = 3,
            requestCameraCode = 2;

    private Bitmap faceBitmap = null;


    UpdateFacePopupWindow popupWindow;

    static public final int PHOTO = 1, ALBUM = 2;

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
                        values.put(Media.TITLE, filename);

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
                default:
                    break;
            }
        }

    };

    @Override
    public boolean isSystemBarTransparent() {
        return true;
    }

    @Override
    public boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);

        w_screen = SharedPreferencesString.getInstances(this).getIntToKey(
                "w_screen");

        userBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (userBean == null)
            userBean = preferences.getUserInfo();

        faceBitmap = getIntent().getParcelableExtra("faceBitmap");

        initView();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(UserBean bean) {
        this.userBean = bean;
        initView();
    }

    private void initView() {
        if (!UserManger.isLogin())
            return;
        if (faceBitmap != null)
            headImg.setImageBitmap(faceBitmap);
        else
            DataUtils.downloadPicture(headImg, userBean.getFace(), R.drawable.def_face);
        View mailbox_item = findViewById(R.id.userinfo_mailbox_item);
        TextView mailbox_name = (TextView) mailbox_item
                .findViewById(R.id.include_userinfo_item_left_text);
        TextView mailbox_text = (TextView) mailbox_item
                .findViewById(R.id.include_userinfo_item_right_text);
        View readPermission = findViewById(R.id.userinfo_readpermission_item);
        TextView readPermission_name = (TextView) readPermission
                .findViewById(R.id.include_userinfo_item_left_text);
        TextView readPermission_text = (TextView) readPermission
                .findViewById(R.id.include_userinfo_item_right_text);
        View point_item = findViewById(R.id.userinfo_point_item);
        TextView point_name = (TextView) point_item
                .findViewById(R.id.include_userinfo_item_left_text);
        TextView point_explain = (TextView) point_item
                .findViewById(R.id.include_userinfo_item_center_text);
        TextView point_text = (TextView) point_item
                .findViewById(R.id.include_userinfo_item_right_text);
        View fermi_item = findViewById(R.id.userinfo_fermi_item);
        TextView fermi_name = (TextView) fermi_item
                .findViewById(R.id.include_userinfo_item_left_text);
        TextView fermi_text = (TextView) fermi_item
                .findViewById(R.id.include_userinfo_item_right_text);

        View sex_item = V.f(this, R.id.userinfo_sex_item);
        TextView sex_name = V.f(sex_item, R.id.include_userinfo_item_left_text);
        TextView sex_text = V.f(sex_item, R.id.include_userinfo_item_right_text);

        View location_item = V.f(this, R.id.userinfo_location_item);
        TextView location_name = V.f(location_item, R.id.include_userinfo_item_left_text);
        TextView location_text = V.f(location_item, R.id.include_userinfo_item_right_text);

        try {
            mailbox_name.setText(getString(R.string.userinfo_mailbox));
            mailbox_text.setText(userBean.getEmail());
            userName.setText(userBean.getMember_username());
            singature.setText(userBean.getSightml());
            location_text.setText(userBean.getResideprovince() + "\t" + userBean.getResidecity());
            if (userBean.getGender() == 1)
                sex_text.setText("男");
            else if (userBean.getGender() == 2)
                sex_text.setText("女");
            else
                sex_text.setText("保密");

            readPermission_name.setText(getString(R.string.userinfo_authority));
            readPermission_text.setText(userBean.getReadaccess() + "");

            point_name.setText(getString(R.string.userinfo_point));
            point_text.setText(userBean.getCredits() + "");
            fermi_name.setText(getString(R.string.userinfo_fermi));
            fermi_text.setText(userBean.getExtcredits6() + "");

            sex_name.setText("性别");
            location_name.setText("所在地");

            DataUtils.setGroupName(userGradle, userBean.getGroupname());

            if (userBean.getHas_sm().equals("2")) {
                rengzhengView.setImageResource(R.drawable.renzheng);
            } else {
                rengzhengView.setImageResource(R.drawable.hui_renzheng);
            }

        } catch (Exception e) {
        }

    }

    @Event({R.id.btn_edit, R.id.userinfo_back, R.id.mll_user_face_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", userBean);
                jumpActivity(EditDataActivity.class, bundle);
                break;
            case R.id.userinfo_back:
                finish();
                break;
            case R.id.mll_user_face_layout:
                if (popupWindow == null)
                    popupWindow = new UpdateFacePopupWindow(this, handler);
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
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
                Bitmap bitmap = BitmapTools.revitionImageSize(originalUri,
                        w_screen / 2, this);
                if (bitmap != null) {

                    for (int i = 1; i < 4; i++) {
                        uploadThreeToUpy(UserInfoActivity.this, bitmap,
                                userBean.getMember_uid(), "face", i);
                    }

                } else {
                    Toast.makeText(UserInfoActivity.this, "获取图片失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private void uploadThreeToUpy(Context context, Bitmap bitmap, String uid,
                                  String name, int index) {
        String sdFile = CacheFileManger.getCacheImagePath();

        UploadTask task = new UploadTask();
        task.uploadFace(uid, index);
        task.setIUploadStatus(this);

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
                this.faceBitmap = middle;
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


    @Override
    public void uploadStatus(boolean result, Object data) {
        if (result) {
            if (faceBitmap != null) {
                headImg.setImageBitmap(faceBitmap);
                EventBus.getDefault().post(faceBitmap);
            }
            Toast.makeText(getApplicationContext(), "成功",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "失败",
                    Toast.LENGTH_LONG).show();
        }
    }
}
