package com.ideal.flyerteacafes.model.entity;

public class MyFrirends {
    private String Version;
    private String Charset;
    private FrirendModel Variables;
    private MessageBean Message;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getCharset() {
        return Charset;
    }

    public void setCharset(String charset) {
        Charset = charset;
    }

    public FrirendModel getVariables() {
        return Variables;
    }

    public void setVariables(FrirendModel variables) {
        Variables = variables;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean message) {
        Message = message;
    }
}
