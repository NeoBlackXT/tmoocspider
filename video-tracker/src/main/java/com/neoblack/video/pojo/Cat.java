package com.neoblack.video.pojo;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Neo on 2017/5/17.
 */
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cId;
    private String cCatname;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcCatname() {
        return cCatname;
    }

    public void setcCatname(String cCatname) {
        this.cCatname = cCatname;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "cId=" + cId +
                ", cCatname='" + cCatname + '\'' +
                '}';
    }
}
