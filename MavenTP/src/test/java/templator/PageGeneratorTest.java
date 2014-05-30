package templator;

import freemarker.template.TemplateException;
import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import resourceSystem.ResourceSystem;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Alena on 5/29/14.
 */
public class PageGeneratorTest {
    private static ResourceSystem resourceSystem = ResourceSystem.getInstance();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPageGeneratorSuccess() throws Exception
    {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("userId", "156");

        String page = PageGenerator.getPage(Templates.INDEX, pageVariables);

        for(Object o: pageVariables.entrySet())
        {
            Map.Entry pair = (Map.Entry) o;
            String value = (String) pair.getValue();

            Assert.assertTrue(page.contains(value));
        }
    }

    @Test(expected = TemplateException.class)
    public void testPageGeneratorFail() throws Exception
    {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("userId", "156");

        String page = PageGenerator.getPage("testPageGeneratorFail.tml", pageVariables);
        //exception.expect(FileNotFoundException.class);

        Assert.assertTrue(page.isEmpty());
    }
}
