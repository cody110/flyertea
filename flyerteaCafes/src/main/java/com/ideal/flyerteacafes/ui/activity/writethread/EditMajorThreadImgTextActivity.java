package com.ideal.flyerteacafes.ui.activity.writethread;

import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;

/**
 * Created by fly on 2017/3/13.
 */

public class EditMajorThreadImgTextActivity extends WriteMajorThreadImgTextActivity{

    //编辑文字的pos
    public static final String BUNDLE_LIST_POS="list_pos";
    private int list_pos;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_pos=getIntent().getIntExtra(BUNDLE_LIST_POS,0);
        toolbar.setTitle("编辑文字");
        text=getIntent().getStringExtra(WriteMajorThreadImgTextActivity.BUNDLE_IMG_TEXT);
        if(!TextUtils.isEmpty(text)) {
            content_edit.setText(text);
            content_edit.setSelection(text.length());
        }
    }

    @Override
    public void clickOk() {
        if(!TextUtils.isEmpty(content_edit.getText().toString().trim())) {
            Bundle bundle=new Bundle();
            bundle.putString(BUNDLE_IMG_TEXT,content_edit.getText().toString());
            bundle.putInt(BUNDLE_LIST_POS,list_pos);
            jumpActivitySetResult(bundle);
        }
    }

    @Override
    public void clickClose() {
        if(TextUtils.isEmpty(text)){
            finish();
        }else{
            closeRemindDialog();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_push_bottom_out);
    }
}
