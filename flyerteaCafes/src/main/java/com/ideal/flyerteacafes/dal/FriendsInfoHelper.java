package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.FriendsInfo;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class FriendsInfoHelper extends BaseHelper {

    private static FriendsInfoHelper helpter;

    public static FriendsInfoHelper getInstance() {
        if (helpter == null)
            helpter = new FriendsInfoHelper();
        return helpter;
    }

    private FriendsInfoHelper() {
    }

    public FriendsInfo getFriendsInfoByUid(String uid) {
        FriendsInfo info = null;
        try {
            info = dbUtils.selector(FriendsInfo.class).where(WhereBuilder.b("uid", "=", uid)).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    public List<FriendsInfo> getFriendsInfoByUserName(String userName) {
        List<FriendsInfo> infoList = null;
        try {
            infoList=dbUtils.selector(FriendsInfo.class).where(WhereBuilder.b("fusername", "LIKE", "%" + userName + "%")).findAll();
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return infoList;
    }



    public void deleteFriendsInfoById(String fuid) {
        try {
            dbUtils.delete(FriendsInfo.class, WhereBuilder.b("fuid", "=", fuid));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}
