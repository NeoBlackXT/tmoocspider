package com.neoblack.course.mapper;

import cn.tedu.course.pojo.Cat;
import com.neoblack.course.pojo.Cat;
import org.springframework.stereotype.Repository;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface CatMapper {
    void saveCat(Cat cat);
}
