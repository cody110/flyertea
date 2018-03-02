package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * 帖子详情页，构造符合接口数据的类
 *
 * @author fly
 */
public class PostDetailsBean extends BaseBean {

    private ThreadDetailsBean threadDetailsBean;

//    @Transient
    private List dataList;
//    @Transient
    private int listSize;

    private String advHtml = "0";//广告html

    public String getAdvHtml() {
        return advHtml;
    }

    public void setAdvHtml(String advHtml) {
        this.advHtml = advHtml;
    }

    public ThreadDetailsBean getThreadDetailsBean() {
        return threadDetailsBean;
    }

    public void setThreadDetailsBean(ThreadDetailsBean threadDetailsBean) {
        this.threadDetailsBean = threadDetailsBean;
    }

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

}
