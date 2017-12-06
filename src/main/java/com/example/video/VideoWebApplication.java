package com.example.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.video.dao")
public class VideoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoWebApplication.class, args);
    }

}
