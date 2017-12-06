package com.example.video.web.controller;

import com.example.video.model.Category;
import com.example.video.model.Configure;
import com.example.video.model.Video;
import com.example.video.model.Videostate;
import com.example.video.service.CategoryService;
import com.example.video.service.ConfigureService;
import com.example.video.service.VideoService;
import com.example.video.service.VideostateService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {

    private static final int FILE_SIZE = 16 * 1024;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ConfigureService configureService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideostateService videostateService;

    @RequestMapping("/get-all")
    public String getAll(Model model) {
        List<Video> videos = videoService.selectByExample(null);
        model.addAttribute(videos);
        // live
        Example var1 = new Example(Video.class);
        var1.setOrderByClause("edittime asc");
        var1.createCriteria().andEqualTo("islive", 1);
        PageHelper.startPage(0, 4);
        List<Video> resultVideoLive = videoService.selectByExample(var1);
        model.addAttribute("resultVideoLive", resultVideoLive);

        // vod
        var1 = new Example(Video.class);
        var1.setOrderByClause("edittime asc");
        var1.createCriteria().andEqualTo("islive", 0);
        PageHelper.startPage(0, 4);
        List<Video> resultVideoVod = videoService.selectByExample(var1);
        model.addAttribute("resultVideoVod", resultVideoVod);
        return "videolist1";
    }

    @RequestMapping("/get/{id}")
    public String get(@PathVariable String id, Model model) {
        Video video = videoService.selectByKey(id);
        model.addAttribute(video);
        return "videocontent";
    }

    @RequestMapping("/update")
    public String update(Video video) {
        videoService.updateNotNull(video);
        return "videocontent";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id, ServletContext servletContext) {
        Video video = videoService.selectByKey(id);
        //相对路径
        String thumbnailPath = video.getThumbnailurl();
        String path = video.getUrl();
        String oripath = video.getOriurl();
        //获取根路径（绝对路径）
        String thumbnailrealpath = servletContext.getRealPath("/").replace('\\', '/')
                + thumbnailPath;
        String realpath = servletContext.getRealPath("/").replace('\\', '/')
                + path;
        String orirealpath = servletContext.getRealPath("/").replace('\\', '/')
                + oripath;
        File thumbnailfile = new File(thumbnailrealpath);
        File videofile = new File(realpath);
        File orivideofile = new File(orirealpath);
        //删除与之相关的截图文件和视频文件
        if (thumbnailfile != null) {
            thumbnailfile.delete();
        }
        if (videofile != null) {
            videofile.delete();
        }
        if (orivideofile != null) {
            orivideofile.delete();
        }
        //最后才删除该记录
        videoService.delete(id);
        return "videolist1";
    }

    @RequestMapping("/add")
    public String add(Video video, MultipartFile file, ServletContext servletContext) throws IOException {

        int order = 1;

        video.setEdittime(new Timestamp(new Date().getTime()));
        Example var1 = new Example(Configure.class);
        var1.createCriteria().andEqualTo("name", "folder_videoori");
        List<Configure> configures = configureService.selectByExample(var1);

        var1 = new Example(Configure.class);
        var1.createCriteria().andEqualTo("name", "folder_thumbnail");
        List<Configure> configures2 = configureService.selectByExample(var1);

        Configure folder_videoori_cfg = configures.get(0);
        Configure folder_thumbnail_cfg = configures2.get(0);
        if (video.getIslive() == 0) {
            //点播
            String oriurl = folder_videoori_cfg.getVal() + "/" + video.getName();
            video.setOriurl(oriurl);
            Category category = categoryService.selectByKey(1);
            video.setCategoryid(category.getId());
            //状态设置：等待上传
            var1 = new Example(Videostate.class);
            var1.createCriteria().andEqualTo("Videostate", order);
            List<Videostate> videostates = videostateService.selectByExample(var1);
            Videostate videostate = videostates.get(0);
            video.setVideostateid(videostate.getId());
            //Default Thumbnail
            String defaultthumbnail = folder_thumbnail_cfg.getVal() + "/default.jpg";
            video.setThumbnailurl(defaultthumbnail);
            videoService.save(video);

            //上传视频文件
            String realfileoriDir = servletContext.getRealPath(folder_videoori_cfg.getVal()).replace('\\', '/');
            //Check
            File realfileoriDirFile = new File(realfileoriDir);
            if (!realfileoriDirFile.exists() && !realfileoriDirFile.isDirectory()) {
                System.out.println("Directory not exist. Create it.");
                System.out.println(realfileoriDirFile);
                realfileoriDirFile.mkdir();
            }
            String realfileoriPath = realfileoriDir + "/" + video.getName();
            File targetFile = new File(realfileoriPath);
            // 保存文件
            FileCopyUtils.copy(file.getBytes(), targetFile);
            //等待截图
            var1 = new Example(Videostate.class);
            var1.createCriteria().andEqualTo("Videostate", order + 1);
            videostates = videostateService.selectByExample(var1);
            videostate = videostates.get(0);
            video.setVideostateid(videostate.getId());
            videoService.updateNotNull(video);
        } else {
            //直播
            Category category = categoryService.selectByKey(2);
            video.setCategoryid(category.getId());
            //等待截图
            var1 = new Example(Videostate.class);
            var1.createCriteria().andEqualTo("Videostate", order + 1);
            List<Videostate> videostates = videostateService.selectByExample(var1);
            Videostate videostate = videostates.get(0);
            video.setVideostateid(videostate.getId());
            //Default Thumbnail
            String defaultthumbnail = folder_thumbnail_cfg.getVal() + "/default.jpg";
            video.setThumbnailurl(defaultthumbnail);

            videoService.save(video);
        }
        return "videolist1";
    }


}
