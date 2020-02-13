package com.yaxon.centralplainlion.bean;

/**
 * Description:
 * Created by kimiffy on 2020/1/15.
 */

public class BannerBean {

    public BannerBean(String imagePath, String title) {
        this.imagePath = imagePath;
        this.title = title;
    }

    private String imagePath;
    private String title;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
