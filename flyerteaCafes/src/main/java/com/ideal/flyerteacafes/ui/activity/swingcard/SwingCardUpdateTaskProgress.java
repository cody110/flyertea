package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;
import com.ideal.flyerteacafes.model.entity.TaskPriodBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.popupwindow.TaskChooseTimePop;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.UriTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by fly on 2017/4/17.
 */
@ContentView(R.layout.activity_updatetask)
public class SwingCardUpdateTaskProgress extends BaseActivity {

    public static final String BUNDLE_MISSIONID = "missionid";
    public static final int REQUESTIMAGECODE = 1;
    public static final String BUNDLE_DATA = "data";
    int missionid;

    TaskDetailsBean taskDetailsBean;
    private String imgPath;
    private long time;
    private String myCardMissionPeriodId = "";

    @ViewInject(R.id.swingcard_price)
    EditText swingcard_price;
    @ViewInject(R.id.swingcard_time)
    TextView swingcard_time;
    @ViewInject(R.id.swingcard_shop)
    EditText swingcard_shop;
    @ViewInject(R.id.swingcard_mcc)
    EditText swingcard_mcc;
    @ViewInject(R.id.swingcard_pic)
    ImageView swingcard_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        showSoftInputFromWindow(this, swingcard_price);
        taskDetailsBean = (TaskDetailsBean) getIntent().getSerializableExtra(BUNDLE_DATA);
        if (taskDetailsBean == null) {
            missionid = getIntent().getIntExtra(BUNDLE_MISSIONID, 0);
            requestDetails();
        }


        final Calendar c = Calendar.getInstance();
        time = c.getTimeInMillis() / 1000;
        swingcard_time.setText("今天 " + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
    }

    private void requestDetails() {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TASK_DETAILS);
        params.addQueryStringParameter("missionid", String.valueOf(missionid));
        XutilsHttp.Get(params, new DataCallback<TaskDetailsBean>(DataCallback.DATA_KEY_TASK_DETAILS) {
            @Override
            public void flySuccess(DataBean<TaskDetailsBean> result) {
                taskDetailsBean = result.getDataBean();
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

    /**
     * 上传图片
     */
    private void sendPic() {
        final String webPath = "/flyertea-app/" + UserManger.getUserInfo().getMember_uid() + "/" + DateUtil.getDateline() + ".jpg";

        UploadTask task = new UploadTask();
        task.setIUploadStatus(new UploadTask.IUploadStatus() {
            @Override
            public void uploadStatus(boolean result, Object data) {
                if (result) {
                    requestCommit("http://app.flyert.com" + webPath);
                } else {
                    dialogDismiss();
                }
            }
        });
        task.uploadFeed(0, webPath);
        task.execute(imgPath);
    }


    private void requestCommit(String imgWebPath) {
        if (taskDetailsBean == null) return;
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TASK_PROGRESS);
        HashMap<String, String> map = new HashMap<>();
        map.put("MCC", swingcard_mcc.getText().toString());
        map.put("bankId", taskDetailsBean.getBankId());
        map.put("billPic", imgWebPath);//图片链接
        map.put("cardMissionId", taskDetailsBean.getCardMissionId());
        map.put("channelId", taskDetailsBean.getChannelId());
        map.put("myCardMissionId", taskDetailsBean.getMyCardMissionId());
        map.put("periodid", myCardMissionPeriodId);//周期id
        map.put("posTime", String.valueOf(time));
        map.put("posValue", swingcard_price.getText().toString());
        map.put("shop", swingcard_shop.getText().toString());
        params.setBodyJson(map);
        XutilsHttp.Post(params, new MapCallback() {

            @Override
            public void flySuccess(MapBean result) {
                if (result.getData() == null) {
                    ToastUtils.showToast(result.getMessage());
                } else {
                    ToastUtils.showToast("更新任务进度成功");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isStatusChange", true);
                    bundle.putInt(SwingCardTaskDetails.BUNDLE_MISSIONID, DataTools.getInteger(taskDetailsBean.getMyCardMissionId()));
                    jumpActivity(SwingCardTaskDetails.class, bundle);
                    finish();
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

    @Event({R.id.btn_ok, R.id.toolbar_left, R.id.swingcard_pic, R.id.swingcard_time_layout})
    private void onclick(View v) {
        switch (v.getId()) {

            case R.id.toolbar_left:
                finish();
                break;
            case R.id.btn_ok:
                if (taskDetailsBean == null) return;
                myCardMissionPeriodId = "";
                float limit = 0;
                for (TaskPriodBean bean : taskDetailsBean.getPeriods()) {

                    if (DataTools.getInteger(bean.getEndTime()) >= time && DataTools.getInteger(bean.getStartTime()) < time) {
                        myCardMissionPeriodId = bean.getMyCardMissionPeriodId();
                        limit = bean.getPertimeLimit();
                    }

                }

                if (TextUtils.isEmpty(myCardMissionPeriodId)) {
                    DialogUtils.remindDialog(SwingCardUpdateTaskProgress.this, "刷卡日期不在任务周期内");
                } else if (TextUtils.isEmpty(swingcard_price.getText().toString())) {
                    DialogUtils.remindDialog(SwingCardUpdateTaskProgress.this, "请输入刷卡金额");
                } else if (DataTools.getDouble(swingcard_price.getText().toString()) < limit) {
                    DialogUtils.remindDialog(SwingCardUpdateTaskProgress.this, "刷卡金额不能小于" + limit + "元");
                } else {
                    showDialog();
                    if (TextUtils.isEmpty(imgPath)) {
                        requestCommit("");
                    } else {
                        sendPic();
                    }
                }
                break;

            case R.id.swingcard_pic:
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, REQUESTIMAGECODE);
                break;

            case R.id.swingcard_time_layout:
                TaskChooseTimePop pop = new TaskChooseTimePop(SwingCardUpdateTaskProgress.this);
                pop.showAtLocation(swingcard_price, Gravity.BOTTOM, 0, 0);
                pop.setIChooseTime(new TaskChooseTimePop.IChooseTime() {
                    @Override
                    public void chooseData(String showTime, long dataline) {
                        swingcard_time.setText(showTime);
                        time = dataline / 1000;
                    }
                });
                break;


        }
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
                    swingcard_pic.setImageBitmap(bitmap);

                    String path;// 图片路径
                    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                    if (!isKitKat) {
                        path = UriTools.getFilePathByUri(this, originalUri);
                    } else {
                        path = UriTools.getFilePathByUriTWO(
                                SwingCardUpdateTaskProgress.this, originalUri);
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
}
