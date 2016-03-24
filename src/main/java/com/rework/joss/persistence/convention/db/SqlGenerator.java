package com.rework.joss.persistence.convention.db;


import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rework.joss.persistence.convention.constant.OperateConstant;
import com.rework.joss.persistence.convention.utils.EntityUtil.EntityColumn;
import com.rework.joss.persistence.convention.utils.EntityUtil.EntityTable;

/** 
 * <p>Description: 动态sql拼写工具</p>
 * @author zhangyang
 * <p>日期:2015年6月17日 上午11:30:15</p> 
 * @version V1.0 
 */
public class SqlGenerator {
	
	protected Logger logger	= LoggerFactory.getLogger(this.getClass());

	public static String operateSqlByType(String sqlType,EntityTable entityObject){
		StringBuffer sql = new StringBuffer("<script> ");
		if(OperateConstant.SQLID_INSERT.equals(sqlType)){
			insertTable(sql,entityObject.getName());
			if(OperateConstant.SEQ_TYPE_JDBC.equals(entityObject.getEntityClassPKColumn().getGenerator())){
				insert(sql,entityObject.getEntityClassExceptPkColumns(),false);
			}else{
				insert(sql,entityObject.getEntityClassColumns(),false);
			}	
			
		}else if(OperateConstant.SQLID_INSERT_BATCH.equals(sqlType)){
			insertTable(sql,entityObject.getName());
			if(OperateConstant.SEQ_TYPE_JDBC.equals(entityObject.getEntityClassPKColumn().getGenerator())){
				insert(sql,entityObject.getEntityClassExceptPkColumns(),true);
			}else{
				insert(sql,entityObject.getEntityClassColumns(),true);
			}	
		}else if(OperateConstant.SQLID_UPDATE.equals(sqlType)){
			updateTable(sql,entityObject.getName());
			update(sql,entityObject.getEntityClassExceptPkColumns());
			commonWherePk(sql,entityObject.getEntityClassPKColumn());
		}else if(OperateConstant.SQLID_UPDATE_BATCH.equals(sqlType)){
			updateBatchTalbe(sql,entityObject.getName());
			sql.append(" values ");
			commonListWithComma(sql, entityObject.getEntityClassColumns());
		}else if(OperateConstant.SQLID_DELETE.equals(sqlType)){
			deleteTable(sql,entityObject.getName());
			commonWhere(sql,entityObject.getEntityClassColumns());
		}else if(OperateConstant.SQLID_DELETE_BATCH.equals(sqlType)){
			deleteTable(sql,entityObject.getName());
			commonList(sql,entityObject.getEntityClassColumns());
		}else if(OperateConstant.SQLID_COUNT_BATCH.equals(sqlType)){
			countTable(sql,entityObject.getName());
			commonWhere(sql,entityObject.getEntityClassColumns());
		}else if(OperateConstant.SQLID_SELECT.equals(sqlType)){
			selectColumn(sql,entityObject.getEntityClassColumns(),entityObject.getName());
			select(sql,entityObject.getEntityClassColumns(),false,false);
		}else if(OperateConstant.SQLID_SELECT_BATCH.equals(sqlType)){
			selectColumn(sql,entityObject.getEntityClassColumns(),entityObject.getName());
			select(sql,entityObject.getEntityClassColumns(),true,false);
		}else if(OperateConstant.SQLID_SELECT_CRITERIA.equals(sqlType)){
			selectColumn(sql,entityObject.getEntityClassColumns(),entityObject.getName());
			select(sql,entityObject.getEntityClassColumns(),false,true);
		}else if(OperateConstant.SQLID_SELECT_BATCH_CRITERIA.equals(sqlType)){
			selectColumn(sql,entityObject.getEntityClassColumns(),entityObject.getName());
			select(sql,entityObject.getEntityClassColumns(),true,true);
		}else if(OperateConstant.SQLID_SELECT_ONEBYID.equals(sqlType)){
			selectColumn(sql,entityObject.getEntityClassColumns(),entityObject.getName());
			commonWherePk(sql,entityObject.getEntityClassPKColumn());
		}else if(OperateConstant.SQLID_DELETE_ONEBYID.equals(sqlType)){
			deleteTable(sql,entityObject.getName());
			commonWherePk(sql,entityObject.getEntityClassPKColumn());
		}
		sql.append(" </script>");
		return sql.toString();
	}
	
	
	
	private static void insertTable(StringBuffer sql,String table){
		sql.append("INSERT INTO `"+table+"` " );
	}
	
	private static void updateTable(StringBuffer sql,String table){
		sql.append("UPDATE `"+table+"` " );
	}
	
	private static void deleteTable(StringBuffer sql,String table){
		sql.append("DELETE FROM `"+table+"` " );
	}
	
	private static void updateBatchTalbe(StringBuffer sql,String table){
		sql.append("REPLACE INTO `"+table+"` " );
	}
	
	private static void countTable(StringBuffer sql,String table){
		sql.append("SELECT count(*) from `"+table+"` ");
	}
	
	
	private static void insert(StringBuffer sql,Set<EntityColumn> entityClassColumns,boolean list){
		sql.append("(");
		if(entityClassColumns !=null && entityClassColumns.size() !=0){
			for(EntityColumn column:entityClassColumns){
				sql.append(column.getColumn()+",");
			}
			
			sql.deleteCharAt(sql.length()-1);
			sql.append(") values");
			
			if(list){
				commonListWithComma(sql,entityClassColumns);
			}else{
				sql.append(" (");
				for(EntityColumn column:entityClassColumns){
					sql.append("#{"+column.getProperty()+"},");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
			}
			
		}
	}
	
	private static void selectColumn(StringBuffer sql,Set<EntityColumn> entityClassColumns,String table){
		if(entityClassColumns !=null && entityClassColumns.size() !=0){
			sql.append("SELECT ");
			for(EntityColumn column:entityClassColumns){
				sql.append(column.getColumn()+",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" FROM `"+table+"`");
		}
	}
	
	private static void select(StringBuffer sql,Set<EntityColumn> entityClassColumns,boolean list ,boolean criteria){
		if(entityClassColumns !=null && entityClassColumns.size() !=0){
			
			if(list){
				commonList(sql,entityClassColumns);
			}else{
				sql.append("<if test=\"object !=null\"><where>");
				for(EntityColumn column:entityClassColumns){
					sql.append("<if test=\"object."+column.getProperty()+" != null\"> and "+column.getColumn() +" = #{object."+column.getProperty() +"} </if>" );
				}
				sql.append("</where></if>");
			}
			
			if(criteria){
				sql.append("<if test=\"orderby != null\"> order by  ${orderby}</if>");
				sql.append("<if test=\"offset !=null and limit !=null\">limit #{offset},#{limit} </if>");
			}
		}
	}
	
	private static void update(StringBuffer sql,Set<EntityColumn> entityClassColumns){
		sql.append("<set> ");
		for(EntityColumn column:entityClassColumns){
			sql.append("<if test=\""+column.getProperty()+" != null\"> "+column.getColumn() +" = #{"+column.getProperty()+"}, </if>");
		}
		sql.append("</set>");
	}
	
	private static void commonWhere(StringBuffer sql,Set<EntityColumn> entityClassColumns){
		sql.append("<where> 1=1 ");
		if(entityClassColumns != null && entityClassColumns.size() !=0){
			for(EntityColumn column:entityClassColumns){
				sql.append("<if test=\""+column.getProperty()+" != null\"> and "+column.getColumn()+" = #{"+column.getProperty()+"} </if>");
			}
		}
		sql.append(" </where>");
	}
	
	private static void commonWherePk(StringBuffer sql,EntityColumn entityClassPKColumn){
		sql.append("where ");
		sql.append(entityClassPKColumn.getColumn() +" = #{"+ entityClassPKColumn.getProperty()+"}");
	}
	
	private static void commonList(StringBuffer sql,Set<EntityColumn> entityClassColumns){
		if(entityClassColumns != null && entityClassColumns.size() !=0){
			sql.append("<if test=\"list !=null\"><where><foreach collection=\"list\" item=\"item\" index=\"index\" separator=\"or\">");
			sql.append("(<trim suffix=\"\" prefixOverrides=\"AND\" >");
			for(EntityColumn column:entityClassColumns){
				sql.append("<if test=\"item."+column.getProperty()+" != null\"> and "+column.getColumn() +" = #{item."+column.getProperty() +"} </if>" );
			}
			sql.append("</trim>)</foreach></where></if>");
		}
	}
	
	private static void commonListWithComma(StringBuffer sql,Set<EntityColumn> entityClassColumns){
		sql.append(" <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">(");
		for(EntityColumn column:entityClassColumns){
			sql.append("#{item."+column.getProperty()+"},");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(") </foreach>");
	}
	
	
	
	
	

}
