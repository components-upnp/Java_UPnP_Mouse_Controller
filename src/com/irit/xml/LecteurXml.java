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

    private float x, y;
    private String udn, commande;

    public LecteurXml(String xml) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean isUdn = false;
            boolean isX = false;
            boolean isY = false;
            boolean isCommande = false;

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
                if (qName.equalsIgnoreCase("Commande")) {
                    isCommande = true;
                }
            }

            @Override
            public void characters(char ch[], int start, int length) {
                if (isUdn) {
                    isUdn = false;
                    udn = new String(ch, start, length);
                }
                if (isX) {
                    isX = false;
                    String temp = new String(ch, start, length);
                    System.out.println(temp);
                    x =  Float.parseFloat(temp);
                }
                if (isY) {
                    isY = false;
                    String temp = new String(ch, start, length);
                    System.out.println(temp);
                    y = Float.parseFloat(temp);
                }
                if (isCommande) {
                    isCommande = false;
                    commande = new String(ch, start, length);
                }
            }
        };
        sp.parse(new InputSource(new StringReader(xml)), handler);
    }

    public String getUdn() {
        return udn;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getCommande() {
        return commande;
    }
}
