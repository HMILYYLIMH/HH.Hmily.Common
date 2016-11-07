package com.hmily.common.app;

import android.app.Application;

import com.hmily.common.enums.DeviceType;

/**
 * Application 抽象类。
 *
 * @author hehui
 *         ~
 * @date 2016/11/6 23:01
 */
public abstract class BaseApplication extends Application {

    /**
     * 获取应用程序的名称。
     *
     * @return
     */
    public abstract String getName();

    /**
     * 获取应用程序的设备类型。
     *
     * @return
     */
    public abstract DeviceType getDeviceType();
}