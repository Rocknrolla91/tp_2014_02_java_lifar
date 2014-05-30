package database;

import junit.framework.Assert;
import org.hibernate.Session;
import org.junit.Test;
import resourceSystem.ResourceSystem;

/**
 * Created by Alena on 5/30/14.
 */
public class DatabaseConnectorTest {
    DatabaseConnector databaseConnector;

    @Test
    public void testGetSessionFactoryMySQL() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();

        databaseConnector = new DatabaseConnector(resourceSystem.getConfigFile("MySQL"));
        Session session = databaseConnector.getSessionFactory().openSession();
        Assert.assertTrue(session.isConnected());
        session.close();
    }

    @Test
    public void testGetSessionFactoryH2() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();

        databaseConnector = new DatabaseConnector(resourceSystem.getConfigFile("H2"));
        Session session = databaseConnector.getSessionFactory().openSession();
        Assert.assertTrue(session.isConnected());
        session.close();
    }
}
