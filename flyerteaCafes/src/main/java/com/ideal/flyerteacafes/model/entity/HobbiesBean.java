package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 兴趣
 *
 * @author fly
 */
public class HobbiesBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String hobbies;//

    private int mark; //默认0，选中为1

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


}
