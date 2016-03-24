package com.rework.joss.persistence;

import java.io.Serializable;
import java.util.List;

/** 
 * <p>Description: baseDao接口</p>
 * @param T 实体类泛型
 * @param Pk 实体类主键类型
 * @author zhangyang
 * <p>日期:2015年6月17日 上午11:24:52</p> 
 * @version V1.0 
 */
public interface IBaseDao <T, PK extends Serializable>{

	
	/**
	 * 插入单条信息
	 * <p>注：插入实体类时，请保证信息完整，否则会异常推出</p> 
	 *
	 * @param object
	 * @return long
	 */
	public abstract  PK create(T object);
	/**
	 * 编辑单条信息
	 *<p>注：编辑时保证主键和更改信息 不为空</p> 
	 * @param object
	 * @return int
	 */
	public abstract  int update(T object);
	
	/**
	 * 删除单条信息
	 *<p>注：object为空 将异常退出，这样做是为了保证 不会在误操作的情况下 删除全表</p> 
	 *
	 * @param object
	 * @return int
	 */
	public abstract  int remove(T object);
	
	/**
	 * 统计信息条数
	 *<p>注：分表是分库统计加和</p> 
	 * @param object
	 * @return int
	 */
	public abstract  int count(T object);
	
	/**
	 * 批量添加多条信息
	 *<p>注：list不能为空，且bean内信息完整</p> 
	 *
	 * @param list
	 * @return boolean
	 */
	public abstract  boolean createOfBatch(List<T> list);
	
	
	
	/**
	 * 批量更新多条信息
	 *<p>注：因为没使用常规update更新，批量更新时请确定bean存在且内信息完整</p> 
	 * @param list
	 * @return int
	 */
	public abstract  int updateOfBatch(List<T> list);
	
	/**
	 * 批量删除多条信息
	 *<p>注：list为空 将异常退出，这样做是为了保证 不会在误操作的情况下 删除全表</p> 
	 * @param list
	 * @return int
	 */
	public abstract  int removeOfBacth(List<T> list);
	
	/**
	 * 获取多条信息
	 *<p>注：T为空时将返回表内所有数据</p> 
	 * @param list
	 * @return list
	 */
	public abstract  List<T> queryByObject(T object);

	/**
	 * 批量获取多条信息
	 *<p>注：T为空时将返回表内所有数据</p> 
	 * @param list
	 * @return list
	 */
	public abstract  List<T> queryByList(List<T> list);
	
	/**
	* <p>Description:带参数的查询 </p> 
	* @param object 实体类
	* @param orderby 排序  username desc
	* @param offset limit
	* @param limit  limit
	* @return
	*/
	public abstract List<T> queryListByCriteria(T object,String orderby,Integer offset,Integer limit);
	
	/**
	* <p>Description:带复杂参数的查询 </p> 
	* @param list  list
	* @param orderby 排序
	* @param offset limit
	* @param limit limit
	* @return
	*/
	public abstract List<T> queryListByBatchCriteria(List<T> list,String orderby,Integer offset,Integer limit);
	
	/**
	 * 根据主键值查找一条记录
	* <p>Description: TODO</p> 
	* @param id
	* @return T
	* @throws
	 */
	public abstract T findById(PK id);
	
	/**
	 * 根据主键删除一条记录
	* <p>Description: TODO</p> 
	* @param id
	* @return boolean
	* @throws
	 */
	public abstract boolean removeById(PK id);
	
	
	
}
