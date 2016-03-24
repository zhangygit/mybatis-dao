package com.rework.joss.persistence.convention.exception;

import com.rework.joss.persistence.convention.constant.ExceptionConstant;

/** 
 * <p>Description: 实体类主键不存在异常</p> 
 * @author zhangyang
 * <p>日期:2015年4月8日 上午11:10:14</p> 
 * @version V1.0 
 */
public class PrimaryKeyNotFoundException extends RuntimeException {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
    public PrimaryKeyNotFoundException(){
        super(ExceptionConstant.PrimaryKeyNotFoundMsg);
    }

    public PrimaryKeyNotFoundException(String msg){
        super(msg);
    }

    public PrimaryKeyNotFoundException(Throwable cause){
        super(cause);
    }

    public PrimaryKeyNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }

}
