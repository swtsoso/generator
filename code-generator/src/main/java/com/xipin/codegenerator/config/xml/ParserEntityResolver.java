package com.xipin.codegenerator.config.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserEntityResolver implements EntityResolver {

    /**
	 *  
	 */
    public ParserEntityResolver() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
     * java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        if (XmlConstants.GENERATOR_PUBLIC_ID.equalsIgnoreCase(publicId)) {
            InputStream is = getClass().getClassLoader().getResourceAsStream(
                    "generator-config.dtd"); 
            InputSource ins = new InputSource(is);

            return ins;
        } else {
            return null;
        }
    }
}