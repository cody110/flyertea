package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.LoginVideoActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NotLoginFragment extends BaseFragment {

    @ViewInject(R.id.not_login_to_register)
    TextView toRegisterText;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_not_login, container, false);
        x.view().inject(this, view);
        toRegisterText.setText(Html.fromHtml("<u>" + "立即注册" + "</u>"));
        return view;
    }

    @Event({R.id.not_login_tologin_btn, R.id.not_login_to_register})
    public void onActionClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.not_login_tologin_btn:
                jumpActivity(LoginVideoActivity.class, null);
                break;

            case R.id.not_login_to_register:
                Bundle bundle = new Bundle();
                bundle.putString("activity", "register");
                jumpActivity(LoginVideoActivity.class, bundle);
                break;

            default:
                break;
        }
    }

}
