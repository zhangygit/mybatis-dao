#通用DAO的代码

将本项目中的代码文件复制到你自己的项目中，代码文件如下：

* `com.rework.joss.persistence`包下面的是通用DAO的DAO接口

   * `IBaseDao`:所有使用通用DAO要继承的DAO接口

* `com.rework.joss.persistence.convention.annotation`包下面的是通用DAO的实体类的注解，参照JPA1.0


* `com.rework.joss.persistence.convention.constant`包下面是通用DAO需要的常量

* `com.rework.joss.persistence.convention.db`包下面是通用DAO的动态sql封装工具

   * `SqlGenerator`:动态sql封装工具

* `com.rework.joss.persistence.convention.exception`包下面的是通用DAO自定义的异常

* `com.rework.joss.persistence.convention.id`包下面的是UUID生成工具

* `com.rework.joss.persistence.convention.utils`包下面的是通用DAO所需的封装工具
	* `EntityUtil` 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
	* `ReflectionUtils`反射工具类

* `com.rework.joss.persistence.convention.BaseDAO.java`baseDao接口实现
 
* `com.rework.joss.persistence.convention.SqlMapper`MyBatis执行sql工具
 
* `com.rework.joss.persistence.convention.baseDao.xml`配置文件，使用时需要引入

