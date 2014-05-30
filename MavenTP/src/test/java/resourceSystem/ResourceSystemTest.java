package resourceSystem;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Alena on 5/30/14.
 */
public class ResourceSystemTest {
    @Test
    public void testGetResource() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();
        Assert.assertTrue(resourceSystem.getResource("PagePath") != null);
        Assert.assertTrue(resourceSystem.getResource("Templates") != null);
    }

    @Test
    public void testGetConfigFiles() throws Exception
    {
        ResourceSystem resourceSystem = ResourceSystem.getInstance();
        Map<String, String> configFile = resourceSystem.getConfigFile("Server");
        Assert.assertTrue(Integer.parseInt(configFile.get("portNumber")) == 8080);
    }
}
