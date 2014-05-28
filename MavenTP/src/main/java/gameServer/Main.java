package gameServer;

import database.DatabaseConnector;
import resourceSystem.ResourceSystem;
import templator.PagePath;

/**
 * Created by Alena on 5/18/14.
 */
public class Main {
    public static void main(String[] args) throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();
        String port = resourceSystem.getConfigFile("Server").get("portNumber");

//        PagePath pagePath = (PagePath) resourceSystem.getResource("PagePath");

        GameServer gameServer = new GameServer(Integer.parseInt(port),
                new DatabaseConnector(resourceSystem.getConfigFile("MySQL")));
        gameServer.start();
    }
}
