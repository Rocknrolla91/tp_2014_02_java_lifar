package resourceSystem;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Alena on 5/30/14.
 */
public class DOMParcerTest {
    @Test
    public void testParse() throws Exception
    {
        String xml =
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
        "<config name=\"server\">" +
        "<property name=\"portNumber\">8080</property>" +
        "</config>";

        Map<String, String> config = DOMParser.parse(xml);
        Assert.assertTrue(config.get("portNumber").equals("8080"));
    }
}
