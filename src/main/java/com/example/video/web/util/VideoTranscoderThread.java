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
import org.springframework.stereotype.Component;
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
 * 转码
 */
@Component
public class VideoTranscoderThread extends Thread {

    private ServletContext servletContext;

    public VideoTranscoderThread(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void run() {
        try {
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            VideoService videoService = ctx.getBean(VideoService.class);
            ConfigureService configureService = ctx.getBean(ConfigureService.class);
            VideostateService videostateService = ctx.getBean(VideostateService.class);

            // 待转码
            int order = 3;
            //Load Configure
            List<Configure> configures = configureService.selectByExample(null);
            Map<String, String> attrs = new HashMap<>();
            for (Configure configure : configures) {
                attrs.put(configure.getName(), configure.getVal());
            }
            Settings settings = JSON.parseObject(JSON.toJSONString(attrs), Settings.class);
            //Folder of Watermark
            String[] watermarkstrlist = settings.getTranscoder_watermark_url().split("/");
            String watermarkDir = "";
            String watermarkFile = watermarkstrlist[watermarkstrlist.length - 1];
            for (int i = 0; i < watermarkstrlist.length - 1; i++) {
                watermarkDir += watermarkstrlist[i] + "/";
            }

            ClassPathResource classPathResource = new ClassPathResource("static/");
            String staticPath = classPathResource.getFile().getPath() + "/";
            String realwatermarkDir = staticPath + watermarkDir;
            File realwatermarkDirFile = new File(realwatermarkDir);
            //Check
            if (!realwatermarkDirFile.exists() && !realwatermarkDirFile.isDirectory()) {
                System.out.println("Directory not exist. Create it.");
                System.out.println(realwatermarkDirFile);
                realwatermarkDirFile.mkdir();
            }


            String realfileDir = staticPath + settings.getFolder_video();
            //Check
            File realfileDirFile = new File(realfileDir);
            if (!realfileDirFile.exists() && !realfileDirFile.isDirectory()) {
                System.out.println("Directory not exist. Create it.");
                System.out.println(realfileDirFile);
                realfileDirFile.mkdir();
            }

            do {
                Example var2 = new Example(Video.class);
                var2.createCriteria().andEqualTo("videostateid", order);
                // order=3都是等待转码的video
                List<Video> resultvideo = videoService.selectByExample(var2);

                if (resultvideo != null) {
                    for (Video video : resultvideo) {
                        //Transcode
                        String filePath = settings.getFolder_video() + "/" + video.getId() + "." + settings.getTranscoder_outfmt();
                        //System.out.println(filePath);
                        video.setUrl(filePath);
                        String realfilePath = staticPath + video.getUrl();

                        String realfileoriginalPath = staticPath + video.getOriurl();
                        //转码命令如下所示
                        //ffmpeg -i xxx.mkv -ar 22050 -b 600k -vcodec libx264
                        //-vf scale=w=640:h=360:force_original_aspect_ratio=decrease,pad=w=640:h=360:x=(ow-iw)/2:y=(oh-ih)/2[aa];
                        //movie=watermark.png[bb];[aa][bb]overlay=5:5 yyy.flv
                        //AVFilter参数作用如下所示
                        //scale:视频拉伸滤镜。force_original_aspect_ratio用于强制保持宽高比
                        //pad:用于加黑边，四个参数含义分别为：处理后宽，处理后高，输入图像左上角x坐标，输入视频左上角Y坐标。
                        //其中ow,oh为输出（填充后）视频的宽高；iw,ih为输入（填充前）视频的宽高。
                        //movie：用于指定需要叠加的水印Logo（PNG文件）。
                        //overlay:用于叠加水印Logo和视频文件
                        //命令行不同的执行方式
                        //cmd /c xxx 是执行完xxx命令后关闭命令窗口。
                        //cmd /k xxx 是执行完xxx命令后不关闭命令窗口。
                        //cmd /c start xxx 会打开一个新窗口后执行xxx指令，原窗口会关闭。
                        String videotranscodecommand = "cmd ";
                        videotranscodecommand += "/c start ";
                        //videotranscodecommand+="/c ";
                        videotranscodecommand += "ffmpeg -y ";
                        videotranscodecommand += "-i ";
                        videotranscodecommand += "\"" + realfileoriginalPath + "\" ";
                        videotranscodecommand += "-vcodec " + settings.getTranscoder_vcodec() + " ";
                        videotranscodecommand += "-b:v " + settings.getTranscoder_bv() + " ";
                        videotranscodecommand += "-r " + settings.getTranscoder_framerate() + " ";
                        videotranscodecommand += "-acodec " + settings.getTranscoder_acodec() + " ";
                        videotranscodecommand += "-b:a " + settings.getTranscoder_ba() + " ";
                        videotranscodecommand += "-ar " + settings.getTranscoder_ar() + " ";
                        videotranscodecommand += "-vf ";
                        videotranscodecommand += "scale=w=" + settings.getTranscoder_scale_w() + ":h=" + settings.getTranscoder_scale_h();
                        if (settings.getTranscoder_keepaspectratio().equals("true")) {
                            videotranscodecommand += ":" + "force_original_aspect_ratio=decrease,pad=w=" +
                                    settings.getTranscoder_scale_w() + ":h=" + settings.getTranscoder_scale_h() + ":x=(ow-iw)/2:y=(oh-ih)/2";
                        }
                        videotranscodecommand += "[aa]";
                        if (settings.getTranscoder_watermarkuse().equals("true")) {
                            videotranscodecommand += ";movie=";
                            videotranscodecommand += watermarkFile;
                            videotranscodecommand += "[bb];";
                            videotranscodecommand += "[aa][bb]";
                            videotranscodecommand += "overlay=x=" + settings.getTranscoder_watermark_x() + ":y=" + settings.getTranscoder_watermark_y() + " ";
                        } else {
                            videotranscodecommand += " ";
                        }
                        videotranscodecommand += "\"";
                        videotranscodecommand += realfilePath;
                        videotranscodecommand += "\"";


                        System.out.println(videotranscodecommand);
                        Process process = Runtime.getRuntime().exec(videotranscodecommand, null, realwatermarkDirFile);
                        //------------------------
                        BufferedInputStream in = new BufferedInputStream(process.getInputStream());
                        BufferedInputStream err = new BufferedInputStream(process.getErrorStream());
                        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
                        BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
                        String lineStr;
                        while ((lineStr = inBr.readLine()) != null) {
                            System.out.println(lineStr);
                        }
                        while ((lineStr = errBr.readLine()) != null) {
                            System.out.println(lineStr);
                        }

                        if (process.waitFor() != 0) {
                            if (process.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                                System.err.println("Failed!");
                        }
                        inBr.close();
                        in.close();

                        Example var3 = new Example(Videostate.class);
                        var3.createCriteria().andEqualTo("order", order + 1);
                        // 转码完成后的状态
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
