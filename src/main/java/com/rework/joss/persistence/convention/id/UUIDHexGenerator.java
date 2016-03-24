package com.rework.joss.persistence.convention.id;

import java.util.UUID;
/**
 * 
* <p>Description: UUID生成工具</p>
* @author zhangyang
* <p>日期:2015年6月25日 上午11:30:19</p> 
* @version V1.0
 */
public class UUIDHexGenerator {

	public static String get() {
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	public static void main(String[] args) {
		System.out.print(UUIDHexGenerator.get());
	}
}
