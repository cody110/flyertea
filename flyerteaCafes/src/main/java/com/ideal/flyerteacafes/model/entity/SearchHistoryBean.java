package com.ideal.flyerteacafes.model.entity;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 搜索历史
 *
 * @author fly
 */
@Table(name = "searchHistory")
public class SearchHistoryBean {



    @Column(
            name = "ID",
            isId = true,
            autoGen = true
    )
    private int id;

    @Column(name = "searchName")
    private String searchName;


    @Column(name = "type")
    private int type;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
