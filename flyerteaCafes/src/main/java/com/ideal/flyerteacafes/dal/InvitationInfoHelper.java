package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.InvitationInfo;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class InvitationInfoHelper extends BaseHelper {


    /**
     * 查询本地数据库帖子猎豹
     *
     * @param invitationList
     * @param fid
     * @return
     */
    public List<InvitationInfo> getInvitationListByDB(List<InvitationInfo> invitationList, int fid) {
        List<InvitationInfo> listLimit = getList(InvitationInfo.class, "fid", "=", fid);
        if (listLimit != null)
            invitationList.addAll(listLimit);
        return invitationList;
    }

    /**
     * 更新数据库，帖子信息
     *
     * @param fid
     * @param invitationList
     * @return
     */
    public boolean saveInvitationListByDB(int fid,List<InvitationInfo> invitationList) {
        boolean flag = true;
        try {
            dbUtils.delete(InvitationInfo.class, WhereBuilder.b("fid", "=", fid));
            dbUtils.save(invitationList);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }

}
