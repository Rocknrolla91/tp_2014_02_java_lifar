package gameServer;

import database.DatabaseConnector;
import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.Timeout;
import resourceSystem.ResourceSystem;

/**
 * Created by Alena on 5/30/14.
 */
public class GameServerTest {

    GameServer gameServer;

    @Rule
    public Timeout timeout = new Timeout(100000);

    @Before
    public void setUp() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();
        String port = resourceSystem.getConfigFile("Server").get("portNumber");

        gameServer = new GameServer(Integer.parseInt(port),
                new DatabaseConnector(resourceSystem.getConfigFile("H2")));
    }

    @After
    public void tearDown() throws Exception
    {
        gameServer.stop();
    }

    private void startServer() {
        Thread gameThread = new Thread(() -> {
            try {
                gameServer.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
    }

    @Test
    public void testStart() throws Exception
    {
        startServer();

        while (!gameServer.isRunning())
        {
            Thread.sleep(100);
        }
        Assert.assertTrue(gameServer.isRunning());
    }

    @Test
    public void testStop() throws Exception
    {
        startServer();
        while (!gameServer.isRunning())
        {
            Thread.sleep(100);
        }

        gameServer.stop();

        Assert.assertFalse(gameServer.isRunning());
    }

    @Test
    public void testRunningServer() throws Exception
    {
        Assert.assertFalse(gameServer.isRunning());
    }
}
