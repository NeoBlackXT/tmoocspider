package com.neoblack.video.pojo;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Neo on 2017/5/17.
 */
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cId;
    private Integer cCatId;
    private String cCourseName;
    private String cCourseUrl;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getcCatId() {
        return cCatId;
    }

    public void setcCatId(Integer cCatId) {
        this.cCatId = cCatId;
    }

    public String getcCourseName() {
        return cCourseName;
    }

    public void setcCourseName(String cCourseName) {
        this.cCourseName = cCourseName;
    }

    public String getcCourseUrl() {
        return cCourseUrl;
    }

    public void setcCourseUrl(String cCourseUrl) {
        this.cCourseUrl = cCourseUrl;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cId=" + cId +
                ", cCatId=" + cCatId +
                ", cCourseName='" + cCourseName + '\'' +
                ", cCourseUrl='" + cCourseUrl + '\'' +
                '}';
    }
}
