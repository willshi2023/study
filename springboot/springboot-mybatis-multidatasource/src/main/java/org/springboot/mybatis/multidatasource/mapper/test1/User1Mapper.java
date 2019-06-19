package org.springboot.mybatis.multidatasource.mapper.test1;

import org.apache.ibatis.annotations.Param;

public interface User1Mapper {
	/**
	 * 根据id查找用户名
	 * @param id
	 * @return
	 */
	String getUsernameById(@Param("id")Integer id);
}
