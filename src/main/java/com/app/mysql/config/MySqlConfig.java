package com.app.mysql.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.app.mysql.repo", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class MySqlConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mysqlDatasource")
    DataSource mysqlDataSource() {
        return mysqlDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("mysqlDatasource")
    DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.app.mysql.entities");
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "create");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(prop);
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "mysqlTransactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("mysqlEntityManagerFactory")
    EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
