package org.springboot.transaction.mapper;

import org.apache.ibatis.annotations.Param;
import org.springboot.transaction.entity.User;

public interface UserMapper {
	void insertUser(@Param("user") User user);
}
