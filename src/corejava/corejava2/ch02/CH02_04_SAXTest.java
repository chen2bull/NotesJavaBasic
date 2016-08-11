package corejava.corejava2.ch02;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This program demonstrates how to use a SAX parser. The program prints all hyperlinks links of an
 * XHTML web page. <br>
 * Usage: java SAXTest url
 * 
 * @version 1.00 2001-09-29
 * @author Cay Horstmann
 */
public class CH02_04_SAXTest {
    public static void main(String[] args) throws Exception {
        String url;
        if (args.length == 0) {
            url = "http://www.w3c.org";
            System.out.println("Using " + url);
        } else
            url = args[0];
        
        DefaultHandler handler = new DefaultHandler() {
            // 遇到开始便签就调用startElement方法
            public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) {
                // 4.namespaceURI是命名空间的URI(如果解析器支持命名空间特性)
                // lname就是本地名(如果解析器支持命名空间特性)
                // qname 元素名(如果解析器不支持命名空间特性)
                // attrs是属性的列表
                if (lname.equals("a") && attrs != null) {
                    for (int i = 0; i < attrs.getLength(); i++) {
                        String aname = attrs.getLocalName(i);
                        if (aname.equals("href"))
                            System.out.println(attrs.getValue(i));
                    }
                }
            }
            // 遇到结束便签就调用endElement方法
            public void endElement(String uri, String localName, String qName) throws SAXException {
                
            }
            // 遇到字符串就调用characters方法
            public void characters(char[] ch, int start, int length) throws SAXException {
                // ch字符数据数组
                
                String content = new String(ch, start, length);  
                /*但是用这个方法会有一点问题，其中ch这个char数组的默认最长长度为2048，如果超过这个长度，将产生一个
                 * 新的char数组（也是2048长度）来存储数据，于是如果我们需要解析的数据在两个char之间的话（分别在一
                 * 个的尾部和下一个的头部），那么我将会得到残缺不全的数据，因此我们当我们读到char数据的末尾时，我们
                 * 需要先暂时存储一下得到的数据，然后补在下一次得到的内容的前面。参考代码(以下被注掉的代碼是有問題的)如下：
                */
                 
//                if (start + length == CHARACTERS_MAX_LENGTH) {
//                    preMaxLengthCharacters = content;
//                } else {
//                  ...
//                }
//                if (start == 0) {
//                    content = preMaxLengthCharacters + content;
//                }
            }
//            final int CHARACTERS_MAX_LENGTH = 2048;  
//            String preMaxLengthCharacters = ""; 
        };
        

        SAXParserFactory factory = SAXParserFactory.newInstance();  // 1 创建SAXParserFactory
        factory.setNamespaceAware(true);    // 打开对命名空间的支持,如果打开了,startElement中的namespaceURI就是命名空间,lname就是本地名
        SAXParser saxParser = factory.newSAXParser();   // 2 从SAXParserFactory得到SAXParser
        InputStream in = new URL(url).openStream();
        saxParser.parse(in, handler); // 3 输入可以是一个文件,一个URL字符串或者是一个输入流
    }
}
