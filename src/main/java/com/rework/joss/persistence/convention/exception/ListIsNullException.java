package com.rework.joss.persistence.convention.exception;

import com.rework.joss.persistence.convention.constant.ExceptionConstant;


/** 
 * <p>Description: List参数为空 异常</p> 
 * @author zhangyang
 * <p>日期:2015年4月8日 下午5:39:16</p> 
 * @version V1.0 
 */
public class ListIsNullException extends RuntimeException {
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
    public ListIsNullException(){
        super(ExceptionConstant.ListIsNullMsg);
    }

    public ListIsNullException(String msg){
        super(msg);
    }

    public ListIsNullException(Throwable cause){
        super(cause);
    }

    public ListIsNullException(String msg, Throwable cause){
        super(msg, cause);
    }
}
