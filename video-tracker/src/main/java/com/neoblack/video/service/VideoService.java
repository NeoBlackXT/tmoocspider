package com.neoblack.video.service;

import cn.tedu.video.common.ConnectionUtils;
import cn.tedu.video.common.FileUtils;
import cn.tedu.video.mapper.CatMapper;
import cn.tedu.video.mapper.CourseMapper;
import cn.tedu.video.mapper.VideoMapper;
import cn.tedu.video.pojo.Cat;
import cn.tedu.video.pojo.Course;
import cn.tedu.video.pojo.Video;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neo on 2017/5/20.
 */
@Service
public class VideoService {
    private static final Logger logger = Logger.getLogger(VideoService.class);
    private static final String baseDirStr = "D:/tmooc_s";
    @Resource
    private CatMapper catMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private VideoMapper videoMapper;

    public void dirInit() {
        //初始化总文件目录
        File baseDir = new File(baseDirStr);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
        //初始化阶段文件目录
        List<Cat> allCat = catMapper.findAllCat();
        for (Cat cat : allCat) {
            String catDirStr = (cat.getcId() > 9 ? cat.getcId() : "0" + cat.getcId()) + "-" + cat.getcCatname();
            String path = FileUtils.buildFilePath(baseDirStr, catDirStr);
            File catDir = new File(path);
            if (!catDir.exists()) {
                catDir.mkdir();
            }
        }
        //初始化课程文件目录
        List<Course> allCourse = courseMapper.findAllCourse();
        for (Course course : allCourse) {
            Cat cat = courseMapper.findItsCatById(course.getcId());
            String catDirStr = (cat.getcId() > 9 ? cat.getcId() : "0" + cat.getcId()) + "-" + cat.getcCatname();
            String courseDirStr = course.getcCourseName();
            String path = FileUtils.buildFilePath(baseDirStr, catDirStr, courseDirStr);
            File courseDir = new File(path);
            if (!courseDir.exists()) {
                courseDir.mkdir();
            }
        }
        //初始化视频文件目录
        List<Video> allVideo = videoMapper.findAllVideo();
        for (Video video : allVideo) {
            Cat cat = videoMapper.findItsCatById(video.getcId());
            Course course = videoMapper.findItsCourseById(video.getcId());
            String catDirStr = (cat.getcId() > 9 ? cat.getcId() : "0" + cat.getcId()) + "-" + cat.getcCatname();
            String courseDirStr = course.getcCourseName();
            String videoDirStr = video.getcVideoName();
            String path = FileUtils.buildFilePath(baseDirStr, catDirStr, courseDirStr, videoDirStr);
            File videoDir = new File(path);
            if (!videoDir.exists()) {
                videoDir.mkdir();
            }

        }

    }

    public void persistAllVideo() {
        List<Video> videos = videoMapper.findAllVideo();
        persistVideo(videos);
    }

    public void persistVideoAfterId(int cId) {
        List<Video> videos = videoMapper.findVideoAfterId(cId);
        persistVideo(videos);
    }

    void persistVideo(List<Video> videos) {
        for (Video video : videos) {
            Cat cat = videoMapper.findItsCatById(video.getcId());
            Course course = videoMapper.findItsCourseById(video.getcId());
            String catDirStr = (cat.getcId() > 9 ? cat.getcId() : "0" + cat.getcId()) + "-" + cat.getcCatname();
            String courseDirStr = course.getcCourseName();
            String videoDirStr = video.getcVideoName();

            //获取m3u8播放列表文件
            String playlistFileStr = videoDirStr + ".m3u8";
            String playlistPath = FileUtils.buildFilePath(baseDirStr, catDirStr, courseDirStr, videoDirStr, playlistFileStr);

            File playlistFile = new File(playlistPath);
            byte[] bytes = VideoJsoup.fetchFileBytes(video.getcVideoUrl());
            if (bytes == null) {
                continue;
            }
            FileUtils.writeBytes(bytes, playlistFile);

            //获取static.key文件
            String keyFileStr = "static.key";
            String keyFilePath = FileUtils.buildFilePath(baseDirStr, catDirStr, courseDirStr, videoDirStr, keyFileStr);
            File keyFile = new File(keyFilePath);

            String playListUrl = video.getcVideoUrl();
            int index = playListUrl.lastIndexOf("/");
            String _keyUrl = playListUrl.substring(0, index);
            String keyUrl = _keyUrl + "/static.key";

            byte[] bytes2 = VideoJsoup.fetchFileBytes(keyUrl);
            if (bytes2 == null) {
                continue;
            }
            FileUtils.writeBytes(bytes2,keyFile);

            //解析m3u8播放列表文件
            try {
                String playlistContent = new String(bytes, "UTF-8");
                Matcher matcher = Pattern.compile("http:.+?\\.ts").matcher(playlistContent);
                while (matcher.find()) {
                    String tsUrl = matcher.group();
                    String tsFileStr = tsUrl.substring(tsUrl.lastIndexOf("/") + 1);
                    String tsFilePath = FileUtils.buildFilePath(baseDirStr, catDirStr, courseDirStr, videoDirStr, tsFileStr);
                    File tsFile = new File(tsFilePath);
                    byte[] bytes3 = VideoJsoup.fetchFileBytes(tsUrl);
                    FileUtils.writeBytes(bytes3,tsFile);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //遍历播放列表获取ts文件
        }

    }
    static class VideoJsoup {
        static byte[] fetchFileBytes(String url) {
            Connection connection = Jsoup.connect(url);
            ConnectionUtils.init(connection);

            Connection.Response response = null;
            try {
                response = connection.execute();
            } catch (HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    logger.error("Status=404,URL=" + e.getUrl());
                    return null;
                } else {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response == null) {
                return null;
            }
            byte[] bytes = response.bodyAsBytes();
            return bytes;
        }

    }
}
