package com.neoblack.convert.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Created by neo on 2017/5/22.
 */
public class CryptUtils {
    private CryptUtils(){}

    public static byte[] decrypt(File dataFile, File keyFile,int iv) {
        try {

            FileInputStream keyfis = new FileInputStream(keyFile);
            byte[] keyBytes = new byte[16];
            keyfis.read(keyBytes);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            byte[] buf = new byte[1024];
            byte[] toDecrypt = new byte[0];
            FileInputStream datafis = new FileInputStream(dataFile);
            int read = 0;
            while ((read = datafis.read(buf)) > 0) {
                int len = toDecrypt.length;
                toDecrypt= Arrays.copyOf(toDecrypt, len + read);
                System.arraycopy(buf,0,toDecrypt,len,read);
            }
            datafis.close();
            keyfis.close();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            byte[] temp = new byte[16];
            temp[15] = (byte) (iv >> (8 * 0));
            temp[14] = (byte) (iv >> (8 * 1));
            temp[13] = (byte) (iv >> (8 * 2));
            temp[12] = (byte) (iv >> (8 * 3));

            IvParameterSpec ivps = new IvParameterSpec(temp);
            cipher.init(Cipher.DECRYPT_MODE, key,ivps);// 初始化
            byte[] result = cipher.doFinal(toDecrypt);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(File dataFile, File keyFile) {
        String fileName = dataFile.getName();
        int i1 = fileName.indexOf("-");
        int i2 = fileName.indexOf(".");
        String ivStr = fileName.substring(i1 + 1, i2);
        int iv = Integer.parseInt(ivStr);
        byte[] bytes = decrypt(dataFile, keyFile, iv);
        return bytes;
    }

}
