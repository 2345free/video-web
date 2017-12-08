package com.example.video.web.controller;

import com.example.video.model.Configure;
import com.example.video.service.ConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config")
public class ConfigureController {

    @Autowired
    private ConfigureService configureService;

    @RequestMapping("/get-all")
    public String getAll(Model model) {
        Configure configure = configureService.selectByKey(1);
        model.addAttribute("configure", configure);
        return "configure";
    }

    @RequestMapping("/update")
    public String update(Configure configure) {
        configureService.updateNotNull(configure);
        return "configure";
    }

}
