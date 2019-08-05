package org.interview.service;

import org.interview.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionTestServiceImpl implements TransactionTestService {
	@Autowired
    private UserService userService;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 执行不同类的有事务的嵌套方法，事务生效
	 */
	@Transactional
	public void testNever() {
		userService.findById(1L);
	}
	
	/**
	 * 测试事务类型为required的嵌套方法的调用
	 * 嵌套方法如果出异常了，会设置成rollback-only，无法被捕获，只能回滚
	 * 继续commit的话会导致提交rollback-only的事务发生异常报错
	 * 数据库也会回滚，不会commit
	 * 测试事务类型为required的嵌套方法的会将其和调用的方法一起绑定事务，不能使用trycatch
	 */
	@Transactional
	public void testRequired() {
	    try {
	        userService.insertUser("abc", "123");
	    } catch (Exception e) {
	        //do Nothing
	    }
	    userMapper.updateUserPassWord(1L, "456");
	}
	
	/**
	 * nested支持嵌套事务，如果当前事务方法已经有事务，那么在进入事务类型为nested的不同类的嵌套方法的时候，嵌套方法
	 * 就会另起一个事务，该事务的成功与否不会影响当前方法的事务。可以处理事务类型为required无法trycatch的问题。
	 */
	@Transactional
	public void testNested() {
	    try {
	        userService.insertUser2("abc", "123");
	    } catch (Exception e) {
	        //do Nothing
	    }
	    userMapper.updateUserPassWord(1L, "456");
	}
}
