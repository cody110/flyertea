package com.ideal.flyerteacafes.ui.fragment.viewholder;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.MyTaskBean;
import com.ideal.flyerteacafes.model.entity.NumberBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.viewholder.BaseViewHolder;
import com.ideal.flyerteacafes.ui.layout.BannerViewpager;
import com.ideal.flyerteacafes.ui.layout.TaskListGroupLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by fly on 2017/5/16.
 */

public class MyFragmentVH extends BaseViewHolder implements View.OnClickListener {


    MyFragmentVH.IActionListener iActionListener;

    @Override
    public void onClick(View view) {
        if (iActionListener != null) iActionListener.actionClick(view);
    }

    public interface IActionListener {


        //设置
        int ID_TO_SETTING = R.id.my_person_setting;
        //消息中心
        int ID_TO_MESSAGE = R.id.linetool_remind;
        //登录
        int ID_TO_LOGIN = R.id.my_login_btn;
        //头像
        int ID_TO_FACE_CHANGE = R.id.my_datum_head_layout;
        //用户信息右侧部分
        int ID_TO_USER_RIGHT = R.id.user_info_layout;
        //威望
        int ID_TO_WEIWANG = R.id.weiwangs_layout;
        //飞米
        int ID_TO_FEIMI = R.id.feimi_layout;
        //余额
        int ID_TO_MONEY = R.id.money_layout;
        //勋章
        int ID_TO_XUNZHANG = R.id.xunzhang_layout;
        //好友
        int ID_TO_friends = R.id.linetool_friends;
        //帖子
        int ID_TO_POST = R.id.linetool_post;
        //收藏
        int ID_TO_COLLECTION = R.id.linetool_collection;
        //我的任务
        int ID_TO_TASK = R.id.linetool_task;
        //查看更多
        int ID_SEE_MORE_TASK = R.id.see_more;

        //浏览记录
        int ID_TO_SEE_JILU = R.id.mrl_record;
        //推荐好友
        int ID_TO_TUIJIAN_FRIEND = R.id.mrl_recommend_friends;
        //意见反馈
        int ID_TO_FANKUI = R.id.mrl_feedback;
        //飞米商城
        int ID_TO_FLYING_MALL = R.id.feimi_tv;
        //用户名
        int ID_TO_USER = R.id.mtv_nickname;
        //完善个人资料
        int ID_TO_PERFECT_INFORMATION = R.id.my_member_privileges;
        //飞客攻略电子书
        int ID_TO_RAIDERS_BOOK = R.id.mrl_raiders_book;


        void actionClick(View view);

        void taskClick(MyTaskBean bean);

        //banner 点击
        void bannerClick(MyTaskAllBean.BannerBean bean);
    }


    private View mView;
    @ViewInject(R.id.user_info_layout)
    private View user_info_layout;
    @ViewInject(R.id.login_register_view)
    private View login_register_view;

    @ViewInject(R.id.mrl_raiders_book)
    private View mrl_raiders_book;
    @ViewInject(R.id.mrl_record)
    private View mrl_record;
    @ViewInject(R.id.mrl_recommend_friends)
    private View mrl_recommend_friends;
    @ViewInject(R.id.mrl_feedback)
    private View mrl_feedback;

    @ViewInject(R.id.mtv_flowers)
    private TextView mtv_flowers;
    @ViewInject(R.id.mtv_weiwangs)
    private TextView mtv_weiwangs;
    @ViewInject(R.id.mtv_feimi)
    private TextView mtv_feimi;
    @ViewInject(R.id.mtv_money)
    private TextView mtv_money;
    @ViewInject(R.id.mtv_xunzhang)
    private TextView mtv_xunzhang;

    @ViewInject(R.id.drawer_face_img)
    private ImageView drawer_face_img;
    @ViewInject(R.id.migv_rengzheng)
    private ImageView migv_rengzheng;
    @ViewInject(R.id.migv_menber_grade)
    private ImageView migv_menber_grade;
    @ViewInject(R.id.migv_menber_grade_2)
    private ImageView migv_menber_grade_2;
    @ViewInject(R.id.migv_remind_message)
    private ImageView migv_remind_message;

    @ViewInject(R.id.mtv_nickname)
    private TextView mtv_nickname;

    @ViewInject(R.id.num_friends)
    private TextView num_friends;
    @ViewInject(R.id.num_post)
    private TextView num_post;
    @ViewInject(R.id.num_collection)
    private TextView num_collection;
    @ViewInject(R.id.num_task)
    private TextView num_task;
    @ViewInject(R.id.newbie_task_layout)
    private ViewStub newbie_task_layout;
    @ViewInject(R.id.remind_task_layout)
    private ViewStub remind_task_layout;
    @ViewInject(R.id.remind_num_task)
    private View remind_num_task;
    private View newbie_task, remind_task;
    @ViewInject(R.id.feimi_tv)
    private TextView feimi_tv;
    @ViewInject(R.id.my_member_privileges)
    private TextView my_member_privileges;

    @ViewInject(R.id.banner_layout)
    private BannerViewpager<MyTaskAllBean.BannerBean> banner_layout;


    @Event({R.id.my_login_btn, R.id.linetool_remind,
            R.id.linetool_friends, R.id.linetool_post, R.id.linetool_collection,
            R.id.mrl_feedback, R.id.user_info_layout, R.id.mrl_record,
            R.id.mrl_recommend_friends, R.id.linetool_task, R.id.my_person_setting,
            R.id.feimi_layout, R.id.money_layout, R.id.my_datum_head_layout,
            R.id.weiwangs_layout, R.id.xunzhang_layout, R.id.mtv_nickname,
            R.id.migv_menber_grade, R.id.migv_menber_grade_2, R.id.feimi_tv,
            R.id.my_member_privileges, R.id.mrl_raiders_book})
    private void click(View v) {
        if (iActionListener != null) iActionListener.actionClick(v);
    }

    public MyFragmentVH(View mView, IActionListener iActionListener) {
        this.mView = mView;
        this.iActionListener = iActionListener;
        x.view().inject(this, mView);
        bindInit();
    }


    ImageOptions image_FIT_XY = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.drawable.xinka)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.xinka)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    private void bindInit() {
        bindItem(mrl_raiders_book, R.mipmap.icon_ts, "《飞客攻略007》图书");
        TextView comment_person_right_tv = (TextView) mrl_raiders_book.findViewById(R.id.comment_person_right_tv);
        comment_person_right_tv.setText("新人必读");
        comment_person_right_tv.setVisibility(View.VISIBLE);
        comment_person_right_tv.setTextColor(mContext.getResources().getColor(R.color.topthread_type_gongao));
        bindItem(mrl_record, R.mipmap.icon_record, "浏览记录");
        bindItem(mrl_recommend_friends, R.mipmap.icon_recommend, "推荐好友");
        bindItem(mrl_feedback, R.mipmap.icon_yijian_fankui, "意见反馈");
        mrl_feedback.findViewById(R.id.item_person_divider).setVisibility(View.GONE);
    }


    /**
     * 设置显示用户信息
     */
    public void bindUserInfo(UserBean bean) {
        if (bean == null) return;

        user_info_layout.setVisibility(View.VISIBLE);
        login_register_view.setVisibility(View.GONE);
        migv_rengzheng.setVisibility(View.VISIBLE);

        DataUtils.downloadPicture(drawer_face_img, bean.getFace(), R.drawable.def_face_2);

        if (bean.getHas_sm().equals("2")) {
            migv_rengzheng.setImageResource(R.drawable.renzheng);
        } else {
            migv_rengzheng.setImageResource(R.drawable.hui_renzheng);
        }
        WidgetUtils.setText(mtv_nickname, bean.getMember_username());


        x.image().bind(migv_menber_grade, bean.getGroupicon(), image_FIT_XY);
        if (!DataUtils.isEmpty(bean.getExtgroups())) {
            x.image().bind(migv_menber_grade_2, bean.getExtgroups().get(0).getGroupicon(), image_FIT_XY);
        }
        WidgetUtils.setVisible(migv_menber_grade_2, !DataUtils.isEmpty(bean.getExtgroups()));


        WidgetUtils.setText(mtv_weiwangs, String.valueOf(bean.getCredits()));
        WidgetUtils.setText(mtv_feimi, bean.getExtcredits6());
        WidgetUtils.setText(mtv_flowers, bean.getUser_flower());


    }

    /**
     * 绑定getnumber返回的数据
     *
     * @param numBean
     */
    public void bindNumber(NumberBean numBean) {
        if (numBean == null) return;

        if (numBean.getNewprompt() > 0 || numBean.getNewpm() > 0) {
            migv_remind_message.setVisibility(View.VISIBLE);
        } else {
            migv_remind_message.setVisibility(View.GONE);
        }

        WidgetUtils.setText(mtv_flowers, String.valueOf(numBean.getFlowers()));
        WidgetUtils.setText(mtv_weiwangs, String.valueOf(numBean.getCredit()));
        WidgetUtils.setText(mtv_feimi, String.valueOf(numBean.getFeimi()));
        WidgetUtils.setText(mtv_money, String.valueOf(numBean.getMoney()));
        WidgetUtils.setText(mtv_xunzhang, String.valueOf(numBean.getMedals()));

        int count = BaseHelper.getInstance().getCount(FriendsInfo.class);
        WidgetUtils.setText(num_friends, "好友 " + count);
        WidgetUtils.setText(num_post, "帖子 " + numBean.getPostnum());
        //TODO 赞不知道字段
        WidgetUtils.setText(num_collection, "收藏 " + numBean.getThreadnum());
        WidgetUtils.setText(num_task, "任务 " + numBean.getTaskdone() + "/" + numBean.getTaskall());

        WidgetUtils.setVisible(remind_num_task, numBean.getNewtask() != 0);


    }

    /**
     * 修改了头像
     */
    public void updateFace(String locaPath) {
        DataUtils.downloadPicture(drawer_face_img, locaPath, R.drawable.def_face);
    }


    /**
     * 用户退出重置状态
     */
    public void loginOut() {
        mtv_flowers.setText("--");
        mtv_weiwangs.setText("--");
        mtv_feimi.setText("--");
        mtv_money.setText("--");
        mtv_xunzhang.setText("--");

        num_friends.setText("好友");
        num_post.setText("帖子");
        num_collection.setText("收藏");
        num_task.setText("任务");

        migv_remind_message.setVisibility(View.GONE);
        migv_rengzheng.setVisibility(View.GONE);
        user_info_layout.setVisibility(View.GONE);
        login_register_view.setVisibility(View.VISIBLE);
        drawer_face_img.setImageResource(R.drawable.def_face_2);

        WidgetUtils.setVisible(newbie_task, false);
        WidgetUtils.setVisible(remind_task, false);
        WidgetUtils.setVisible(remind_num_task, false);
        WidgetUtils.setVisible(banner_layout, false);

        bindIsSigin(false);
    }


    /**
     * 设置include引用的item
     *
     * @param view
     * @param resId
     * @param value
     */
    private void bindItem(View view, int resId, String value) {
        TextView labelFeedBack = V.f(view, R.id.comment_person_name);
        WidgetUtils.setText(labelFeedBack, value);
        TvDrawbleUtils.chageDrawble(labelFeedBack, resId);
    }


    /**
     * 任务
     *
     * @param allBean
     */
    public void bindTaskData(final MyTaskAllBean allBean) {
        if (newbie_task == null)
            newbie_task = newbie_task_layout.inflate();
        bindDailyTask(newbie_task, allBean.getAlldaily_tasks());


        if (remind_task == null)
            remind_task = remind_task_layout.inflate();
        bindActivityTask(remind_task, allBean.getAdvanced_tasks());

        bindBanner(allBean.getBanner());

    }


    //每日任务设置
    private void bindDailyTask(View taskLayout, final List<MyTaskBean> datas) {
        V.f(taskLayout, R.id.see_more).setVisibility(View.GONE);
        bindTaskData(taskLayout, "每日任务", datas, true);
    }

    //活动任务设置
    private void bindActivityTask(View taskLayout, final List<MyTaskBean> datas) {
        //最多显示两条数据
        int showMaxSize = 2;
        int allSize = datas == null ? 0 : datas.size();
        //总数量大于最多显示数量，显示查看更多
        WidgetUtils.setVisible(V.f(taskLayout, R.id.see_more), allSize > showMaxSize);

        if (allSize > showMaxSize) {
            List<MyTaskBean> showData = datas.subList(0, showMaxSize);
            bindTaskData(taskLayout, "活动任务", showData, false);
        } else {
            bindTaskData(taskLayout, "活动任务", datas, false);
        }
    }

    private void bindTaskData(View taskLayout, String titleName, final List<MyTaskBean> datas, boolean isStatus) {
        WidgetUtils.setVisible(taskLayout, !DataUtils.isEmpty(datas));
        if (DataUtils.isEmpty(datas)) return;
        TextView title = V.f(taskLayout, R.id.title);
        title.setText(titleName);
        TaskListGroupLayout tasklist = V.f(taskLayout, R.id.tasklist);
        tasklist.removeAllViews();
        V.f(taskLayout, R.id.see_more).setOnClickListener(this);
        tasklist.bindData(datas, isStatus);
        for (int i = 0; i < tasklist.getChildCount(); i++) {
            final int finalI = i;
            tasklist.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iActionListener != null) {
                        iActionListener.taskClick(datas.get(finalI));
                    }
                }
            });
        }
    }

    /**
     * 根据是否设置过生日
     * true=还没有没有设置过生日
     *
     * @param bol
     */
    public void bindBirthdayText(boolean bol) {
        my_member_privileges.setText(bol ? "完善生日领礼包" : "完善个人资料");
    }

    /**
     * true =已签到
     *
     * @param isSigin
     */
    public void bindIsSigin(boolean isSigin) {
//        qiandao_tv.setText(isSigin ? "已签到" : "签到");
    }

    /**
     * 设置banner
     *
     * @param bannerBeanList
     */
    public void bindBanner(List<MyTaskAllBean.BannerBean> bannerBeanList) {
        WidgetUtils.setVisible(banner_layout, !DataUtils.isEmpty(bannerBeanList));

        banner_layout.bindData(bannerBeanList, new BannerViewpager.IBannerSetting<MyTaskAllBean.BannerBean>() {
            @Override
            public void bannerIgvSetting(ImageView igv, final MyTaskAllBean.BannerBean data) {
                ImageOptions image_FIT_XY = new ImageOptions.Builder()
                        .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
                        .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
                        .build();
                x.image().bind(igv, data.getImage_url(), image_FIT_XY);
                igv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iActionListener != null) iActionListener.bannerClick(data);
                    }
                });
            }
        });

    }


}
