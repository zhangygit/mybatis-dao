package com.rework.joss.persistence.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
* <p>Description: 测试配置文件 基类</p>
* @author zhangyang
* <p>日期:2015年4月23日 上午11:06:43</p> 
* @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:applicationContext-test.xml"
})
@TransactionConfiguration(transactionManager="transactionManager") //可选，默认就是这个
@Transactional
public class BaseSpringTestCase {
}
