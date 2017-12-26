package com.neoblack.video.mapper;

import cn.tedu.video.pojo.Cat;
import cn.tedu.video.pojo.Course;
import com.neoblack.video.pojo.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface CourseMapper {
    List<Course> findAllCourse();
    //查询所属分类
    Cat findItsCatById(int cId);
}
