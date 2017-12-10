package com.example.video.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`")
    private String name;

    private Integer parentid;

    private String remark;

}