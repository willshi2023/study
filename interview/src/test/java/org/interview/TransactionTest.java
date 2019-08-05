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
}
