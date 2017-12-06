package com.example.video.web.controller;

import com.example.video.model.Configure;
import com.example.video.service.ConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/config")
public class ConfigureController {

    @Autowired
    private ConfigureService configureService;

    @RequestMapping("/get-all")
    public String getAll(Model model) {
        List<Configure> configures = configureService.selectByExample(null);
        model.addAttribute(configures);
        return "configure";
    }

    @RequestMapping("/update")
    public String update(Configure configure) {
        configureService.updateNotNull(configure);
        return "configure";
    }

}
