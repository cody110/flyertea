package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;

import org.xutils.ex.DbException;

import java.util.List;

public class SearchHistoryHelper extends BaseHelper {


    public static final int TYPE_THREAD_CODY = 0, TYPE_REPORT_CODY = 1, TYPE_RAIDERS_CODY = 2;


    //得到所有历史
    public List<SearchHistoryBean> getSearchHistoryList() {
        List<SearchHistoryBean> listBean = null;
        try {
            listBean = dbUtils.selector(SearchHistoryBean.class).orderBy("id", true).limit(10).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return listBean;
    }

    public List<SearchHistoryBean> getSearchHistoryList(int type) {
        List<SearchHistoryBean> listBean = null;
        try {
            listBean = dbUtils.selector(SearchHistoryBean.class).where("type", "=", type).orderBy("id", true).limit(10).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return listBean;
    }

    //插入搜索历史
    public void insertSearchHistory(SearchHistoryBean history) {
        try {
            dbUtils.save(history);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteSearchHistory() {
        try {
            dbUtils.delete(SearchHistoryBean.class);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据搜索名删除
     *
     * @param text
     */
    public void deleteBySearchName(String text) {
        delete(SearchHistoryBean.class, "searchName", "=", text);
    }

    public void deleteSearchHistory(int type) {
        delete(SearchHistoryBean.class, "type", "=", type);
    }


}
