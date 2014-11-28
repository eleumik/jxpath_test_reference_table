package org.apache.commons.jxpath.referencetable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class JAXP
{
    public static Document getDocument(InputSource is)
    throws Exception
    {

        final DocumentBuilder builder = getDocumentBuilder();
        return builder.parse(is);
    }

    private static DocumentBuilder getDocumentBuilder()
    {
        try
        {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            factory.setExpandEntityReferences(false);
            return factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new Error("JAXP config error:" + e.getMessage(), e);
        }

    }

}