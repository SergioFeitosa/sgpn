package br.com.j4business.saga;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider; 
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.mchange.v2.c3p0.*;


/*
package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

} 
 */



@Configuration
public class MvcConfig  implements WebMvcConfigurer {

	@Override
	public void addViewControllers(@NonNull ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}
	
	
    @Bean
    DataSource dataSource() throws IllegalStateException, PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/sgpncom_saga?autoReconnect=true&useSSL=false");
        dataSource.setUser("sgpncom_desenv");
        dataSource.setPassword("DEsenv2@3#4$");
        return dataSource;
    }
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("br.com.j4business.saga.*.model");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
		
		Properties jpaProterties = new Properties();
		entityManagerFactoryBean.setJpaProperties(jpaProterties);
		return entityManagerFactoryBean;
	}

	@Bean
	JpaTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Override
	public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> argumentResolvers) {
		
		PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver();
		phmar.setFallbackPageable(PageRequest.of(0, 15));
		argumentResolvers.add(phmar);
	}

}
