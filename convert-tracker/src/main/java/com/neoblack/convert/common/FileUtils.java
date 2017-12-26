package com.neoblack.convert.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by neo on 2017/5/22.
 */
public class FileUtils {
    private FileUtils() {
    }

    public static File toTargetFile(File sourceFile) {
        String sourceDirStr = null;
        try {
            sourceDirStr = sourceFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String targetDirStr = sourceDirStr.replace("D:", "C:");
        File targetDir = new File(targetDirStr);
        return targetDir;
    }

    @SuppressWarnings("Duplicates")
    public static void closeFOS(FileOutputStream fos) {
        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            fos = null;
            e.printStackTrace();
        }
    }
}
