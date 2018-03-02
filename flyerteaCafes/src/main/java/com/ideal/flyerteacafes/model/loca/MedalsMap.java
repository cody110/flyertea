package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.model.entity.MedalsBean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by fly on 2017/10/19.
 */

public class MedalsMap implements Serializable {
    private Map<String, MedalsBean> map;

    public Map<String, MedalsBean> getMap() {
        return map;
    }

    public void setMap(Map<String, MedalsBean> map) {
        this.map = map;
    }
}
