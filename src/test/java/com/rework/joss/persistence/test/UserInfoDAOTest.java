package com.rework.joss.persistence.test;

import static org.junit.Assert.*;

import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.rework.joss.persistence.base.BaseSpringTestCase;
import com.rework.joss.persistence.convention.utils.ConventionUtils;
import com.rework.joss.persistence.dao.UserInfoDAO;
import com.rework.joss.persistence.entity.UserInfoDTO;

/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年6月25日 上午9:27:04</p> 
 * @version V1.0 
 */
public class UserInfoDAOTest extends BaseSpringTestCase{

	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private final String email = "wangli@163.com";
	
	private final String userId= "200cb94c99e64b21aed5e10b94f09eeb";

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#create(java.lang.Object)}.
	 */
	@Test
	@Rollback(false)
	public void testCreate() {
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail(email);
		user.setUserId(userId);
		user.setPhone("5454");
		System.err.println(userInfoDAO.create(user));
		
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("newupdate@");
		user.setUserId(userId);
		user.setId(34);
		int update = userInfoDAO.update(user);
		Assert.assertEquals(1, update);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#remove(java.lang.Object)}.
	 */
	@Test
	public void testRemove() {
		UserInfoDTO user = new UserInfoDTO();
		user.setUserId(userId);
		user.setId(34);
		int remove =userInfoDAO.remove(user);
		Assert.assertEquals(1, remove);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#count(java.lang.Object)}.
	 */
	@Test
	public void testCount() {
		int count =userInfoDAO.count(null);
		Assert.assertEquals(8, count);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#createOfBatch(java.util.List)}.
	 */
	@Test
	@Rollback(false)
	public void testCreateOfBatch() {
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("list1");
		user.setUserId("42e4e3ca6d874d8993f6ea190bbcaf66");
		UserInfoDTO user1 = new UserInfoDTO();
		user1.setEmail("list1");
		user1.setUserId("42e4e3ca6d874d8993f6ea190bbcaf66");
		boolean temp= userInfoDAO.createOfBatch(ConventionUtils.toList(user,user1));
		
		Assert.assertEquals(true, temp);
		
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#updateOfBatch(java.util.List)}.
	 */
	@Test
	@Rollback(false)
	public void testUpdateOfBatch() {
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("list1");
		List<UserInfoDTO> list = userInfoDAO.queryByObject(user);
		for(UserInfoDTO userInfo:list){
			userInfo.setPhone("1869658556");
		}
		int update =userInfoDAO.updateOfBatch(list);
		Assert.assertEquals(4, update);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#removeOfBacth(java.util.List)}.
	 */
	@Test
	public void testRemoveOfBacth() {
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("list1");
		int remove =userInfoDAO.removeOfBacth(ConventionUtils.toList(user));
		Assert.assertEquals(1, remove);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryByObject(java.lang.Object)}.
	 */
	@Test
	public void testQueryByObject() {
		UserInfoDTO user  = new UserInfoDTO();
		user.setId(35);
		List<UserInfoDTO> list = userInfoDAO.queryByObject(user);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryByList(java.util.List)}.
	 */
	@Test
	public void testQueryByList() {
		UserInfoDTO user  = new UserInfoDTO();
		user.setId(35);
		List<UserInfoDTO> list = userInfoDAO.queryByList(ConventionUtils.toList(user));
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryListByCriteria(java.lang.Object, java.lang.String, java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testQueryListByCriteria() {
		UserInfoDTO user  = new UserInfoDTO();
		user.setUserId("200cb94c99e64b21aed5e10b94f09eeb");
		List<UserInfoDTO> list = userInfoDAO.queryListByCriteria(user, "phone desc", 0, 2);
		Assert.assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryListByBatchCriteria(java.util.List, java.lang.String, java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testQueryListByBatchCriteria() {
		UserInfoDTO user  = new UserInfoDTO();
		user.setUserId("200cb94c99e64b21aed5e10b94f09eeb");
		List<UserInfoDTO> list = userInfoDAO.queryListByBatchCriteria(ConventionUtils.toList(user), "phone desc", 0, 2);
		Assert.assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#findById(java.io.Serializable)}.
	 */
	@Test
	public void testFindById() {
		UserInfoDTO user = userInfoDAO.findById(36);
		Assert.assertNotEquals(null, user);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#removeById(java.io.Serializable)}.
	 */
	@Test
	public void testRemoveById() {
		 boolean ti = userInfoDAO.removeById(36);
		 Assert.assertNotEquals(true, ti);
	}

}
