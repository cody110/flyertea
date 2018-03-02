package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadPreview;
import com.ideal.flyerteacafes.ui.activity.writethread.ThreadPreviewActivity;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;

import java.util.List;

/**
 * Created by fly on 2017/3/14.
 */

public class ThreadPreviewPresenter extends BasePresenter<IThreadPreview> {

    String title, forumName, location, fid1, fid2, fid3, hotelid, subject;
    public List<TuwenInfo> datas;

    private String img = "<img class='smileyImage' src='file:///android_asset/post_not_image_def.png' />";


    private String threadTemplate;

    @Override
    public void init(Activity activity) {
        super.init(activity);
        threadTemplate = FileUtil.readAssetsFile(context, "thread/thread_preview.html");
        List<SmileyBean> smileyBeanList = BaseHelper.getInstance().getListAll(SmileyBean.class);

        hotelid = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_HOTELID);
        fid1 = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_FID_1);
        fid2 = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_FID_2);
        fid3 = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_FID_3);
        title = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_TITLE);
        subject = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_SUBJECT);
        forumName = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_FORUMNAME);
        location = activity.getIntent().getStringExtra(ThreadPreviewActivity.BUNDLE_LOCATION);
        datas = (List<TuwenInfo>) activity.getIntent().getSerializableExtra(ThreadPreviewActivity.BUNDLE_CONTENT);

        StringBuffer sb = new StringBuffer();

        sb.append("<h4 style='color:#333; font-size: 1.3125rem; line-height: 2rem; font-weight: 700;'>" + title + "</h4>");
//        sb.append("<span style = 'color: #999; font-size: 0.6875rem;'>" + forumName + "</span>");
        if (!TextUtils.isEmpty(location)) {
            sb.append("<p class=\"locate\" style=' color: #999; font-size: 0.6875rem;'><img\n" +
                    "                    src=\"file:///android_asset/thread/img/dingwei.png\" style='height: 0.875rem; vertical-align: middle; margin-right: 0.375rem; '>" + location + "</p>");
        }
        if (!TextUtils.isEmpty(subject)) {
            sb.append(subject);
            sb.append("<br/><br/>");
        }

        for (TuwenInfo info : datas) {
            if (info.getType() == TuwenInfo.TYPE_TU) {
                sb.append("<img id='imageid' class='contentImage' style = 'width:100%' src='file:///");
                sb.append(info.getImgPath());
                sb.append("' index='<!--  CONTENTIMAGE INDEX  -->'/>");
                if (!TextUtils.isEmpty(info.getText())) {
                    sb.append("<br/>");
                    sb.append(info.getText());
                }
            } else if (info.getType() == TuwenInfo.TYPE_VIDEO) {
                String videoTem = "<div style=\"position:relative;\" class=\"video\"><img style = 'width:100%' src=\"<!-- imgpath -->\" class=\"videoImage\" /><img class=\"videoPlay\" id=\"video-<!-- vid -->\" src=\"<!-- play_btn_path -->\" style=\"position:absolute;z-index:2;top:50%;left:50%;width:3rem;height:3rem;margin-left:-1.5rem;margin-top:-1.5rem;\"/><span style=\"position:absolute; z-index:2;font-size: 0.75rem;color: #FFF; right:0.625rem; bottom:0.5rem;font-weight:300\"><!-- video_time --></span></div>";
                videoTem = StringTools.replaceFirst(videoTem, "<!-- imgpath -->", "file:///" + info.getVideoInfo().getThumbnailLocalPath());
                videoTem = StringTools.replaceFirst(videoTem, "<!-- play_btn_path -->", "file:///android_asset/thread/img/player_black.png");
                videoTem = StringTools.replaceFirst(videoTem, "<!-- vid -->", info.getVideoInfo().getVids());

                StringBuffer sbTime = new StringBuffer();
                int tmpTime = info.getVideoInfo().getTimelength();

                int temp = (tmpTime % 3600 / 60);
                sbTime.append((temp < 10) ? ("0" + temp + ":") : (temp + ":"));

                temp = (int) (tmpTime % 3600 % 60);
                sbTime.append((temp < 10) ? ("0" + temp + "") : (temp + ""));

                videoTem = StringTools.replaceFirst(videoTem, "<!-- video_time -->", sbTime.toString());
                sb.append(videoTem);

                if (!TextUtils.isEmpty(info.getText())) {
                    sb.append("<br/>");
                    sb.append(info.getText());
                }
            } else {
                sb.append(info.getText());
            }
            sb.append("<br/><br/>");
        }


        String content = sb.toString();

        for (SmileyBean bean : smileyBeanList) {
            if (content.contains(bean.getCode())) {
                content = StringTools.replaceAll(content, bean.getCode(), "<img class='smileyImage' style = 'width:8%' src='file:///android_asset/template/smiley/" + bean.getImage() + "' />");
            }
        }

        threadTemplate = threadTemplate.replace("<!-- content -->", content);

        getView().bind(threadTemplate);

    }


}
