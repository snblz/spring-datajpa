package by.koles.springdatajpa.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = {
        "by.koles.springdatajpa"
})
@EnableTransactionManagement
@PropertySource("classpath:postgresql.properties")
@ComponentScan("by.koles.springdatajpa")
public class PersistenceContext {

	@Autowired
	private Environment env;
	
	@Bean(destroyMethod = "close")
	DataSource dataSource() {
		//create connection pool
				ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
				
				//set the jdbc driver class
				try {
					pooledDataSource.setDriverClass(env.getProperty("jdbc.driver"));
				} catch (PropertyVetoException e) {
					throw new RuntimeException(e);
				}
				
				//set database connection properties
				pooledDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
				pooledDataSource.setUser(env.getProperty("jdbc.user"));
				pooledDataSource.setPassword(env.getProperty("jdbc.password"));
				
				//set connection pool properties
				pooledDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
				pooledDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
				pooledDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
				pooledDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
				
				return pooledDataSource;
	}

	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("by.koles.springdatajpa");

		Properties jpaProperties = new Properties();

		// Configures the used database dialect. This allows Hibernate to create SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));

		// Specifies the action that is invoked to the database when the Hibernate
		// SessionFactory is created or closed.
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));

		
		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}
	
	@Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
