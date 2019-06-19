package org.springboot.mybatis.multidatasource.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "org.springboot.mybatis.multidatasource.mapper.test01", sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSourceConfig1 {
	@Bean(name = "test1DataSource")
	@Primary // 表示这个数据源是默认数据源
	@ConfigurationProperties(prefix = "spring.datasource.test1")
	public DataSource getDateSource1() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "test1SqlSessionFactory")
	// 表示这个数据源是默认数据源
	@Primary
	// @Qualifier表示查找Spring容器中名字为test1DataSource的对象
	public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource datasource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		bean.setMapperLocations(
				// 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
		return bean.getObject();
	}

	@Bean("test1SqlSessionTemplate")
	// 表示这个数据源是默认数据源
	@Primary
	public SqlSessionTemplate test1sqlsessiontemplate(
			@Qualifier("test1SqlSessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
