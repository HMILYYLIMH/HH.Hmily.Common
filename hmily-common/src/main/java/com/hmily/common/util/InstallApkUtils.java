package com.hmily.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hmily.common.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/***
 * 安装APK以及保存下载APK状态的标识
 *
 * @author hehui
 */
public class InstallApkUtils {

    private static final String TAG = "InstallApkUtils";

    /**
     * 安装APK。
     *
     * @param context
     * @param apkFilePath
     */
    public static void installAPK(Context context, String apkFilePath) {
        if (apkFilePath.startsWith(FileUtil.getAppPersistentDataDirectory(context))) {
            exec(new String[]{"chmod", "777", FileUtil.getParentPath(apkFilePath)});// 修改下载升级包目录权限为：rwx--rwx--rwx
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFilePath)),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 执行相应指令。
     *
     * @param args
     * @return
     */
    private static String exec(String[] args) {
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            Logger.e(TAG, "Failed to exec: " + Arrays.toString(args), e);
        } finally {
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inIs != null) {
                try {
                    inIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }

        Logger.d(TAG, "exec result: " + result);
        return result;
    }
}
