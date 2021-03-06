package com.example.video.web.controller;

import com.example.video.model.Category;
import com.example.video.model.Configure;
import com.example.video.model.Video;
import com.example.video.model.Videostate;
import com.example.video.service.CategoryService;
import com.example.video.service.ConfigureService;
import com.example.video.service.VideoService;
import com.example.video.service.VideostateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

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

    @RequestMapping("/list/{isLive}")
    public String list(@PathVariable Integer isLive, Model model) {

        Example var1 = new Example(Video.class);
        var1.setOrderByClause("edittime asc");
        Example.Criteria var1Criteria = var1.createCriteria();
        if (isLive == 1) {
            // live
            var1Criteria.andEqualTo("islive", 1);
        } else {
            // vod
            var1Criteria.andEqualTo("islive", 0);
        }
        List<Video> videos = videoService.selectByExample(var1);
        // 查找videostate
        if (!CollectionUtils.isEmpty(videos)) {
            Videostate videostate = videostateService.selectByKey(videos.get(0).getVideostateid());
            for (Video video : videos) {
                video.setVideostate(videostate);
            }
        }
        model.addAttribute("videos", videos);
        model.addAttribute("isLive", isLive);

        return "videolist1";
    }

    @RequestMapping("/get/{id}")
    public String get(@PathVariable Integer id, Model model) {
        Video video = videoService.selectByKey(id);
        model.addAttribute("video", video);
        return "videocontent";
    }

    @RequestMapping("/update")
    public String update(Video video) {
        videoService.updateNotNull(video);
        return "redirect:get/" + video.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) throws IOException {
        Video video = videoService.selectByKey(id);
        //获取根路径（绝对路径）
        ClassPathResource classPathResource = new ClassPathResource("static/");
        String staticPath = classPathResource.getFile().getPath() + "/";

        String thumbnailrealpath = staticPath + video.getThumbnailurl();
        String realpath = staticPath + video.getUrl();
        String orirealpath = staticPath + video.getOriurl();

        File thumbnailfile = new File(thumbnailrealpath);
        File videofile = new File(realpath);
        File orivideofile = new File(orirealpath);
        //删除与之相关的截图文件和视频文件
        if (thumbnailfile.exists()) {
            thumbnailfile.delete();
        }
        if (videofile.exists()) {
            videofile.delete();
        }
        if (orivideofile.exists()) {
            orivideofile.delete();
        }
        //最后才删除该记录
        videoService.delete(id);
        return "redirect:/video/list/" + video.getIslive();
    }

    @GetMapping("/toAdd")
    public String toAdd() {
        return "videoedit";
    }

    @RequestMapping("/add")
    public String add(Video video, @RequestParam(name = "videofile", required = false) MultipartFile file) throws IOException {

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
            String oriurl = folder_videoori_cfg.getVal() + "/" + file.getOriginalFilename();
            video.setOriurl(oriurl);
            Category category = categoryService.selectByKey(1);
            video.setCategoryid(category.getId());
            //状态设置：等待上传
            var1 = new Example(Videostate.class);
            var1.createCriteria().andEqualTo("order", order);
            List<Videostate> videostates = videostateService.selectByExample(var1);
            Videostate videostate = videostates.get(0);
            video.setVideostateid(videostate.getId());
            //Default Thumbnail
            String defaultthumbnail = folder_thumbnail_cfg.getVal() + "/default.jpg";
            video.setThumbnailurl(defaultthumbnail);
            videoService.save(video);

            //上传视频文件
            ClassPathResource classPathResource = new ClassPathResource("static/" + folder_videoori_cfg.getVal());
            String realfileoriDir = classPathResource.getFile().getPath();
            //Check
            File realfileoriDirFile = new File(realfileoriDir);
            if (!realfileoriDirFile.exists() && !realfileoriDirFile.isDirectory()) {
                System.out.println("Directory not exist. Create it.");
                System.out.println(realfileoriDirFile);
                realfileoriDirFile.mkdir();
            }
            String realfileoriPath = realfileoriDir + "/" + file.getOriginalFilename();
            File targetFile = new File(realfileoriPath);
            // 保存文件
            FileCopyUtils.copy(file.getBytes(), targetFile);
            //等待截图
            var1 = new Example(Videostate.class);
            var1.createCriteria().andEqualTo("order", order + 1);
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
            var1.createCriteria().andEqualTo("order", order + 1);
            List<Videostate> videostates = videostateService.selectByExample(var1);
            Videostate videostate = videostates.get(0);
            video.setVideostateid(videostate.getId());
            //Default Thumbnail
            String defaultthumbnail = folder_thumbnail_cfg.getVal() + "/default.jpg";
            video.setThumbnailurl(defaultthumbnail);

            videoService.save(video);
        }
        return "redirect:list/" + video.getIslive();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Video video = videoService.selectByKey(id);
        model.addAttribute("video", video);
        return "videoedit";
    }


}
