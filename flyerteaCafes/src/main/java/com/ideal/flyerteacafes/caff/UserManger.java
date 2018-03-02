package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.PersonalBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.entity.YouzanBean;
import com.ideal.flyerteacafes.utils.IntentKey;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/8/23.
 * UserBean 用户信息管理类
 */
public class UserManger {

    private static UserManger instance;

    private UserManger() {
        EventBus.getDefault().register(this);
    }

    public static UserManger getInstance() {
        if (instance == null) {
            synchronized (UserManger.class) {
                instance = new UserManger();
            }
        }
        return instance;
    }


    private static UserBean userInfo;
    private static PersonalBean personalBean;

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserBean getUserInfo() {
        if (userInfo == null)
            userInfo = SharedPreferencesString.getInstances().getUserInfo();
        return userInfo;
    }

    /**
     * `
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (userInfo == null)
            userInfo = SharedPreferencesString.getInstances().getUserInfo();
        return userInfo != null;
    }

    /**
     * 是否可以编辑视频贴
     *
     * @return
     */
    public static boolean isWriteVideoThread() {
        if (getUserInfo() != null) {
            if (TextUtils.equals(getUserInfo().getAllowpostvideo(), "1")) {
                return true;
            }
        }
        return false;
    }


//    public static boolean isLogin() {
//        String uid = SharedPreferencesString.getInstances().getStringToKey("uid");
//        if (uid.equals(""))
//            return false;
//        else
//            return true;
//    }

    /**
     * 刷新了数据，从本地重新获取用户信息
     */
    public static void refreshenUserInfo() {
        if (isLogin()) {
            userInfo = SharedPreferencesString.getInstances().getUserInfo();
        } else {
            userInfo = null;
        }
    }

    public static void refreshenUserInfo(UserBean info) {
        if (isLogin()) {
            userInfo = info;
        } else {
            userInfo = null;
        }
    }

    public static void loginOut() {
        SharedPreferencesString.getInstances().savaToString("userinfo");
        SharedPreferencesString.getInstances().savaToString("password");
        SharedPreferencesString.getInstances().savaToString("addId");
        SharedPreferencesString.getInstances().savaToString("cookie");
        SharedPreferencesString.getInstances().savaToInt("missions", 0);
        SharedPreferencesString.getInstances().savaToString(IntentKey.QUPAI_TOKEN);
        SharedPreferencesString.getInstances().commit();
        userInfo = null;
    }

    public static String getFormhash() {
        return SharedPreferencesString.getInstances().getStringToKey("formhash");
    }


    List<MyFavoriteBean> favList = new ArrayList<>();

    public List<MyFavoriteBean> getFavList() {
        return favList;
    }

    public void setFavList(List<MyFavoriteBean> data) {
        favList.clear();
        if (data != null)
            favList.addAll(data);
    }

    /**
     * 关注变化
     *
     * @param tagEvent
     */
    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.FAV_LIST_CHANGE) {
            List<MyFavoriteBean> favList = (List<MyFavoriteBean>) tagEvent.getBundle().getSerializable("data");
            this.favList.clear();
            this.favList.addAll(favList);
        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_FOLLOW) {
            MyFavoriteBean bean = (MyFavoriteBean) tagEvent.getBundle().getSerializable("data");
            this.favList.add(bean);
        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_CANCLE) {
            MyFavoriteBean bean = (MyFavoriteBean) tagEvent.getBundle().getSerializable("data");
            for (MyFavoriteBean my : favList) {
                if (TextUtils.equals(my.getFavid(), bean.getFavid())) {
                    favList.remove(my);
                    break;
                }
            }
        }
    }

    /**
     * 获取任务数量
     *
     * @return
     */
    public int getMissions() {
        return SharedPreferencesString.getInstances().getIntToKey("missions");
    }

    public void savaMissions(int missions) {
        SharedPreferencesString.getInstances().savaToInt("missions", missions);
        SharedPreferencesString.getInstances().commit();
    }

    public static String getCookie() {
        return SharedPreferencesString.getInstances().getStringToKey("cookie");
    }


    private YouzanBean loginYouzanBean;
    private YouzanBean initYouzanBean;

    public YouzanBean getYouzanLoginBean() {
        return loginYouzanBean;
    }

    public YouzanBean getYouzanInitBean() {
        return initYouzanBean;
    }

    public void setYouzanLoginBean(YouzanBean bean) {
        loginYouzanBean = bean;
    }

    public void setYouzanInitBean(YouzanBean bean) {
        initYouzanBean = bean;
    }

    public void setYouzanLogOut() {
        loginYouzanBean = null;
    }

}
