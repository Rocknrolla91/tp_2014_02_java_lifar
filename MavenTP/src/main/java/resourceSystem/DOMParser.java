package resourceSystem;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alena on 5/18/14.
 */
public class DOMParser {
    public static Map<String, String> parse(String fileXML) throws ParserException
    {
        Map<String, String> result = new HashMap<>();

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream inputStream = new ByteArrayInputStream((fileXML.getBytes("UTF-8")));
            Document document = builder.parse(inputStream);
            inputStream.close();

            NodeList children = document.getDocumentElement().getElementsByTagName("property");
            for(int i = 0; i < children.getLength(); ++i)
            {
                Node node = children.item(i);
                NamedNodeMap attributes = node.getAttributes();
                String name = attributes.getNamedItem("name").getNodeValue();
                String value = node.getTextContent();

                result.put(name, value);
            }
        } catch (ParserConfigurationException | UnsupportedEncodingException e) {
            throw new ParserException(e);
        } catch (SAXException | IOException e) {
            throw new ParserException(e);
        }
        return result;
    }
}
