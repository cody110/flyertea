package com.ideal.flyerteacafes.ui.activity.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TableLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseToolbarActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.upyun.upplayer.widget.UpVideoView2;

/**
 * Created by fly on 2018/1/29.
 */

public class DeleteVideoActivity extends PlayVideoActivity {


    @Override
    protected void setTitleBar(ToolBar mToolbar) {
        mToolbar.setTitle("视频预览");
        mToolbar.setRightText("删除");
    }

    @Override
    protected void toolbarRightClick(View v) {
        super.toolbarRightClick(v);
        Intent intent = getIntent();
        intent.setClass(this, WriteMajorThreadContentActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }


}
