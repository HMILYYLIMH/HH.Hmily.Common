package com.hmily.common.enums;

/**
 * 设备类型。
 *
 * @author hehui
 *         ~
 * @date 2016-10-10 14:59
 */
public enum DeviceType {

    /**
     * None
     */
    None((byte) 255, "None"),

    /**
     * PC
     */
    PC((byte) 1, "PC"),

    /**
     * iPhone
     */
    iOS_Phone((byte) 2, "iPhone"),

    /**
     * iPad
     */
    iOS_Pad((byte) 3, "iPad"),

    /**
     * Android Phone
     */
    Android_Phone((byte) 2, "Android Phone"),

    /**
     * Android Pad
     */
    Android_Pad((byte) 5, "Android Pad"),

    /**
     * BS
     */
    BS((byte) 6, "BS"),

    /**
     * CS
     */
    CS((byte) 7, "CS");

    private byte mVale;
    private String mName;

    private DeviceType(byte value, String name) {
        mVale = value;
        mName = name;
    }

    /**
     * 获取设备类型的值。
     *
     * @return
     */
    public byte value() {
        return mVale;
    }

    /**
     * 获取设备类型的名称。
     *
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * 将整型值转换为 {@link DeviceType} 类型。
     *
     * @param value
     * @return
     */
    public static DeviceType valueOf(byte value) {
        switch (value) {
            case 1:
                return PC;
            case 2:
                return iOS_Phone;
            case 3:
                return iOS_Pad;
            case 4:
                return Android_Phone;
            case 5:
                return Android_Pad;
            case 6:
                return BS;
            case 7:
                return CS;
            default:
                return None;
        }
    }
}