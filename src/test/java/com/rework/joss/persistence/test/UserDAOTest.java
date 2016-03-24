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
import com.rework.joss.persistence.dao.UserDAO;
import com.rework.joss.persistence.entity.UserDTO;

/** 
 * <p>Description: TODO</p>
 * @author zhangyang
 * <p>日期:2015年6月25日 上午9:26:31</p> 
 * @version V1.0 
 */
public class UserDAOTest extends BaseSpringTestCase{

	@Autowired
	private UserDAO userDAO;

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#create(java.lang.Object)}.
	 */
	@Test
	@Rollback(false)
	public void testCreate() {
		UserDTO user = new UserDTO();
		user.setUserName("huttol");
		userDAO.create(user);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#update(java.lang.Object)}.
	 */
	@Test
	@Rollback(false)
	public void testUpdate() {
		UserDTO user = new UserDTO();
		user.setUserId("200cb94c99e64b21aed5e10b94f09eeb");
		user.setUserName("newHotl");
		int update = userDAO.update(user);
		 Assert.assertEquals(1, update);
		
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#remove(java.lang.Object)}.
	 */
	@Test
	public void testRemove() {
		UserDTO user = new UserDTO();
		user.setUserName("newHotl");
		int remove = userDAO.remove(user);
		Assert.assertEquals(1, remove);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#count(java.lang.Object)}.
	 */
	@Test
	public void testCount() {
		int count = userDAO.count(null);
		Assert.assertEquals(1, count);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#createOfBatch(java.util.List)}.
	 */
	@Test
	@Rollback(false)
	public void testCreateOfBatch() {
		UserDTO user = new UserDTO();
		user.setUserName("list1");
		UserDTO user2 = new UserDTO();
		user2.setUserName("list2");
		boolean result =userDAO.createOfBatch(ConventionUtils.toList(user,user2));
		Assert.assertEquals(true, result);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#updateOfBatch(java.util.List)}.
	 */
	@Test
	public void testUpdateOfBatch() {
		UserDTO user = userDAO.findById("9fab0adc74e14621a3d4e97e05eba600");
		user.setUserName("new Res");
		userDAO.updateOfBatch(ConventionUtils.toList(user));
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#removeOfBacth(java.util.List)}.
	 */
	@Test
	public void testRemoveOfBacth() {
		UserDTO user2 = new UserDTO();
		user2.setUserName("list2");
		int remove =userDAO.removeOfBacth(ConventionUtils.toList(user2));
		Assert.assertEquals(1, remove);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryByObject(java.lang.Object)}.
	 */
	@Test
	public void testQueryByObject() {
		UserDTO user = new UserDTO();
		user.setUserName("list1");
		List<UserDTO> list  = userDAO.queryByObject(user);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryByList(java.util.List)}.
	 */
	@Test
	public void testQueryByList() {
		UserDTO user = new UserDTO();
		user.setUserName("list2");
		List<UserDTO> list  = userDAO.queryByList(ConventionUtils.toList(user));
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryListByCriteria(java.lang.Object, java.lang.String, java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testQueryListByCriteria() {
		List<UserDTO> list  = userDAO.queryListByCriteria(null, "user_id desc", 0, 8);
		Assert.assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#queryListByBatchCriteria(java.util.List, java.lang.String, java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testQueryListByBatchCriteria() {
		List<UserDTO> list  = userDAO.queryListByCriteria(null, "user_id desc", 0, 2);
		Assert.assertEquals(2, list.size());
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#findById(java.io.Serializable)}.
	 */
	@Test
	public void testFindById() {
		UserDTO user =userDAO.findById("200cb94c99e64b21aed5e10b94f09eeb");
		Assert.assertNotEquals(null, user);
	}

	/**
	 * Test method for {@link com.rework.joss.persistence.IBaseDao#removeById(java.io.Serializable)}.
	 */
	@Test
	public void testRemoveById() {
		boolean t = userDAO.removeById("200cb94c99e64b21aed5e10b94f09eeb");
		Assert.assertEquals(true, t);
	}
	
	/** 
	 * <p>Description: TODO</p>  void
	 * @throws 
	 */
	@Test
	public void testLikeName() {
		// TODO Auto-generated method stub
		List<UserDTO> list =userDAO.querylikeName("newHot");
		Assert.assertNotEquals(0, list.size());
	}
	
	@Test
	public void testSqlSession(){
	}

}
