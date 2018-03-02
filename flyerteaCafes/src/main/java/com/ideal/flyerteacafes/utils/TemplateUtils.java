package com.ideal.flyerteacafes.utils;

import android.content.Context;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.ForumDataManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ArticleReplyBean;
import com.ideal.flyerteacafes.model.entity.Attachments;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;

import java.util.List;


/**
 * Created by fly on 2016/11/24.
 * html模板替换处理类
 */

public class TemplateUtils {

    private String commentImgOne = "<img style='width: 66.66666%; height:100%;'  id='imageid' class='contentImage' index='<!--  CONTENTIMAGE INDEX  -->' src='file:///android_asset/post_not_image_def.png'>";
    private String commentImgli = "<li><div><span><img id='imageid' class='contentImage' index='<!--  CONTENTIMAGE INDEX  -->' src='file:///android_asset/post_not_image_def.png'></span></div></li>";
    private String img = "<img id='imageid' class='contentImage' style = 'width:100%;height:100' src='file:///android_asset/post_not_image_def.png' index='<!--  CONTENTIMAGE INDEX  -->'/>";

    Context context = FlyerApplication.getContext();
    private String threadTemplate;
    private String rengzheng = "2";//已认证
    private String friend = "1";//朋友

    public TemplateUtils() {
        threadTemplate = FileUtil.readAssetsFile(context, "thread/comment_detail_special.html");
    }

    /**
     * 只有评论的模板
     *
     * @return
     */
    public String getTemplateOnceComment() {
        String template = threadTemplate;
        template = StringTools.replaceAll(template, "<!-- ARTICLE IS_COMMENT -->", "none");
        return template;
    }

    /**
     * 评论条数
     */
    public String getReplyHeaderHTML(Object replies, boolean isDesc) {


        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"reply-header\">");
        sb.append("评论 " + replies);
        sb.append("<span class=\"fr\"> <span id=\"reply-time-up\"");
        if (!isDesc) {
            sb.append("class=\"select\"");
        }
        sb.append(">正序</span>");
        sb.append("<span> / </span>");
        sb.append("<span id=\"reply-time-down\"");
        if (isDesc) {
            sb.append("class=\"select\"");
        }
        sb.append(">倒序</span></span></div>");
        sb.append("<div class=\"reply-header-split\"></div>");
        return sb.toString();
    }


    /**
     * 得到显示的征文html
     *
     * @param bean
     * @return
     */
    public String getTemplateContentHtml(ThreadDetailsBean bean, boolean isNormal, int commentSize) {
        String content = replaceTemplateByContent(bean, isNormal, commentSize);
        if (bean.getAttachList() != null) {
            content = replaceAttachmentToImageUrl(-1, content, bean.getAttachList());
        }
        return content;
    }


    /**
     * 替换html模板的正文部分
     *
     * @return
     */
    private String replaceTemplateByContent(ThreadDetailsBean bean, boolean isNormal, int commentSize) {
        String msg = threadTemplate;

        if (bean == null) return msg;

        if (isNormal) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE NORMAL -->", "block");
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE MAJOR -->", "none");

            if (bean.getReplies() > 0) {
                msg = StringTools.replaceAll(msg, "<!-- COMMENT SIZE -->", "评论" + bean.getReplies());
            }
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE NORMAL -->", "none");
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE MAJOR -->", "block");
        }

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUTHOR -->",
                bean.getAuthor());

        if (DataUtils.isPictureMode(context)) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR AVATAR -->", bean.getAvatar());
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR AVATAR -->", "file:///android_asset/def_face.png");
        }

        if (TextUtils.equals(bean.getGender(), "1") || TextUtils.equals(bean.getGender(), "0")) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR GENDER -->", "file:///android_asset/thread/img/male.png");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR GENDER -->", "file:///android_asset/thread/img/female.png");
        }

        if (TextUtils.equals(bean.getHas_sm(), rengzheng)) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE HAS_SM -->", "inline-block");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE HAS_SM -->", "none");
        }

        if (TextUtils.equals(bean.getIsfriend(), friend)) {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "已添加");
        } else {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "加好友");
        }

        if (UserManger.isLogin() && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid())) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE IS_FRIEND -->", "none");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE IS_FRIEND -->", "block");
        }

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE TITLE -->", bean.getSubject());

        String forum = null;
        if (!TextUtils.isEmpty(bean.getForumname()))
            forum = bean.getForumname();
        if (!TextUtils.isEmpty(forum))
            forum += "-";
        if (!TextUtils.isEmpty(bean.getSubforumname()))
            forum += bean.getSubforumname();

        if (TextUtils.isEmpty(forum)) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_FORUM -->", "none");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_FORUM -->", "block");
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE FORUM -->", forum);
        }

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE LEVEL -->", bean.getAuthortitle());

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE POST DATE -->", DataUtils.conversionTime(bean.getDbdateline()));

        String message = StringTools.replaceAll(bean.getMessage(), "alt=\"\"", "style=\"width:20px\"");

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE CONTENT -->", message);

        if (isShowThreadDeleteBtn(bean)) {
            msg = StringTools.replaceAll(msg, "<!-- CONTENT DELETEBLOCK VISIBILITY -->", "none");
        }

        if (TextUtils.isEmpty(bean.getLocation())) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", "");
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_LOCATION -->", "none");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", bean.getLocation());
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_LOCATION -->", "block");
        }


        msg = StringTools.replaceAll(msg, "<!-- ADVERT HTML -->", bean.getAdv());


        if (UserManger.isLogin()) {
            if (commentSize > 0) {//没有任何回复时，不出现
                if (TextUtils.equals(bean.getBestable(), "1")) {//未设置最佳答案
                    if (TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid()) || UserManger.getUserInfo().getGroupid() == 1
                            || UserManger.getUserInfo().getGroupid() == 2 || UserManger.getUserInfo().getGroupid() == 3) {//发帖人可见、管理员可见(乘务长，职员，空乘)
                        if (TextUtils.equals(bean.getTypeid(), "1")) {//求助问答
                            msg = StringTools.replaceAll(msg, "<!-- WENDA-REMIND -->", "<div id =\"wenda-remind\">请点击回帖右上角“···”设置最佳答案，鼓励热心会员！</div>");
                        }
                    }
                }
            }
        }

        if (bean.getCollection() != null) {
            String topicTemplate = FileUtil.readAssetsFile(context, "thread/topic_template.html");
            topicTemplate = StringTools.replace(topicTemplate, "<!-- topic_name -->", bean.getCollection().getName());
            //TODO 一周热帖 不需要浏览数
            if (!TextUtils.equals(bean.getCollection().getType(), TopicBean.TYPE_FID)) {
                topicTemplate = StringTools.replace(topicTemplate, "<!-- topic_views -->", "浏览" + bean.getCollection().getViews() + "次  " + "参与" + bean.getCollection().getUsers() + "人");
            }
            msg = StringTools.replaceAll(msg, "<!-- TOPIC -->", topicTemplate);
        }

        if (!DataUtils.isEmpty(bean.getTags())) {
//            String topicTemplate = FileUtil.readAssetsFile(context, "thread/tag_template.html");
            StringBuffer tagSb = new StringBuffer();
            tagSb.append("<div id=\"tag-item\" class=\"tag-item\">");
            tagSb.append("<span id=\"tag-item-name\">相关标签：</span>");
            tagSb.append("<span class=\"tag-item-value\" id=\"tag-item-value\">" + bean.getTags().get(0).getTagname() + "</span>");
            if (bean.getTags().size() > 1) {
                tagSb.append("<span class=\"tag-item-value\" id=\"tag-item-more\"> · · · </span>");
            }
            tagSb.append("</div>");

            msg = StringTools.replaceAll(msg, "<!-- TAG -->", tagSb.toString());
        }


        //TODO 标题后追加帖子的标识图标
        StringBuffer headerImg = new StringBuffer();
        if (!TextUtils.equals(bean.getHeatlevel(), "0")) {
            headerImg.append("<img src=\"file:///android_asset/thread/img/heatlevel.png\">");
        }
        if (!TextUtils.equals(bean.getPushedaid(), "0")) {
            headerImg.append("<img src=\"file:///android_asset/thread/img/pushedaid.png\">");
        }
        if (!TextUtils.equals(bean.getDigest(), "0")) {
            headerImg.append("<img src=\"file:///android_asset/thread/img/digest.png\">");
        }
        msg = StringTools.replaceAll(msg, "<!-- ARTICLE IMG -->", headerImg.toString());


        return msg;
    }


    /**
     * 替换html模板的评论列表
     *
     * @param commentList
     * @return
     */
    @SuppressWarnings("unused")
    public String getTemplateCommentHtml(int allSize, List<CommentBean> commentList, String tAuthorid) {
        if (DataUtils.isEmpty(commentList)) return "";
        String comment = FileUtil.readAssetsFile(context,
                "thread/comment_template.html");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < commentList.size(); i++) {
            int id = i + allSize - commentList.size();// 对应里面的id
            CommentBean cBean = commentList.get(i);
            String message = cBean.getMessage();
//            message = message.replaceAll("<a[^>]+>[^<]*</a>", "");// 去掉所有超链接

            if (!TextUtils.isEmpty(message)) {
                if (message.contains("<br />")) {
                    message = message.replace("<br />", "");
                }
            }

            cBean.setMessage(message);
            String change = comment;
            change = StringTools.replace(change, "<!--COMMENT MESSAGETD ID-->", id
                    + "messagetd");
            change = StringTools.replace(change, "<!--  COMMENT ID  -->", id + "comment");

            if (DataUtils.isPictureMode(context)) {
                change = StringTools.replace(change, "<!-- COMMENT AVATAR -->", cBean.getAvatar());
            } else {
                change = StringTools.replace(change, "<!-- COMMENT AVATAR -->", "file:///android_asset/def_face.png");
            }

            change = StringTools.replaceAll(change, "<!-- COMMENT LEVEL -->", cBean.getAuthortitle());

            if (TextUtils.equals(cBean.getGender(), "1") || TextUtils.equals(cBean.getGender(), "0")) {
                change = StringTools.replaceAll(change, "<!-- COMMENT GENDER -->", "file:///android_asset/thread/img/male.png");
            } else {
                change = StringTools.replaceAll(change, "<!-- COMMENT GENDER -->", "file:///android_asset/thread/img/female.png");
            }

            change = StringTools.replace(change, "<!-- COMMENT AVATAR ID -->", DataUtils.getImageId(cBean.getAvatar()) + id);
            change = StringTools.replace(change, "<!--  COMMENT INDEX  -->", "" + id);
//            change = StringTools.replace(change, "<!-- INDEX -->", "" + id);
            change = StringTools.replace(change, "<!-- COMMENT AUTHOR -->", cBean.getAuthor());
            change = StringTools.replace(change, "<!-- COMMENT POST DATE -->", DataUtils.conversionTime(cBean.getDbdateline()));


            if (TextUtils.equals(cBean.getIslike(), "1")) {
                change = StringTools.replace(change, "<!-- COMMENT FLOWER IMG -->", "file:///android_asset/thread/img/flower-light.png");
            } else {
                change = StringTools.replace(change, "<!-- COMMENT FLOWER IMG -->", "file:///android_asset/thread/img/flower.png");
            }

            if (!TextUtils.isEmpty(cBean.getFlowers()) && DataTools.getInteger(cBean.getFlowers()) > 0)
                change = StringTools.replace(change, "<!-- COMMENT FLOWER NUM -->", cBean.getFlowers());


            if (TextUtils.equals(cBean.getQuote(), Marks.THREAD_COMMENT_TO_COMMENT)) {
                change = StringTools.replace(change, "<!-- COMMENT TO AUTHOR -->", cBean.getToauthor() + ":");
                change = StringTools.replace(change, "<!-- COMMENT TO CONTENT -->", cBean.getTomessage());
                change = StringTools.replace(change, "<!-- IS_SHOW_REPLY-TO-INFO -->", "block");
            } else {
                change = StringTools.replace(change, "<!-- IS_SHOW_REPLY-TO-INFO -->", "none");
            }


            change = StringTools.replace(change, "<!-- COMMENT MESSAGE -->", cBean.getMessage());

            if (isShowCommentDeleteBtn(commentList.get(i))) {
                String flower_div = "<div class='deleteReply'>"
                        + "删除" + "</div>";
                change = StringTools.replace(change, "<!-- COMMENT DELETE DIV-->",
                        flower_div);
            }

            if (DataTools.getInteger(cBean.getFlowers()) > 0) {
                String flower_div = "<div class='flower'>"
                        + cBean.getFlowers() + "</div>";
                change = StringTools.replace(change, "<!-- COMMENT FLOWER DIV-->",
                        flower_div);
            }

            if (commentList.get(i).getAttachments() != null) {
//                if (commentList.get(i).getAttachments().size() == 1) {
//                    String imgLi = operateHtmlImgByAttachments(commentImgOne, id, 0, commentList.get(i).getAttachments().get(0));
//                    change = StringTools.replace(change, "<!-- IMG ONE -->", imgLi);
//                    change = StringTools.replace(change, "<!-- IS_SHOW_IMG-IMG -->", "block");
//                } else {
                change = StringTools.replace(change, "<!-- IS_SHOW_IMG-IMG -->", "none");
                change = StringTools.replace(change, "<!-- COMMENT IMG LIST -->", getCommentImgLi(id, commentList.get(i).getAttachments()));
//                }
            }


            if (TextUtils.equals(cBean.getIsbest(), "1")) {
                change = StringTools.replace(change, "<!-- IS_BEST_DAAN -->", "block");
            } else {
                change = StringTools.replace(change, "<!-- IS_BEST_DAAN -->", "none");
            }

//            if (TextUtils.equals(cBean.getIsexcellent(), "1")) {
//                change = StringTools.replace(change, "<!-- IS_BEST_HUIFU -->", "block");
//            } else {
            change = StringTools.replace(change, "<!-- IS_BEST_HUIFU -->", "none");
//            }

            if (TextUtils.equals(cBean.getHas_sm(), "2")) {
                change = StringTools.replace(change, "<!-- COMMENT HAS_SM -->", "block");
            } else {
                change = StringTools.replace(change, "<!-- COMMENT HAS_SM -->", "none");
            }

            if (TextUtils.equals(cBean.getAuthorid(), tAuthorid)) {
                change = StringTools.replace(change, "<!-- LOU ZHU -->", "block");
                change = StringTools.replace(change, "<!-- LOU ZHU CLASS -->", TextUtils.equals(cBean.getHas_sm(), "2") ? "louzhu2" : "louzhu1");
            } else {
                change = StringTools.replace(change, "<!-- LOU ZHU -->", "none");
            }


            buffer.append(change);
        }
        return buffer.toString();
    }

    /**
     * 评论列表九宫格图片
     *
     * @param pos
     * @param list
     * @return
     */
    private String getCommentImgLi(int pos, List<Attachments> list) {
        StringBuffer sb = new StringBuffer();
        int size = list.size() > 3 ? 3 : list.size();
        for (int i = 0; i < size; i++) {
            Attachments attach = list.get(i);
            String imgLi = commentImgli;
            imgLi = operateHtmlImgByAttachments(imgLi, pos, i, attach);
            sb.append(imgLi);
        }

        return sb.toString();
    }


    /**
     * 正则匹配到message里的图片id，并替换成url
     *
     * @param msg
     */
    public String replaceAttachmentToImageUrl(int pos, String msg,
                                              List<Attachments> list) {

        for (int i = 0; i < list.size(); i++) {
            Attachments attach = list.get(i);
            String tag = "[attach]" + attach.getAid() + "[/attach]";
            if (DataUtils.isPictureMode(context)) {
                msg = StringTools.replaceAll(msg, tag, operateHtmlImgByAttachments(img, pos, i, attach));
            } else {
                //TODO 多一次替换，防止没<br />的情况
                String tag1 = "[attach]" + attach.getAid() + "[/attach]<br />";
                msg = StringTools.replaceAll(msg, tag1, "");
                msg = StringTools.replaceAll(msg, tag, "");
            }
        }
        if (DataUtils.isPictureMode(context))
            msg = msg + "<br/><br/>";
        return msg;
    }

    /**
     * 把html代码，占位符替换成img标签，并下载本地没有的图片
     *
     * @return
     */
    public String operateHtmlImgByAttachments(String imgTag, int pos, int index,
                                              final Attachments attach) {

        String img1 = imgTag;
        img1 = img1.replace("<!--  CONTENTIMAGE INDEX  -->", pos + "," + index);

        if (attach == null) {
            img1 = img1.replace("imageid", "-1");
            return img1;
        }

        img1 = img1.replace("imageid", DataUtils.getImageId(attach.getKimageurl()));
        if (DataUtils.isPictureMode(context)) {
            img1 = img1.replace("file:///android_asset/post_not_image_def.png", attach.getKimageurl());
        }
        return img1;
    }


    /**
     * 帖子是否显示删除按钮
     *
     * @param bean
     * @return
     */
    private boolean isShowThreadDeleteBtn(ThreadDetailsBean bean) {
        if (UserManger.getUserInfo() != null) {
            if (UserManger.getUserInfo().getGroupid() == 1 || UserManger.getUserInfo().getGroupid() == 2 || UserManger.getUserInfo().getMember_uid().equals(bean.getAuthorid())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否显示评论标签
     *
     * @param bean
     * @return
     */
    private boolean isShowCommentDeleteBtn(CommentBean bean) {
        if (UserManger.getUserInfo() != null) {
            if (UserManger.getUserInfo().getGroupid() == 1 || UserManger.getUserInfo().getGroupid() == 2 || UserManger.getUserInfo().getMember_uid().equals(bean.getAuthorid())) {
                return true;
            }
        }
        return false;
    }


    public String getArticleTemplateContentHtml(ArticleContentBean bean) {
        String content = replaceArticleTemplateByContent(bean);
        if (bean == null) return content;
        if (!DataTools.isEmpty(bean.getAttachments())) {
            content = replaceAttachmentToImageUrl(-1, content, bean.getAttachments());
        }
        return content;
    }


    /**
     * 获取文章正文
     * 替换html模板的正文部分
     *
     * @return
     */
    public String replaceArticleTemplateByContent(ArticleContentBean bean) {
        String msg = threadTemplate;

        if (bean == null) return msg;

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE NORMAL -->", "none");
        msg = StringTools.replaceAll(msg, "<!-- ARTICLE MAJOR -->", "block");

        if (TextUtils.isEmpty(bean.getLocation())) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", "");
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_LOCATION -->", "none");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", bean.getLocation());
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE_IS_SHOW_LOCATION -->", "block");
        }


        if (TextUtils.equals(bean.getIsfriend(), friend)) {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "已添加");
        } else {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "加好友");
        }

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUTHOR -->",
                bean.getAuthor());

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE LEVEL -->", bean.getGroupname());

        if (DataUtils.isPictureMode(context)) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR AVATAR -->", bean.getAvatar());
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR AVATAR -->", "file:///android_asset/def_face.png");
        }

        if (TextUtils.equals(bean.getGender(), "1") || TextUtils.equals(bean.getGender(), "0")) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR GENDER -->", "file:///android_asset/thread/img/male.png");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE AUHTOR GENDER -->", "file:///android_asset/thread/img/female.png");
        }

        if (TextUtils.equals(bean.getHas_sm(), rengzheng)) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE HAS_SM -->", "block");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE HAS_SM -->", "none");
        }

        if (TextUtils.equals(bean.getIsfriend(), friend)) {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "已添加");
        } else {
            msg = StringTools.replaceAll(msg, "<!--ARTICLE ISFRIEND-->", "加好友");
        }

        if (UserManger.isLogin() && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid())) {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE IS_FRIEND -->", "none");
        } else {
            msg = StringTools.replaceAll(msg, "<!-- ARTICLE IS_FRIEND -->", "block");
        }

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE TITLE -->", bean.getSubject());

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE FORUM -->", ForumDataManger.getInstance().getForumName(String.valueOf(bean.getFid())));

        msg = StringTools.replaceAll(msg, "<!-- ARTICLE POST DATE -->", DataUtils.conversionTime(bean.getDbdateline()));

        String message = StringTools.replaceAll(bean.getMessage(), "alt=\"\"", "style=\"width:20px\"");
        msg = StringTools.replaceAll(msg, "<!-- ARTICLE CONTENT -->", message);

        msg = StringTools.replaceAll(msg, "<!-- CONTENT DELETEBLOCK VISIBILITY -->", "none");

//        if (TextUtils.isEmpty(bean.getLocation())) {
        msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", "");
//        } else {
//            msg = StringTools.replaceAll(msg, "<!-- ARTICLE LOCATION -->", bean.getLocation());
//        }


        msg = StringTools.replaceAll(msg, "<!-- ADVERT HTML -->", "");

        msg = StringTools.replaceAll(msg, "<!-- COMMENT SIZE -->", "评论" + bean.getReplies());


        return msg;
    }


    /**
     * 替换html模板的评论列表
     *
     * @param commentList
     * @return
     */
    @SuppressWarnings("unused")
    public String getArticleTemplateCommentHtml(int allSize, List<ArticleReplyBean> commentList, String tAuthorid) {
        if (DataUtils.isEmpty(commentList)) return "";
        String comment = FileUtil.readAssetsFile(context,
                "thread/comment_template.html");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < commentList.size(); i++) {
            int id = i + allSize - commentList.size();// 对应里面的id
            ArticleReplyBean cBean = commentList.get(i);
            String message = cBean.getMessage();
//            message = message.replaceAll("<a[^>]+>[^<]*</a>", "");// 去掉所有超链接

            if (!TextUtils.isEmpty(message)) {
                if (message.contains("<br />")) {
                    message = message.replace("<br />", "");
                }
            }

            cBean.setMessage(message);
            String change = comment;
            change = StringTools.replace(change, "<!--COMMENT MESSAGETD ID-->", id
                    + "messagetd");
            change = StringTools.replace(change, "<!--  COMMENT ID  -->", id + "comment");

            if (DataUtils.isPictureMode(context)) {
                change = StringTools.replace(change, "<!-- COMMENT AVATAR -->", cBean.getAvatar());
            } else {
                change = StringTools.replace(change, "<!-- COMMENT AVATAR -->", "file:///android_asset/def_face.png");
            }

            if (TextUtils.equals(cBean.getGender(), "1") || TextUtils.equals(cBean.getGender(), "0")) {
                change = StringTools.replaceAll(change, "<!-- COMMENT GENDER -->", "file:///android_asset/thread/img/male.png");
            } else {
                change = StringTools.replaceAll(change, "<!-- COMMENT GENDER -->", "file:///android_asset/thread/img/female.png");
            }

            change = StringTools.replace(change, "<!-- COMMENT AVATAR ID -->", DataUtils.getImageId(cBean.getAvatar()) + id);
            change = StringTools.replace(change, "<!--  COMMENT INDEX  -->", "" + id);
            change = StringTools.replace(change, "<!-- INDEX -->", "" + id);
            change = StringTools.replace(change, "<!-- COMMENT AUTHOR -->", cBean.getAuthor());
            change = StringTools.replace(change, "<!-- COMMENT POST DATE -->", DataUtils.conversionTime(cBean.getDbdateline()));

            change = StringTools.replace(change, "<!-- COMMENT IS NEED FLOWER -->", "none");
            change = StringTools.replace(change, "<!-- IS_SHOW_IMG-IMG -->", "none");
            change = StringTools.replace(change, "<!-- IS_SHOW_REPLY-TO-INFO -->", "none");

            change = StringTools.replace(change, "<!-- COMMENT MESSAGE -->", cBean.getMessage());


            if (TextUtils.equals(cBean.getHas_sm(), "2")) {
                change = StringTools.replace(change, "<!-- COMMENT HAS_SM -->", "block");
            } else {
                change = StringTools.replace(change, "<!-- COMMENT HAS_SM -->", "none");
            }

            if (TextUtils.equals(cBean.getAuthorid(), tAuthorid)) {
                change = StringTools.replace(change, "<!-- LOU ZHU -->", "block");
                change = StringTools.replace(change, "<!-- LOU ZHU CLASS -->", TextUtils.equals(cBean.getHas_sm(), "2") ? "louzhu2" : "louzhu1");
            } else {
                change = StringTools.replace(change, "<!-- LOU ZHU -->", "none");
            }

            change = StringTools.replaceAll(change, "<!-- COMMENT LEVEL -->", cBean.getAuthortitle());

            if (commentList.get(i).getAttachments() != null) {
//                if (commentList.get(i).getAttachments().size() == 1) {
//                    String imgLi = operateHtmlImgByAttachments(commentImgOne, id, 0, commentList.get(i).getAttachments().get(0));
//                    change = StringTools.replace(change, "<!-- IMG ONE -->", imgLi);
//                    change = StringTools.replace(change, "<!-- IS_SHOW_IMG-IMG -->", "block");
//                } else {
                change = StringTools.replace(change, "<!-- IS_SHOW_IMG-IMG -->", "none");
                change = StringTools.replace(change, "<!-- COMMENT IMG LIST -->", getCommentImgLi(id, commentList.get(i).getAttachments()));
//                }
            }


            change = StringTools.replace(change, "<!-- IS_BEST_DAAN -->", "none");
            change = StringTools.replace(change, "<!-- IS_BEST_HUIFU -->", "none");
            change = StringTools.replace(change, "<!-- COMMENT FR_MORE -->", "none");


            buffer.append(change);
        }
        return buffer.toString();
    }


}
