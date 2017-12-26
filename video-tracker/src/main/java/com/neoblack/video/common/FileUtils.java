package com.neoblack.video.common;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neo on 2017/5/21.
 */
public class FileUtils {
    private FileUtils(){}

    public static String fixFilePath(String path) {
        String[] toReplaces = {"\\\\", "/", ":", "\\*", "\\?", "\"", "<", ">", "\\|"};
        for (String toReplace : toReplaces) {
            path=path.replaceAll(toReplace, "");
        }
        Matcher matcher = Pattern.compile("Unit0(\\d{2})").matcher(path);
        if (matcher.find()) {
            path = path.replaceAll("Unit0(\\d{2})", "Unit" + matcher.group(1));
        }
        return path;

    }

    public static String buildFilePath(String... fileNames){
        StringBuilder sb = new StringBuilder();
        if (fileNames.length >= 1) {
            //首个目录带有盘符等特殊字符，不进行修正 eg:D:/tmooc
            sb.append(fileNames[0]);
        }
        for (int i=1;i<fileNames.length;i++) {
            sb.append("/").append(fixFilePath(fileNames[i]));
        }
        return sb.toString();
    }

    @SuppressWarnings("Duplicates")
    public static void writeBytes(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

}
