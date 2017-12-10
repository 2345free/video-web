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

import com.alibaba.fastjson.JSON;
import com.example.video.model.Configure;
import com.example.video.model.Settings;
import com.example.video.model.Video;
import com.example.video.model.Videostate;
import com.example.video.service.ConfigureService;
import com.example.video.service.VideoService;
import com.example.video.service.VideostateService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletContext;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 截取缩略图
 */
public class VideoThumbnailThread extends Thread {

    private ServletContext servletContext;

    public VideoThumbnailThread(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void run() {
        try {
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            VideoService videoService = ctx.getBean(VideoService.class);
            ConfigureService configureService = ctx.getBean(ConfigureService.class);
            VideostateService videostateService = ctx.getBean(VideostateService.class);

            // 待截图
            int order = 2;
            List<Configure> configures = configureService.selectByExample(null);
            Map<String, String> attrs = new HashMap<>();
            for (Configure configure : configures) {
                attrs.put(configure.getName(), configure.getVal());
            }
            Settings settings = JSON.parseObject(JSON.toJSONString(attrs), Settings.class);

            ClassPathResource classPathResource = new ClassPathResource("static/");
            String staticPath = classPathResource.getFile().getPath() + "/";
            String realthumbnailDir = staticPath + settings.getFolder_thumbnail();
            //Check
            File realthumbnailDirFile = new File(realthumbnailDir);
            if (!realthumbnailDirFile.exists() && !realthumbnailDirFile.isDirectory()) {
                System.out.println("Directory not exist. Create it.");
                System.out.println(realthumbnailDirFile);
                realthumbnailDirFile.mkdir();
            }

            do {
                Example var2 = new Example(Video.class);
                var2.createCriteria().andEqualTo("videostateid", order);
                // order=2都是等待截图的video
                List<Video> resultvideo = videoService.selectByExample(var2);

                if (resultvideo != null) {
                    for (Video video : resultvideo) {
                        String realfileoriPath;
                        if (video.getIslive() == 0) {
                            realfileoriPath = staticPath + video.getOriurl();
                        } else {
                            realfileoriPath = video.getUrl();
                            String a[] = realfileoriPath.split(":");
                            //RTMP FIX: libRTMP URL
                            if (a[0].equals("rtmp") || a[0].equals("rtmpe") || a[0].equals("rtmpte") || a[0].equals("rtmps")) {
                                realfileoriPath = realfileoriPath + " live=1";
                            }
                        }
                        // 截图物理存放位置
                        String realthumbnailPath = realthumbnailDir + "/" + video.getId() + ".jpg";

                        String videothumbnailcommand = "cmd /c start ffmpeg -y -i " + "\"" + realfileoriPath + "\"" +
                                " -ss " + settings.getThumbnail_ss() + " -s 220x110 -f image2 -vframes 1 " + "\"" + realthumbnailPath + "\"";
                        System.out.println(videothumbnailcommand);
                        // 使用java调用系统安装的ffmpeg命令截取视频的一帧画面作为视频的预览图
                        Process process = Runtime.getRuntime().exec(videothumbnailcommand);
                        //------------------------
                        BufferedInputStream in = new BufferedInputStream(process.getInputStream());
                        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
                        String lineStr;
                        while ((lineStr = inBr.readLine()) != null)
                            System.out.println(lineStr);
                        if (process.waitFor() != 0) {
                            if (process.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                                System.err.println("Failed!");
                        }
                        inBr.close();
                        in.close();

                        // 设置截图地址相对路径
                        video.setThumbnailurl(settings.getFolder_thumbnail() + "/" + video.getId() + ".jpg");

                        Example var3 = new Example(Videostate.class);
                        Example.Criteria var3Criteria = var3.createCriteria();
                        if (video.getIslive() == 0) {
                            var3Criteria.andEqualTo("order", order + 1);
                        } else {
                            var3Criteria.andEqualTo("order", order + 2);
                        }
                        Videostate nextvideostate = videostateService.selectByExample(var3).get(0);
                        video.setVideostateid(nextvideostate.getId());
                        video.setVideostate(nextvideostate);

                        videoService.updateNotNull(video);
                        //Rest--------------------------
                        sleep(10 * 1000);
                    }
                }
                sleep(10 * 1000);
            } while (true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
