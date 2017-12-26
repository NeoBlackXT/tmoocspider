package com.neoblack.video.mapper;

import cn.tedu.video.pojo.Cat;
import cn.tedu.video.pojo.Course;
import cn.tedu.video.pojo.Video;
import com.neoblack.video.pojo.Cat;
import com.neoblack.video.pojo.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface VideoMapper {
    List<Video> findAllVideo();
    List<Video> findVideoAfterId(int cId);
    //查询所属课程
    Course findItsCourseById(int cId);
    //查询所属分类
    Cat findItsCatById(int cId);
}
