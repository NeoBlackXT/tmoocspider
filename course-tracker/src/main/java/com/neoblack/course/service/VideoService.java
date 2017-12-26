package com.neoblack.course.service;

import cn.tedu.course.common.ConnectionUtils;
import cn.tedu.course.mapper.CourseMapper;
import cn.tedu.course.mapper.VideoMapper;
import cn.tedu.course.pojo.Course;
import cn.tedu.course.pojo.Video;
import com.neoblack.course.common.ConnectionUtils;
import com.neoblack.course.pojo.Course;
import com.neoblack.course.pojo.Video;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/20.
 */
@Service
public class VideoService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private VideoMapper videoMapper;

    public void saveAllVideo() {
        List<Course> allCourse = courseMapper.findAllCourse();
        saveVideo(allCourse);

    }

    public void saveVideoAfterCourseId(int cId) {
        List<Course> allCourse = courseMapper.findCourseAfterId(cId);
        saveVideo(allCourse);

    }

    private void saveVideo(List<Course> allCourse) {
        for (Course course : allCourse) {
            Video[] videos = VideoJsoup.fetchAllVideo(course);
            if (videos == null) {
                continue;
            }
            videoMapper.saveVideo(videos[0]);
            videoMapper.saveVideo(videos[1]);
        }
    }
    static class VideoJsoup{
        static Video[] fetchAllVideo(Course course) {
            String courseUrl = course.getcCourseUrl();
            Connection connection = Jsoup.connect(courseUrl);
            ConnectionUtils.init(connection);

            Map cookies = new HashMap();
            cookies.put("sessionid", "374ca5d937464736a9820c62b8b170c7%7C964985821%40qq.com");
            connection.cookies(cookies);

            connection.referrer("http://tts8.tmooc.cn/studentCenter/toMyttsPage?isCenter=yes");
            connection.ignoreContentType(true);
            Document document=null;
            try {
                document = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] fileNames = new String[2];
            fileNames[0] = document.select("#url_apan_id_1").text();
            fileNames[1] = document.select("#url_apan_id_2").text();
            int flag = 0;
            for (;flag<2;flag++) {
                if (!fileNames[flag].equals("")) {
                    break;
                }
            }
            if (flag == 2) {
                return null;
            }
            String baseUrl = "http://videotts.it211.com.cn/";

            String[] videoNames = new String[2];
            videoNames[0] = fileNames[0].split("\\.")[0];
            videoNames[1] = fileNames[1].split("\\.")[0];

            String[] urls = new String[2];
            urls[0] = baseUrl + videoNames[0] + "/" + fileNames[0];
            urls[1] = baseUrl + videoNames[1] + "/" + fileNames[1];

            Video[] videos = new Video[2];
            for (int i=0;i<2;i++) {
                videos[i] = new Video();
                videos[i].setcCourseId(course.getcId());
                videos[i].setcVideoName(videoNames[i]);
                videos[i].setcVideoUrl(urls[i]);

            }
            return videos;
        }
    }
}
