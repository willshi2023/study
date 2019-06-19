package org.springboot.mybatis.multidatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "org.springboot.mybatis.multidatasource.mapper.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
@MapperScan(basePackages = "org.springboot.mybatis.multidatasource.mapper.test2", sqlSessionTemplateRef  = "test2SqlSessionTemplate")
public class SpringbootMybatisMultidatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisMultidatasourceApplication.class, args);
	}
}
