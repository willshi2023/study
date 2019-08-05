package org.interview.service;

import org.interview.entity.User;
import org.interview.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * Propagation.NEVER:以无事务的方式执行，如果当前有事务则报错
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.NEVER)
	public User findById(Long id) {
		User user = userMapper.findById(id);
		System.out.println("find user:" + user);
		return user;
	}

	/**
	 * 如果使用@Transaction方法里嵌套调用的是同一个类的方法，spring代理会忽略嵌套方法的@Transaction配置。
	 * 但是，如果是其他注入对象的方法，那么@Transaction配置就会生效。
	 * 所以这里虽然加上了默认的require事务并且调用了嵌套了Propagation.NEVER的方法的事务，但是嵌套方法的事务被忽略，仍然不会报错
	 */
	@Transactional
	public void transactionTest(int t) {
		findById(t + 0L);
	}

	/**
	 * 当前方法以无事务的方式执行，由于忽略了嵌套方法的事务配置，所以嵌套方法也是无事务的 所以报错不会回滚
	 * 
	 * @param t
	 */
	@Transactional(propagation = Propagation.NEVER)
	public void transactionTest2(int t) {
		findById(t + 0L);
		insertUser("huangxl", "abc123");
	}

	@Transactional
	public int insertUser(String name, String password) {
		User user = new User();
		user.setPassword(password);
		user.setUsername(name);
		int insertCount = userMapper.insertEntity(user);
		if (insertCount == 1) {
			throw new RuntimeException("test transaction roll back");
		}
		return insertCount;
	}

	/**
	 * NESTED：如果当前有事务，则在当前事务内部嵌套一个事务，内部事务的回滚不影响当前事务。如果当前没有事务，就相当于REQUIRED
	 */
	@Transactional(propagation = Propagation.NESTED)
	public int insertUser2(String name, String password) {
		User user = new User();
		user.setPassword(password);
		user.setUsername(name);
		int insertCount = userMapper.insertEntity(user);
		if (insertCount == 1) {
			throw new RuntimeException("test transaction roll back");
		}
		return insertCount;
	}

	@Transactional(propagation = Propagation.NESTED)
	@Override
	public int insertUser3(String username, String password) {
		User user = new User();
		user.setPassword(password);
		user.setUsername(username);
		int insertCount = userMapper.insertEntity(user);
		return insertCount;
	}

	/**
	 * REQUIRED_NEW：新建一个事务，同时将当前事务挂起
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public int insertUser4(String username, String password) {
		User user = new User();
		user.setPassword(password);
		user.setUsername(username);
		int insertCount = userMapper.insertEntity(user);
		return insertCount;
	}

	/**
	 * REQUIRES_NEW会启用一个新的事务，事务拥有完全独立的能力，
	 * 它不依赖于当前事务，执行时会挂起当前事务，直到REQUIRES_NEW事务完成提交后才会提交当前事务， 如果当前事务与REQUIRES_NEW
	 * 存在锁竞争，会导致死锁。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public int updateUserPassWord(Long id, String password) {
		int count = userMapper.updateUserPassWord(id, password);
		return count;
	}

	/**
	 * NOT_SUPPORTED：以无事务的方式执行，如果当前有事务则将其挂起
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public int updateUserPassWord2(Long id, String password) {
		int updateRow = userMapper.updateUserPassWord(id, password);
		if (updateRow == 1) {
			throw new RuntimeException("roll back test");
		}
		return updateRow;
	}

	/**
	 * NOT_SUPPORTED会挂起当前事务，并且NOT_SUPPORTED定义的方法内部不启用显示事务，
	 * 如果NOT_SUPPORTED和当前事务存在锁竞争，会发生死锁。
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public int updateUserPassWord3(Long id, String password) {
		int update = userMapper.updateUserPassWord(id, password);
		return update;
	}

	/**
	 * MANDATORY：支持当前事务，如果没有事务则报错
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public int updateUserPassWord4(Long id, String password) {
		int updateRow = userMapper.updateUserPassWord(id, password);
		return updateRow;
	}
}
