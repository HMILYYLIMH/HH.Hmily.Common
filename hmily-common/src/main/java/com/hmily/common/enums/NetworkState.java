package com.hmily.common.enums;

/**
 * 网络状态。
 * 
 * @author hehui
 *
 */
public enum NetworkState {
	/** 未知网络 */
	NETWORK_UNKNOWN((byte) 0, "Unknown"),

	/** 无网络 */
	NETWORK_NO((byte) 1, "No"),

	/** 2G网络 */
	NETWORK_2G((byte) 2, "2G"),

	/** 3G网络 */
	NETWORK_3G((byte) 3, "3G"),

	/** 4G网络 */
	NETWORK_4G((byte) 4, "4G"),

	/** Wifi */
	NETWORK_WIFI((byte) 5, "Wifi");

	private byte mVale;
	private String mName;

	private NetworkState(byte value, String name) {
		mVale = value;
		mName = name;
	}

	/**
	 * 获取网络状态的值。
	 * 
	 * @return
	 */
	public byte value() {
		return mVale;
	}

	/**
	 * 获取网络状态的名称。
	 * 
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * 将整型值转换为 {@link NetworkState} 类型。
	 * 
	 * @param value
	 * @return
	 */
	public static NetworkState valueOf(byte value) {
		switch (value) {
		case 1:
			return NETWORK_NO;
		case 2:
			return NETWORK_2G;
		case 3:
			return NETWORK_3G;
		case 4:
			return NETWORK_4G;
		case 5:
			return NETWORK_WIFI;
		default:
			return NETWORK_UNKNOWN;
		}
	}
}
