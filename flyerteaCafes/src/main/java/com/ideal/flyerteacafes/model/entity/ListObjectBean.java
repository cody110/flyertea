package com.ideal.flyerteacafes.model.entity;

import java.util.List;

public class ListObjectBean extends BaseBean {
    private List dataList;
    private int listSize;

    /**
     * @return the dataList
     */
    public List getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List dataList) {
        this.dataList = dataList;
        if (dataList == null) {
            this.listSize = 0;
        } else {
            this.listSize = dataList.size();
        }
    }

    /**
     * @return the listSize
     */
    public int getListSize() {
        return listSize;
    }

    /**
     * @param listSize the listSize to set
     */
    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public void print() {
        System.out.println("ret_code:" + this.getCode());
        System.out.println("ret_msg:" + this.getMessage());
        System.out.println("version:" + this.getVersion());
        System.out.println("list size:" + listSize);
    }

}
