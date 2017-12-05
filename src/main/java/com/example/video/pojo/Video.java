package com.example.video.pojo;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table video
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Data
@Table(name = "video")
public class Video {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.id
     *
     * @mbg.generated
     */
    @Id
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.intro
     *
     * @mbg.generated
     */
    private String intro;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.edittime
     *
     * @mbg.generated
     */
    private Date edittime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.categoryid
     *
     * @mbg.generated
     */
    private Integer categoryid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.islive
     *
     * @mbg.generated
     */
    private Integer islive;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.oriurl
     *
     * @mbg.generated
     */
    private String oriurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.thumbnailurl
     *
     * @mbg.generated
     */
    private String thumbnailurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.videostateid
     *
     * @mbg.generated
     */
    private Integer videostateid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video.remark
     *
     * @mbg.generated
     */
    private String remark;

}