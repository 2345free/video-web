package com.example.video.web.controller;

import com.example.video.model.Video;
import com.example.video.service.VideoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private VideoService videoService;

    @RequestMapping({"", "/", "/index"})
    public String index(Map<String, Object> model) {
        Example var1 = new Example(Video.class);
        var1.createCriteria().andEqualTo("islive", "0");
        List<Video> allVideo = videoService.selectByExample(var1);

        //图片数量
        int num;
        int count = allVideo.size();
        if (count > 6) {
            num = 6;
        } else {
            num = count;
        }
        List<Video> resultVideo = new ArrayList();
        for (int i = 0; i < num; i++) {
            //随机数
            int idx = (int) (Math.random() * count);
            if (idx < count) {
                Video video = allVideo.get(idx);
                resultVideo.add(video);
            }
        }
        model.put("resultVideo", resultVideo);

        // live
        var1 = new Example(Video.class);
        var1.setOrderByClause("edittime asc");
        var1.createCriteria().andEqualTo("islive", 1);
        PageHelper.startPage(0, 4);
        List<Video> resultVideoLive = videoService.selectByExample(var1);
        model.put("resultVideoLive", resultVideoLive);

        // vod
        var1 = new Example(Video.class);
        var1.setOrderByClause("edittime asc");
        var1.createCriteria().andEqualTo("islive", 0);
        PageHelper.startPage(0, 4);
        List<Video> resultVideoVod = videoService.selectByExample(var1);
        model.put("resultVideoVod", resultVideoVod);

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
