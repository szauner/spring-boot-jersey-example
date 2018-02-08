package de.szse.service;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.szse.service.model.JpaMarkerModel;
import de.szse.service.persistence.JpaMarkerRepositories;

/**
 * The Main class of the REST service application, which allows to start the application via Spring Boot.
 */
@SpringBootApplication
@EnableJpaRepositories(
    enableDefaultTransactions = false,
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackageClasses = {JpaMarkerRepositories.class}
)
@EnableTransactionManagement
public class ExampleServiceApplication extends SpringBootServletInitializer {
    private final static Logger log = LogManager.getLogger(ExampleServiceApplication.class);

    public static void main(String[] args) {
        new ExampleServiceApplication()
                .configure(new SpringApplicationBuilder(ExampleServiceApplication.class))
                .run(args);
    }

    /**
     * These static classes are required because it makes it possible to use
     * different properties files for every Spring profile.
     *
     * See: <a href="http://stackoverflow.com/a/14167357/313554">This StackOverflow answer</a> for more details.
     */
    @Profile(Profiles.PRODUCTION)
    @Configuration
    @PropertySource("classpath:application.properties")
    static class ApplicationProperties {}


    /**
     * Creates and configures a data-source bean that manages the connection to the persistent storage.
     *
     * @param env the runtime environment of the application
     *
     * @return the data-source ready for usage
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource(Environment env) {
        // Use the Tomcat Connection Pool as data source
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));
        ds.setUrl(env.getRequiredProperty("db.url"));
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));
        ds.setInitialSize(env.getRequiredProperty("db.pool.initialSize", Integer.class));
        ds.setMaxActive(env.getRequiredProperty("db.pool.maxActive", Integer.class));
        ds.setMinIdle(env.getRequiredProperty("db.pool.minIdle", Integer.class));
        ds.setMaxIdle(env.getRequiredProperty("db.pool.maxIdle", Integer.class));
        ds.setJmxEnabled(env.getRequiredProperty("db.pool.jmxEnabled", Boolean.class));
        return ds;
    }

    /**
     *
     * For the configuration of the factory see the file 'hibernate.properties'.
     *
     * @param dataSource the object that enables the entity manager factory to connect to the persistent storage
     * @param env the runtime environment of the application
     *
     * @return an entity manager factory bean ready for usage
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setDatabase(Database.H2);
      vendorAdapter.setGenerateDdl(true);

      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setJpaVendorAdapter(vendorAdapter);
      factory.setDataSource(dataSource);

      String entityPackage = JpaMarkerModel.class.getPackage().getName();
      log.info("EntityManager will scan for entities in package [{}].", entityPackage);
      factory.setPackagesToScan(entityPackage);

      return factory;
    }

    /**
     * Creates a transaction manager bean and connects it to the entity manager factory.
     *
     * @param entityManagerFactory  the used JPA entity manager factory
     *
     * @return a transaction manager ready for usage
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}