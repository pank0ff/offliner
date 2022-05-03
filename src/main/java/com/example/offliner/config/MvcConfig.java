package com.example.offliner.config;

import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = {"src/main/resources/db.migration"};

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway fly = new Flyway();
        String url = "jdbc:postgresql://localhost/offliner";
        String user = "postgres";
        String password = "CUmmon1610";
        fly.setDataSource(url, user, password);
        fly.setBaselineOnMigrate(true);
        fly.setSchemas("SBA_DIALOG");
        fly.setLocations("filesystem:C:/dev/offliner/src/main/resources/db.migration");
        fly.migrate();
        return fly;
    }

    @Bean(name = "entityManagerFactory")
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());

        return entityManagerFactoryBean;
    }

    private HibernateJpaVendorAdapter vendorAdaptor() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:postgresql://localhost/offliner";
        String username = "postgres";
        String password = "CUmmon1610";

        return DataSourceBuilder.create().username(username).password(password).url(url)
                .build();
    }

    private Properties addProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "false");
        return properties;
    }

}
