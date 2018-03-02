package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.MyPostBean;

import org.xutils.ex.DbException;

import java.util.List;

public class MyPostHelper extends BaseHelper {

    public void deteleAll() {
        try {
            dbUtils.delete(MyPostBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public List<MyPostBean> getPostListByDB(int mark) {
        List<MyPostBean> postList = getList(MyPostBean.class, "mark", "=", mark);
        return postList;
    }

    @SuppressWarnings("unused")
    public void setPostListByDB(List<MyPostBean> postList, int mark) {
        try {
            delete(MyPostBean.class, "mark", "=", mark);
            dbUtils.save(postList);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
