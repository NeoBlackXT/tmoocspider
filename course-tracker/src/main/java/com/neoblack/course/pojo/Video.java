package com.neoblack.course.pojo;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Neo on 2017/5/17.
 */
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cId;
    private Integer cCourseId;
    private String cVideoName;
    private String cVideoUrl;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getcCourseId() {
        return cCourseId;
    }

    public void setcCourseId(Integer cCourseId) {
        this.cCourseId = cCourseId;
    }

    public String getcVideoName() {
        return cVideoName;
    }

    public void setcVideoName(String cVideoName) {
        this.cVideoName = cVideoName;
    }

    public String getcVideoUrl() {
        return cVideoUrl;
    }

    public void setcVideoUrl(String cVideoUrl) {
        this.cVideoUrl = cVideoUrl;
    }

    @Override
    public String toString() {
        return "Video{" +
                "cId=" + cId +
                ", cCourseId=" + cCourseId +
                ", cVideoName='" + cVideoName + '\'' +
                ", cVideoUrl='" + cVideoUrl + '\'' +
                '}';
    }
}
