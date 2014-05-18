package resourceSystem;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alena on 5/18/14.
 */
public class SAXParser {
    public static Resource parse(String fileXML) throws ParserException {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();

            InputStream inputStream = new ByteArrayInputStream(fileXML.getBytes("UTF-8"));
            inputStream.close();

            return handler.getResource();
        }
        catch (ParserConfigurationException | UnsupportedEncodingException e) {
            throw new ParserException(e);
        } catch (IOException | SAXException e) {
            throw new ParserException(e);
        }
    }
}
