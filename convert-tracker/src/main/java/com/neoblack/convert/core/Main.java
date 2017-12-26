package com.neoblack.convert.core;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by neo on 2017/5/22.
 */
public class Main {
    private static final String sourceBaseDirStr = "D:/tmooc";
    private static final String targetBaseDirStr = "C:/tmooc";

    public static void main(String[] args) {
        File sourceBaseDir = new File(sourceBaseDirStr);
        File[] sourceCatDirs = sourceBaseDir.listFiles();
        for (File sourceCatDir : sourceCatDirs) {
            File[] sourceCourseDirs = sourceCatDir.listFiles();
            for (File sourceCourseDir : sourceCourseDirs) {
                File[] sourceVideoDirs = sourceCourseDir.listFiles();
                for (File sourceVideoDir : sourceVideoDirs) {
                    FilenameFilter filter = new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".ts");
                        }
                    };
                    File[] sourceVideoFiles = sourceVideoDir.listFiles(filter);
                    if (0 == sourceVideoFiles.length) {
                        continue;
                    }
                    new VideoConvert(sourceVideoDir, sourceVideoFiles).convert();

                }
            }
        }

    }
}
