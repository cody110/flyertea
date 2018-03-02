package com.ideal.flyerteacafes.ui.activity.writethread;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/3/13.
 */

@ContentView(R.layout.activity_write_majorthread_text)
public class WriteMajorThreadImgTextActivity extends BaseActivity {


    @ViewInject(R.id.toolbar)
    ToolBar toolbar;
    @ViewInject(R.id.content_edit)
    EditText content_edit;
    @ViewInject(R.id.max_text_size_remind)
    TextView max_text_size_remind;
    @ViewInject(R.id.chat_biaoqing_layout)
    View biaoqingView;
    @ViewInject(R.id.choose_look_img)
    ImageView choose_look_img;

    public static final String BUNDLE_IMG_TEXT = "IMG_TEXT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        biaoqingView.setVisibility(View.GONE);

        DataUtils.addDelSmileyListener(content_edit, smileyBeanList, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int surplusSize = i + i2;
                if (surplusSize < 950) {
                    max_text_size_remind.setVisibility(View.GONE);
                } else {
                    max_text_size_remind.setVisibility(View.VISIBLE);
                    setContentSizeRemind(1000 - surplusSize);
                }
                DataUtils.noInputEmoji(content_edit, charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        content_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hintFaceView();
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.choose_look_img})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                clickClose();
                break;

            case R.id.toolbar_right:
                clickOk();
                break;

            case R.id.choose_look_img:
                if (biaoqingView.getVisibility() == View.VISIBLE) {
                    hintFaceView();
                } else {
                    choose_look_img.setImageResource(R.drawable.jianpan);
                    biaoqingView.setTag(true);
                    hideSoftInput(getCurrentFocus().getWindowToken());
                    biaoqingView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void hintFaceView() {
        if (biaoqingView.getVisibility() == View.VISIBLE) {
            choose_look_img.setImageResource(R.drawable.reply_face);
            biaoqingView.setVisibility(View.GONE);
            InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 正文内容不操过1000字，过950提醒
     *
     * @param surplusSize
     */
    private void setContentSizeRemind(int surplusSize) {
        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode modeBlack = new SegmentedStringMode(surplusSize + "/", R.dimen.app_body_size_2, R.color.app_black);
        SegmentedStringMode modeGrey = new SegmentedStringMode("1000", R.dimen.app_body_size_2, R.color.grey);
        modeList.add(modeBlack);
        modeList.add(modeGrey);
        max_text_size_remind.setText(DataUtils.getSegmentedDisplaySs(modeList));
    }

    /**
     * 点击完成
     */
    public void clickOk() {
        if (!TextUtils.isEmpty(content_edit.getText().toString().trim())) {
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_IMG_TEXT, content_edit.getText().toString());
            jumpActivitySetResult(bundle);
        }
    }

    /**
     * 点击关闭
     */
    public void clickClose() {
        if (!TextUtils.isEmpty(content_edit.getText().toString().trim())) {
            closeRemindDialog();
        } else {
            finish();
        }

    }

    List<SmileyBean> smileyBeanList = new ArrayList<>();

    public void onEventMainThread(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(content_edit);
        } else {
            smileyBeanList.add(bean);
            content_edit.getText().insert(content_edit.getSelectionStart(), bean.getCode());
        }
    }

    protected void closeRemindDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("是否保存修改内容");
        builder.setNegativeButton("放弃", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                finish();
            }
        });

        builder.setPositiveButton("保存", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                clickOk();
            }
        });
        builder.create().show();
    }



}
