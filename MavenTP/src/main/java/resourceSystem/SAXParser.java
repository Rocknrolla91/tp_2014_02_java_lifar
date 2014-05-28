package resourceSystem;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alena on 5/24/14.
 */
public class SaxParser {
    public static Resource parse(String fileXML) throws ParserException {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            InputStream inputStream = new ByteArrayInputStream(fileXML.getBytes("UTF-8"));
            Resource resource = parse(saxParser, inputStream);
            inputStream.close();

            return resource;
        }
        catch (ParserConfigurationException | UnsupportedEncodingException e) {
            throw new ParserException(e);
        } catch (IOException | SAXException e) {
            throw new ParserException(e);
        }
    }

    private static Resource parse(SAXParser parser, InputStream inputStream) throws SAXException, IOException {
        SaxHandler saxHandler = new SaxHandler();
        parser.parse(inputStream, saxHandler);
        return saxHandler.getResource();
    }
}
