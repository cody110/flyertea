package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2016/1/12.
 */
public class LoginBase {

    UserBean Variables;

    BaseBean Message;

    public UserBean getVariables() {
        return Variables;
    }

    public void setVariables(UserBean variables) {
        Variables = variables;
    }

    public BaseBean getMessage() {
        return Message;
    }

    public void setMessage(BaseBean message) {
        Message = message;
    }
}
