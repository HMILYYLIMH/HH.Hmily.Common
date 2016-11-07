package com.hmily.common.log;

import android.util.Log;

/**
 * 日志记录器。
 *
 * @author hehui
 */
public class Logger {
    private static final String TAG = Logger.class.getName();

    /**
     * 记录 V 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    /**
     * 记录 V 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void v(String tag, Object msg) {
        v(tag, String.valueOf(msg));
    }

    /**
     * 记录 V 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void v(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    /**
     * 记录 V 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void v(String tag, Object msg, Throwable tr) {
        v(tag, String.valueOf(msg), tr);
    }

    /**
     * 记录 D 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    /**
     * 记录 D 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void d(String tag, Object msg) {
        d(tag, String.valueOf(msg));
    }

    /**
     * 记录 D 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void d(String tag, String msg, Throwable tr) {
        Log.d(tag, msg, tr);
    }

    /**
     * 记录 D 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void d(String tag, Object msg, Throwable tr) {
        d(tag, String.valueOf(msg), tr);
    }

    /**
     * 记录 I 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    /**
     * 记录 I 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void i(String tag, Object msg) {
        i(tag, String.valueOf(msg));
    }

    /**
     * 记录 I 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void i(String tag, String msg, Throwable tr) {
        Log.i(tag, msg, tr);
    }

    /**
     * 记录 I 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void i(String tag, Object msg, Throwable tr) {
        i(tag, String.valueOf(msg), tr);
    }

    /**
     * 记录 W级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    /**
     * 记录 W级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void w(String tag, Object msg) {
        w(tag, String.valueOf(msg));
    }

    /**
     * 记录 W 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    /**
     * 记录 W 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void w(String tag, Object msg, Throwable tr) {
        w(tag, String.valueOf(msg), tr);
    }

    /**
     * 记录 E 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * 记录 E 级别日志消息。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     */
    public static void e(String tag, Object msg) {
        e(tag, String.valueOf(msg));
    }

    /**
     * 记录 E 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    /**
     * 记录 E 级别日志消息和异常。
     *
     * @param tag 用于标识日志的来源。
     * @param msg 消息。
     * @param tr  异常。
     */
    public static void e(String tag, Object msg, Throwable tr) {
        e(tag, String.valueOf(msg), tr);
    }
}
