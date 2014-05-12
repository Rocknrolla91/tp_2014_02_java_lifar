package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Alena on 10.03.14.
 */
public abstract class DatabaseConnector {
    private SessionFactory sessionFactory;

    public DatabaseConnector() {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();

        configureConnection(cfg);
        configureDataSets(cfg);
        configureSessionBuilder(cfg);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected abstract void configureConnection(org.hibernate.cfg.Configuration cfg);

    private void configureDataSets(org.hibernate.cfg.Configuration cfg) {
        cfg.addAnnotatedClass(AccountsDataSet.class);
    }

    private void configureSessionBuilder(org.hibernate.cfg.Configuration cfg) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(cfg.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        this.sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }
}
