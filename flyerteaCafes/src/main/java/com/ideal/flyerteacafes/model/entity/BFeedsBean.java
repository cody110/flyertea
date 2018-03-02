package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2015/12/5.
 */
public class BFeedsBean {


    private boolean hasNext;
    private List<FeedsBean> feeds;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<FeedsBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<FeedsBean> feeds) {
        this.feeds = feeds;
    }
}
