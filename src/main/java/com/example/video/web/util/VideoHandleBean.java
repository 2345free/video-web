/**
 * 最简单的视频网站
 * Simplest Video Website
 * <p>
 * 雷霄骅 Lei Xiaohua
 * <p>
 * leixiaohua1020@126.com
 * 中国传媒大学/数字电视技术
 * Communication University of China / Digital TV Technology
 * http://blog.csdn.net/leixiaohua1020
 * <p>
 * 本程序是一个最简单的视频网站视频。它支持
 * 1.直播
 * 2.点播
 * This software is the simplest video website.
 * It support:
 * 1. live broadcast
 * 2. VOD
 */
package com.example.video.web.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;

/**
 * @author 雷霄骅
 * 服务器开始运行后，初始化以下几个线程
 */
@Component
public class VideoHandleBean implements ServletContextAware {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    //Test
    public void GetFFmpegInfo() {

        try {
            String realdir = servletContext.getRealPath("/").replace('\\', '/') + "test/";
            File realdirFile = new File(realdir);
            System.out.println(realdirFile);
            Process p;
            p = Runtime.getRuntime().exec("cmd /c ffmpeg -version >" + realdir + "ffmpeg_version.txt", null, realdirFile);
            p = Runtime.getRuntime().exec("cmd /c ffmpeg -formats >" + realdir + "support_formats.txt", null, realdirFile);
            p = Runtime.getRuntime().exec("cmd /c ffmpeg -decoders >" + realdir + "support_decoders.txt", null, realdirFile);
            p = Runtime.getRuntime().exec("cmd /c ffmpeg -encoders >" + realdir + "support_encoders.txt", null, realdirFile);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {

        //GetFFmpegInfo();
        //初始化的时候运行以下几个线程
        //截图
        VideoThumbnailThread videoThumbnailThread = new VideoThumbnailThread(servletContext);
        videoThumbnailThread.start();
        //转码线程
        VideoTranscoderThread videoConvertThread = new VideoTranscoderThread(servletContext);
        videoConvertThread.start();
    }

}
