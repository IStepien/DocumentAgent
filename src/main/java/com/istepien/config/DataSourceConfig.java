package com.istepien.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Bean(initMethod="start",destroyMethod="stop")
    public org.h2.tools.Server h2WebConsonleServer () throws SQLException {
        return org.h2.tools.Server.createWebServer("-web","-webAllowOthers","-webDaemon","-webPort", "8083");
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"com.istepien"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/docEx;DB_CLOSE_DELAY=-1;");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty(
                        "hibernate.hbm2ddl.auto", "create-drop");
                setProperty(
                        "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }
    @Bean
    InitializingBean init() throws SQLException {
        Connection conn = dataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO ROLES (ROLEID, ROLENAME) VALUES (3, 'ROLE_ADMIN');\n" +
                "INSERT INTO ROLES (ROLEID, ROLENAME) VALUES (2, 'ROLE_MODERATOR');\n" +
                "INSERT INTO ROLES (ROLEID, ROLENAME) VALUES (1, 'ROLE_USER');" +
                "INSERT  INTO USERS ( USERID, USERNAME, EMAIL, FIRSTNAME, LASTNAME, PASSWORD) VALUES (1, 'asd', 'asd', 'asd', 'asd',  '$2a$10$a6JP9sfC8YPwOd4aY1Xdse7kj0gNVvAIR7hcf.gPtPFOqQLSkNyfG');" +
                "INSERT INTO USER_ROLE  (USERID, ROLEID) VALUES (1, 1);" +
                "INSERT INTO USER_ROLE  (USERID, ROLEID) VALUES (1, 2);" +
                "INSERT INTO USER_ROLE  (USERID, ROLEID) VALUES (1, 3);" );
        return () -> {
        };
    }


}