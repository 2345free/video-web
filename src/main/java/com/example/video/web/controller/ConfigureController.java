package com.example.video.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.video.model.Configure;
import com.example.video.model.Settings;
import com.example.video.service.ConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/config")
public class ConfigureController {

    @Autowired
    private ConfigureService configureService;

    @GetMapping("/list")
    public String getAll(Model model) {
        List<Configure> configures = configureService.selectByExample(null);
        for (Configure configure : configures) {
            model.addAttribute(configure.getName(), configure.getVal());
        }
        return "configure";
    }

    @PostMapping("/update")
    public String update(Settings settings) {
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(settings), new TypeReference<Map<String, String>>() {
        });
        List<Configure> configures = configureService.selectByExample(null);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Configure configure = new Configure();
            configure.setName(entry.getKey());
            configure.setVal(entry.getValue());
            for (Configure config : configures) {
                if (config.getName().equals(entry.getKey())) {
                    configure.setId(config.getId());
                    break;
                }
            }
            configureService.updateNotNull(configure);
        }
        return "redirect:list";
    }

}
