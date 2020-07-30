package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@ComponentScan({"com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita"})
public class ApplicationConfig {

    @Value("${driver}")
    private String databaseDriver;

    @Value("${database.user}")
    private String user;

    @Value("${database.user.password}")
    private String password;

    @Value("${database.url}")
    private String url;

    @Value("${entity.package}")
    private String entityPath;

    public ApplicationConfig() {
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfiguere() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource configDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(configDataSource());
        entityManagerFactory.setPackagesToScan(entityPath);
        JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(jpaAdapter);
        entityManagerFactory.setJpaProperties(customProp());

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private Properties customProp() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");

        return properties;
    }

}
