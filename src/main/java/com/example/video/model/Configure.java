package com.example.video.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "`configure`")
public class Configure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`")
    private String name;

    private String val;

    private String remark;

}