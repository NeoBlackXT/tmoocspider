package com.neoblack.course.service;

import cn.tedu.course.common.ConnectionUtils;
import cn.tedu.course.mapper.CourseMapper;
import cn.tedu.course.pojo.Cat;
import cn.tedu.course.pojo.Course;
import com.neoblack.course.common.ConnectionUtils;
import com.neoblack.course.pojo.Course;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/20.
 */
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;
    public void saveAllCourse() {
        List<Course> courses = CourseJsoup.fetchAllCourse();
        for (Course course:courses) {
            courseMapper.saveCourse(course);
        }
    }

    static class CourseJsoup{
        static List<Course> fetchAllCourse() {
            Connection connection = Jsoup.connect("http://tts8.tmooc.cn/studentCenter/toMyttsPage?isCenter=yes");
            ConnectionUtils.init(connection);

            Map cookies = new HashMap();
            cookies.put("sessionid", "374ca5d937464736a9820c62b8b170c7%7C964985821%40qq.com");
            connection.cookies(cookies);

            connection.referrer("http://tmooc.cn/web/index_new.html?tedu");

            Document document = null;
            try {
                document = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Course> courses = new ArrayList<>();
            //获取每个课程阶段
            Elements elementEles = document.select(".ttscourselist .ttscoursecon");
            int cCatId = 1;
            //遍历课程阶段
            for (Element element : elementEles) {
                Elements courseEles = element.select(".clearfloat");
                //遍历每个课程，获取课程名和课程URL
                for (Element courseEle : courseEles) {
                    String courseName = courseEle.select("p").text().replaceAll(" ","");
                    String url = courseEle.select(".userlink .m4").attr("href");
                    url = url.substring(0, url.indexOf("&"));
                    Course course = new Course();
                    course.setcCatId(cCatId);
                    course.setcCourseName(courseName);
                    course.setcCourseUrl(url);
                    courses.add(course);
                }
                cCatId++;
            }
            return courses;
        }

    }
}
