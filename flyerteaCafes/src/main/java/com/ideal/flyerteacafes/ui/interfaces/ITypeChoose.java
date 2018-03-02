package com.ideal.flyerteacafes.ui.interfaces;

import com.ideal.flyerteacafes.model.entity.TypeBaseBean;

public interface ITypeChoose {
    public void typeChoose(TypeBaseBean.TypeInfo oneInfo, TypeBaseBean.TypeInfo twoInfo);

    public void popDismiss();
}
