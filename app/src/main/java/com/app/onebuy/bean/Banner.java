package com.app.onebuy.bean;

/**
 * Created by otis on 2018/4/20.
 */

public class Banner extends BasisBean {

    private String title;
    private String category_id;
    private String open_type;
    private String open_content;
    private String images_id;
    private String description;
    private String images_url;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public String getCategory_id() {
        return category_id;
    }

    public void setOpen_type(String open_type) {
        this.open_type = open_type;
    }
    public String getOpen_type() {
        return open_type;
    }

    public void setOpen_content(String open_content) {
        this.open_content = open_content;
    }
    public String getOpen_content() {
        return open_content;
    }

    public void setImages_id(String images_id) {
        this.images_id = images_id;
    }
    public String getImages_id() {
        return images_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setImages_url(String images_url) {
        this.images_url = images_url;
    }
    public String getImages_url() {
        return images_url;
    }

}
