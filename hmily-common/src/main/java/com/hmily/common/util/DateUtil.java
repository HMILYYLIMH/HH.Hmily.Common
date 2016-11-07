package com.hmily.common.util;
import com.hmily.common.log.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期、时间工具类。
 * 
 * @author hehui
 * 
 */
public class DateUtil {
	private static String TAG = DateUtil.class.getName();

	/**
	 * 默认时间格式。
	 */
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将字符串转换为 {@link java.sql.Timestamp} 类型，且字符串格式必须满足
	 * "yyyy-MM-dd HH:mm:ss.SSSSSSSSS"，其中纳秒位数可不固定。
	 *
	 * @param str
	 *            待转换的字符串。
	 * @return 如转换失败则返回 {@code null}。
	 */
	public static final Timestamp parseTimestamp(String str) {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		try {
			return Timestamp.valueOf(str);
		} catch (IllegalArgumentException e) {
			Logger.e(TAG, "Failed to parseTimestamp! str:" + str, e);
			return null;
		}
	}

	/**
	 * 使用默认格式将 {@link java.sql.Timestamp} 对象转换为字符串。
	 *
	 * @param time
	 *            待转换时间对象。
	 * @return 如果 {@code date} 为 {@code null} 则返回空字符串。
	 */
	public static final String formatTimestamp(Timestamp time) {
		return time == null ? "" : new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(time);
	}

	/**
	 * 使用指定格式将 {@link java.sql.Timestamp} 对象转换为字符串。
	 *
	 * @param time
	 *            待转换时间对象。
	 * @param format
	 *            如果为 {@code null} 则使用默认格式。
	 * @return 如果 {@code date} 为 {@code null} 则返回空字符串。
	 */
	public static final String formatTimestamp(Timestamp time, SimpleDateFormat format) {
		if (time == null) {
			return "";
		}

		if (format == null) {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(time);
		}

		Pattern pattern = Pattern.compile("S+");
		String formatString = format.toPattern();
		Matcher matcher = pattern.matcher(formatString);
		if (matcher.find()) {
			String theNanoFormat = matcher.group();
			String theNanoString = (time.getNanos() + "000000000").substring(0,
					theNanoFormat.length());

			return new SimpleDateFormat(formatString.replace(theNanoFormat, "")).format(time)
					+ theNanoString;
		}
		return format.format(time);
	}

	/**
	 * 使用默认的格式将字符串转换为 {@link java.util.Date} 类型。
	 *
	 * @param str
	 *            待转换的字符串。
	 * @return 如转换失败则返回 {@code null}。
	 */
	public static final Date parseDate(String str) {
		return parseDate(str, new SimpleDateFormat(DEFAULT_DATE_FORMAT));
	}

	/**
	 * 使用指定的格式将字符串转换为 {@link java.util.Date} 类型。
	 *
	 * @param str
	 *            待转换的字符串。
	 * @param format
	 *            如果为 {@code null} 则使用默认格式。
	 * @return 如转换失败则返回 {@code null}。
	 */
	public static final Date parseDate(String str, SimpleDateFormat format) {
		try {
			return Util.isNullOrEmpty(str) ? null : (format == null ? new SimpleDateFormat(
					DEFAULT_DATE_FORMAT).parse(str) : format.parse(str));
		} catch (ParseException e) {
			Logger.e(TAG, "Failed to parseDate! str:" + str, e);
			return null;
		}
	}

	/**
	 * 使用默认格式将 {@link java.util.Date} 对象转换为字符串。
	 *
	 * @param date
	 *            待转换时间对象。
	 * @return 如果 {@code date} 为 {@code null} 则返回空字符串。
	 */
	public static final String formatDate(Date date) {
		return date == null ? "" : new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
	}

	/**
	 * 使用指定格式将 {@link java.util.Date} 对象转换为字符串。
	 * 
	 * @param date
	 *            待转换时间对象。
	 * @param format
	 *            如果为 {@code null} 则使用默认格式。
	 * @return 如果 {@code date} 为 {@code null} 则返回空字符串。
	 */
	public static final String formatDate(Date date, SimpleDateFormat format) {
		return date == null ? "" : (format == null ? new SimpleDateFormat(DEFAULT_DATE_FORMAT)
				.format(date) : format.format(date));
	}
}
