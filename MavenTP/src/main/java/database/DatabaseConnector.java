package database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Alena on 10.03.14.
 */
public interface DatabaseConnector {
    public SessionFactory createSessionFactory(Configuration configuration);
    public SessionFactory getSessionFactory();
}
