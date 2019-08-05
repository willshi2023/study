package org.interview.service;

import org.interview.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public void transactionTest(int t);
	public void transactionTest2(int t);
	public User findById(Long id);
	public int insertUser(String username, String password);
	public int insertUser2(String username, String password);
	public int insertUser3(String username, String password);
	public int insertUser4(String username, String password);
	public int updateUserPassWord(Long id, String password);
	public int updateUserPassWord2(Long id, String password);
	public int updateUserPassWord3(Long id, String password);
	public int updateUserPassWord4(Long id, String password);
}
