package org.interview.mapper;

import org.apache.ibatis.annotations.Param;
import org.interview.entity.User;

public interface UserMapper {

	User findById(@Param("id")Long id);

	int insertEntity(@Param("user")User user);

	int updateUserPassWord(@Param("id")Long id, @Param("password")String password);

}
