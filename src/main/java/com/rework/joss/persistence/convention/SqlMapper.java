package com.rework.joss.persistence.convention;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import com.rework.joss.persistence.convention.utils.EntityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
* <p>Description: MyBatis执行sql工具</p>
* @author zhangyang
* <p>日期:2015年6月25日 上午11:54:07</p> 
* @version V1.0
 */
public class SqlMapper {

	 private final MSUtils msUtils;
	 private final SqlSessionTemplate sqlSession;
    /**
     * 构造方法，默认缓存MappedStatement
     *
     * @param sqlSession
     */
    public SqlMapper(SqlSessionTemplate sqlSession) {
    	 this.sqlSession = sqlSession;
         this.msUtils = new MSUtils(sqlSession.getConfiguration());
    }

    /**
     * 获取List中最多只有一个的数据
     *
     * @param list List结果
     * @param <T>  泛型类型
     * @return
     */
    private <T> T getOne(List<T> list) {
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }



    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql        执行的sql
     * @param resultType 返回的结果类型
     * @param <T>        泛型类型
     * @return
     */
    protected <T> T selectOne(String sql, Class<T> resultType,String msId) {
        List<T> list = selectList(sql, resultType,msId);
        return getOne(list);
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql        执行的sql
     * @param value      参数
     * @param resultType 返回的结果类型
     * @param <T>        泛型类型
     * @return
     */
    protected <T> T selectOne(String sql, Object value, Class<T> resultType,String msId) {
        List<T> list = selectList(sql, value, resultType,msId);
        return getOne(list);
    }



    /**
     * 查询返回指定的结果类型
     *
     * @param sql        执行的sql
     * @param resultType 返回的结果类型
     * @param <T>        泛型类型
     * @return
     */
    protected <T> List<T> selectList(String sql, Class<T> resultType,String msId) {
        String newMsId;
        if (resultType == null) {
            newMsId = msUtils.select(sql,msId);
        } else {
            newMsId = msUtils.select(sql, resultType,msId);
        }
        return sqlSession.selectList(newMsId);
    }

    /**
     * 查询返回指定的结果类型
     *
     * @param sql        执行的sql
     * @param value      参数
     * @param resultType 返回的结果类型
     * @param <T>        泛型类型
     * @return
     */
    protected <T> List<T> selectList(String sql, Object value, Class<T> resultType,String msId) {
        String newMsId;
        Class<?> parameterType = value != null ? value.getClass() : null;
        if (resultType == null) {
            newMsId = msUtils.selectDynamic(sql, parameterType,msId);
        } else {
            newMsId = msUtils.selectDynamic(sql, parameterType, resultType,msId);
        }
        return sqlSession.selectList(newMsId, value);
    }

    /**
     * 插入数据
     *
     * @param sql 执行的sql
     * @return
     */
    protected int insert(String sql,String msId) {
        String newMsId = msUtils.insert(sql,msId);
        return sqlSession.insert(newMsId);
    }

    /**
     * 插入数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return
     */
    protected int insert(String sql, Object value,String msId) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String newMsId = msUtils.insertDynamic(sql, parameterType,msId);
        return sqlSession.insert(newMsId, value);
    }
    
    /**
     * 
     * 插入数据（主键自增）
    * @param sql
    * @param value
    * @param msId
    * @param column
    * @return int
    * @throws
     */
    protected int insertSelectKey(String sql, Object value,String msId,EntityUtil.EntityColumn column){
    	
    	Class<?> parameterType = value != null ? value.getClass() : null;
        String newMsId = msUtils.insertSelectKeyDynamic(sql, parameterType,msId,column);
        return sqlSession.insert(newMsId, value);
    }

    /**
     * 更新数据
     *
     * @param sql 执行的sql
     * @return
     */
    protected int update(String sql,String msId) {
        String newMsId = msUtils.update(sql,msId);
        return sqlSession.update(newMsId);
    }

    /**
     * 更新数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return
     */
    protected int update(String sql, Object value,String msId) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String newMsId = msUtils.updateDynamic(sql, parameterType,msId);
        return sqlSession.update(newMsId, value);
    }

    /**
     * 删除数据
     *
     * @param sql 执行的sql
     * @return
     */
    protected int delete(String sql,String msId) {
        String newMsId = msUtils.delete(sql,msId);
        return sqlSession.delete(newMsId);
    }

    /**
     * 删除数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return
     */
    protected int delete(String sql, Object value,String msId) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String newMsId = msUtils.deleteDynamic(sql, parameterType,msId);
        return sqlSession.delete(newMsId, value);
    }

    private class MSUtils {
        private Configuration configuration;
        private LanguageDriver languageDriver;

        private MSUtils(Configuration configuration) {
            this.configuration = configuration;
            languageDriver = configuration.getDefaultScriptingLanuageInstance();
        }

        /**
         * 创建MSID
         *
         * @param sql 执行的sql
         * @param sql 执行的sqlCommandType
         * @return
         */
        private String newMsId(String sql, SqlCommandType sqlCommandType) {
            StringBuilder msIdBuilder = new StringBuilder(sqlCommandType.toString());
            msIdBuilder.append(".").append(sql.hashCode());
            return msIdBuilder.toString();
        }

        /**
         * 是否已经存在该ID
         *
         * @param msId
         * @return
         */
        private boolean hasMappedStatement(String msId) {
            return configuration.hasStatement(msId, false);
        }

        /**
         * 创建一个查询的MS
         *
         * @param msId
         * @param sqlSource  执行的sqlSource
         * @param resultType 返回的结果类型
         */
        private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
            MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, SqlCommandType.SELECT)
                    .resultMaps(new ArrayList<ResultMap>() {
                        {
                            add(new ResultMap.Builder(configuration, "defaultResultMap", resultType, new ArrayList<ResultMapping>(0)).build());
                        }
                    })
                    .build();
            //缓存
            configuration.addMappedStatement(ms);
        }

        /**
         * 创建一个简单的MS
         *
         * @param msId
         * @param sqlSource      执行的sqlSource
         * @param sqlCommandType 执行的sqlCommandType
         */
        private void newUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType) {
            MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, sqlCommandType)
                    .resultMaps(new ArrayList<ResultMap>() {
                        {
                            add(new ResultMap.Builder(configuration, "defaultResultMap", int.class, new ArrayList<ResultMapping>(0)).build());
                        }
                    })
                    .build();
            //缓存
            configuration.addMappedStatement(ms);
        }
        /**
         * 创建一个简单的MS
         *
         * @param msId
         * @param sqlSource      执行的sqlSource
         * @param sqlCommandType 执行的sqlCommandType
         * @param column         主键相关信息
         */
        private void newInsertKeyMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType,EntityUtil.EntityColumn column){
            MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, sqlCommandType)
            .resultMaps(new ArrayList<ResultMap>() {
                {
                    add(new ResultMap.Builder(configuration, "defaultResultMap", int.class, new ArrayList<ResultMapping>(0)).build());
                }
            })
            .build();
            //缓存
            configuration.addMappedStatement(ms);
            newSelectKeyMappedStatement(ms,configuration,column);
            
        }
        
        
        /**
         * 新建SelectKey节点 - 只对mysql的自动增长有效，Oracle序列直接写到列中
        * @param ms
        * @param configuration
        * @param column void
        * @throws
         */
        private void newSelectKeyMappedStatement(MappedStatement ms,Configuration configuration,EntityUtil.EntityColumn column){
        	String keyId = ms.getId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
            if (configuration.hasKeyGenerator(keyId)) {
                return;
            }
            Class<?> entityClass = column.getJavaType();
            //defaults
            KeyGenerator keyGenerator;
            Boolean executeBefore = false;
            SqlSource newSqlSource = new RawSqlSource(configuration, "SELECT LAST_INSERT_ID()", entityClass);

            MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, keyId, newSqlSource, SqlCommandType.SELECT);
            statementBuilder.resource(ms.getResource());
            statementBuilder.fetchSize(null);
            statementBuilder.statementType(StatementType.STATEMENT);
            statementBuilder.keyGenerator(new NoKeyGenerator());
            statementBuilder.keyProperty(column.getProperty());
            statementBuilder.keyColumn(null);
            statementBuilder.databaseId(null);
            statementBuilder.lang(configuration.getDefaultScriptingLanuageInstance());
            statementBuilder.resultOrdered(false);
            statementBuilder.resulSets(null);
            statementBuilder.timeout(configuration.getDefaultStatementTimeout());

            List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
            ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(
                    configuration,
                    statementBuilder.id() + "-Inline",
                    entityClass,
                    parameterMappings);
            statementBuilder.parameterMap(inlineParameterMapBuilder.build());

            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
            ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                    configuration,
                    statementBuilder.id() + "-Inline",
                    column.getJavaType(),
                    new ArrayList<ResultMapping>(),
                    null);
            resultMaps.add(inlineResultMapBuilder.build());
            statementBuilder.resultMaps(resultMaps);
            statementBuilder.resultSetType(null);

            statementBuilder.flushCacheRequired(false);
            statementBuilder.useCache(false);
            statementBuilder.cache(null);

            MappedStatement statement = statementBuilder.build();
            try {
                configuration.addMappedStatement(statement);
            } catch (Exception e) {
                //ignore
            }
            MappedStatement keyStatement = configuration.getMappedStatement(keyId, false);
            keyGenerator = new SelectKeyGenerator(keyStatement, executeBefore);
            try {
                configuration.addKeyGenerator(keyId, keyGenerator);
            } catch (Exception e) {
                //ignore
            }
            //keyGenerator
            try {
                MetaObject msObject = SystemMetaObject.forObject(ms);
                msObject.setValue("keyGenerator", keyGenerator);
                msObject.setValue("keyProperties", column.getTable().getKeyProperties());
                msObject.setValue("keyColumns", column.getTable().getKeyColumns());
            } catch (Exception e) {
                //ignore
            }
        }

        private String select(String sql,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newSelectMappedStatement(msId, sqlSource, Map.class);
            return msId;
        }

        private String selectDynamic(String sql, Class<?> parameterType,String msId ) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newSelectMappedStatement(msId, sqlSource, Map.class);
            return msId;
        }

        private String select(String sql, Class<?> resultType,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newSelectMappedStatement(msId, sqlSource, resultType);
            return msId;
        }

        private String selectDynamic(String sql, Class<?> parameterType, Class<?> resultType,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newSelectMappedStatement(msId, sqlSource, resultType);
            return msId;
        }

        private String insert(String sql,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
            return msId;
        }

        private String insertDynamic(String sql, Class<?> parameterType,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
            return msId;
        }
        
        
        private String insertSelectKeyDynamic(String sql, Class<?> parameterType,String msId,EntityUtil.EntityColumn column) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newInsertKeyMappedStatement(msId, sqlSource, SqlCommandType.INSERT,column);
            return msId;
        }
        

        private String update(String sql,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
            return msId;
        }

        private String updateDynamic(String sql, Class<?> parameterType,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
            return msId;
        }

        private String delete(String sql,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            return msId;
        }

        private String deleteDynamic(String sql, Class<?> parameterType,String msId) {
            if (hasMappedStatement(msId)) {
                return msId;
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            return msId;
        }
    }
}
