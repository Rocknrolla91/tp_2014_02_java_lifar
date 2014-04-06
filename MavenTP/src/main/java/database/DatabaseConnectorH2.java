package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Alena on 4/5/14.
 */
public class DatabaseConnectorH2 implements DatabaseConnector {

    private Configuration configuration = new Configuration();
    private SessionFactory sessionFactory = createSessionFactory(configuration);
    private void setDataSets()
    {
        configuration.addAnnotatedClass(AccountsDataSet.class);
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    @Override
    public SessionFactory createSessionFactory(Configuration configuration)
    {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:java_db;INIT=RUNSCRIPT FROM 'src/myDB1.sql';MVCC=true");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "vodolaz456");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        setDataSets();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
