package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.Userinfo;

public class UserinfoHelper extends BaseHelper {

    public Userinfo getUserinfoByUid(String uid) {
        Userinfo info = getBeanByFirst(Userinfo.class, "uid", "=", uid);
        return info;
    }

    public void deleteUserinfoById(String uid) {
        delete(Userinfo.class, "uid", "=", uid);
    }


}
