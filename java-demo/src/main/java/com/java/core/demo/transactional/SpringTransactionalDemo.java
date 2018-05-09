package com.java.core.demo.transactional;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-04-13 下午11:47
 */
public class SpringTransactionalDemo extends TransactionTemplate {
    /*
    *  事务四大特性：
    *  1、 原子性 Atomicity： 事务是一个原子操作，由一系列动作组成。事务的原子性确保动作要么全部完成，要么完全不起作用。
    *  2、一致性 Consistency：事务在完成时，必须是所有的数据都保持一致状态。
    *  3、隔离型 Isolation：并发事务执行之间无影响，在一个事务内部的操作对其他事务是不产生影响，这需要事务隔离级别来指定隔离性。
    *  4、持久性 Durability：一旦事务完成，数据库的改变必须是持久化的。
    *
    * */

}
