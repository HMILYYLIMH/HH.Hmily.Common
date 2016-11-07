package com.hmily.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hmily.common.app.Consts;
import com.hmily.common.enums.NetworkState;
import com.hmily.common.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类。
 *
 * @author hehui
 */
public class Util {

    private static final String TAG = "Util";

    /**
     * 获取随机UUID字符串。
     *
     * @return：UUID字符串。
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static byte[] getUuidAsByteArray() {
        return getUuidAsByteArray(UUID.randomUUID());
    }

    public static byte[] getUuidAsByteArray(UUID uuid) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream(16);
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        try {
            dataOut.writeLong(uuid.getMostSignificantBits());
            dataOut.writeLong(uuid.getLeastSignificantBits());
            byte[] buffer = byteOut.toByteArray();

            return buffer;
        } catch (IOException e) {
            Logger.e(TAG, "Failed to get uuid as a byte array.", e);
            return null;
        } finally {
            try {
                byteOut.close();
            } catch (IOException e) {
            }
            try {
                dataOut.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长]时长的单位是毫秒
     *
     * @param context ：上下文。
     * @param pattern ：匹配数组。
     * @param isRepeat ：是否重复。
     */
    public static void vibrate(Context context, long[] pattern, boolean isRepeat, boolean isVibrate) {
        Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (isVibrate) {
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        } else {
            vib.vibrate(new long[]{0}, isRepeat ? 1 : -1);
        }
    }

    /***
     * 是否需要声音提示
     *
     * @param context : 上下文
     * @param isVoice : true需要声音提示； false不需要声音提示
     */
    public static void voice(Context context, boolean isVoice) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (isVoice) {
            int defaultVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) * 4 / 5;
            if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, defaultVolume,
                        AudioManager.FLAG_PLAY_SOUND);
            }
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 1, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    /**
     * 验证是否正确的邮箱地址
     *
     * @param email : 邮箱地址
     *
     * @return ：是否正确。
     */
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证是否正确的手机号码
     *
     * @param mobile : 手机号码
     *
     * @return ：是否正确。
     */
    public static boolean isMobile(String mobile) {
        if (mobile == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^((\\+86)|(86)|\\(\\+86\\)|\\(86\\))?1[3|4|5|7|8]\\d{9}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 检测文件路径是否合法。
     *
     * @param filePath : 路径
     *
     * @return ：是否合法。
     */
    public static boolean isIllegalFilePath(String filePath) {
        if (isNullOrEmpty(filePath)) {
            return true;
        }

        Matcher matcher = Pattern.compile("((\\/\\/)+)|((\\\\)+)|(:+)|((\\*)+)|(\\?+)|(\"+)|(<+)|(>+)|(\\|+)").matcher(
                filePath);
        boolean findIllegal = false;
        while (matcher.find()) {
            findIllegal = true;
            break;
        }
        return findIllegal;
    }

    /**
     * 检测内容是否包含中文字符。
     *
     * @param content ：中文字符。
     *
     * @return ：是否包含中文。
     */
    public static boolean containsChinese(String content) {
        if (isNullOrEmpty(content)) {
            return false;
        }

        Pattern pattern = Pattern.compile("[(\u4E00-\u9FA5)|(\uF900-\uFA2D)]+");
        Matcher matcher = pattern.matcher(content);
        boolean hasChinese = false;
        while (matcher.find()) {
            hasChinese = true;
            break;
        }
        return hasChinese;
    }

    /**
     * 去除字符串末的连续指定的字符 。
     *
     * @param timeString ：字符串。
     * @param filterSpecifyChar ：指定的字符。
     *
     * @return ：返回处理过的字符串。
     */
    public static String trimEnd(String timeString, char filterSpecifyChar) {
        if (isNullOrEmpty(timeString)) {
            return timeString;
        }

        char[] value = timeString.toCharArray();
        int start = 0;
        int end = timeString.length() - 1;

        while ((end >= start) && (value[end] == filterSpecifyChar)) {
            end--;
        }

        return new String(value, start, end - start + 1);
    }

    /**
     * 字符串是否为{@code null}或者空字符串。
     *
     * @param s：字符串s。
     *
     * @return ：是否为空。
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * 比较字符串是否相等。
     *
     * @param s1：字符串s1。
     * @param s2：字符串s2。
     *
     * @return ：是否相等。
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * 比较字符串是否相等，忽略大小写。
     *
     * @param s1：字符串s1。
     * @param s2：字符串s2。
     *
     * @return ：是否相等。
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

    /**
     * 检查当前线程是否是UI线程。
     *
     * @return ：是否处于主线程。
     */
    public static boolean isUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /**
     * 隐藏键盘。
     *
     * @param activity ：键盘所属界面。
     */
    public static void hideSoftInput(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = activity.getCurrentFocus();
        if (v == null) {
            return;
        }

        android.os.IBinder iBinder = v.getWindowToken();
        if (iBinder == null) {
            return;
        }

        imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘。
     *
     * @param view ：视图。
     */
    public static void hideSoftInput(View view) {
        Context context = view.getContext();
        if (context == null) {
            return;
        }

        android.os.IBinder iBinder = view.getWindowToken();
        if (iBinder == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 该方法被调用时若键盘处于关闭状态则触发打开键盘；若键盘处于打开状态则触发关闭键盘；
     *
     * @param activity ：当前所属界面。
     */
    public static void toggleSoftInput(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 开启键盘；
     *
     * @param view ：视图。
     */
    public static void showSoftInput(View view) {
        if (view == null) {
            return;
        }

        Context context = view.getContext();
        if (context == null) {
            return;
        }

        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 获取本地IP地址。
     *
     * @return ：返回字符串地址。
     */
    public static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getClass() == java.net.Inet4Address.class
                            && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.e(TAG, "Failed to get the local IP.", ex);
        }
        return "";
    }

    /**
     * 清除 DNS 缓存。
     */
    @SuppressWarnings("rawtypes")
    public static void clearDNSCache() {
        try {
            Class inetAddressClass = java.net.InetAddress.class;
            Field cacheField = inetAddressClass.getDeclaredField("addressCache");
            cacheField.setAccessible(true);
            Object obj = (Object) cacheField.get(inetAddressClass);
            Class cacheClazz = obj.getClass();

            if (android.os.Build.VERSION.SDK_INT >= 12) {
                Field cache = cacheClazz.getDeclaredField("cache");
                cache.setAccessible(true);
                obj = (Object) cache.get(obj);
                Class cacheClass = obj.getClass();

                Field cacheMapField = cacheClass.getDeclaredField("map");
                cacheMapField.setAccessible(true);
                Map cacheMap = (Map) cacheMapField.get(obj);
                cacheMap.clear();
            } else {
                Field cacheMapField = cacheClazz.getDeclaredField("map");
                cacheMapField.setAccessible(true);
                Map cacheMap = (Map) cacheMapField.get(obj);
                cacheMap.clear();
            }
        } catch (Exception e) {
            Logger.e(TAG, "Failed to clear DNS cache.", e);
        }
    }

    /**
     * 获取流媒体等参数信息。
     *
     * @param context : 上下文。
     * @param streamType : 建议传入 {@link AudioManager#STREAM_SYSTEM} 参数获取系统设置。
     * @return: new new Object[3] { audioManager.getMode(),
     * audioManager.getStreamVolume(streamType), audioManager.isSpeakerphoneOn() }
     */
    public static Object[] getStreamParams(Context context, int streamType) {
        Object[] objs = new Object[3];
        objs[0] = AudioManager.MODE_NORMAL;
        objs[1] = -1;
        objs[2] = false;
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                Logger.w(TAG, "Failed to get AudioManager service.");
                return objs;
            }

            objs[0] = audioManager.getMode();
            objs[1] = audioManager.getStreamVolume(streamType);
            objs[2] = audioManager.isSpeakerphoneOn();
            return objs;
        } catch (Exception e) {
            Logger.e(TAG, "Failed to getStreamParams", e);
        }
        return objs;
    }

    /**
     * 打开扬声器。
     *
     * @param context　：上下文．
     * @return 返回 false 表示获取不到音频管理器 或者 出现异常行为。
     */
    @SuppressWarnings("deprecation")
    public static boolean openSpeaker(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                Logger.w(TAG, "Failed to get AudioManager service.");
                return false;
            }

            if (!audioManager.isSpeakerphoneOn()) {
                // audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setMode(AudioManager.ROUTE_SPEAKER);
                audioManager.setSpeakerphoneOn(true);
                audioManager
                        .setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                                audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                                AudioManager.STREAM_VOICE_CALL);
            }
            return true;
        } catch (Exception e) {
            Logger.e(TAG, "Failed to openSpeaker", e);
        }
        return false;
    }

    /**
     * 还原打开扬声器前获取的系统音量值并关闭扬声器。
     *
     * @param context ：上下文。
     * @param prevVolume ：声音值．
     * @param prevMode　：声音模式．
     * @param isResumeOriginal　：是否还原成原始模样．
     * @return 返回 false 表示获取不到音频管理器 或者 出现异常行为。
     */
    public static boolean closeSpeaker(Context context, int prevVolume, int prevMode, boolean isResumeOriginal) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                Logger.w(TAG, "Failed to get AudioManager service.");
                return false;
            }

            audioManager.setMode(isResumeOriginal ? prevMode : AudioManager.MODE_IN_CALL);
            if (audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(false);
                audioManager
                        .setStreamVolume(AudioManager.STREAM_VOICE_CALL, prevVolume, AudioManager.STREAM_VOICE_CALL);
            }
            return true;
        } catch (Exception e) {
            Logger.e(TAG, "Failed to closeSpeaker", e);
        }
        return false;
    }

    /**
     * 获取相对于基准机型而计算出来的比例。
     *
     * @param activity ：当前界面。
     *
     * @return ：动态比例值。
     */
    public static float getDynamicScale(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        float widthMulDensity = width * density;
        float heightMulDensity = height * density;
        if (height > width) {// 竖屏
            return Math.min(widthMulDensity / Consts.CUST_BASE_WIDTH, heightMulDensity / Consts.CUST_BASE_HEIGHT);
        } else {// 横屏
            return Math.min(widthMulDensity / Consts.CUST_BASE_HEIGHT, heightMulDensity / Consts.CUST_BASE_WIDTH);
        }
    }

    /**
     * 获取应用的版本信息。
     *
     * @param context ：上下文。
     *
     * @return ：应用版本信息。
     */
    public static String getApplicaionVersion(Context context) {
        String versionName = "";
        try {
            versionName = "V"
                    + context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            Logger.e(TAG, "Failed to get versionName.", e);
        }
        return versionName;
    }

    /**
     * 将秒转换为时间字符串。格式为00：00：00
     *
     * @param diff ：单位（秒）。
     *
     * @return
     */
    public static String parseSeconds(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long miao = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

        StringBuilder sb = new StringBuilder();
        if (hours <= 9) {
            sb.append("0").append(hours).append(":");
        } else {
            sb.append(hours).append(":");
        }
        if (minutes <= 9) {
            sb.append("0").append(minutes).append(":");
        } else {
            sb.append(minutes).append(":");
        }
        if (miao <= 9) {
            sb.append("0").append(miao);
        } else {
            sb.append(miao);
        }
        return sb.toString();
    }

    /**
     * 获取当前网络状态。
     *
     * @param context ：上下文。
     *
     * @return ：网络状态对象。
     */
    public static NetworkState getNetworkState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        NetworkState state = NetworkState.NETWORK_NO;
        if (ni != null && ni.isConnectedOrConnecting()) {
            int type = ni.getType();
            // WIFI
            if (type == ConnectivityManager.TYPE_WIFI) {
                state = NetworkState.NETWORK_WIFI;
            }
            // SIM卡
            else if (type == ConnectivityManager.TYPE_MOBILE) {
                int subType = ni.getSubtype();
                // 2G
                if (subType == TelephonyManager.NETWORK_TYPE_GPRS // 联通2g
                        || subType == TelephonyManager.NETWORK_TYPE_CDMA // 电信2g
                        || subType == TelephonyManager.NETWORK_TYPE_EDGE // 移动2g
                        || subType == TelephonyManager.NETWORK_TYPE_1xRTT
                        || subType == TelephonyManager.NETWORK_TYPE_IDEN) {
                    state = NetworkState.NETWORK_2G;
                }
                // 3G
                else if (subType == TelephonyManager.NETWORK_TYPE_EVDO_A // 电信3g
                        || subType == TelephonyManager.NETWORK_TYPE_UMTS
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_HSPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B
                        || subType == TelephonyManager.NETWORK_TYPE_EHRPD
                        || subType == TelephonyManager.NETWORK_TYPE_HSPAP) {
                    state = NetworkState.NETWORK_3G;
                }
                // 3G 华为荣耀6手机 TDS_HSDPA
                else if (subType == 18 && android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.KITKAT) {
                    state = NetworkState.NETWORK_3G;
                }
                // 4G
                else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                    state = NetworkState.NETWORK_4G;
                }
                // 未知网络
                else {
                    state = NetworkState.NETWORK_UNKNOWN;
                }
            } else {
                state = NetworkState.NETWORK_UNKNOWN;
            }
        }
        return state;
    }

    /**
     * 获取设备的唯一识别码。
     *
     * @param context ：上下文对象。
     * @return 设备的唯一识别码。
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String deviceId = tm.getDeviceId();
        if (deviceId != null) {
            return deviceId;
        }

        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if (androidId != null) {
            return androidId;
        }

        return "";
    }

    /**
     * 经纬度转换成距离
     *
     * @param a 第一个点
     * @param b 第二个点
     * @return 返回-1为传入的数值不正确
     */
    public static double getDistance(double[] a, double[] b) {
        if (a == null || b == null) {
            return -1;
        }
        // 忽略距离地面的高度
        if (a.length > 2 || b.length > 2) {
            return -1;
        }
        double R = 6371229; // 地球的半径
        double x, y, distance;
        x = (b[0] - a[0]) * Math.PI * R * Math.cos(((a[1] + b[1]) / 2) * Math.PI / 180) / 180;
        y = (b[1] - a[1]) * Math.PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

    /**
     * UNICODE 转 UTF8 。
     *
     * @param strUnicode : UNICODE字符串
     * @return 转换后所得UTF8字符串
     */
    public static String decodeUnicode(String strUnicode) {
        char aChar;
        int len = strUnicode.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = strUnicode.charAt(x++);
            if (aChar == '\\') {
                aChar = strUnicode.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = strUnicode.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 获取分辨率对象。
     *
     * @return ：获取Display对象。
     */

    /**
     * 获取分辨率对象。
     *
     * @param activity ：当前界面。
     * @return ：获取Display对象。
     */
    public static Display getDisplay(Activity activity) {
        if (activity == null) {
            return null;
        }
        if (activity.isFinishing()) {
            return null;
        }

        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);// 获取分辨率
        return display;
    }

    /**
     * 将 {@linkplain Map}对象的数据进行反转操作。
     *
     * @param map ：键值对。
     * @return ：翻转后的数据。
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> reverse(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return map;
        }

        LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<K, V>();
        int size = map.size();
        Object[] objs = map.keySet().toArray();
        for (int i = size - 1; i >= 0; i--) {
            K key = (K) objs[i];
            linkedHashMap.put(key, map.get(key));
        }
        return linkedHashMap;
    }

    /**
     * 将字符串集合使用分隔符连接生成字符串。
     *
     * @param collection ：字符串集合。
     * @param separator ：分割字符串。
     * @return ：拼接起来的字符串。
     */
    public static String join(Collection<String> collection, String separator) {
        if (collection == null || collection.size() == 0) {
            return "";
        }

        StringBuilder bs = new StringBuilder();
        for (String str : collection) {
            bs.append(str).append(separator);
        }
        bs.delete(bs.length() - separator.length(), bs.length());
        return bs.toString();
    }

    /**
     * 将字符串数组使用分隔符连接生成字符串。
     *
     * @param array ：字符串数组。
     * @param separator ：分割字符串。
     * @return ：拼接起来的字符串。
     */
    public static String join(String[] array, String separator) {
        if (array == null || array.length == 0) {
            return "";
        }

        StringBuilder bs = new StringBuilder();
        for (String str : array) {
            bs.append(str).append(separator);
        }
        bs.delete(bs.length() - separator.length(), bs.length());
        return bs.toString();
    }

    /**
     * 是否是网络地址。
     *
     * @param networkUrl ：网络地址。
     * @return ：是否是网络地址。
     */
    public static boolean isNetworkUrl(String networkUrl) {
        if (isNullOrEmpty(networkUrl)) {
            return false;
        }

        if (networkUrl.regionMatches(true, 0, "http://", 0, 7) || networkUrl.regionMatches(true, 0, "https://", 0, 8)
                || networkUrl.regionMatches(true, 0, "ftp://", 0, 6)
                || networkUrl.regionMatches(true, 0, "tftp://", 0, 7)) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为数字（包括是否带小数的判断）。
     *
     * @param digit：数字内容，整数，小数都可。
     * @param minPointCount：小数点后面位数的最小值。
     * @param maxPointCount：小数点后面位数的最大值。
     * @return ：是否是数字。
     */
    public static boolean isDigit(String digit, int minPointCount, int maxPointCount) {
        if (Util.isNullOrEmpty(digit)) {
            return false;
        }

        if (maxPointCount < 0 || maxPointCount < 0) {
            return false;
        }

        if (maxPointCount < minPointCount) {
            return false;
        }

        try {
            Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + minPointCount + "," +
                    maxPointCount + "})?$");

            Matcher matcher = pattern.matcher(digit);

            return matcher.matches();
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        }

        return false;
    }

    /**
     * 格式化小数点。
     *
     * @param content：需要格式化的内容。
     * @param pointCount：保留几个小数点个数。
     * @return ：格式化后的字符串。
     */
    public static String formatPoint(String content, int pointCount) {
        if (Util.isNullOrEmpty(content)) {
            return "0";
        }

        if (pointCount <= 0) {
            return content;
        }

        try {
            BigDecimal bd = new BigDecimal(content);
            bd = bd.setScale(pointCount, BigDecimal.ROUND_HALF_UP);
            return String.valueOf(bd);
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("0.");
        for (int i = 0; i < pointCount; i++) {
            sb.append("0");
        }

        return sb.toString();
    }
}