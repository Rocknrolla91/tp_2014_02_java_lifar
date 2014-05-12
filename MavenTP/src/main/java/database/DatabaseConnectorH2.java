package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Alena on 4/5/14.
 */
public class DatabaseConnectorH2 extends DatabaseConnector {

    public void configureConnection(Configuration configuration)
    {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        String url = "jdbc:h2:mem:mydb;" +
                "INIT=RUNSCRIPT FROM '../db/myDB1.sql';MVCC=true";
        configuration.setProperty("hibernate.connection.url", url/*+ "INIT=RUNSCRIPT FROM '/myDB1.sql';MVCC=true"*/);
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.show_sql", "true");
       // configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
    }
}
