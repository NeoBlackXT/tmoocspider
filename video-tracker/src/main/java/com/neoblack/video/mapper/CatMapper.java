package com.neoblack.video.mapper;

import cn.tedu.video.pojo.Cat;
import com.neoblack.video.pojo.Cat;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neo on 2017/5/17.
 */
@Repository
public interface CatMapper {
    List<Cat> findAllCat();
}
