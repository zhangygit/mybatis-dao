通用Mybatis-DAO
=======

通用DAO简介及特征

通用Dao是一种持久化解决方案。 具有以下特征:

* 1.把单表具有重复性的操作抽离出来，避免创建不必要的xml文件
* 2.不影响原mybatis框架的使用
* 3.SQL语句和java代码的分离
* 4.极大的缩减使用mybatis 和 spring 集成框架下的工作量 
* 5.可支持uuid和mysql自增的主键生成策略

使用说明：作用于mybatisDAO层，依附mybatis-spring

待改进：

* 1.目前只支持mysql
* 2.其他


※实体的增删改查不需要写sql,支持SQL自动生成



### 接口定义[MessageDao.java]  

	public class MessageDao extends BaseDao<Message,Long>{}
    
### BaseDAO实现的接口 
	<!-- 插入单条信息 -->
	public PK create(T object);
	<!-- 编辑单条信息 -->
	public int update(T object);
	<!-- 删除单条信息 -->
	public int remove(T object);
	<!-- 统计信息条数 -->
	public int count(T object);
	<!-- 批量添加多条信息 -->
	public void createOfBatch(List<T> list);
	<!-- 批量更新多条信息 -->
	public int updateOfBatch(List<T> list);
	<!--批量删除多条信息 -->
	public int removeOfBacth(List<T> list);
	<!-- 获取多条信息 -->
	public List<T> queryByObject(T object);
	<!-- 批量获取多条信息 -->
	public List<T> queryByList(List<T> list);
	<!-- 带参数的查询 排序分页 -->
	public List<T> queryListByCriteria(T object,String orderby,Integer offset,Integer limit);
	<!-- 带复杂参数的查询 排序分页-->
	public List<T> queryListByBatchCriteria(List<T> list,String orderby,Integer offset,Integer limit);
	<!-- 根据主键值查找一条记录 -->
	public T findById(PK id);
	<!-- 根据主键删除一条记录 -->
	public boolean removeById(PK id);


### 通用DAO接口配置
        <!-- 需要mybatis-spring.jar -->
	<import resource="classpath:com/rework/joss/persistence/convention/baseDao.xml" />

##实体类注解

从上面效果来看也能感觉出这是一种类似hibernate的用法，因此也需要实体和表对应起来，因此使用了JPA注解。更详细的内容可以看下面的<b>项目文档</b>。

Country代码：

	@Table(name="user")
	public class UserDTO implements Serializable{
    	@Transient
		private static final long serialVersionUID = -9015714985719637211L;
		
		@GeneratedValue(generator = "UUID")
		@Id
	    private String userId;
	    
	    private String userName;
	}
    
### 测试代码
	public void testCreate() {
		UserDTO user = new UserDTO();
		user.setUserName("huttol");
		userDAO.create(user);
	}
	
	public void testRemoveOfBacth() {
		UserDTO user2 = new UserDTO();
		user2.setUserName("list2");
		int remove =userDAO.removeOfBacth(ConventionUtils.toList(user2));
		Assert.assertEquals(1, remove);
	}

	public void testRemoveById() {
		boolean t = userDAO.removeById("200cb94c99e64b21aed5e10b94f09eeb");
		Assert.assertEquals(true, t);
	}


##使用Maven

稍后支持

##[更新日志](http://git.oschina.net/zhangyosc/mybatis-dao/blob/master/wiki/Changelog.md?dir=0&filepath=wiki%2FChangelog.md&oid=092489e2884d4d7bf33589eb245c542e86281ad3&sha=d81d6c879ed779f0a9ff1ff2536bca2c456abbc0)
###v1.0.0正式发布版
* 修改并发下报key值重复的bug
* 可以直接使用[SqlSessionTemplate](http://mybatis.github.io/spring/zh/sqlsession.html#SqlSessionTemplate)调用mapper.xml中的id,从而减少类数量，增加灵活性 
###v1.0.0版本说明
支持mysql主键自增，增加批量参数处理

##作者信息
作者邮箱： cydy2011@163.com

