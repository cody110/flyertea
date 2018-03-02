package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.CitysBean;

import org.xutils.ex.DbException;

import java.util.List;

public class CityHelper extends BaseHelper {

    /**
     * 获取省
     *
     * @return
     */
    public List<CitysBean> getShengList() {
        try {
            List<CitysBean> cityList = dbUtils.selector(CitysBean.class).where("mark", "=", 0).findAll();
            return cityList;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取市
     *
     * @param cid
     * @return
     */
    public List<CitysBean> getShiList(int cid) {
        try {
            List<CitysBean> cityList = dbUtils.selector(CitysBean.class).where("mark", "=", 1).and("cid", "=", cid).findAll();
            return cityList;
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
