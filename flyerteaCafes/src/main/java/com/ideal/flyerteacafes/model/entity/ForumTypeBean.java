package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 版块类型
 *
 * @author fly
 */
public class ForumTypeBean implements Serializable {

    private int typeId;

    private String typeName;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
