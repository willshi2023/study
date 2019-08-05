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
	 * 测试事务类型为required的嵌套方法的调用 嵌套方法如果出异常了，会设置成rollback-only，无法被捕获，只能回滚
	 * 继续commit的话会导致提交rollback-only的事务发生异常报错 数据库也会回滚，不会commit
	 * 测试事务类型为required的嵌套方法的会将其和调用的方法一起绑定事务，不能使用trycatch
	 */
	@Transactional
	public void testRequired() {
		try {
			userService.insertUser("abc", "123");
		} catch (Exception e) {
			// do Nothing
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
			// do Nothing
		}
		userMapper.updateUserPassWord(1L, "456");
	}

	/**
	 * NESTED子事务回滚不会影响当前事务的提交(catch回滚异常的情况下)，
	 * 但是当前事务回滚会回滚子事务。也就是说只有当前事务提交成功了，子事务才会提交成功。
	 */
	@Transactional
	public void testNested2() {
		userService.insertUser3("abc", "123");
		int updateRow = userMapper.updateUserPassWord(1L, "456");
		if (updateRow == 1) {
			throw new RuntimeException("transational roll back");
		}
	}

	@Transactional
	public void testRequiresNew() {
		userService.insertUser4("abc", "testRequiresNew");
		int updateRow = userMapper.updateUserPassWord(1L, "456");
		if (updateRow == 1) {
			throw new RuntimeException("transational roll back");
		}
	}

	/**
	 * REQUIRES_NEW会启用一个新的事务，事务拥有完全独立的能力，
	 * 它不依赖于当前事务，执行时会挂起当前事务，直到REQUIRES_NEW事务完成提交后才会提交当前事务， 如果当前事务与REQUIRES_NEW
	 * 存在锁竞争，会导致死锁。
	 */
	@Transactional
	@Override
	public void testRequiresNew2() {
		// 当前事务
		userMapper.updateUserPassWord(1L, "123456");
		// 执行REQUIRES_NEW事务
		userService.updateUserPassWord(1L, "000000");
		System.out.println("commit");
	}

	/**
	 * NOT_SUPPORTED：以无事务的方式执行，如果当前有事务则将其挂起
	 */
	@Transactional
	@Override
	public void testNotSupported() {
		userService.updateUserPassWord2(1L, "000000");
	}

	/**
	 * NOT_SUPPORTED会挂起当前事务，并且NOT_SUPPORTED定义的方法内部不启用显示事务，
	 * 如果NOT_SUPPORTED和当前事务存在锁竞争，会发生死锁。
	 */
	@Override
	@Transactional
	public void testNotSupported2() {
		// 当前事务
		userMapper.updateUserPassWord(1L, "testNotSupported2a");
		// 执行REQUIRES_NEW事务
		userService.updateUserPassWord3(1L, "testNotSupported2b");
		System.out.println("commit");
	}
}
