package org.springboot.mybatis.multidatasource.mapper.test2;

import org.apache.ibatis.annotations.Param;

public interface User2Mapper {
	/**
	 * 根据id查找用户名
	 * @param id
	 * @return
	 */
	String getUsernameById(@Param("id")Integer id);
}
