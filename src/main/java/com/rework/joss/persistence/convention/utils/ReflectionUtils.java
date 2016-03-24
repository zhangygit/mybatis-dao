package com.rework.joss.persistence.convention.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.beanutils.BeanUtils;

/** 
 * <p>Description: 反射工具类</p>
 * @author zhangyang
 * <p>日期:2015年6月16日 下午5:16:31</p> 
 * @version V1.0 
 */
public class ReflectionUtils {
	
	
	/**
	 * 获得参数化类型的泛型类型，取第一个参数的泛型类型，（默认去的第一个）
	 * @param clazz 参数化类型
	 * @return 泛型类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 根据参数索引获得参数化类型的泛型类型，（通过索引取得）
	 * @param clazz 参数化类型
	 * @param index 参数索引
	 * @return 泛型类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClassGenricType(
			final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
	
	/**
	 * 反射 取值、设值,合并两个对象(Field same only )
	 * 
	 * @param from
	 * @param to
	 */
	public static <T> void copyProperties(T fromobj, T toobj) {
		try {
			BeanUtils.copyProperties(fromobj, toobj);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 调用Getter方法
	 * 
	 * @param obj
	 *            对象
	 * @param propertyName
	 *            属性名
	 * @return
	 */
	public static String invokeGetterMethod(Object obj, String propertyName) {
		try {
			return BeanUtils.getProperty(obj, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 调用Setter方法,不指定参数的类型
	 * 
	 * @param obj
	 * @param propertyName
	 * @param value
	 */
	public static void invokeSetterMethod(Object obj, String propertyName,
			Object value) {
		try {
			BeanUtils.setProperty(obj, propertyName, value);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
