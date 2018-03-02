package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by fly on 2017/6/2.
 */

public class LoginVideoVH extends BaseViewHolder {


    public interface IActionListener {

        int ID_BACK = R.id.toolbar_left;
        int ID_PHONE_LOGIN = R.id.phone_login_layout;
        int ID_USER_LOGIN = R.id.username_login_layout;
        int ID_CLEAR_USERNAME = R.id.username_edit_clear_btn;
        int ID_GET_CODE = R.id.password_get_code_btn;
        int ID_LOGIN = R.id.login_btn;
        int ID_REGIST = R.id.new_user_regist_tv;
        int ID_WECHAT = R.id.wechat_btn;
        int ID_QQ = R.id.qq_btn;
        int ID_SINE = R.id.sina_btn;

        void actionClick(View view);

    }

    IActionListener iActionListener;


    @ViewInject(R.id.phone_login_text)
    private TextView phone_login_text;
    @ViewInject(R.id.phone_login_select)
    private View phone_login_select;
    @ViewInject(R.id.username_login_text)
    private TextView username_login_text;
    @ViewInject(R.id.username_login_select)
    private View username_login_select;
    @ViewInject(R.id.username_edit)
    private EditText username_edit;
    @ViewInject(R.id.username_edit_clear_btn)
    private ImageView username_edit_clear_btn;
    @ViewInject(R.id.password_edit)
    private EditText password_edit;
    @ViewInject(R.id.password_get_code_btn)
    private TextView password_get_code_btn;
    @ViewInject(R.id.new_user_regist_tv)
    private TextView new_user_regist_tv;


    @Event({R.id.phone_login_layout, R.id.username_login_layout, R.id.username_edit_clear_btn, R.id.password_get_code_btn,
            R.id.login_btn, R.id.new_user_regist_tv, R.id.wechat_btn, R.id.qq_btn, R.id.sina_btn, R.id.toolbar_left})
    private void click(View view) {
        iActionListener.actionClick(view);
    }

    public LoginVideoVH(View view, final IActionListener iActionListener) {
        x.view().inject(this, view);
        this.iActionListener = iActionListener;
        setSelectPhone();

        username_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                WidgetUtils.setVisible(username_edit_clear_btn, charSequence.toString().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String str = "立即注册";
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        new_user_regist_tv.setText(content);
    }


    /**
     * 设置选中手机号登录
     */
    public void setSelectPhone() {
        phone_login_text.setTextColor(mContext.getResources().getColor(R.color.text_black));
        phone_login_select.setVisibility(View.VISIBLE);
        username_login_text.setTextColor(mContext.getResources().getColor(R.color.text_black));
        username_login_select.setVisibility(View.INVISIBLE);
        password_get_code_btn.setVisibility(View.VISIBLE);
        username_edit.setHint("请输入手机号");
        password_edit.setHint("请输入验证码");
        username_edit.setText("");
        password_edit.setText("");
    }

    /**
     * 设置选中用户名登录
     */
    public void setSelectUser() {
        phone_login_text.setTextColor(mContext.getResources().getColor(R.color.text_black));
        phone_login_select.setVisibility(View.INVISIBLE);
        username_login_text.setTextColor(mContext.getResources().getColor(R.color.text_black));
        username_login_select.setVisibility(View.VISIBLE);
        password_get_code_btn.setVisibility(View.GONE);
        username_edit.setHint("请输入用户名");
        password_edit.setHint("请输入密码");
        username_edit.setText("");
        password_edit.setText("");
    }

    /**
     * 设置清空用户名
     */
    public void setClearUsername() {
        username_edit.setText("");
    }

    /**
     * 倒计时
     */
    public void setGetCodeTime() {

        final int time = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(time) //设置循环11次
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return time - aLong; //
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        password_get_code_btn.setText("获取验证码");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        password_get_code_btn.setText(aLong + "s");
                    }
                });
    }

    /**
     * 设置获取验证码按钮，初始状态
     */
    public void setGetCodeInitStatus() {
        password_get_code_btn.setText("获取验证码");
    }

    /**
     * 是否需要调用获取ｃｏｄｅ接口
     *
     * @return
     */
    public boolean isNeedGetCode() {
        return TextUtils.equals(password_get_code_btn.getText().toString(), "获取验证码");
    }

    /**
     * 用戶用戶名手機號
     *
     * @return
     */
    public String getUserNameText() {
        return username_edit.getText().toString();
    }

    public String getPassWordText() {
        return password_edit.getText().toString();
    }

}
