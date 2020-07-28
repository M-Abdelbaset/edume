package com.edume;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringJUnitConfig(classes = {AbstractJpaTest.DevConfig.class})
public abstract class AbstractJpaTest {

	@Autowired private JdbcTemplate jdbcTemplate;
	
	protected int countRows(String table) {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, table);
	}
	
	protected int countRowsWhere(String table, String where) {
		return JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, table, where);
	}
	
	@Profile("dev")
	@Configuration
	public static class DevConfig {
		
		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
					.generateUniqueName(true)
					.setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8")
					.ignoreFailedDrops(true)
					.build();
		}
		
		@Bean
		public SessionFactory sessionFactory() {
			return new LocalSessionFactoryBuilder(dataSource())
					.scanPackages("com.edume.entity")
					.setProperty("hibernate.show_sql", "true")
					.setProperty("hibernate.hbm2ddl.auto", "update")
					.buildSessionFactory();
		}
		
		@Bean
		public PlatformTransactionManager transactionManager() {
			return new HibernateTransactionManager(sessionFactory());
		}
		
		@Bean
		public JdbcTemplate jdbcTemplate() {
			return new JdbcTemplate(dataSource());
		}
	}
}
