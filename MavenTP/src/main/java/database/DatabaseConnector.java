package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import resourceSystem.VirtualFileSystem;
import resourceSystem.VirtualFileSystemImpl;

import java.util.Map;

/**
 * Created by Alena on 10.03.14.
 */
public class DatabaseConnector {
    private SessionFactory sessionFactory;

    public DatabaseConnector(Map<String, String> config) {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();

        cfg.addAnnotatedClass(AccountsDataSet.class);
        for(Map.Entry<String, String> entry: config.entrySet())
        cfg.setProperty(entry.getKey(), entry.getValue());

        sessionFactory = createSessionFactory(cfg);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
    builder.applySettings(configuration.getProperties());
    ServiceRegistry serviceRegistry = builder.build();
    return configuration.buildSessionFactory(serviceRegistry);
}
}
