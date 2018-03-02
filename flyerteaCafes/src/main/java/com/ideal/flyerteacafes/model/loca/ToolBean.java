package com.ideal.flyerteacafes.model.loca;

/**
 * Created by fly on 2016/6/6.
 */
public class ToolBean {

    private int background;

    private int icon;

    private int arrow;

    private String title;

    private String subject;

    private String description;


    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArrow() {
        return arrow;
    }

    public void setArrow(int arrow) {
        this.arrow = arrow;
    }

    public ToolBean(int background, int icon, int arrow, String title, String subject, String description) {
        this.background = background;
        this.icon = icon;
        this.arrow = arrow;
        this.title = title;
        this.subject = subject;
        this.description = description;
    }
}
