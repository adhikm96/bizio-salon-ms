package com.thebizio.biziosalonms.config;

import com.thebizio.biziosalonms.service.multi_data_source.MultiDataSourceHolder;
import com.thebizio.biziosalonms.service.multi_data_source.MultiRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.thebizio.biziosalonms",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class PersistenceConfiguration {
    final MultiDataSourceHolder multiDataSourceHolder;
    private final String PACKAGE_SCAN = "com.thebizio.biziosalonms";

    public PersistenceConfiguration(MultiDataSourceHolder multiDataSourceHolder) {
        this.multiDataSourceHolder = multiDataSourceHolder;
    }

    @Bean(name = "multiRoutingDataSource")
    public MultiRoutingDataSource multiRoutingDataSource() {
        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();

        multiDataSourceHolder.setMultiRoutingDataSource(multiRoutingDataSource);
        multiDataSourceHolder.loadDBs();

        // TO DO - set default DB in multiRoutingDataSource - for now setting first data-source as default
        multiRoutingDataSource.setDefaultTargetDataSource(multiDataSourceHolder.getDS(multiDataSourceHolder.getDataSourceMap().keySet().stream().findFirst().get().toString()));

        multiRoutingDataSource.setTargetDataSources(multiDataSourceHolder.getDataSourceMap());
        return multiRoutingDataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                multiEntityManager().getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.implicit_naming_strategy", "jpa");
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        return properties;
    }
}
