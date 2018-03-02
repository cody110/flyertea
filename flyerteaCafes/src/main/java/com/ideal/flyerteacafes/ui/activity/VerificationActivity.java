package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_verification)
public class VerificationActivity extends BaseActivity {

    @ViewInject(R.id.include_title_right_btn)
    View rightView;
    @ViewInject(R.id.include_title_text)
    TextView titleView;
    @ViewInject(R.id.include_title_right_img)
    ImageView rightImg;
    @ViewInject(R.id.include_title_right_text)
    TextView rightText;
    @ViewInject(R.id.verification_edittext)
    EditText verificationET;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleView.setText(getString(R.string.verification_title));
        rightView.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        rightText.setText(getString(R.string.verification_right_text));
        uid = getIntent().getStringExtra("uid");
        verificationET.setText("我是");
        verificationET.setSelection(verificationET.getText().length());

    }

    @Event({R.id.include_title_menu_btn, R.id.include_title_right_btn})
    private void onActionClick(View v) {
        switch (v.getId()) {
            case R.id.include_title_menu_btn:
                finish();
                break;

            case R.id.include_title_right_btn:
                String message = verificationET.getText().toString();
                if (message.length() > 0) {
                    requsetAddFriend(message);
                } else {
                    BToast("请输入验证消息");
                }
                break;

            default:
                break;
        }
    }

    private void requsetAddFriend(String message) {
        String formhash = SharedPreferencesString.getInstances(this).getStringToKey("formhash");
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_ADDFRIEND);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("addsubmit", "true");
        params.addBodyParameter("formhash", formhash);
        params.addBodyParameter("gid", "6");
        params.addBodyParameter("handlekey", "a_near_friend_" + uid);
        params.addBodyParameter("note", message);
        params.addQueryStringParameter("transcode", "yes");

        XutilsHttp.Post( params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean != null) {
                    BToast(bean.getMessage());
                } else {
                    BToast("验证消息发送失败!");
                }
                finish();
            }
        });
    }

}
