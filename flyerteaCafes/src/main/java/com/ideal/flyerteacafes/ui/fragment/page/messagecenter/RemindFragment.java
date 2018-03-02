package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.entity.PersonalBean;
import com.ideal.flyerteacafes.model.entity.PersonalMode;
import com.ideal.flyerteacafes.ui.activity.PostAchievementActivity;
import com.ideal.flyerteacafes.ui.activity.RecommendArticleActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.AddMyFriendsActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.FlyerActivitiesActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.SystemRemindActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.ReplyMineActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.SendFlowersActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleCommentActivity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IMessageFm;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.MessageFmPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cindy on 2017/3/16.
 */
public class RemindFragment extends MVPBaseFragment<IMessageFm, MessageFmPresenter> implements IMessageFm {

    /**
     * 回复我的
     **/
    @ViewInject(R.id.mrl_reply_my)
    private RelativeLayout mrl_reply_my;
    /**
     * 送我鲜花
     **/
    @ViewInject(R.id.mrl_send_flowers)
    private RelativeLayout mrl_send_flowers;
    /**
     * 加我好友
     **/
    @ViewInject(R.id.mrl_add_friends)
    private RelativeLayout mrl_add_friends;
    /**
     * 帖子成就
     **/
    @ViewInject(R.id.mrl_post_achievement)
    private RelativeLayout mrl_post_achievement;
    /**
     * 其他消息
     **/
    @ViewInject(R.id.mrl_other_messages)
    private RelativeLayout mrl_other_messages;
    /**
     * 飞客活动
     **/
    @ViewInject(R.id.mrl_flyer_activies)
    private RelativeLayout mrl_flyer_activies;
    /**
     * 好文推荐
     **/
    @ViewInject(R.id.mrl_flyer_recommend)
    private RelativeLayout mrl_flyer_recommend;


    private TextView replyNewnum, flowersNewNum, addFriendsNewNum, postAchievementNewNum, otherMessageNewNum, activiesNewNum, recommemdNewNum, activitys_comment_time, recommemd_comment_time;
    private TextView mtvPrompt, mtvRecommend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        x.view().inject(this, view);
        initViews();
        mPresenter.init(getActivity());
        return view;
    }


    /**
     * @description: init View
     * @author: Cindy
     * @date: 2017/3/10 18:34
     */
    public void initViews() {
        TextView labelReply = V.f(mrl_reply_my, R.id.comment_person_name);
        labelReply.setText(R.string.message_center_ac_reply_my);
        TvDrawbleUtils.chageDrawble(labelReply, R.drawable.icon_other_message);
        replyNewnum = V.f(mrl_reply_my, R.id.comment_person_remind_num);

        TextView labelSendFlowers = V.f(mrl_send_flowers, R.id.comment_person_name);
        labelSendFlowers.setText(R.string.message_center_ac_send_flowers);
        TvDrawbleUtils.chageDrawble(labelSendFlowers, R.drawable.icon_send_flowers);
        flowersNewNum = V.f(mrl_send_flowers, R.id.comment_person_remind_num);

        TextView labelAddFriends = V.f(mrl_add_friends, R.id.comment_person_name);
        labelAddFriends.setText(R.string.message_center_ac_add_friends);
        TvDrawbleUtils.chageDrawble(labelAddFriends, R.drawable.icon_add_friends);
        addFriendsNewNum = V.f(mrl_add_friends, R.id.comment_person_remind_num);


        TextView labelPostAchievement = V.f(mrl_post_achievement, R.id.comment_person_name);
        labelPostAchievement.setText(R.string.message_center_ac_post_achievement);
        TvDrawbleUtils.chageDrawble(labelPostAchievement, R.drawable.icon_post_achievement);
        postAchievementNewNum = V.f(mrl_post_achievement, R.id.comment_person_remind_num);

        TextView labelOtherMessage = V.f(mrl_other_messages, R.id.comment_person_name);
        labelOtherMessage.setText(R.string.message_center_ac_other_message);
        TvDrawbleUtils.chageDrawble(labelOtherMessage, R.drawable.icon_reply_mine);
        otherMessageNewNum = V.f(mrl_other_messages, R.id.comment_person_remind_num);

        TextView labelFlyerActivies = V.f(mrl_flyer_activies, R.id.comment_person_name);
        activiesNewNum = V.f(mrl_flyer_activies, R.id.comment_person_remind_num);
        labelFlyerActivies.setText(R.string.message_center_ac_flyer_activies);
        ImageView labelFlyerActiviesIcon = V.f(mrl_flyer_activies, R.id.comment_person_icon);
        labelFlyerActiviesIcon.setImageResource(R.drawable.icon_flyer_activies);
        mtvPrompt = V.f(mrl_flyer_activies, R.id.comment_person_name_prompt);
        mtvPrompt.setText("暂无消息");
        activitys_comment_time = V.f(mrl_flyer_activies, R.id.comment_time);


        TextView labelRecommendActivies = V.f(mrl_flyer_recommend, R.id.comment_person_name);
        recommemdNewNum = V.f(mrl_flyer_recommend, R.id.comment_person_remind_num);
        labelRecommendActivies.setText(R.string.message_center_ac_flyer_recommend);
        ImageView labelRecommendActiviesIcon = V.f(mrl_flyer_recommend, R.id.comment_person_icon);
        labelRecommendActiviesIcon.setImageResource(R.drawable.icon_flyer_recommend);
        mtvRecommend = V.f(mrl_flyer_recommend, R.id.comment_person_name_prompt);
        mtvRecommend.setText("暂无消息");
        recommemd_comment_time = V.f(mrl_flyer_recommend, R.id.comment_time);

        V.f(mrl_other_messages, R.id.item_person_divider).setVisibility(View.INVISIBLE);
    }

    private void showNewNum(PersonalBean personalBean) {
        if (personalBean == null)
            return;
        setMsgNum(replyNewnum, personalBean.getPosts());
        setMsgNum(flowersNewNum, personalBean.getFlowers());
        setMsgNum(addFriendsNewNum, personalBean.getFriends());
        setMsgNum(otherMessageNewNum, personalBean.getSystem());
        setMsgNum(postAchievementNewNum, personalBean.getReward());
        setMsgNum(activiesNewNum, personalBean.getActivity());
        setMsgNum(recommemdNewNum, personalBean.getRecommend());

        if (!DataUtils.isEmpty(personalBean.getActivities())) {
            PersonalMode flyerActivies = personalBean.getActivities().get(0);
            WidgetUtils.setTextHtml(mtvPrompt, flyerActivies.getSubject());
            WidgetUtils.setText(activitys_comment_time, DataUtils.conversionTime(flyerActivies.getDateline()));
        }

        if (!DataUtils.isEmpty(personalBean.getRecommends())) {
            PersonalMode flyerActivies = personalBean.getRecommends().get(0);
            WidgetUtils.setTextHtml(mtvRecommend, flyerActivies.getSubject());
            WidgetUtils.setText(recommemd_comment_time, DataUtils.conversionTime(flyerActivies.getDateline()));
        }

    }

    private void setMsgNum(TextView tv, String value) {
        int num = Integer.valueOf(value);
        if (num == 0) {
            tv.setVisibility(View.GONE);
        } else if (num > 0 && num < 100) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(num + "");
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.no_read_message);
        }
    }


    @Event({R.id.mrl_reply_my, R.id.mrl_send_flowers, R.id.mrl_add_friends, R.id.mrl_other_messages, R.id.mrl_flyer_activies, R.id.mrl_post_achievement, R.id.mrl_flyer_recommend})
    private void click(View v) {
        String name = "";
        Map<String, String> map = new HashMap<>();

        switch (v.getId()) {
            case R.id.mrl_reply_my://回复我的
                name = getString(R.string.message_center_ac_reply_my);
                jumpActivityForResult(ReplyMineActivity.class, null, FinalUtils.REQUEST_CODE_MESSAGE);
                break;
            case R.id.mrl_send_flowers://送我鲜花
                name = getString(R.string.message_center_ac_send_flowers);
                jumpActivityForResult(SendFlowersActivity.class, null, FinalUtils.REQUEST_CODE_MESSAGE);
                break;
            case R.id.mrl_add_friends://加我好友
                name = getString(R.string.message_center_ac_add_friends);
                jumpActivityForResult(AddMyFriendsActivity.class, null, FinalUtils.REQUEST_CODE_MESSAGE);
                break;
            case R.id.mrl_other_messages://系统提醒消息
                name = getString(R.string.message_center_ac_other_message);
                jumpActivityForResult(SystemRemindActivity.class, null, FinalUtils.REQUEST_CODE_MESSAGE);
                break;
            case R.id.mrl_flyer_activies://活动精选
                name = getString(R.string.message_center_ac_flyer_activies);
                if (mPresenter.getPersonalBean() != null && !DataUtils.isEmpty(mPresenter.getPersonalBean().getRecommends())) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) mPresenter.getPersonalBean().getActivities());
                    bundle.putString(Marks.MarkType.MARKTYPE, Marks.MarkType.ACTIVITY);
                    jumpActivityForResult(FlyerActivitiesActivity.class, bundle, FinalUtils.REQUEST_CODE_MESSAGE);
                }
                break;
            case R.id.mrl_flyer_recommend://好文推荐
                name = getString(R.string.message_center_ac_flyer_recommend);
                if (mPresenter.getPersonalBean() != null && !DataUtils.isEmpty(mPresenter.getPersonalBean().getRecommends())) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) mPresenter.getPersonalBean().getRecommends());
                    bundle.putString(Marks.MarkType.MARKTYPE, Marks.MarkType.RECOMMEND);
                    jumpActivityForResult(RecommendArticleActivity.class, bundle, FinalUtils.REQUEST_CODE_MESSAGE);
                }
                break;
            case R.id.mrl_post_achievement://贴子成就
                name = getString(R.string.message_center_ac_post_achievement);
                jumpActivityForResult(PostAchievementActivity.class, null, FinalUtils.REQUEST_CODE_MESSAGE);
                break;
        }

        map.put("name", name);
        MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.message_category_click, map);
    }

    @Override
    protected MessageFmPresenter createPresenter() {
        return new MessageFmPresenter();
    }

    /**
     * 飞客活动
     *
     * @param flyerActivies
     */
    @Override
    public void callbackFlyerActivies(NotificationBean flyerActivies) {
        if (flyerActivies == null) {
            mrl_flyer_activies.setVisibility(View.GONE);
        } else {
            mrl_flyer_activies.setVisibility(View.VISIBLE);
            mtvPrompt.setText(flyerActivies.getContent());
        }
    }

    /**
     * 消息中心提醒数量
     *
     * @param personalBean
     */
    @Override
    public void callbackPersonal(PersonalBean personalBean) {
        showNewNum(personalBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.getActivity().RESULT_OK == resultCode) {
            mPresenter.requestPersonal();
        }
    }
}
