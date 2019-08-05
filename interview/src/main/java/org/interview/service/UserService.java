package org.interview.service;

import org.interview.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public void transactionTest(int t);
	public void transactionTest2(int t);
	public User findById(Long id);
	public int insertUser(String username, String password);
}
