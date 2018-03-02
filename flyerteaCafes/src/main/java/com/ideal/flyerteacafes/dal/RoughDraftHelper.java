package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.RoughDraftBean;

import org.xutils.ex.DbException;

public class RoughDraftHelper extends BaseHelper {


    public RoughDraftBean getRoughDraftByFid(int fid, int typeid) {
        try {
            return dbUtils.selector(RoughDraftBean.class).where("fid", "=", fid).and("typeid", "=", typeid).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveRoughDraftByDB(RoughDraftBean bean) {
        try {
            dbUtils.save(bean);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deteleRoughDraftByFid(int fid) {
        delete(RoughDraftBean.class, "fid", "=", fid);
    }

    public void deteleAll() {
        try {
            dbUtils.delete(RoughDraftBean.class);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateRoughDraftByDB(RoughDraftBean bean) {
        deteleAll();
        saveRoughDraftByDB(bean);
    }

}
