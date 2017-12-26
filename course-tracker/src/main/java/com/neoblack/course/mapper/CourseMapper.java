package com.neoblack.course.mapper;

import cn.tedu.course.pojo.Cat;
import cn.tedu.course.pojo.Course;
import com.neoblack.course.pojo.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface CourseMapper {
    void saveCourse(Course course);
    List<Course> findAllCourse();
    List<Course> findCourseAfterId(int cId);

}
