package com.example.video.model;

import java.util.Date;
import javax.persistence.*;

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String intro;

    private Date edittime;

    private Integer categoryid;

    private Integer islive;

    private String url;

    private String oriurl;

    private String thumbnailurl;

    private Integer videostateid;

    private String remark;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return edittime
     */
    public Date getEdittime() {
        return edittime;
    }

    /**
     * @param edittime
     */
    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    /**
     * @return categoryid
     */
    public Integer getCategoryid() {
        return categoryid;
    }

    /**
     * @param categoryid
     */
    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * @return islive
     */
    public Integer getIslive() {
        return islive;
    }

    /**
     * @param islive
     */
    public void setIslive(Integer islive) {
        this.islive = islive;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return oriurl
     */
    public String getOriurl() {
        return oriurl;
    }

    /**
     * @param oriurl
     */
    public void setOriurl(String oriurl) {
        this.oriurl = oriurl;
    }

    /**
     * @return thumbnailurl
     */
    public String getThumbnailurl() {
        return thumbnailurl;
    }

    /**
     * @param thumbnailurl
     */
    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    /**
     * @return videostateid
     */
    public Integer getVideostateid() {
        return videostateid;
    }

    /**
     * @param videostateid
     */
    public void setVideostateid(Integer videostateid) {
        this.videostateid = videostateid;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}