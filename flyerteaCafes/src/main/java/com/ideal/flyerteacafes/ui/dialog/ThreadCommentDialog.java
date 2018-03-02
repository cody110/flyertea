package com.ideal.flyerteacafes.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadCommentDialog;
import com.ideal.flyerteacafes.ui.fragment.page.LookFragment;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/23.
 */
public class ThreadCommentDialog extends DialogFragment {


    @ViewInject(R.id.dialog_thread_comment_layout)
    View dialog_thread_comment_layout;
    @ViewInject(R.id.dialog_thread_comment_edit)
    private EditText dialog_thread_comment_edit;
    @ViewInject(R.id.dialog_thread_comment_look_layout)
    private View dialog_thread_comment_look_layout;
    @ViewInject(R.id.dialog_thread_comment_tupian)
    private View dialog_thread_comment_tupian;
    @ViewInject(R.id.dialog_thread_comment_biaoqing)
    private ImageView dialog_thread_comment_biaoqing;
    @ViewInject(R.id.btn_send)
    private TextView btn_send;

    private IThreadCommentDialog linster;
    private String hint;
    private int index;

    private FragmentManager fm;
    private LookFragment lookFragment;
    private List<String> emjoyList = new ArrayList<>();
    private boolean isShowLookView = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getChildFragmentManager();
        EventBus.getDefault().register(this);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_CustomDialog);
        Bundle bundle = getArguments();
        hint = bundle.getString("hint");
        index = bundle.getInt("index");
    }

    public int getIndex() {
        return index;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //键盘出现时，从底部整体上移，自动调整高度，可以做出底部跟着键盘上移效果
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        View view = inflater.inflate(R.layout.dialog_thread_comment_layout, container, false);
        x.view().inject(this, view);

        dialog_thread_comment_edit.setHint(hint);


        dialog_thread_comment_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setShowLookView(false);
                return false;
            }
        });

        setShowLookView(false);

        dialog_thread_comment_edit.addTextChangedListener(new TextWatcher() {
            //文本改变之前调用
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            //当文本改变时被调用
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    btn_send.setTextColor(getResources().getColor(R.color.app_body_grey));
                } else {
                    btn_send.setTextColor(getResources().getColor(R.color.app_bg_title));
                }
            }

            //文本改变结束后调用
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return view;
    }

    @Event({R.id.btn_close, R.id.btn_send, R.id.dialog_thread_comment_top_layout, R.id.dialog_thread_comment_biaoqing, R.id.dialog_thread_comment_tupian, R.id.dialog_thread_comment_down})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.dialog_thread_comment_top_layout:
                dismiss();
                break;

            case R.id.dialog_thread_comment_biaoqing:
                setShowLookView(!isShowLookView);
                break;

            case R.id.dialog_thread_comment_tupian:
                linster.actionToPictureComment(dialog_thread_comment_edit.getText().toString());
                dismiss();
                break;

            case R.id.dialog_thread_comment_down:
                dismiss();
                break;
            case R.id.btn_send:
                sendComment();
                break;

        }
    }

    public void sendComment() {
        linster.actionSendComment(dialog_thread_comment_edit.getText().toString());
    }


    public void jianIsClose() {
        if (dialog_thread_comment_look_layout != null && dialog_thread_comment_look_layout.getVisibility() == View.GONE) {
            dismissDialog();
        }
    }

    public void dismissDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            super.dismissAllowingStateLoss();
        }
    }


    /**
     * 是否显示表情界面
     *
     * @param bol
     */
    private void setShowLookView(boolean bol) {

        isShowLookView = bol;

        if (bol) {
            dialog_thread_comment_biaoqing.setImageResource(R.drawable.jianpan);
        } else {
            dialog_thread_comment_biaoqing.setImageResource(R.drawable.reply_face);
        }


        lookFragment = (LookFragment) fm.findFragmentByTag("lookFragment");
        FragmentTransaction transaction = fm.beginTransaction();

        if (bol) {
            if (lookFragment == null) {
                lookFragment = new LookFragment();
                transaction.add(R.id.dialog_thread_comment_look_layout, lookFragment, "lookFragment");
            } else {
                transaction.show(lookFragment);
            }
            transaction.commit();
            dialog_thread_comment_look_layout.setVisibility(View.VISIBLE);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(dialog_thread_comment_edit.getWindowToken(), 0); //强制隐藏键盘

        } else {
            if (dialog_thread_comment_look_layout.getVisibility() == View.VISIBLE) {
                if (lookFragment != null) {
                    transaction.hide(lookFragment);
                    transaction.commit();
                }
                dialog_thread_comment_look_layout.setVisibility(View.GONE);

                //打开软键盘

                TaskUtil.postOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });


            }


        }

    }


    public void setCommentListener(IThreadCommentDialog linster) {
        this.linster = linster;
    }

    /**
     * 选择的表情
     *
     * @param bean
     */
    public void onEventMainThread(SmileyBean bean) {

        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(dialog_thread_comment_edit);
        } else {
            dialog_thread_comment_edit.getText().insert(dialog_thread_comment_edit.getSelectionStart(), bean.getCode());
            emjoyList.add(bean.getCode());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void setHintChooseImg() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                dialog_thread_comment_tupian.setVisibility(View.GONE);
            }
        });
    }

}
