package com.ideal.flyerteacafes.ui.activity.presenter;

import com.ideal.flyerteacafes.utils.tools.StringTools;

/**
 * Created by fly on 2016/11/22.
 * 帖子评论
 */

public class MajorCommentPresenter extends ThreadPresenter {


    @Override
    protected void handlerWebViewLoadData() {
        if (isLoadContentOk && isLoadCommentOk) {
            String content = templateUtils.getTemplateOnceComment();
            String comment = getCommentStr(commentBeanList.size(), commentBeanList);
            content = StringTools.replace(content, "<!-- FIRST LOAD COMMENT -->", comment);
            getView().loadDataWithBaseURL(imageDir, content, "text/html", "UTF-8", null);
            getView().settingView(threadBean);

        }
    }

    @Override
    public void saveReads() {
    }

    @Override
    protected boolean isNeedReplyHeader() {
        return false;
    }
}
