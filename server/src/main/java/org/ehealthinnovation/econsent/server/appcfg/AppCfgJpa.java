package org.ehealthinnovation.econsent.server.appcfg;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.vendor.Database;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Spring configuration for the JPA server
 */
@Configuration
@EnableTransactionManagement()
public class AppCfgJpa {
    //TODO: set value based on environment variable
    @Value("DERBY")
    private Database database;

    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource() {
        String url;
        String driver;
        BasicDataSource dataSrc = new BasicDataSource();

        switch (database) {
            case POSTGRESQL:
                url = "jdbc:postgresql://localhost:5432/consent";
                driver = "org.postgresql.Driver";
                //TODO: set these values with environment variables
                dataSrc.setUsername("consentuser");
                dataSrc.setPassword("123");
                break;
            case DERBY:
                url = "jdbc:derby:memory:jpaserver_derby_files;create=true";
                driver = "org.apache.derby.jdbc.EmbeddedDriver";
                break;
            default:
                throw new RuntimeException("Database type not known.");
        }

        dataSrc.setUrl(url);
        dataSrc.setDriverClassName(driver);

        return dataSrc;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        transactionManager.afterPropertiesSet();
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource());
        List<String> packagesToScan = new ArrayList<>();
        packagesToScan.add("ca.uhn.fhir.jpa.entity");
        emFactory.setPackagesToScan(packagesToScan.toArray(new String[packagesToScan.size()]));
        emFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        emFactory.setJpaProperties(jpaProperties());
        emFactory.afterPropertiesSet();
        return emFactory.getNativeEntityManagerFactory();
    }

    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.search.autoregister_listeners", "false");

        switch (database) {
            case POSTGRESQL:
                jpaProperties.put("hibernate.dialect", org.hibernate.dialect.PostgreSQL94Dialect.class.getName());
                jpaProperties.put("hibernate.default_schema", "public");
                break;
            case DERBY:
                jpaProperties.put("hibernate.dialect", org.hibernate.dialect.DerbyTenSevenDialect.class.getName());
                break;
            default:
                throw new RuntimeException("Database type not known.");
        }

        return jpaProperties;
    }
}
