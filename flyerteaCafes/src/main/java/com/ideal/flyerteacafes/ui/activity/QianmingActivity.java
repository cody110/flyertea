package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 个性签名
 *
 * @author fly
 */
@ContentView(R.layout.activity_verification)
public class QianmingActivity extends BaseActivity {

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
    @ViewInject(R.id.verification_shuoming)
    TextView shuoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }

    protected void init() {
        String qianming = getIntent().getStringExtra("qianming");
        if (!DataUtils.isEmpty(qianming)) {
            verificationET.setText(qianming);
            verificationET.setSelection(qianming.length());
        }
        titleView.setText(getString(R.string.qianming));
        rightView.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        rightText.setText(getString(R.string.accomplish));
        shuoming.setVisibility(View.INVISIBLE);
    }

    @Event({R.id.include_title_menu_btn, R.id.include_title_right_btn})
    private void onActionClick(View v) {
        switch (v.getId()) {
            case R.id.include_title_menu_btn:
                finish();
                break;

            case R.id.include_title_right_btn:
                requestUpdate(verificationET.getText().toString());
                break;

        }

    }


    private void requestUpdate(final String sightml) {
        if (TextUtils.isEmpty(sightml)) return;
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMIT);
        params.addBodyParameter("formhash", UserManger.getFormhash());
        params.addBodyParameter("sightml", sightml);
        params.addBodyParameter("profilesubmit", "true");
        params.addBodyParameter("profilesubmitbtn", "true");
        params.addBodyParameter("timeoffset", "8");
        params.addQueryStringParameter("transcode", "yes");

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean != null) {
                    if (bean.getCode() != null && bean.getCode().equals("update_profile_done")) {
                        BToast("个性签名修改成功！");
                        Bundle bundle = new Bundle();
                        bundle.putString("qianming", sightml);
                        jumpActivitySetResult(bundle);
                    } else {
                        BToast("个性签名修改失败！");
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
