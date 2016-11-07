package com.hmily.common.util;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Zip文件压缩和解压工具
 *
 * @author hehui
 */
public class ZipUtil {

    /**
     * 解压Zip文件
     *
     * @param is           zip文件数据流
     * @param outputFolder 解压到路径
     * @throws IOException 解压出错抛出异常
     */
    @SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
    public static void unzip(InputStream is, String outputFolder) throws IOException {
        byte[] buffer = new byte[1024];
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry ze;

        while ((ze = zis.getNextEntry()) != null) {

            String fileName = ze.getName();
            File newFile = new File(outputFolder + File.separator + fileName);

            //如果对象是目录,则创建目录
            if (ze.isDirectory()) {
                newFile.mkdirs();
                continue;
            }


            //如果对象是文件,则解压文件

            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            zis.closeEntry();
        }
        zis.close();
    }

    @SuppressWarnings("unused")
    public static void zip() {
        //TODO Zip压缩未实现
    }

    /**
     * 异步解压Zip文件
     *
     * @param is           zip文件数据流
     * @param outputFolder 解压到路径
     * @param listener     解压结果监听
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void asyncUnzip(final InputStream is, final String outputFolder, final
    UnzipListener listener) {
        listener.onStart();
        final byte[] buffer = new byte[1024];
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry ze;

                try {
                    while ((ze = zis.getNextEntry()) != null) {

                        String fileName = ze.getName();
                        File newFile = new File(outputFolder + File.separator + fileName);

                        //如果对象是目录,则创建目录
                        if (ze.isDirectory()) {
                            if (!newFile.exists())
                                if (!newFile.mkdirs()) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onFailed("解压文件夹失败!", null);
                                        }
                                    });
                                    return;
                                }
                            continue;
                        }

                        //如果对象是文件,则解压文件

                        File parentFile = new File(newFile.getParent());
                        if (!parentFile.exists() && !parentFile.mkdirs()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFailed("创建文件夹失败!", null);
                                }
                            });
                            return;
                        }

                        FileOutputStream fos = new FileOutputStream(newFile);

                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                        fos.close();
                        zis.closeEntry();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(outputFolder);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailed(e.getMessage(), e);
                        }
                    });
                } finally {
                    try {
                        zis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Zip解压过程监听
     */
    public interface UnzipListener {
        void onStart();

        void onFailed(String errMsg, Throwable t);

        void onSuccess(String destPath);
    }
}
