package database;

import org.hibernate.cfg.Configuration;

/**
 * Created by Alena on 5/12/14.
 */
public class DatabaseConnectorHsqldb extends DatabaseConnector{

    public void configureConnection(Configuration configuration)
    {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:mem:testdb";// +
        //"INIT=RUNSCRIPT FROM '../db/myDB1.sql';MVCC=true";
        configuration.setProperty("hibernate.connection.url", url);
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
    }
}
