package corejava.corejava2.ch02;

import java.io.*;
import java.net.*;
import javax.xml.stream.*;

/**
 * This program demonstrates how to use a StAX parser. The program prints all hyperlinks links of an
 * XHTML web page. <br>
 * Usage: java StAXTest url
 * 
 * @author Cay Horstmann
 * @version 1.0 2007-06-23
 */
public class CH02_05_StAXTest {
    public static void main(String[] args) throws Exception {
        String urlString;
        if (args.length == 0) {
            urlString = "http://www.w3c.org";
            System.out.println("Using " + urlString);
        } else
            urlString = args[0];
        URL url = new URL(urlString);
        InputStream in = url.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();    // A XMLInputFactory
        XMLStreamReader parser = factory.createXMLStreamReader(in); // B XMLStreamReader
        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {    // 判断事件的类型
//                parser.isStartElement(); // 也可以用这种方法
//                parser.isEndElement();
//                parser.isCharacters();
//                parser.isWhiteSpace();
                
                if (parser.getLocalName().equals("a")) {    // 可以获得与事件相关的元素的内容
//                    parser.getName();
//                    parser.getLocalName();
//                    parser.getText();
                    String href = parser.getAttributeValue(null, "href");
                    if (href != null)
                        System.out.println(href);
                }
            }
        }
    }
}
