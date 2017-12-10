package com.example.video.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "videostate")
public class Videostate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`order`")
    private Integer order;

    private String cssstyle;

    private String remark;

}