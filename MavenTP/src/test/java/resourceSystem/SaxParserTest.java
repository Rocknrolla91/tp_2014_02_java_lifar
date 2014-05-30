package resourceSystem;

import org.junit.Assert;
import org.junit.Test;
import templator.PagePath;

import javax.xml.parsers.SAXParser;

/**
 * Created by Alena on 5/30/14.
 */
public class SaxParserTest {
    @Test
    public void testParse() throws Exception
    {
        String xml =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<class name=\"templator.PagePath\">" +
            "<INDEX_P>/index</INDEX_P>" +
            "<AUTH_P>/auth</AUTH_P>" +
            "<REGIST_P>/regist</REGIST_P>" +
            "<TIMER_P>/timer</TIMER_P>" +
            "<WAIT_P>/wait</WAIT_P>" +
            "</class>";

        PagePath pagePath = (PagePath) SaxParser.parse(xml);
        Assert.assertTrue(PagePath.AUTH_P.equals("/auth"));
    }
}
