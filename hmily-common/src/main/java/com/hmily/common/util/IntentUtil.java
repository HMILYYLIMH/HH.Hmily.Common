package com.hmily.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Intent 跳转工具类。
 *
 * @author hehui
 *         ~
 * @date 2016/11/01
 */
public class IntentUtil {

    public static void view(@NonNull Context currentContext, Uri data, Bundle extras) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(data);
        if (extras != null) {
            intent.putExtras(extras);
        }
        currentContext.startActivity(intent);
    }

    @SuppressWarnings("unused")
    public static void dial(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel://" + phone));
    }

    public static void install(Context currentContext, String apkFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.startActivity(intent);
    }

    /**
     * 启动Activity
     */
    public static void startActivity(Context currentContext, Class<? extends Activity> targetClass) {
        startActivity(currentContext, targetClass, null);
    }

    public static void startActivity(Context currentContext, Class<? extends Activity> targetClass, Bundle
            extras) {
        Intent intent = new Intent(currentContext, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        currentContext.startActivity(intent);
    }

    /**
     * 启动Activity并监听返回结果
     */
    public static void startActivityForResult(Activity currentActivity, Class<? extends Activity>
            targetClass, int requestCode) {
        startActivityForResult(currentActivity, targetClass, null, requestCode);
    }

    public static void startActivityForResult(Activity currentActivity, Class<? extends Activity>
            targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(currentActivity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        currentActivity.startActivityForResult(intent, requestCode);
    }
}
