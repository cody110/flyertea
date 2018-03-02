package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.GroupmembershipsBean;
import com.ideal.flyerteacafes.model.entity.UserCardBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.popupwindow.ChooseTimePopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.CommonBottomPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.GroupTypePopupWindow;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.ui.view.LtreLayout;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.UriTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/22.
 */
@ContentView(R.layout.activity_add_regular_card)
public class AddRegularCardActivity extends BaseActivity {

    @ViewInject(R.id.type_layout)
    private LtctriLayout type_layout;
    @ViewInject(R.id.grade_layout)
    private LtctriLayout grade_layout;
    @ViewInject(R.id.id_layout)
    private LtreLayout id_layout;
    @ViewInject(R.id.youxiaoqi_layout)
    LtctriLayout youxiaoqi_layout;
    @ViewInject(R.id.rengzhen_img)
    private ImageView rengzhen_img;

    private UserCardBean.CardInfo cardInfo;
    private GroupmembershipsBean data;
    private GroupmembershipsBean.Type type;
    GroupmembershipsBean.Memberships memberships;
    public static final int REQUESTIMAGECODE = 1;
    private String imgPath;
    private String groupname, groupid, mid, mname, from, medalid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        cardInfo = (UserCardBean.CardInfo) getIntent().getSerializableExtra("data");
        if (cardInfo != null) {
            bindData(cardInfo);
        } else {
            medalid = getIntent().getStringExtra("medalid");
            groupname = getIntent().getStringExtra("groupname");
            groupid = getIntent().getStringExtra("groupid");
            mid = getIntent().getStringExtra("mid");
            mname = getIntent().getStringExtra("mname");
            from = getIntent().getStringExtra("from");
            if (!TextUtils.isEmpty(groupname)) {
                type_layout.setTextByCt(groupname);
                grade_layout.setTextByCt(mname);
            }
        }
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void requestData() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_GROUPMEMBERSHIPS);
        XutilsHttp.Get(params, new DataCallback<GroupmembershipsBean>() {
            @Override
            public void flySuccess(DataBean<GroupmembershipsBean> result) {
                data = result.getDataBean();
            }
        });
    }

    private void bindData(UserCardBean.CardInfo cardInfo) {
        type_layout.setTextByCt(cardInfo.getName());
        grade_layout.setTextByCt(cardInfo.getMembership_name());
        id_layout.setTextByRe(cardInfo.getCode());
        DataUtils.downloadPicture(rengzhen_img, cardInfo.getImg_url(), R.drawable.icon_def);
    }

    @Event({R.id.toolbar_left, R.id.type_layout, R.id.grade_layout, R.id.img_layout, R.id.youxiaoqi_layout, R.id.save_btn})
    private void click(View v) {

        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;
            case R.id.type_layout:
                if (data == null) return;
                if (!TextUtils.isEmpty(groupid)) return;
                GroupTypePopupWindow popupWindow = new GroupTypePopupWindow(AddRegularCardActivity.this, data.getHotelMembership(), new CommonBottomPopupwindow.ISureOK<GroupmembershipsBean.Type>() {
                    @Override
                    public void selectItem(GroupmembershipsBean.Type type, int pos) {
                        AddRegularCardActivity.this.type = type;
                        type_layout.setTextByCt(type.getName());
                    }
                });
                popupWindow.showAtLocation(type_layout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.grade_layout:
                if (data == null) return;
                if (!TextUtils.isEmpty(mid)) return;
                if (TextUtils.isEmpty(type_layout.getTextByCt())) {
                    ToastUtils.showToast("请先选择常客卡类型");
                } else {
                    if (type == null) {
                        for (int i = 0; i < data.getHotelMembership().size(); i++) {
                            if (TextUtils.equals(data.getHotelMembership().get(i).getGroupid(), cardInfo.getGroupid())) {
                                type = data.getHotelMembership().get(i);
                            }
                        }
                    }
                    if (DataUtils.isEmpty(type.getMemberships())) return;
                    CommonBottomPopupwindow popupwindow = new CommonBottomPopupwindow<GroupmembershipsBean.Memberships>(AddRegularCardActivity.this, type.getMemberships(), new CommonBottomPopupwindow.ISureOK<GroupmembershipsBean.Memberships>() {
                        @Override
                        public void selectItem(GroupmembershipsBean.Memberships memberships, int pos) {
                            grade_layout.setTextByCt(memberships.getName());
                            AddRegularCardActivity.this.memberships = memberships;
                        }
                    }) {
                        @Override
                        protected void bindDatas(List<GroupmembershipsBean.Memberships> datas) {
                            ListWheelAdapter adapter = new ListWheelAdapter<GroupmembershipsBean.Memberships>(AddRegularCardActivity.this, datas) {
                                @Override
                                public CharSequence getItemText(int index) {
                                    return items.get(index).getName();
                                }
                            };
                            wheelView.setViewAdapter(adapter);
                        }
                    };
                    popupwindow.showAtLocation(type_layout, Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.img_layout:
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, REQUESTIMAGECODE);
                break;

            case R.id.youxiaoqi_layout:
                ChooseTimePopupWindow chooseTimePopupWindow = new ChooseTimePopupWindow(this);
                chooseTimePopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;

            case R.id.save_btn:
                if (cardInfo != null) {
                    if (imgPath != null) {
                        showDialog();
                        sendPic();
                    } else {
                        requestCommit(cardInfo.getImg_url());
                    }
                } else {
                    if (type == null && TextUtils.isEmpty(groupid)) {
                        ToastUtils.showToast("请选择常客卡类型");
                    } else if (memberships == null && TextUtils.isEmpty(mid)) {
                        ToastUtils.showToast("请选择常客卡等级");
                    } else if (DataUtils.isEmpty(id_layout.getTextByRe())) {
                        ToastUtils.showToast("请输入常客卡卡号");
                    } else if (imgPath == null) {
                        ToastUtils.showToast("请选择认证图片");
                    } else if (TextUtils.isEmpty(youxiaoqi_layout.getTextByCt())) {
                        ToastUtils.showToast("请选择有效期");
                    } else {
                        showDialog();
                        sendPic();
                    }
                }
                break;
        }
    }

    /**
     * 上传图片
     */
    private void sendPic() {


        Calendar date = Calendar.getInstance();

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);
        final StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append("-");
        sb.append(String.format("%0" + 2 + "d", month));
        sb.append("-");
        sb.append(String.format("%0" + 2 + "d", day));
        sb.append("/" + DateUtil.getDateline() + ".jpg");

        UploadTask task = new UploadTask();
        task.setIUploadStatus(new UploadTask.IUploadStatus() {
            @Override
            public void uploadStatus(boolean result, Object data) {
                if (result) {
                    requestCommit(sb.toString());
                } else {
                    dialogDismiss();
                }
            }
        });
        task.uploadThread("/medals/" + sb.toString());
        task.execute(imgPath);
    }


    /**
     * 保存
     *
     * @param img_url
     */
    private void requestCommit(String img_url) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MEDALS_HOTEL_SHENGLING);
//        Map<String, String> value = new HashMap<>();
//        if (cardInfo == null) {
//            value.put("code", id_layout.getTextByRe());
//            if (type == null) {
//                value.put("groupid", groupid);
//            } else {
//                value.put("groupid", type.getGroupid());
//            }
//            if (memberships == null) {
//                value.put("mid", mid);
//            } else {
//                value.put("mid", memberships.getMid());
//            }
//
//            value.put("type", "hotel");
//        } else {
//            value.put("id", cardInfo.getId());
//            value.put("code", id_layout.getTextByRe());
//            if (type == null) {
//                value.put("groupid", cardInfo.getGroupid());
//            } else {
//                value.put("groupid", type.getGroupid());
//            }
//            if (memberships == null) {
//                value.put("mid", cardInfo.getMid());
//            } else {
//                value.put("mid", memberships.getMid());
//            }
//            value.put("type", "hotel");
//        }
//        params.setBodyJson(value);


        params.addQueryStringParameter("hyh", id_layout.getTextByRe());
        params.addQueryStringParameter("yxq", youxiaoqi_layout.getTextByCt());
        params.addQueryStringParameter("medalid", medalid);
        params.addQueryStringParameter("picurl", img_url);


        XutilsHttp.Get(params, new DataCallback<UserCardBean.CardInfo>() {

            @Override
            public void flySuccess(DataBean<UserCardBean.CardInfo> result) {
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {

                    if (TextUtils.equals(from, "MyMedalsFragment")) {
                        showRemindDialog(result.getDataBean());
                    } else {
                        ToastUtils.showToast("保存成功");
                        jumpResult(result.getDataBean());
                    }
                } else {
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

    private void shengLingCard() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MEDALS_HOTEL_SHENGLING);
        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("medalid", medalid);
        params.addBodyParameter("operation", "");
        params.addBodyParameter("is_upload", "1");
        params.addBodyParameter("handlekey", "medal2");
        params.addBodyParameter("hyh", "23434343");
        params.addBodyParameter("is_upload", "1");
        params.addBodyParameter("yxq", "2018-12-05");
        params.addBodyParameter("medalsubmit", "true");
        params.addBodyParameter("hyzl[]", new File(imgPath));

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {

            }
        });
    }


    public void jumpResult(UserCardBean.CardInfo data) {
        Bundle bundle = new Bundle();
        bundle.putString("medalid", medalid);
        bundle.putSerializable("data", data);
        jumpActivitySetResult(bundle);
    }


    private void showRemindDialog(final UserCardBean.CardInfo data) {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setIsOneButton(true);
        builder.setMessage("申请已收到，会尽快审核");
        builder.setPositiveButton("确定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                jumpResult(data);
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTIMAGECODE) {
                Bitmap bitmap = null;
                Uri originalUri = data.getData();
                if (originalUri == null)
                    return;
                bitmap = BitmapTools.revitionImageSize(originalUri, DensityUtil.dip2px(40), this);
                if (bitmap != null) {
                    rengzhen_img.setImageBitmap(bitmap);

                    String path;// 图片路径
                    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                    if (!isKitKat) {
                        path = UriTools.getFilePathByUri(this, originalUri);
                    } else {
                        path = UriTools.getFilePathByUriTWO(AddRegularCardActivity.this, originalUri);
                    }

                    if (path == null || path.length() == 0)
                        return;
                    imgPath = path;

                } else {
                    ToastUtils.showToast("获取图片失败");
                }
            }
        }
    }

    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_SHENGQING_MEDAILS_TIME) {
            youxiaoqi_layout.setTextByCt(tagEvent.getBundle().getString("data"));
        }

    }
}
