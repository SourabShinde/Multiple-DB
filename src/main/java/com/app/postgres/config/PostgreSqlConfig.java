package com.app.postgres.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.app.postgres.repo", entityManagerFactoryRef = "postgresqlEntityManagerFactory", transactionManagerRef = "postgresqlTransactionManager")
public class PostgreSqlConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.postgresql")
    DataSourceProperties postGSQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresqlDatasource")
    DataSource postgressqlDataSource() {
        return postGSQLDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresqlEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("postgresqlDatasource")
    DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.app.postgres.entities");
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "create");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(prop);
        return entityManagerFactoryBean;
    }

    @Bean(name = "postgresqlTransactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("postgresqlEntityManagerFactory")
    EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
