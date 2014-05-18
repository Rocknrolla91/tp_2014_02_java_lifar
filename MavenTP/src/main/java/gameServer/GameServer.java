package gameServer;

import database.*;
import frontend.Frontend;
import messageSystem.MessageSystem;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.server.Handler;
import resourceSystem.ResourceSystem;
import templator.PagePath;

/**
 * Created by Alena on 2/20/14.
 */

public class GameServer {
    Server server;
    boolean running = false;

    public GameServer(int portNumber, DatabaseConnector databaseConnector) throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        AccountService accountService = new AccountServiceImpl(databaseConnector, messageSystem);
        Frontend frontend = new Frontend(messageSystem);

        ResourceSystem resourceSystem = ResourceSystem.getInstance();

        new Thread(frontend).start();
        new Thread(accountService).start();

        server = new Server(portNumber);

        RewriteHandler rewriteHandler = new RewriteHandler();

        rewriteHandler.setRewriteRequestURI(false);
        rewriteHandler.setRewritePathInfo(false);
        rewriteHandler.setOriginalPathAttribute("requestedPath");
        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");
        rule.setReplacement("/index");
        rule.setHandling(true);

        rewriteHandler.addRule(rule);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{rewriteHandler, resourceHandler, context});
        server.setHandler(handlers);
    }

    public void start() throws Exception {
        server.start();
        running = true;
        server.join();
        running = false;

    }
}