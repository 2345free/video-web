package com.example.video.model;

import lombok.Data;

/**
 * Created by luoxx on 2017/12/8.
 */
@Data
public class Settings {

    private String transcoder_vcodec;

    private String transcoder_bv;

    private String transcoder_framerate;

    private String transcoder_acodec;

    private String transcoder_ar;

    private String transcoder_ba;

    private String transcoder_scale_w;

    private String transcoder_scale_h;

    private String transcoder_watermarkuse;

    private String transcoder_watermark_url;

    private String transcoder_watermark_x;

    private String transcoder_watermark_y;

    private String transcoder_keepaspectratio;

    private String transcoder_outfmt;

    private String thumbnail_ss;

    private String folder_videoori;

    private String folder_video;

    private String folder_thumbnail;

}
