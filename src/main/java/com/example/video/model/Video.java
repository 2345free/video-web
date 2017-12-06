package com.example.video.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
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

}