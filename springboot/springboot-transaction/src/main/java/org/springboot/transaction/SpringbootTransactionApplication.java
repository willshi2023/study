package org.springboot.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("org.springboot.transaction.mapper")
public class SpringbootTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTransactionApplication.class, args);
	}
}
