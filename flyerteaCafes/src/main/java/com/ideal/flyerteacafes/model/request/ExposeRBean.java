package com.ideal.flyerteacafes.model.request;

/**
 * Created by fly on 2015/12/9.
 */
public class ExposeRBean {

    private int feed_id ;
    private int status ;
    private int exposer_id;
    private String exposed_reason;

    public ExposeRBean(int feed_id, int status, int exposer_id,String exposed_reason) {
        this.feed_id = feed_id;
        this.status = status;
        this.exposer_id = exposer_id;
        this.exposed_reason=exposed_reason;
    }

}
