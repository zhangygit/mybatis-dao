package com.rework.joss.persistence.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rework.joss.persistence.entity.UserDTO;
import com.rework.joss.persistence.mapper.UserMapper;
import com.rework.joss.persistence.convention.BaseDao;
/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年6月25日 上午9:13:02</p> 
 * @version V1.0 
 */
@Repository
public class UserDAO extends BaseDao<UserDTO,String>{
	@Autowired
	private UserMapper userMapper;
	
	/**
	* <p>Description:TODO </p> 
	* @param name
	* @return
	*/
	public List<UserDTO> querylikeName(String name) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getMapperMethodId("querylikeName"), name);
		//return userMapper.querylikeName(name);
	}
}
