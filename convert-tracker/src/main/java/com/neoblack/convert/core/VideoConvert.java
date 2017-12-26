package com.neoblack.convert.core;

import com.neoblack.convert.common.CryptUtils;
import com.neoblack.convert.common.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by neo on 2017/5/22.
 */
public class VideoConvert {
    private File sourceVideoDir;
    private File[] sourceVideoFiles;

    public VideoConvert(File sourceVideoDir, File[] sourceVideoFiles) {
        this.sourceVideoDir = sourceVideoDir;
        Arrays.sort(sourceVideoFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String o1Name = o1.getName();
                String o1IndexStr = o1Name.substring(o1Name.indexOf("-") + 1, o1Name.indexOf(".ts"));
                String o2Name = o2.getName();
                String o2IndexStr = o2Name.substring(o2Name.indexOf("-") + 1, o2Name.indexOf(".ts"));
                int o1Index = Integer.parseInt(o1IndexStr);
                int o2Index = Integer.parseInt(o2IndexStr);
                return o1Index-o2Index;
            }

        });
        this.sourceVideoFiles = sourceVideoFiles;
    }

    public void convert() {
        File targetCourseDir = FileUtils.toTargetFile(sourceVideoDir.getParentFile());
        targetCourseDir.mkdirs();
        String targetVideoFileStr = sourceVideoDir.getParent() + "/" + sourceVideoDir.getName() + ".ts";
        File targetVideoFile = FileUtils.toTargetFile(new File(targetVideoFileStr));
        FileOutputStream fos = null;
        try {
            targetVideoFile.createNewFile();
            fos = new FileOutputStream(targetVideoFile,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int len = 0;
        for (File sourceVideoFile : sourceVideoFiles) {
            File keyFile = new File(sourceVideoDir + "/static.key");
//            File targetVideoFile = FileUtils.toTargetFile(sourceVideoFile);

            byte[] decrypt = CryptUtils.decrypt(sourceVideoFile, keyFile);
            try {
                fos.write(decrypt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtils.closeFOS(fos);
    }
}
