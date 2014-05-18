package gameServer;

import database.*;
import frontend.Frontend;
import messageSystem.MessageSystem;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.server.Handler;

/**
 * Created by Alena on 2/20/14.
 */

public class Server {
    private int portNumber = 8080;
    private DatabaseConnector databaseConnector;
    org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(portNumber);

    public Server() {
        this.databaseConnector = new DatabaseConnectorMySQL();

    }

    public Server(int portNumber, DatabaseConnector databaseConnector)
    {
        this.portNumber = portNumber;
        this.databaseConnector = databaseConnector;
    }

    public void run() throws Exception {
        server.setHandler(getServerHandlers());

        server.start();
        server.join();
    }

    public void stop() throws Exception
    {
        server.stop();
    }

    private HandlerList getServerHandlers() {
        MessageSystem messageSystem = MessageSystem.getInstance();
        AccountService accountService1 = new AccountServiceImpl(databaseConnector, messageSystem);
        Frontend frontend = new Frontend(messageSystem);

        (new Thread(frontend)).start();
        (new Thread(accountService1)).start();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/regist");
        context.addServlet(new ServletHolder(frontend), "/auth");
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setOriginalPathAttribute("requestedPath");

        RedirectRegexRule regRule = new RedirectRegexRule();
        regRule.setRegex("/");
        regRule.setReplacement("/index");
        rewriteHandler.addRule(regRule);

        HandlerList handList = new HandlerList();
        handList.setHandlers(new Handler[]{rewriteHandler, resourceHandler, context});

        return handList;
    }

    public static void main (String[] args) throws Exception {

        Server server = new Server();
        server.run();
    }
}
