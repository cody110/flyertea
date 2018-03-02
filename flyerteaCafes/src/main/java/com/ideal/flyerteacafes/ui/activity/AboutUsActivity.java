package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.model.entity.TeaBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity {

    /**
     * 关注微信
     **/
    @ViewInject(R.id.mrl_focus_wechart)
    View mrl_focus_wechart;
    @ViewInject(R.id.about_us_flyer_caff)
    View flyercaffView;
    @ViewInject(R.id.about_us_flyertea)
    View flyerteaView;
    @ViewInject(R.id.about_us_phone)
    View phoneView;
    /**
     * 评论我们
     **/
    @ViewInject(R.id.mrl_comment_us)
    View mrl_comment_us;
    @ViewInject(R.id.toolbar)
    ToolBar toolBar;
    @ViewInject(R.id.about_version_text)
    TextView versionView;

    TextView phoneText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        toolBar.setTitle(getString(R.string.title_name_about_us));

        ImageView wechartImg = (ImageView) mrl_focus_wechart
                .findViewById(R.id.include_about_us_img);
        TextView wechartText = (TextView) mrl_focus_wechart
                .findViewById(R.id.include_about_us_text);
        wechartImg.setImageResource(R.mipmap.about_us_ac_weixin);
        wechartText.setText(getString(R.string.about_us_taoist_priest));

        ImageView flyercaffImg = (ImageView) flyercaffView
                .findViewById(R.id.include_about_us_img);
        TextView flyercaffText = (TextView) flyercaffView
                .findViewById(R.id.include_about_us_text);
        flyercaffImg.setImageResource(R.mipmap.about_us_ac_sina);
        flyercaffText.setText(getString(R.string.about_us_flyer_caff));

        ImageView flyerteaImg = (ImageView) flyerteaView
                .findViewById(R.id.include_about_us_img);
        TextView flyerteaText = (TextView) flyerteaView
                .findViewById(R.id.include_about_us_text);
        flyerteaImg.setImageResource(R.mipmap.about_us_ac_website);

        flyerteaText.setText(getString(R.string.about_us_flyertea));

        ImageView phoneImg = (ImageView) phoneView
                .findViewById(R.id.include_about_us_img);
        phoneText = (TextView) phoneView
                .findViewById(R.id.include_about_us_text);
        phoneImg.setImageResource(R.mipmap.about_us_ac_phone);

        phoneText.setText(getString(R.string.about_us_phone));

        versionView.setText("V" + Tools.getVersion(this));

        TextView labelCommentUS = V.f(mrl_comment_us, R.id.include_about_us_text);
        labelCommentUS.setText(R.string.about_us_comment_us);
        ImageView migvCommentUS = V.f(mrl_comment_us, R.id.include_about_us_img);
        migvCommentUS.setImageResource(R.mipmap.about_us_ac_comment_us);


        if (BaseDataManger.getInstance().getTeaBean() != null) {
            TeaBean teaBean = BaseDataManger.getInstance().getTeaBean();
            phoneText.setText(teaBean.getTel());
        }
    }


    @Event({R.id.toolbar_left, R.id.about_us_flyer_caff, R.id.about_us_flyertea,
            R.id.about_us_phone, R.id.mrl_comment_us, R.id.mrl_focus_wechart})
    private void aboutOnClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;
            case R.id.mrl_focus_wechart://关注微信
                Intent intent = new Intent(this, TbsWebActivity.class);
                intent.putExtra("url", "http://weibo.com/flyertea");
                startActivity(intent);
            case R.id.about_us_flyer_caff:
                Intent intentFlyercaff = new Intent(this, TbsWebActivity.class);
                intentFlyercaff.putExtra("url", "http://weibo.com/flyertea");
                startActivity(intentFlyercaff);
                break;
            case R.id.about_us_flyertea:
                Intent intentFlyertea = new Intent(this, TbsWebActivity.class);
                intentFlyertea.putExtra("url", "http://www.flyertea.com/forum.php?mobile=yes&in_api=yes");
                startActivity(intentFlyertea);
                break;
            case R.id.mrl_comment_us://评论我们
                if (IntentTools.isAppInstalled(this, "com.tencent.android.qqdownloader")) {
                    IntentTools.toTencentDownloaderAPP(this);
                } else {
                    IntentTools.openAppStore(this);
                }
                break;
            case R.id.about_us_phone:
                IntentTools.intentPhone(AboutUsActivity.this, phoneText.getText().toString());
                break;
        }
    }
}
