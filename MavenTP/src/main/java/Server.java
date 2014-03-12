
import database.AccountsDAO;
import database.DataService;
import database.DatabaseConnector;
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

    public Server() {
        this.databaseConnector = new DatabaseConnector();
    }

    public void run() throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(portNumber);

        server.setHandler(getServerHandlers());

        server.start();
        server.join();
    }

    private HandlerList getServerHandlers() {
        AccountsDAO userDAO = new AccountsDAO(databaseConnector.getSessionFactory());
        DataService dataService = new DataService(userDAO);
        Frontend frontend = new Frontend(dataService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/regist");
        context.addServlet(new ServletHolder(frontend), "/auth");
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
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
