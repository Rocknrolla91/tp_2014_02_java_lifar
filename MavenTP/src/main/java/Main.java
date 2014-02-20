
import org.eclipse.jetty.server.Server;

/**
 * Created by Alena on 2/20/14.
 */

public class Main {
    public static void main(String []args) throws Exception{
        runServer();
    }
    private static void runServer() throws Exception{
        Server server = new Server(8080);
        server.setHandler(new JettyServer());
        server.start();
        server.join();
    }

}
