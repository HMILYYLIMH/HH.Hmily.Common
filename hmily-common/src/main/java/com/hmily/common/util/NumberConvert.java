package com.hmily.common.util;

import java.nio.ByteOrder;

/**
 * 数值与字节数组的转换工具类。
 * 
 * @author hehui
 *
 */
public class NumberConvert {
	/**
	 * 将 short 类型值转换为字节数组。
	 * 
	 * @param value
	 *            ：数值。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final byte[] convert(short value, ByteOrder order) {
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return new byte[] { (byte) (value >> 8), (byte) (value & 0x00ff) };
		} else {
			return new byte[] { (byte) (value & 0x00ff), (byte) (value >> 8) };
		}
	}

	/**
	 * 将 int 类型值转换为字节数组。
	 * 
	 * @param number
	 *            ：数值。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final byte[] convert(int number, ByteOrder order) {
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return new byte[] { (byte) (number >> 24), (byte) (number >> 16 & 0xff),
					(byte) (number >> 8 & 0xff), (byte) (number & 0xff) };
		} else {
			return new byte[] { (byte) (number & 0xff), (byte) (number >> 8 & 0xff),
					(byte) (number >> 16 & 0xff), (byte) (number >> 24) };
		}

	}

	/**
	 * 将 long 类型值转换为字节数组。
	 * 
	 * @param number
	 *            ：数值。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final byte[] convert(long number, ByteOrder order) {
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return new byte[] { (byte) (number >> 56), (byte) (number >> 48 & 0xff),
					(byte) (number >> 40 & 0xff), (byte) (number >> 32 & 0xff),
					(byte) (number >> 24 & 0xff), (byte) (number >> 16 & 0xff),
					(byte) (number >> 8 & 0xff), (byte) (number & 0xff) };
		} else {
			return new byte[] { (byte) (number & 0xff), (byte) (number >> 8 & 0xff),
					(byte) (number >> 16 & 0xff), (byte) (number >> 24 & 0xff),
					(byte) (number >> 32 & 0xff), (byte) (number >> 40 & 0xff),
					(byte) (number >> 48 & 0xff), (byte) (number >> 56 & 0xff) };
		}
	}

	/**
	 * 将字节数组转换为 short 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final short convertShort(byte[] array, ByteOrder order) {
		return convertShort(array, 0, order);
	}

	/**
	 * 将字节数组转换为 short 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param start
	 *            ：字节数组的起始位置。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final short convertShort(byte[] array, int start, ByteOrder order) {
		isLegal(array, start, 2);

		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (short) (((array[start] & 0xff) << 8) + (array[start + 1] & 0xff));
		} else {
			return (short) ((array[start] & 0xff) + ((array[start + 1] & 0xff) << 8));
		}
	}

	/**
	 * 将字节数组转换为 int 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final int convertInt(byte[] array, ByteOrder order) {
		return convertInt(array, 0, order);
	}

	/**
	 * 将字节数组转换为 int 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param start
	 *            ：字节数组的起始位置。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final int convertInt(byte[] array, int start, ByteOrder order) {
		isLegal(array, start, 4);

		int value = 0;
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			for (int i = 0; i < 4; i++) {
				value |= (array[start + i] & 0xff) << ((3 - i) * 8);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				value |= (array[start + i] & 0xff) << (i * 8);
			}
		}
		return value;
	}

	/**
	 * 将字节数组转换为 long 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final long convertLong(byte[] array, ByteOrder order) {
		return convertLong(array, 0, order);
	}

	/**
	 * 将字节数组转换为 long 类型值。
	 * 
	 * @param array
	 *            ：字节数组。
	 * @param start
	 *            ：字节数组的起始位置。
	 * @param order
	 *            ：字节序。
	 * @return
	 */
	public static final long convertLong(byte[] array, int start, ByteOrder order) {
		isLegal(array, start, 8);

		long value = 0L;
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			for (int i = 0; i < 8; i++) {
				value |= (long) (array[start + i] & 0xff) << ((7 - i) * 8);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				value |= (long) (array[start + i] & 0xff) << (i * 8);
			}
		}
		return value;
	}

	/**
	 * 判断字节数组是否合法。
	 * 
	 * @param array
	 * @param start
	 * @param length
	 */
	private static final void isLegal(byte[] array, int start, int length) {
		if (start < 0) {
			throw new IllegalArgumentException("start should not be less than 0");
		}

		if (length <= 0) {
			throw new IllegalArgumentException("length should be greater than 0");
		}

		if (array == null || array.length < (start + length)) {
			throw new IllegalArgumentException("the length(" + (array == null ? 0 : array.length)
					+ ") of array should not be less than " + (start + length));
		}
	}
}
