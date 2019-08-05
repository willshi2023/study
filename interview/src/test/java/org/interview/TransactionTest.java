package org.interview;

import org.interview.service.TransactionTestService;
import org.interview.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterviewApplication.class)
public class TransactionTest {
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionTestService transactionTestService;
	
	/**
	 * 测试事务在同一个类中的传递
	 */
	@Test
	public void testTransactionInSameClass() {
		userService.transactionTest(1);
	}
	
	/**
	 * 测试事务在同一个类中的传递
	 */
	@Test
	public void testTransactionInSameClass2() {
		userService.transactionTest2(1);
	}
	
	/**
	 * 测试事务在不同类中的传递
	 */
	@Test
	public void testNever() {
		transactionTestService.testNever();
	}
	
	@Test
	public void testRequired() {
		transactionTestService.testRequired();
	}
	
	/**
	 * 测试事务类型：Nested嵌套
	 */
	@Test
	public void testNested() {
		transactionTestService.testNested();
	}
	
	/**
	 * 测试事务类型：Nested嵌套
	 */
	@Test
	public void testNested2() {
		transactionTestService.testNested2();
	}
	
	/**
	 * 测试事务类型：REQUIRED_NEW：新建一个事务，同时将当前事务挂起
	 */
	@Test
	public void testRequiresNew() {
		transactionTestService.testRequiresNew();
	}
	
	/**
	 * REQUIRES_NEW会启用一个新的事务，事务拥有完全独立的能力，
	 * 它不依赖于当前事务，执行时会挂起当前事务，
	 * 直到REQUIRES_NEW事务完成提交后才会提交当前事务，
	 * 如果当前事务与REQUIRES_NEW 存在锁竞争，会导致死锁。
	 */
	@Test
	public void testRequiresNew2() {
		transactionTestService.testRequiresNew2();
	}
	
	/**
	 * NOT_SUPPORTED：以无事务的方式执行，如果当前有事务则将其挂起
	 */
	@Test
	public void testNotSupported() {
		transactionTestService.testNotSupported();
	}
	
	/**
	 * NOT_SUPPORTED会挂起当前事务，并且NOT_SUPPORTED定义的方法内部不启用显示事务，
	 * 如果NOT_SUPPORTED和当前事务存在锁竞争，会发生死锁。
	 */
	@Test
	public void testNotSupported2() {
		transactionTestService.testNotSupported2();
	}
	
	/**
	 * MANDATORY：支持当前事务，如果没有事务则报错
	 */
	@Test
	public void testMandatory() {
		transactionTestService.testMandatory();
	}
}
