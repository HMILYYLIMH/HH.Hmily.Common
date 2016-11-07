package com.hmily.common.util;

import com.hmily.common.log.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * 设备相关信息获取工具类。
 *
 * @author hehui
 */
public class DeviceInfoUtil {

    private static final String TAG = "DeviceInfoUtil";

    /**
     * 获取基带版本信息。
     *
     * @return
     */
    public static String getBaseBandVersion() {
        String version = "";
        try {
            Class<?> cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", ""});
            version = String.valueOf(result);
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        }

        return version;
    }

    /**
     * 获取内核版本信息。
     *
     * @return
     */
    public static String getLinuxCoreVersion() {
        Process process = null;
        String kernelVersion = "";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            process = Runtime.getRuntime().exec("cat /proc/version");

            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader, 8 * 1024);

            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            if (!Util.isNullOrEmpty(result)) {
                String Keyword = "version ";
                int index = result.indexOf(Keyword);
                line = result.substring(index + Keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    Logger.e(TAG, "", e);
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    Logger.e(TAG, "", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    Logger.e(TAG, "", e);
                }
            }
        }

        return kernelVersion;
    }

    /**
     * 获得处理器信息
     *
     * @return String
     */
    public static String getCPUInfo() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            bufferedReader = new BufferedReader(fileReader);
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                String[] array = info.split(":");
                if (array[0].trim().equals("Hardware")) {
                    return array[1];
                }
            }
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    Logger.e(TAG, "", e);
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Exception e) {
                    Logger.e(TAG, "", e);
                }
            }
        }
        return null;
    }
}
