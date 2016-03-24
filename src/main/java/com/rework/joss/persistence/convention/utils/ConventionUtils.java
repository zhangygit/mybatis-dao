package com.rework.joss.persistence.convention.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * <p>Description: 转换工具类</p>
 * @author zhangyang
 * <p>日期:2015年6月8日 上午11:19:14</p> 
 * @version V1.0 
 */
public class ConventionUtils {
	
	/**
	 * 
	* <p>Description: map转换工具</p> 
	* @param objs
	* @return Map
	* @throws
	 */
	public static Map toMap(Object... objs) {
		HashMap map = new HashMap();
		Object key = null;
		for (int i = 0; i < objs.length; i++) {
			if(i%2 == 0){
				key = objs[i];
			}else{
				map.put(key, objs[i]);
			}
		}
		return map;
	}
	
	/**
	 * 
	* <p>Description: list转换工具</p> 
	* @param objs
	* @return Map
	* @throws
	 */
	public static List toList(Object... objs) {
		List result = new ArrayList();
		for (int i = 0; i < objs.length; i++) {
			result.add(objs[i]);
		}
		return result;
	}
}
