package com.hmily.common.util;

import android.util.Base64;

import com.hmily.common.app.Consts;
import com.hmily.common.log.Logger;

import java.io.UnsupportedEncodingException;

/**
 * 简单加密工具。
 * 
 * @author hehui
 * 
 */
public class SimpleEncriptUtil {
	private static final String TAG = "SimpleEncriptUtil";

	/**
	 * 将用户密码进行简单加密。
	 * 
	 * @param pwd
	 * @return
	 */
	public static String encrypt(String pwd) {
		if (Util.isNullOrEmpty(pwd)) {
			return pwd;
		}

		byte[] uuidBytes = Util.getUuidAsByteArray();
		byte[] pwdBytes = null;
		try {
			pwdBytes = pwd.getBytes(Consts.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			Logger.e(TAG, "Failed to encoding pwd: " + pwd, e);
			return pwd;
		}

		int uuidLen = uuidBytes.length;
		int pwdLen = pwdBytes.length;
		byte[] allBytes = new byte[uuidLen + pwdLen];
		System.arraycopy(uuidBytes, 0, allBytes, 0, uuidLen);
		System.arraycopy(pwdBytes, 0, allBytes, uuidLen, pwdLen);

		return Base64.encodeToString(allBytes, Base64.DEFAULT);
	}

	/**
	 * 解密字符串，得到用户密码。
	 * 
	 * @param pwdDecryptString
	 * @return
	 */
	public static String decrypt(String pwdDecryptString) {
		if (Util.isNullOrEmpty(pwdDecryptString)) {
			return pwdDecryptString;
		}

		byte[] allBytes = Base64.decode(pwdDecryptString, Base64.DEFAULT);
		int len = allBytes.length;
		if (len <= 16) {
			throw new IllegalArgumentException("pwdDecryptString is invalid: " + pwdDecryptString);
		}

		byte[] pwdBytes = new byte[len - 16];
		System.arraycopy(allBytes, 16, pwdBytes, 0, len - 16);
		String pwd = null;
		try {
			pwd = new String(pwdBytes, Consts.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("pwdDecryptString is invalid: " + pwdDecryptString,
					e);
		}
		return pwd;
	}
}
