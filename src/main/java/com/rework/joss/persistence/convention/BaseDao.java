package com.rework.joss.persistence.convention;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.jrockit.jfr.UseConstantPool;
import com.rework.joss.persistence.IBaseDao;
import com.rework.joss.persistence.convention.constant.OperateConstant;
import com.rework.joss.persistence.convention.db.SqlGenerator;
import com.rework.joss.persistence.convention.exception.ListIsNullException;
import com.rework.joss.persistence.convention.exception.PrimaryKeyNotFoundException;
import com.rework.joss.persistence.convention.id.UUIDHexGenerator;
import com.rework.joss.persistence.convention.utils.EntityUtil;
import com.rework.joss.persistence.convention.utils.EntityUtil.EntityColumn;
import com.rework.joss.persistence.convention.utils.ReflectionUtils;
import com.rework.joss.persistence.convention.utils.EntityUtil.EntityTable;

/** 
 * <p>Description: baseDao接口实现</p>
 * @param T 实体类泛型
 * @param Pk 实体类主键类型
 * @author zhangyang
 * <p>日期:2015年6月17日 上午10:27:13</p> 
 * @version V1.0 
 */
public class BaseDao <T, PK extends Serializable> implements IBaseDao<T, PK> {

	@Autowired
	private SqlMapper sqlMapper;
	
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;
	
	
	
	
	protected Logger logger	= LoggerFactory.getLogger(this.getClass());
	
	//实体类类型
	private Class<T> entityClass;
	
	//实体类对应表配置信息
	private final EntityTable entityTable;
	
	//主键生成策略
	private final String seq;
	
	//对应实体类主键名称
	private final String idName;
	
	//正在使用的dao类
	private final Class<?> dao;
	
	//实体类对应主键的信息
	private final EntityColumn entityClassPKColumn;
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public BaseDao() {
		super();
		
		this.entityClass = (Class<T>) ReflectionUtils.getClassGenricType(this.getClass());
		this.entityTable =EntityUtil.getEntityTable(this.entityClass);
		this.seq = EntityUtil.getSeqPkType(this.entityClass);
		this.idName = EntityUtil.getIdName(this.entityClass);
		if(this.getClass().getInterfaces().length !=0){
			this.dao = this.getClass().getInterfaces()[0];
		}else {
			this.dao = this.getClass();
		}
		this.entityClassPKColumn = entityTable.getEntityClassPKColumn();
		
		if(logger.isDebugEnabled()){
			logger.debug("the "+this.dao.getSimpleName()+" has been initialized " );
		}
		
	}
	
	
	private String getMsId(String operate){
		return this.dao.getName()+OperateConstant.SQLNAME_SEPARATOR+operate;
	}
	
	/**
	 * 
	* @Title. getMapperMethodId
	* @Description. 获取对应的xml对应的Id值
	* @param id
	* @return String
	* @exception.
	 */
	protected String getMapperMethodId(String id) {
		StringBuffer xmlId = new StringBuffer();
		xmlId.append(this.dao.getName()).append(OperateConstant.SQLNAME_SEPARATOR)
			.append(id);
		return xmlId.toString();
	}
	
	/**
	* <p>Description:TODO </p> 
	* @param object
	*/
	@SuppressWarnings("unchecked")
	@Override
	public PK create(T object) {
		
		if(OperateConstant.SEQ_TYPE_UUID.equals(seq) ){
			String value = ReflectionUtils.invokeGetterMethod(object, idName);
			if(value == null || "".equals(value)){
				ReflectionUtils.invokeSetterMethod(object, idName, UUIDHexGenerator.get());
			}
			sqlMapper.insert( SqlGenerator.operateSqlByType(OperateConstant.SQLID_INSERT, entityTable), object,getMsId(OperateConstant.SQLID_INSERT));
		}else if(OperateConstant.SEQ_TYPE_JDBC.equals(seq)){
			sqlMapper.insertSelectKey( SqlGenerator.operateSqlByType(OperateConstant.SQLID_INSERT, entityTable), object,getMsId(OperateConstant.SQLID_INSERT),entityClassPKColumn);
		}else if(seq==null){
			String value = ReflectionUtils.invokeGetterMethod(object, idName);
			if(value == null || "".equals(value)){
				throw new PrimaryKeyNotFoundException("主键为空，插入失败");
			}
			sqlMapper.insert( SqlGenerator.operateSqlByType(OperateConstant.SQLID_INSERT, entityTable), object,getMsId(OperateConstant.SQLID_INSERT));
		}
		
		
		
		return (PK) ReflectionUtils.invokeGetterMethod(object, idName);
	}

	/**
	* <p>Description:TODO </p> 
	* @param object
	* @return
	*/
	@Override
	public int update(T object) {
		// TODO Auto-generated method stub
		String value = ReflectionUtils.invokeGetterMethod(object, idName);
		if(value == null || "".equals(value)){
			throw new PrimaryKeyNotFoundException("主键为空，更新失败");
		}
		return sqlMapper.update( SqlGenerator.operateSqlByType(OperateConstant.SQLID_UPDATE, entityTable), object,getMsId(OperateConstant.SQLID_UPDATE));
	}

	/**
	* <p>Description:TODO </p> 
	* @param object
	* @return
	*/
	@Override
	public int remove(T object) {
		// TODO Auto-generated method stub
		return sqlMapper.update( SqlGenerator.operateSqlByType(OperateConstant.SQLID_DELETE, entityTable), object,getMsId(OperateConstant.SQLID_DELETE));
	}

	/**
	* <p>Description:TODO </p> 
	* @param object
	* @return
	*/
	@Override
	public int count(T object) {
		// TODO Auto-generated method stub
		return sqlMapper.selectOne( SqlGenerator.operateSqlByType(OperateConstant.SQLID_COUNT_BATCH, entityTable), object,Integer.class,getMsId(OperateConstant.SQLID_COUNT_BATCH));
	}

	/**
	* <p>Description:TODO </p> 
	* @param list
	*/
	@Override
	public boolean createOfBatch(List<T> list) {
		// TODO Auto-generated method stub
		if(list == null || list.size() ==0){
			throw new ListIsNullException("参数为空，插入失败");
		}
		if(OperateConstant.SEQ_TYPE_UUID.equals(seq)){
			if(list !=null && list.size() !=0){
				for(T object:list){
					String value = ReflectionUtils.invokeGetterMethod(object, idName);
					if(value == null || "".equals(value)){
						ReflectionUtils.invokeSetterMethod(object, idName, UUIDHexGenerator.get());
					}
				}
			}
		}
		int count = sqlMapper.insert( SqlGenerator.operateSqlByType(OperateConstant.SQLID_INSERT_BATCH, entityTable), list,getMsId(OperateConstant.SQLID_INSERT_BATCH));
		if(count >0){
			return true;
		}
		return false;
	}

	/**
	* <p>Description:TODO </p> 
	* @param list
	* @return
	*/
	@Override
	public int updateOfBatch(List<T> list) {
		// TODO Auto-generated method stub
		if(list == null || list.size() ==0){
			throw new ListIsNullException("参数为空，更新失败");
		}
		return sqlMapper.update( SqlGenerator.operateSqlByType(OperateConstant.SQLID_UPDATE_BATCH, entityTable), list,getMsId(OperateConstant.SQLID_UPDATE_BATCH))/2;
	}

	/**
	* <p>Description:TODO </p> 
	* @param list
	* @return
	*/
	@Override
	public int removeOfBacth(List<T> list) {
		// TODO Auto-generated method stub
		return sqlMapper.update( SqlGenerator.operateSqlByType(OperateConstant.SQLID_DELETE_BATCH, entityTable), list,getMsId(OperateConstant.SQLID_DELETE_BATCH));
	}

	/**
	* <p>Description:TODO </p> 
	* @param object
	* @return
	*/
	@Override
	public List<T> queryByObject(T object) {
		// TODO Auto-generated method stub
		Map<String,Object>  criteriaMap =new HashMap<String,Object>();
		criteriaMap.put("object", object);
		return sqlMapper.selectList(SqlGenerator.operateSqlByType(OperateConstant.SQLID_SELECT, entityTable), criteriaMap, this.entityClass,getMsId(OperateConstant.SQLID_SELECT));
	}

	/**
	* <p>Description:TODO </p> 
	* @param list
	* @return
	*/
	@Override
	public List<T> queryByList(List<T> list) {
		// TODO Auto-generated method stub
		Map<String,Object>  criteriaMap =new HashMap<String,Object>();
		criteriaMap.put("list", list);
		return sqlMapper.selectList(SqlGenerator.operateSqlByType(OperateConstant.SQLID_SELECT_BATCH, entityTable), criteriaMap, this.entityClass,getMsId(OperateConstant.SQLID_SELECT_BATCH));
	}

	/**
	* <p>Description:TODO </p> 
	* @param object
	* @param orderby
	* @param offset
	* @param limit
	* @return
	*/
	@Override
	public List<T> queryListByCriteria(T object, String orderby,Integer offset, Integer limit) {
		Map<String,Object>  criteriaMap =new HashMap<String,Object>();
		criteriaMap.put("object", object);
		criteriaMap.put("orderby", orderby);
		criteriaMap.put("offset", offset);
		criteriaMap.put("limit", limit);
		return sqlMapper.selectList(SqlGenerator.operateSqlByType(OperateConstant.SQLID_SELECT_CRITERIA, entityTable), criteriaMap, this.entityClass,getMsId(OperateConstant.SQLID_SELECT_CRITERIA));
	}

	/**
	* <p>Description:TODO </p> 
	* @param list
	* @param orderby
	* @param offset
	* @param limit
	* @return
	*/
	@Override
	public List<T> queryListByBatchCriteria(List<T> list, String orderby,Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		Map<String,Object>  criteriaMap =new HashMap<String,Object>();
		criteriaMap.put("list", list);
		criteriaMap.put("orderby", orderby);
		criteriaMap.put("offset", offset);
		criteriaMap.put("limit", limit);
		return sqlMapper.selectList(SqlGenerator.operateSqlByType(OperateConstant.SQLID_SELECT_BATCH_CRITERIA, entityTable), criteriaMap, this.entityClass,getMsId(OperateConstant.SQLID_SELECT_BATCH_CRITERIA));
	}

	/**
	* <p>Description:TODO </p> 
	* @param id
	* @return
	*/
	@Override
	public T findById(PK id) {
		// TODO Auto-generated method stub
		if(id == null){
			try {
				throw new PrimaryKeyNotFoundException("主键为空，查询失败");
			} catch (PrimaryKeyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return sqlMapper.selectOne(SqlGenerator.operateSqlByType(OperateConstant.SQLID_SELECT_ONEBYID, entityTable), id, this.entityClass,getMsId(OperateConstant.SQLID_SELECT_ONEBYID));
	}


	/**
	* <p>Description:TODO </p> 
	* @param id
	* @return
	*/
	@Override
	public boolean removeById(PK id) {
		// TODO Auto-generated method stub
		if(id == null){
			try {
				throw new PrimaryKeyNotFoundException("主键为空，删除失败");
			} catch (PrimaryKeyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		int del = sqlMapper.delete(SqlGenerator.operateSqlByType(OperateConstant.SQLID_DELETE_ONEBYID, entityTable), id,getMsId(OperateConstant.SQLID_DELETE_ONEBYID));
		if(del >0){
			return true;
		}
		return false;
	}


	
}
