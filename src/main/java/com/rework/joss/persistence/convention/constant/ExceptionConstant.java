package com.rework.joss.persistence.convention.constant;



/** 
 * <p>Description: 定义常量集</p> 
 * @author zhangyang
 * <p>日期:2015年4月8日 上午11:15:48</p> 
 * @version V1.0 
 */
public abstract class  ExceptionConstant {
	
	//log日志输出信息
	public final static String LOGGER_INSERT_NOT_ALL_ERROR="实体类初始化过程信息不完整，操作失败";
	
	public final static String LOGGER_UPDATE_OBJECT_ISNULL="参数实体类为空，操作失败";
	public final static String LOGGER_UPDATE_KEY_NOT_EXIST="主键不存在，操作失败";
	
	public final static String LOGGER_DEL_IS_NULL_ERROR="参数实体类为空，操作失败";
	
	public final static String LOGGER_LIST_IS_NULL_ERROR="参数List为空，操作失败";
	
	//异常默认输出信息
	public final static String PrimaryKeyNotFoundMsg="主键不存在";
	
	public final static String ListIsNullMsg="List 不可为空";
	
	
	public final static String IdTypeNoConf="主键未配置生成策略";
	
	
}
