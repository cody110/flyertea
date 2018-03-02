package com.ideal.flyerteacafes.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UmengShare;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.model.loca.ThreadBottomInfo;
import com.ideal.flyerteacafes.ui.activity.interfaces.IShareDialogGexing;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2016/11/25.
 */

public class ThreadShareDialog extends DialogFragment {


    private List<ThreadBottomInfo> dataList = new ArrayList<>();


    private IShareDialogGexing iShareDialogGexing;
    //分享 收藏 删除

    public static final String BUNDLE_TYPE = "type";//文章 帖子
    public static final int TYPE_THREAD = 1;
    public static final int TYPE_ARTICLE = 2;
    public static final String BUNDLE_CONTENT = "content";//正文类容
    public static final String BUNDLE_THREAD_TYPE = "thread_type";//帖子类型 专业非专业
    public static final int TYPE_THREAD_NORMAL = 1;
    public static final int TYPE_THREAD_MAJOR = 2;

    private CommonAdapter commonAdapter;

    private String title, url, sid, forumName;
    private int type;
    private int thread_type;

    @ViewInject(R.id.thread_bottom_gridview)
    private GridView gridView;
    @ViewInject(R.id.shar_bottom_layout)
    private View shar_bottom_layout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        initShareData();
        Bundle bundle = getArguments();
        type = bundle.getInt(BUNDLE_TYPE);
        thread_type = bundle.getInt(BUNDLE_THREAD_TYPE);
        if (type == TYPE_THREAD) {
            ThreadDetailsBean bean = (ThreadDetailsBean) bundle.getSerializable(BUNDLE_CONTENT);
            setThread(bean);
        } else if (type == TYPE_ARTICLE) {
            ArticleContentBean bean = (ArticleContentBean) bundle.getSerializable(BUNDLE_CONTENT);
            setArticle(bean);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.dialog_thread_share, container, false);
        x.view().inject(this, view);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(commonAdapter = new CommonAdapter<ThreadBottomInfo>(getActivity(), dataList, R.layout.thread_share_item) {
            @Override
            public void convert(ViewHolder holder, ThreadBottomInfo threadBottomInfo) {
                holder.setText(R.id.share_item_title, threadBottomInfo.getTitle());
                holder.setImageResource(R.id.share_item_icon, threadBottomInfo.getResId());
            }
        });

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_in);
        shar_bottom_layout.startAnimation(anim);

        return view;
    }

    public void setThreadLinster(IShareDialogGexing iShareDialogGexing) {
        this.iShareDialogGexing = iShareDialogGexing;
    }

    /**
     * 初始化分享数据
     */
    private void initShareData() {
        ThreadBottomInfo wechat_moment = new ThreadBottomInfo("朋友圈", R.drawable.share_wechat_moment, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN_CIRCLE);
        ThreadBottomInfo wechat_friend = new ThreadBottomInfo("微信好友", R.drawable.share_wechat, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN);
        ThreadBottomInfo qq_room = new ThreadBottomInfo("QQ空间", R.drawable.share_room, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.QZONE);
        ThreadBottomInfo qq_friend = new ThreadBottomInfo("QQ好友", R.drawable.share_qq, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.QQ);
        ThreadBottomInfo sine = new ThreadBottomInfo("新浪微博", R.drawable.share_sine, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.SINA);

        dataList.add(wechat_moment);
        dataList.add(wechat_friend);
        dataList.add(qq_room);
        dataList.add(qq_friend);
        dataList.add(sine);

    }


    /**
     * 文章
     *
     * @param bean
     */
    public void setArticle(ArticleContentBean bean) {

        if (!(UserManger.isLogin() && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid()))) {
            ThreadBottomInfo jubao = new ThreadBottomInfo("举报", R.drawable.thread_bottom_jubao, ThreadBottomInfo.TYPE_JUBAO, null);
            dataList.add(jubao);

        }

        title = bean.getSubject();
        title = bean.getSubject();
        sid = bean.getAid();
        forumName = bean.getForumname();
        url = "http://www.flyertea.com/article-" + bean.getAid() + "-1.html";

    }

    /**
     * 帖子
     *
     * @param bean
     */
    public void setThread(ThreadDetailsBean bean) {

        if (!(UserManger.isLogin() && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid()))) {
            ThreadBottomInfo jubao = new ThreadBottomInfo("举报", R.drawable.thread_bottom_jubao, ThreadBottomInfo.TYPE_JUBAO, null);
            dataList.add(jubao);

        }
        if (UserManger.isLogin() && TextUtils.equals(UserManger.getUserInfo().getMember_uid(), bean.getAuthorid())) {
            ThreadBottomInfo delete = new ThreadBottomInfo("删除", R.drawable.thread_bottom_delete, ThreadBottomInfo.TYPE_DELETE, null);
            dataList.add(delete);
        }

        title = bean.getSubject();
        sid = String.valueOf(bean.getTid());
        forumName = bean.getForumname();
        url = "http://www.flyertea.com/thread-" + bean.getTid() + "-1-1.html";
    }


    @Event({R.id.thread_bottom_top, R.id.thread_bottom_cancle})
    private void click(View v) {
        showEndAnimation();
    }

    @Event(value = R.id.thread_bottom_gridview, type = AdapterView.OnItemClickListener.class)
    private void gridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        ThreadBottomInfo info = dataList.get(position);
        if (info.getType() == ThreadBottomInfo.TYPE_SHARE) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("name", forumName);
            if (type == ThreadShareDialog.TYPE_THREAD) {
                map.put("tid", sid);
                LogFly.e(FinalUtils.EventId.share_post);
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.share_post, map);
            } else if (type == ThreadShareDialog.TYPE_ARTICLE) {
                map.put("aid", sid);
                LogFly.e(FinalUtils.EventId.share_article);
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.share_article, map);
            }

            UmengShare.setShareContent(getActivity(), title, getString(R.string.share_thread_content), url, info.getPlatform(), type, sid);
        } else if (info.getType() == ThreadBottomInfo.TYPE_COLLECT) {
            if (iShareDialogGexing != null) {
                iShareDialogGexing.actionCollect();
            }
        } else if (info.getType() == ThreadBottomInfo.TYPE_DELETE) {
            if (iShareDialogGexing != null) {
                MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.notedetail_deleteBtn_click);
                iShareDialogGexing.deleteThread();
            }
        } else if (info.getType() == ThreadBottomInfo.TYPE_JUBAO) {
            if (iShareDialogGexing != null) {
                iShareDialogGexing.actionJubao();
            }
        }
    }

    /**
     * 显示关闭动画
     */
    public void showEndAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_out);
        shar_bottom_layout.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
