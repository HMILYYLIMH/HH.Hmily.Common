package com.hmily.common.util;

import com.hmily.common.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * MD5值计算工具类。
 *
 * @author hehui
 */
public class MD5Util {
    private static final String TAG = "MD5Util";
    private static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f'};

    /**
     * 获取字符串的MD5值。
     *
     * @param str 字符串。
     * @return 字符串的MD5值，如果字符串为 {@code null} 或者 Empty，则返回 Empty。
     */
    public static String getMD5(String str) {
        if (!Util.isNullOrEmpty(str)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes("UTF-8"));
                byte[] bytes = md.digest();

                return toMD5String(bytes).toUpperCase();
            } catch (Exception e) {
                Logger.e(TAG, "Failed to get string's MD5: " + str, e);
            }
        }
        return "";
    }

    /**
     * 获取文件的MD5值。
     *
     * @param file 文件对象。
     * @return 文件的MD5值，如果不是文件或者获取失败，则返回 Empty 。
     */
    public static String getMD5(File file) {
        if (file != null && file.exists() && file.isFile()) {
            FileInputStream fis = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");

                fis = new FileInputStream(file);
                byte[] buffer = new byte[2048];
                int length = -1;
                while ((length = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, length);
                }
                byte[] bytes = md.digest();

                return toMD5String(bytes).toUpperCase();
            } catch (Exception e) {
                Logger.e(TAG, "Failed to get file's MD5: " + file, e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                    }
                    fis = null;
                }
            }
        }
        return "";
    }

    /**
     * 把byte[]转换成16进制字符串表现形式。
     *
     * @param data 要转换的byte数组。
     * @return 十六进制字符串表现形式。
     */
    private static String toMD5String(byte[] data) {
        if (data == null || data.length < 16) {
            return "";
        }

        // 用字节表示就是16个字节
        char str[] = new char[16 * 2]; // 每个字节用16进制表示的话，使用两个字符,
        // 所以表示成16进制需要32个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) {
            // 从第一个字节开始，对MD5的每一个字节
            // 转换成16进制字符的转换
            byte byte0 = data[i];
            str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];// 取字节中高4位的数字转换
            // >>>为逻辑右移，将符号位一起右移
            str[k++] = HEX_DIGITS[byte0 & 0xf]; // 取字节中低4位的数字转换
        }

        return new String(str); // 转换后的结果转换成字符串
    }
}
