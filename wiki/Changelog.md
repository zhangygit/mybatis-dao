#更新日志
##2015-08-20
* 增加批量插入时参数为空的异常
##v1.0.0正式发布版
* 修改并发下报key值重复的bug
* 可以直接使用[SqlSessionTemplate](http://mybatis.github.io/spring/zh/sqlsession.html#SqlSessionTemplate)调用mapper.xml中的id,从而减少类数量（可无需使用mapper接口），增加灵活性 
##v1.0.0版本说明
支持mysql主键自增，增加批量参数处理