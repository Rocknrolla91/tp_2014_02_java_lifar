package resourceSystem;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.jar.Attributes;

/**
 * Created by Alena on 5/18/14.
 */
public class SaxHandler extends DefaultHandler {
    private static String CLASS_NAME = "class";
    private String element = null;
    private Resource resource = null;

    @Override
    public void startDocument() throws SAXException
    {
        System.out.println("Start document");
    }

    @Override
    public void endDocument() throws SAXException
    {
        System.out.println("End document");
    }

    @Override
    public void startElement (String uri, String localName,
                              String qName, org.xml.sax.Attributes attributes)
            throws SAXException
    {
        if(qName != CLASS_NAME){
            element = qName;
        }
        else{
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            resource = (Resource) ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement (String uri, String localName, String qName)
            throws SAXException
    {
        element = null;
    }

    @Override
    public void characters (char ch[], int start, int length)
            throws SAXException
    {
        if(element != null)
        {
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(resource, element, value);
        }
    }

    public Resource getResource()
    {
        return resource;
    }
}
