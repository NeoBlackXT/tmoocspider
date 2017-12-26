package com.neoblack.course.controller;

import cn.tedu.course.service.CatService;
import cn.tedu.course.service.CourseService;
import cn.tedu.course.service.VideoService;
import com.neoblack.course.service.CourseService;
import com.neoblack.course.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Neo on 2017/5/17.
 */
@Controller
public class CourseController {
    @Resource
    private CatService catService;
    @Resource
    private CourseService courseService;
    @Resource
    private VideoService videoService;

    @RequestMapping("cat")
    public String cat() {
        catService.saveAllCat();
        return "index";
    }

    @RequestMapping("course")
    public String course() {
        courseService.saveAllCourse();
        return "index";
    }

    @RequestMapping("allVideo")
    public String allVideo() {
        videoService.saveAllVideo();
        return "index";
    }

    @RequestMapping("videoAfter")
    public String videoAfter(@RequestParam("cid") int cId) {
        videoService.saveVideoAfterCourseId(cId);
        return "index";
    }
}
