package com.revature.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {

    public String[] parse(String path){

        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("table");
            System.out.println("----------------------------");
            String[] str=new String[nList.getLength()+3];

            for (int i = 3; i < nList.getLength()+3; i++) {
                Node nNode = nList.item(i-3);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    str[i]=eElement
                            .getElementsByTagName("name")
                            .item(0)
                            .getTextContent();

                }

            }
            nList = doc.getElementsByTagName("db_properties");
            Element eElement = (Element) nList.item(0);
            str[0]=eElement
                    .getElementsByTagName("url")
                    .item(0)
                    .getTextContent();
            str[1]=eElement
                    .getElementsByTagName("admin")
                    .item(0)
                    .getTextContent();
            str[2]=eElement
                    .getElementsByTagName("pw")
                    .item(0)
                    .getTextContent();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }



}
