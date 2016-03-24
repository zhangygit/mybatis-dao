package com.rework.joss.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.rework.joss.persistence.convention.BaseDao;
import com.rework.joss.persistence.dao.UserInfoDAO;
import com.rework.joss.persistence.entity.UserInfoDTO;
/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年6月25日 上午9:13:46</p> 
 * @version V1.0 
 */
@Repository
public class UserInfoDAOImpl extends BaseDao<UserInfoDTO, Integer> implements UserInfoDAO {

}
