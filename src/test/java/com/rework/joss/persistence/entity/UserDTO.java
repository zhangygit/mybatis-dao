package com.rework.joss.persistence.entity;



import java.io.Serializable;

import com.rework.joss.persistence.convention.annotation.GeneratedValue;
import com.rework.joss.persistence.convention.annotation.Id;
import com.rework.joss.persistence.convention.annotation.Table;
import com.rework.joss.persistence.convention.annotation.Transient;

/**
 * 
* <p>Description: 测试类</p>
* @author zhangyang
* <p>日期:2015年6月25日 上午11:26:16</p> 
* @version V1.0
 */
@Table(name="user")
public class UserDTO implements Serializable{
    /** 
	* @Fields serialVersionUID : TODO
	*/ 
	@Transient
	private static final long serialVersionUID = -9015714985719637211L;
	
	
	/** 用户id */
	@GeneratedValue(generator = "UUID")
	@Id
    private String userId;

    /** 用户姓名 */
    private String userName;

    	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    
}