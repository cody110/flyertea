package com.ideal.flyerteacafes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.ui.controls.RPLinearLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * 文章回复
 *
 * @author fly
 */
@ContentView(R.layout.activity_replyposts)
public class ArticleReplyActivity extends FragmentActivity {

    @ViewInject(R.id.replyPost_edit)
    private EditText editText;
    @ViewInject(R.id.reply_post_title)
    private TextView titleText;


    @ViewInject(R.id.root)
    private RPLinearLayout rootView;
    @ViewInject(R.id.reply_post_biaoqing_layout)
    private View biaoqingLayout;
    @ViewInject(R.id.reply_post_choose_button_layout)
    private LinearLayout chooseBtnLayout;
    @ViewInject(R.id.replyPost_choose_img)
    private ImageView chooseImg;
    @ViewInject(R.id.reply_post_choose_button_phono)
    private ImageView btnPhono;

    private String message;

    private int tid;
    private int fid;
    private int aid;
    private String activity;
    @ViewInject(R.id.reply_post_choose_button_biaoqing)
    private ImageView faceBtn;

    public static final int SHOW_KEY = 1, HIDE_KEY = 0;


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == HIDE_KEY) {
                faceBtn.setImageResource(R.drawable.jianpan);
                faceBtn.setTag(true);
                if (biaoqingLayout.getTag() != null
                        && (Boolean) biaoqingLayout.getTag()) {
                    biaoqingLayout.setTag(false);
                    biaoqingLayout.setVisibility(View.VISIBLE);
                    chooseBtnLayout.setVisibility(View.VISIBLE);
                } else {
                    if (chooseBtnLayout.getVisibility() != View.GONE)
                        chooseBtnLayout.setVisibility(View.GONE);
                }
            } else if (msg.what == SHOW_KEY) {
                faceBtn.setImageResource(R.drawable.reply_face);
                faceBtn.setTag(false);
                if (!activity.equals("RaidersActivity")) {
                    if (chooseBtnLayout.getVisibility() != View.VISIBLE)
                        chooseBtnLayout.setVisibility(View.VISIBLE);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        biaoqingLayout.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        rootView.setOnResizeListener(resizeListener);
        tid = getIntent().getIntExtra("tid", 0);
        fid = getIntent().getIntExtra("fid", 0);
        aid = getIntent().getIntExtra("aid", 0);
        titleText.setText("回复");
        chooseImg.setVisibility(View.GONE);
        btnPhono.setVisibility(View.GONE);
        activity = getIntent().getStringExtra("activity");
        if (activity.equals("RaidersActivity"))
            chooseBtnLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 选择的表情
     *
     * @param bean
     */
    public void onEventMainThread(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(editText);
        } else {
            editText.getText().insert(editText.getSelectionStart(), bean.getCode());
        }
    }

    private void hintFaceView() {
        biaoqingLayout.setVisibility(View.GONE);
    }

    @Event(R.id.reply_post_choose_button_biaoqing)
    public void showBiaoQingLayout(View v) {
        if ((boolean) faceBtn.getTag()) {
            biaoqingLayout.setVisibility(View.GONE);
            InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            biaoqingLayout.setTag(true);
            hideSoftInput(getCurrentFocus().getWindowToken());
        }
    }

    @Event(R.id.replyPost_close_btn)
    public void closePage(View v) {
        finish();
    }

    @Event(R.id.replyPost_commit)
    public void commit(View v) {

        message = editText.getText().toString();
        if (!DataUtils.isEmpty(message)) {
            requestReply();
        } else {
            BToast(getString(R.string.replypost_not_message_pointout));
        }
    }

    @Event(R.id.replyPost_edit)
    public boolean hideBiaoQingLayout(View v, MotionEvent event) {
        if (biaoqingLayout.getVisibility() != View.GONE) {
            biaoqingLayout.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 回复
     */
    private void requestReply() {

        if (fid != 0) {
            String formhash = SharedPreferencesString.getInstances(this)
                    .getStringToKey("formhash");

            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPLYPOST);
            params.addQueryStringParameter("message", message);
            params.addQueryStringParameter("formhash", formhash);
            params.addQueryStringParameter("tid", tid + "");
            params.addQueryStringParameter("fid", fid + "");
            XutilsHttp.Get(params, new StringCallback() {
                @Override
                public void flySuccess(String result) {
                    BaseBean bean = JsonAnalysis.jsonToReplyPost(result);
                    if (!DataUtils.isEmpty(bean.getMessage()))
                        BToast(bean.getMessage());
                    if (DataUtils.loginIsOK(bean.getCode())) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            });
        } else {
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ARTICLE_REPLY);
            params.addBodyParameter("aid", aid + "");
            params.addBodyParameter("message", message);
            XutilsHttp.Post(params, new StringCallback() {
                @Override
                public void flySuccess(String result) {
                    BaseBean bean = JsonAnalysis.jsonToReplyPost(result);
                    if (!DataUtils.isEmpty(bean.getMessage()))
                        BToast(bean.getMessage());
                    if (DataUtils.loginIsOK(bean.getCode())) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }


    }

    public void BToast(String message) {
        ToastUtils.showToast(message);
    }


    /**
     * 在自定义的布局里，实现这个接口，通过布局高度变化，区分软键盘是否弹出
     */
    private RPLinearLayout.OnResizeListener resizeListener = new RPLinearLayout.OnResizeListener() {

        @SuppressWarnings("unused")
        @Override
        public void OnResize(int w, int h, int oldw, int oldh) {
            if (oldw != 0 && oldh != 0) {
                if (h < oldh) {// 软键盘弹出来了
                    int keyboardHeight = oldh - h;
                    handler.sendEmptyMessage(SHOW_KEY);
                } else {
                    handler.sendEmptyMessage(HIDE_KEY);
                }
            }

        }
    };

    /**
     * 多种隐藏软件盘方法的其中一种
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
