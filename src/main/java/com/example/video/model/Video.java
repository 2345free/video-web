package com.example.video.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`")
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

    @Transient
    private Videostate videostate;

}