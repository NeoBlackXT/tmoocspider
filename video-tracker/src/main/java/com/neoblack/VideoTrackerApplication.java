package com.neoblack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.video.mapper")
public class VideoTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoTrackerApplication.class, args);
	}
}
