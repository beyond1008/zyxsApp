package com.yaxon.centralplainlion.bean;

/**
 * Description:
 * Created by kimiffy on 2020/1/15.
 */

public class HomeFunctionBean {

    private String title;
    private int type;
    private String iconPath;


    public HomeFunctionBean(String title, int type, String iconPath) {
        this.title = title;
        this.type = type;
        this.iconPath = iconPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
