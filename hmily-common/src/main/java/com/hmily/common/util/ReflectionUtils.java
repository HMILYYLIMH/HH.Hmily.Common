package com.hmily.common.util;

import com.hmily.common.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类。
 * 
 * @author hehui
 * 
 */
public class ReflectionUtils {

	private static final String TAG = "ReflectionUtils";

	/**
	 * 循环向上转型, 获取对象的 DeclaredMethod。
	 * 
	 * @param subClassObject
	 *            : 子类对象。
	 * @param superClassMethodName
	 *            : 父类中的方法名。
	 * @param superParameterTypes
	 *            : 父类中的方法参数类型。
	 * @return 父类中的方法对象。
	 */
	public static Method getDeclaredMethod(Object subClassObject, String superClassMethodName,
										   Class<?>... superParameterTypes) {
		Method method = null;

		for (Class<?> clazz = subClassObject.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(superClassMethodName, superParameterTypes);
				return method;
			} catch (Exception e) {
				Logger.w(TAG, "Failed to get declared method, SuperClassMethodName: " + superClassMethodName, e);
			}
		}

		return null;
	}

	/**
	 * 直接调用父类对象方法, 而忽略修饰符(private, protected, default)。
	 * 
	 * @param subClassObject
	 *            : 子类对象。
	 * @param superClassMethodName
	 *            : 父类中的方法名。
	 * @param superClassParameterTypes
	 *            : 父类中的方法参数类型。
	 * @param superClassParameters
	 *            : 父类中的方法参数。
	 * @return 父类中方法的执行结果。
	 */
	public static Object invokeMethod(Object subClassObject, String superClassMethodName,
									  Class<?>[] superClassParameterTypes, Object[] superClassParameters) {
		// 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
		Method method = getDeclaredMethod(subClassObject, superClassMethodName, superClassParameterTypes);

		// 抑制Java对方法进行检查,主要是针对私有方法而言
		method.setAccessible(true);

		try {
			if (null != method) {
				// 调用object 的 method 所代表的方法，其方法的参数是 parameters
				return method.invoke(subClassObject, superClassParameters);
			}
		} catch (Exception e) {
			Logger.w(TAG, "Failed to invoke method, SuperClassMethodName: " + superClassMethodName, e);
		}

		return null;
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField。
	 * 
	 * @param subClassObject
	 *            : 子类对象。
	 * @param superClassFieldName
	 *            : 父类中的属性名。
	 * @return 父类中的属性对象。
	 */
	public static Field getDeclaredField(Object subClassObject, String superClassFieldName) {
		Field field = null;

		Class<?> clazz = subClassObject.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(superClassFieldName);
				return field;
			} catch (Exception e) {
				Logger.w(TAG, "Failed to get declared field, SuperClassFieldName: " + superClassFieldName, e);
			}
		}

		return null;
	}

	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter。
	 * 
	 * @param subClassObject
	 *            : 子类对象。
	 * @param superClassFieldName
	 *            : 父类中的属性名。
	 * @param value
	 *            : 将要设置的值。
	 */
	public static void setFieldValue(Object subClassObject, String superClassFieldName, Object value) {
		// 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(subClassObject, superClassFieldName);

		// 抑制Java对其的检查
		field.setAccessible(true);

		try {
			// 将 object 中 field 所代表的值 设置为 value
			field.set(subClassObject, value);
		} catch (Exception e) {
			Logger.w(TAG, "Failed to set field value, SuperClassFieldName: " + superClassFieldName, e);
		}
	}

	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter。
	 * 
	 * @param subClassObject
	 *            : 子类对象。
	 * @param superClassFieldName
	 *            : 父类中的属性名。
	 * @return : 父类中的属性值。
	 */
	public static Object getFieldValue(Object subClassObject, String superClassFieldName) {
		// 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(subClassObject, superClassFieldName);

		// 抑制Java对其的检查
		field.setAccessible(true);

		try {
			// 获取 object 中 field 所代表的属性值
			return field.get(subClassObject);
		} catch (Exception e) {
			Logger.w(TAG, "Failed to get field value, SuperClassFieldName: " + superClassFieldName, e);
		}

		return null;
	}
}
