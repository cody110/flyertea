package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/10/19.
 */

public class MedalsBean implements Serializable {

    private List<MyMedalsSubBean> medals;
    private String name;

    public List<MyMedalsSubBean> getMedals() {
        return medals;
    }

    public String getName() {
        return name;
    }

}
