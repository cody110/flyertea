package com.ideal.flyerteacafes.caff;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.webkit.WebView;

import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class SharedPreferencesString {


    private static SharedPreferencesString sharedPreferencesString;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferencesString getInstances() {
        return getInstances(FlyerApplication.getContext());
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static SharedPreferencesString getInstances(Context mContext) {
        if (sharedPreferencesString == null) {
            sharedPreferencesString = new SharedPreferencesString();
            if (sharedPreferences == null) {
                sharedPreferences = mContext.getSharedPreferences(
                        Utils.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
            }
            if (editor == null) {
                editor = sharedPreferences.edit();
            }
        }
        return sharedPreferencesString;
    }

    public void savaToInt(String intKey, int intValue) {
        editor.putInt(intKey, intValue);
    }

    public void savaToFloat(String floatKey, Float floatValue) {
        editor.putFloat(floatKey, floatValue);
    }

    public void savaToString(String strKey, Object strVaules) {
        if (strVaules != null)
            editor.putString(strKey, strVaules.toString());
    }

    public void savaToString(String strKey) {
        if (!DataUtils.isEmpty(strKey))
            editor.putString(strKey, "");
    }

    public void savaToBoolean(String strKey, boolean bol) {
        editor.putBoolean(strKey, bol);
    }

    public void commit() {
        editor.commit();
    }

    public boolean getBooleanToKey(String strKey) {
        return sharedPreferences.getBoolean(strKey, false);
    }

    public boolean getBooleanToKey(String strKey,boolean bol) {
        return sharedPreferences.getBoolean(strKey, bol);
    }

    public String getStringToKey(String strKey) {
        return sharedPreferences.getString(strKey, "");
    }

    public String getStringToKey(String strKey, String defaultStr) {
        return sharedPreferences.getString(strKey, defaultStr);
    }

    public int getIntToKey(String strKey) {
        return sharedPreferences.getInt(strKey, 0);
    }

    public int getIntToKey(String strKey, int defaultStr) {
        return sharedPreferences.getInt(strKey, defaultStr);
    }

    public float getFloatToKey(String strKey) {
        return sharedPreferences.getFloat(strKey, 0);
    }


    public void saveUserinfo(UserBean userInfo) {
        saveObject("userinfo", userInfo);
    }

    public UserBean getUserInfo() {
        return (UserBean) readObject("userinfo");
    }

    /**
     * desc:保存对象
     *
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     *            modified:
     */
    public static void saveObject(String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = SharedPreferencesString.getInstances().editor;
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = DataTools.bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param key
     * @return modified:
     */
    public static Object readObject(String key) {
        try {
            SharedPreferences sharedata = SharedPreferencesString.getInstances().sharedPreferences;
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = DataTools.StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    public float getScale() {
        return sharedPreferences.getFloat("scale", 1);
    }


    /**
     * 保存选择了关注版块的用户id 的key
     */
    private String KEY_FORUMFAVUSERID = "ForumFavUserId";

    /**
     * 保存选择了关注版块的用户id
     */
    public void saveForumFavUserId(String userid) {
        String userids = getStringToKey(KEY_FORUMFAVUSERID);
        if (!TextUtils.isEmpty(userids)) {
            userid = DataUtils.splicMark(userids, userid);
        }
        savaToString(KEY_FORUMFAVUSERID, userid);
        commit();
    }

    public int getW_Screen() {
        return getIntToKey("w_screen");
    }

    public int getH_Screen() {
        return getIntToKey("h_screen");
    }

    /**
     * 判断当前用户是否选择过关注版块
     *
     * @param userid
     * @return 是 返回true
     */
    public boolean isHavForumFavUserId(String userid) {
        String userids = getStringToKey(KEY_FORUMFAVUSERID);
        String[] idArray = DataUtils.splitMark(userids);
        if (idArray != null) {
            for (String s : idArray) {
                if (TextUtils.equals(userid, s)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 是否是第一安装
     *
     * @return
     */
    public boolean isFirstInstall() {
        boolean first = sharedPreferences.getBoolean("isFirstInstall", true);
        if (first) {
            savaToBoolean("isFirstInstall", false);
            commit();
        }
        return first;
    }

    /**
     * 是否是第一次进search
     *
     * @return
     */
    public boolean isFirstToSearch() {
        boolean first = sharedPreferences.getBoolean("isFirstToSearch", true);
        return first;
    }

    /**
     * 第一次
     *
     * @return
     */
    public boolean isFirstVideo() {
        boolean first = sharedPreferences.getBoolean("isFirstVideo", true);
        if (first) {
            savaToBoolean("isFirstVideo", false);
            commit();
        }
        return first;
    }

    /**
     * 置为已进
     */
    public void setIsToSearch() {
        savaToBoolean("isFirstToSearch", false);
        commit();
    }


    /**
     * 获取userAgent
     *
     * @return
     */
    public String getUserAgent() {
        return getStringToKey("userAgent");
    }

    /**
     * 设置userAgent
     */
    public void savaUserAgent() {
        String userAgent = new WebView(FlyerApplication.getContext()).getSettings().getUserAgentString();
        savaToString("userAgent", userAgent);
        commit();
    }

}
