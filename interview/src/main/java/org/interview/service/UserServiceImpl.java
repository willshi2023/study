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
	 * 当前方法以无事务的方式执行，由于忽略了嵌套方法的事务配置，所以嵌套方法也是无事务的
	 * 所以报错不会回滚
	 * @param t
	 */
	@Transactional(propagation = Propagation.NEVER)
	public void transactionTest2(int t) {
	    findById(t+0L);
	    insertUser("huangxl","abc123");
	}
	@Transactional
	public int insertUser(String name, String password) {
	    User user = new User();
	    user.setPassword(password);
	    user.setUsername(name);
	    int insertCount =  userMapper.insertEntity(user);
	    if(insertCount == 1 ){
	        throw new RuntimeException("test transaction roll back");
	    }
	    return insertCount;
	}
}
