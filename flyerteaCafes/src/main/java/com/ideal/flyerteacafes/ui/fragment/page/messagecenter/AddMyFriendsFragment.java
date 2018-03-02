package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AddFriendsAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.ui.activity.interfaces.IAddMyFriends;
import com.ideal.flyerteacafes.ui.activity.presenter.AddMyFriendsPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.ideal.flyerteacafes.utils.SmartUtil.BToast;

/**
 * Created by Cindy on 2017/3/20.
 */
public class AddMyFriendsFragment extends NewPullRefreshFragment<NotificationBean> implements AddFriendsAdapter.IAddFriendsAdapterClick, IAddMyFriends {

    private AddMyFriendsPresenter myPresenter;
    private AddFriendsAdapter addFriendsAdapter;
    private SharedPreferencesString preferences;
    private List<NotificationBean> addFriendsList = new ArrayList<>();
    private String uid, fromuid;
    BaseHelper helper;

    @Override
    public void initViews() {
        super.initViews();
        myPresenter.setiAddMyFriends(this);
        preferences = SharedPreferencesString.getInstances();
        helper = BaseHelper.getInstance();
        addNormalLayout(R.layout.include_no_message);
    }

    @Override
    protected CommonAdapter<NotificationBean> createAdapter(final List<NotificationBean> datas) {
        addFriendsList = datas;
        WidgetUtils.setVisible(normalLayout, DataUtils.isEmpty(datas));
        return addFriendsAdapter = new AddFriendsAdapter(getActivity(), datas, R.layout.item_add_my_friends, this);
    }

    @Override
    protected PullRefreshPresenter<NotificationBean> createPresenter() {
        return myPresenter = new AddMyFriendsPresenter();
    }

    /**
     * 拒绝加好友
     *
     * @param position
     * @param notificationBean
     */
    @Override
    public void ignoreFriends(int position, NotificationBean notificationBean) {
        uid = notificationBean.getUid();
        fromuid = notificationBean.getFromuid();
        myPresenter.ignoreAddFriends(fromuid);
    }

    /**
     * 接受加好友
     *
     * @param position
     * @param notificationBean
     */
    @Override
    public void acceptFriends(int position, NotificationBean notificationBean) {
        uid = notificationBean.getUid();
        fromuid = notificationBean.getFromuid();
        myPresenter.agreeAddFriends(fromuid);
    }

    /**
     * 接受添加好友成功
     *
     * @param result
     */
    @Override
    public void callbackAgreeAddFriends(String result) {
        BaseBean msg = JsonAnalysis.getMessageBean(result);
        if (msg != null) {
            if (msg.getMessage() != null
                    && msg.getMessage().equals("您已和{username}成为好友")) {
                BToast("同意添加好友成功");
                preferences.savaToString("addId");
                preferences.commit();
                if (Integer.valueOf(fromuid) < addFriendsList.size()) {
                    NotificationBean newFriendsInfo = addFriendsList.get(Integer.valueOf(fromuid));
                    Userinfo info = new Userinfo();
                    info.setUid(newFriendsInfo.getFromuid());
                    info.setUsername(newFriendsInfo.getFromuser());
                    helper.saveBean(info);
                    addFriendsList.remove(fromuid);
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new TagEvent(TagEvent.TAG_ADD_FRIEND_SUCCESS));
                }
            } else {
                BToast(msg.getMessage());
            }
        }
        headerRefreshing();
    }

    /**
     * 拒绝添加好友成功
     *
     * @param result
     */
    @Override
    public void callbackIgnoreAddFriends(String result) {
        BaseBean msg = JsonAnalysis.getMessageBean(result);
        BToast(msg.getMessage());
        headerRefreshing();
    }
}
