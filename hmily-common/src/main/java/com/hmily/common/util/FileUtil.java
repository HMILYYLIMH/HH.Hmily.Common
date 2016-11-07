package com.hmily.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.hmily.common.app.BaseApplication;
import com.hmily.common.app.Consts;
import com.hmily.common.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件、文件夹处理工具类。
 *
 * @author hehui
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 获取应用程序在内部存储区的目录的路径。
     *
     * @param context
     * @return
     */
    public static String getAppPersistentDataDirectory(Context context) {
        return context.getApplicationInfo().dataDir;
    }

    /**
     * 获取应用程序在SD卡的目录的路径。
     *
     * @param context
     * @return
     */
    public static String getAppExternalStorageDirectory(Context context) {
        if (!checkSDCard()) {
            return null;
        }

        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return FileUtil.getSDCardDir() + File.separator + app.getName() + File.separator
                + app.getDeviceType().getName();
    }

    /**
     * 获取上级目录的路径。
     *
     * @param filePath
     * @return
     */
    public static String getParentPath(String filePath) {
        if (!Util.isNullOrEmpty(filePath)) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > 0) {
                return filePath.substring(0, index);
            }
        }
        return "";
    }

    /**
     * 根据文件路径获取文件名。
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (!Util.isNullOrEmpty(filePath)) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > -1) {
                return filePath.substring(index + 1);
            } else {
                return filePath;
            }
        }
        return "";
    }

    /**
     * 根据文件路径获取不带后缀的文件名。
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (!Util.isNullOrEmpty(filePath)) {
            String fileName = getFileName(filePath);
            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                return fileName.substring(0, index);
            } else {
                return fileName;
            }
        }
        return "";
    }

    /**
     * 根据文件路径获取文件后缀。
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (!Util.isNullOrEmpty(filePath)) {
            String fileName = getFileName(filePath);
            int index = fileName.lastIndexOf(".");
            if (index > -1) {
                return fileName.substring(index);
            }
        }
        return "";
    }

    /**
     * 判断文件或文件夹是否存在。
     *
     * @param path ：文件或文件夹路径。
     * @return
     */
    public static boolean exists(String path) {
        if (Util.isNullOrEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    /**
     * 是否有SD卡。
     *
     * @return
     */
    public static boolean checkSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡目录。
     *
     * @return
     */
    public static String getSDCardDir() {
        if (checkSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取 SD 卡剩余容量（此方法未检测 SD 卡是否存在，若要检测的话请先调用 {@linkplain #checkSDCard()} 方法）。
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardFreeBlocks() {
        try {
            String sdcardPath = android.os.Environment.getExternalStorageDirectory().getPath();
            if (Util.isNullOrEmpty(sdcardPath)) {
                return 0;
            }

            android.os.StatFs statfs = new android.os.StatFs(sdcardPath);

            return (long) statfs.getAvailableBlocks() * (long) statfs.getBlockSize();
        } catch (IllegalArgumentException e) {
            Logger.e(TAG, "Failed to get the free blocks of SDCard.", e);
        }

        return 0;
    }

    /**
     * 获取 SD 卡总容量（此方法未检测 SD 卡是否存在，若要检测的话请先调用 {@linkplain #checkSDCard()} 方法）。
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardTotalBlocks() {
        try {
            String sdcardPath = android.os.Environment.getExternalStorageDirectory().getPath();
            if (Util.isNullOrEmpty(sdcardPath)) {
                return 0;
            }

            android.os.StatFs statfs = new android.os.StatFs(sdcardPath);

            return (long) statfs.getBlockCount() * (long) statfs.getBlockSize();
        } catch (IllegalArgumentException e) {
            Logger.e(TAG, "Failed to get the total blocks of SDCard.", e);
        }

        return 0;
    }

    /**
     * 创建文件夹。
     *
     * @param dirPath 文件夹路径。
     * @return {@code true} 表示文件夹创建成功或者已存在，否则为 {@code false}。
     */
    public static final boolean createDirectory(String dirPath) {
        if (Util.isNullOrEmpty(dirPath)) {
            return false;
        }
        return createDirectory(new File(dirPath));
    }

    /**
     * 创建文件夹。
     *
     * @param dir 文件夹对象。
     * @return {@code true} 表示文件夹创建成功或者已存在，否则为 {@code false}。
     */
    public static final boolean createDirectory(File dir) {
        if (dir == null || dir.isFile()) {
            return false;
        }
        return dir.isDirectory() ? true : dir.mkdirs();
    }

    /**
     * 删除文件或目录
     *
     * @param fileOrDirPath
     */
    public static void deleteFileOrDirectory(String fileOrDirPath) {
        if (!Util.isNullOrEmpty(fileOrDirPath)) {
            deleteFileOrDirectory(new File(fileOrDirPath));
        }
    }

    /**
     * 删除文件或文件夹。
     *
     * @param file
     */
    public static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteFileOrDirectory(subFile);
            }
            file.delete();
        } else if (file.isFile()) {
            file.delete();
        }
    }

    /**
     * 获取文件的 MimeType。
     *
     * @param file
     */
    public static String getFileMimeType(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        String extension = null;
        if (index > -1) {
            extension = fileName.substring(index + 1, fileName.length()).toLowerCase();
        } else {
            extension = "";
        }

        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (Util.isNullOrEmpty(type)) {
            type = "*/*";
        }
        return type;
    }

    /**
     * 打开文件。
     *
     * @param context
     * @param file
     */
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        /* 设置intent的file与MimeType */
        intent.setDataAndType(Uri.fromFile(file), getFileMimeType(file));
        context.startActivity(intent);
    }

    /**
     * 从输入流中读取出所有字符串内容。
     *
     * @param is
     * @return
     */
    public static String readAllText(InputStream is) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            int len = -1;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return new String(outStream.toByteArray(), Consts.DEFAULT_ENCODING);
        } catch (IOException e) {
            Logger.e(TAG, "Failed to read all text from InputStream.", e);
        } finally {
            try {
                outStream.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    /**
     * 保存文件。
     *
     * @param path     ：文件路径。比如：/mnt/sdcard
     * @param fileName ：文件名。比如：cc
     * @param fileType ：文件类型，比如：.jpg、.bmp等等类型。
     * @param buffer   ：字节数组。
     *                 <li></li>构建以上路径为：/mnt/sdcard/cc.jpg
     */
    public static void saveFile(String path, String fileName, String fileType, byte[] buffer) {
        String filePath = path + File.separator + fileName + fileType;
        if (buffer != null) {
            FileOutputStream out = null;
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new FileOutputStream(file);
                out.write(buffer);
            } catch (Exception e) {
                Logger.e(TAG, "", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Logger.w(TAG, e);
                    }
                }
            }
        }
    }

    /**
     * 复制文件或文件夹。
     *
     * @param context      ：上下文。
     * @param srcFileOrDir ：源文件或文件夹。
     * @param dstFileOrDir ：目标文件或文件夹。
     */
    public static void copyFile(Context context, File srcFileOrDir, File dstFileOrDir) {
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }

        if (srcFileOrDir == null) {
            throw new IllegalArgumentException("srcFileOrDir is null.");
        }

        if (dstFileOrDir == null) {
            throw new IllegalArgumentException("dstFileOrDir is null.");
        }

        if (!srcFileOrDir.exists()) {
            Logger.i(TAG, "File or directory does not exist: " + srcFileOrDir.getAbsolutePath());
            return;
        }

        if (srcFileOrDir.isDirectory()) {
            String dstDirPath = dstFileOrDir.getAbsolutePath();
            if (dstFileOrDir.isFile() && !dstFileOrDir.delete()) {
                Logger.i(TAG, "'dstFileOrDir' is file and it can not delete: " + dstDirPath);
                return;
            }
            if (!dstFileOrDir.exists() && !dstFileOrDir.mkdirs()) {
                Logger.i(TAG, "Directory can not create: " + dstDirPath);
                return;
            }

            for (File file : srcFileOrDir.listFiles()) {
                copyFile(context, file, new File(dstDirPath + File.separator + file.getName()));
            }
        } else {
            File parentDir = dstFileOrDir.getParentFile();
            if (parentDir.isFile() && !parentDir.delete()) {
                Logger.i(TAG,
                        "'dstFileOrDir.getParentFile()' is file and it can not delete: " + parentDir.getAbsolutePath());
                return;
            }
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                Logger.i(TAG, "Directory can not create: " + parentDir.getAbsolutePath());
                return;
            }

            byte[] buffer = null;
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(srcFileOrDir);
                int length = fs.available();
                buffer = new byte[length];
                fs.read(buffer);
            } catch (IOException e) {
                Logger.w(TAG, "Failed to read file.", e);
                return;
            } finally {
                if (fs != null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                    }
                    fs = null;
                }
            }

            OutputStream os = null;
            try {
                deleteFileOrDirectory(dstFileOrDir);

                os = new FileOutputStream(dstFileOrDir, false);
                os.write(buffer);
            } catch (IOException e) {
                Logger.w(TAG, "Failed to write new file.", e);
            } finally {
                buffer = null;
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                    }
                    os = null;
                }
            }
        }
    }
}
