package com.rework.joss.persistence.entity;

import java.io.Serializable;

import com.rework.joss.persistence.convention.annotation.GeneratedValue;
import com.rework.joss.persistence.convention.annotation.Id;
import com.rework.joss.persistence.convention.annotation.Table;
import com.rework.joss.persistence.convention.annotation.Transient;
import com.rework.joss.persistence.convention.annotation.Column;

/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年6月15日 上午11:31:48</p> 
 * @version V1.0 
 */
@Table(name="userinfo")
public class UserInfoDTO implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	@Transient
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(generator = "JDBC")
	@Id
	private Integer id;
	
	private String userId;
	
	private String phone;
	
	@Column(name="e_mail")
	private String email;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	
	
	

}
