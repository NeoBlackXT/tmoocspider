package com.neoblack.course.mapper;

import cn.tedu.course.pojo.Video;
import com.neoblack.course.pojo.Video;
import org.springframework.stereotype.Repository;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface VideoMapper {
    void saveVideo(Video video);
}
