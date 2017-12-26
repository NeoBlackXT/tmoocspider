package com.neoblack.video.controller;

import cn.tedu.video.service.VideoService;
import com.neoblack.video.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Neo on 2017/5/20.
 */
@Controller
public class VideoController {
    @Resource
    private VideoService videoService;

    @RequestMapping("init")
    public String dirInit() {
        videoService.dirInit();
        return "index";
    }

    @RequestMapping("all")
    public String persistAll() {
        videoService.persistAllVideo();
        return "index";
    }

    @RequestMapping("after")
    public String persistAfter(@RequestParam("cid") int cId) {
        videoService.persistVideoAfterId(cId);
        return "index";
    }

}
