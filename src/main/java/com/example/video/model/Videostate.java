package com.example.video.model;

import javax.persistence.*;

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
     * @return order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return cssstyle
     */
    public String getCssstyle() {
        return cssstyle;
    }

    /**
     * @param cssstyle
     */
    public void setCssstyle(String cssstyle) {
        this.cssstyle = cssstyle;
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