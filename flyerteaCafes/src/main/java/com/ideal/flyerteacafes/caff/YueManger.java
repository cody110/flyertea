package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;
import android.util.Log;

import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/2/23.
 * 阅读记录置灰
 * 最多存储300，达到300删除早起100条
 */

public class YueManger {

    //TODO 方便测试改了条数
    private static final int KEY_MAX_SIZE = 300;
    private static final int KEY_HOLD_SIZE = 200;
    private static final String KEY_READS_USER_ID = "reads_is_";


    private Map<String, Integer> readsMap = new HashMap<>();

    private static YueManger instance;

    private YueManger() {
    }

    public static YueManger getInstance() {
        if (instance == null) {
            synchronized (YueManger.class) {
                instance = new YueManger();
            }
        }
        return instance;
    }


    /**
     * 用户登录了就调用次方法
     * 初始化用户的阅读记录
     */
    public void initUserReadIds() {
        String readsText = SharedPreferencesString.getInstances().getStringToKey(KEY_READS_USER_ID + UserManger.getUserInfo().getMember_uid());

        if (!TextUtils.isEmpty(readsText)) {
            readsMap = GsonTools.getMapInteger(readsText);
        }

    }


    /**
     * 退出时，
     * 保存用户阅读记录
     * 需在清除用户数据之前调用，因为需要uid
     */
    public void saveReadIdsByLoca() {
        if (UserManger.getUserInfo() == null) return;
        String readsText = "";
        if (readsMap.size() > 0)
            readsText = GsonTools.objectToJsonString(readsMap);
        SharedPreferencesString.getInstances().savaToString(KEY_READS_USER_ID + UserManger.getUserInfo().getMember_uid(), readsText);
        SharedPreferencesString.getInstances().commit();
    }

    /**
     * 方法类，做了未登录判断，外部可以不管
     * 保存阅读记录
     *
     * @param id
     */
    public void save(String id) {
        if (!UserManger.isLogin()) return;
        readsMap.put(String.valueOf(id), (int) DateUtil.getDateline());
        if (readsMap.size() >= KEY_MAX_SIZE) {
            clearMoreData();
        }
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_YUE_CHANGE));
    }

    /**
     * 可以不做登录判断
     * 是否已读
     *
     * @param id
     * @return
     */
    public boolean isRead(String id) {
        return readsMap.containsKey(id);
    }


    /**
     * 使用 Map按value进行排序
     *
     * @return
     */
    public void clearMoreData() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(
                readsMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        readsMap.clear();
        int i = 0;
        while (iter.hasNext() && i < KEY_HOLD_SIZE) {
            tmpEntry = iter.next();
            readsMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            i++;
        }
    }


    class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {


        @Override
        public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
//            return me2.getValue() - me1.getValue();
            return me2.getValue() > me1.getValue() ? 1 : (Objects.equals(me2.getValue(), me1.getValue()) ? 0 : -1);
        }
    }


}
