package com.irit.xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by mkostiuk on 12/06/2017.
 */
public class LecteurXml {

    private int x, y;
    private String udn;

    public LecteurXml(String xml) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean isUdn = false;
            boolean isX = false;
            boolean isY = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("UDN")) {
                    isUdn = true;
                }
                if (qName.equalsIgnoreCase("X")) {
                    isX = true;
                }
                if (qName.equalsIgnoreCase("Y")) {
                    isY = true;
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {

            }

            public void characters(char ch[], int start, int length) {
                if (isUdn) {
                    isUdn = false;
                    udn = new String(ch, start, length);
                }
                if (isX) {
                    isX = false;
                    x = Integer.getInteger(new String(ch, start, length));
                }
                if (isY) {
                    isY = false;
                    y = Integer.getInteger(new String(ch, start, length));
                }
            }
        };
        sp.parse(new InputSource(new StringReader(xml)), handler);
    }

    public String getUdn() {
        return udn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
