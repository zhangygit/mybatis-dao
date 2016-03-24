package com.rework.joss.persistence.convention.constant;

/**
 *  
 * <p>Description: 增删改查方法常量</p>.
 * @author zhangyang
 * <p>日期:2015年6月15日 上午10:08:34</p> 
 * @version V1.0
 */
public abstract class OperateConstant {
    /** The Constant SQLID_INSERT. */
	public static final String SQLID_INSERT = "Create";  
    
    /** The Constant SQLID_INSERT_BATCH. */
    public static final String SQLID_INSERT_BATCH = "CreateOfBatch";  
    
    /** The Constant SQLID_UPDATE. */
    public static final String SQLID_UPDATE = "Update";  
    
    /** The Constant SQLID_UPDATE_BATCH. */
    public static final String SQLID_UPDATE_BATCH = "UpdateOfBacth";  
    
    /** The Constant SQLID_DELETE. */
    public static final String SQLID_DELETE = "Remove";  
    
    /** The Constant SQLID_DELETE_BATCH. */
    public static final String SQLID_DELETE_BATCH = "RemoveOfBatch";
    
    /** The Constant SQLID_SELECT. */
    public static final String SQLID_SELECT="QueryByObject";
    
    /** The Constant SQLID_SELECT_BATCH. */
    public static final String SQLID_SELECT_BATCH="QueryByList";
    
    /** The Constant SQLID_SELECT_CRITERIA. */
    public static final String SQLID_SELECT_CRITERIA="QueryListByCriteria";
    
    
    /** The Constant SQLID_SELECT_BATCH_CRITERIA. */
    public static final String SQLID_SELECT_BATCH_CRITERIA = "QueryListByBatchCriteria"; 
    
    /** The Constant SQLID_COUNT_BATCH. */
    public static final String SQLID_COUNT_BATCH = "Count";  
    
    /** The Constant SQLID_SELECT_ONEBYID. */
    public static final String SQLID_SELECT_ONEBYID = "FindById";
    
    /** The Constant SQLID_DELETE_ONEBYID. */
    public static final String SQLID_DELETE_ONEBYID="RemoveById";
    
    /** The Constant SQLNAME_SEPARATOR. */
    public static final String SQLNAME_SEPARATOR = ".";
    
    

    
    /** The Constant SEQ_TYPE_JDBC. */
    public static final String SEQ_TYPE_JDBC ="JDBC";
    
    /** The Constant SEQ_TYPE_UNID. */
    public static final String SEQ_TYPE_UNID="UNID";
    
    /** The Constant SEQ_TYPE_UUID. */
    public static final String SEQ_TYPE_UUID="UUID";
}
