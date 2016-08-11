package corejava.corejava2.ch02.p07;

import java.io.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This program demonstrates XSL transformations. It applies a transformation to a set of employee
 * records. The records are stored in the file employee.dat and turned into XML format. Specify the
 * stylesheet on the command line, e.g. java TransformTest makeprop.xsl
 * 
 * @version 1.01 2007-06-25
 * @author Cay Horstmann
 */
public class TransformTest {
    public static void main(String[] args) throws Exception {
        String filename;
        if (args.length > 0)
            filename = args[0];
        else
            filename = "src/corejava.corejava2/ch02/p07/makehtml.xsl";
        File styleSheet = new File(filename);
        StreamSource styleSource = new StreamSource(styleSheet);  // 1 用xsl文件来构造StreamSource

        Transformer t = TransformerFactory.newInstance().newTransformer(styleSource); // 2 并用StreamSource来构造Transformer
        t.setOutputProperty(OutputKeys.INDENT, "yes");    // 是否缩进
        t.setOutputProperty(OutputKeys.METHOD, "xml");                            // 魔咒
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");    // 魔咒

        // 3 调用transform方法,t.transform(source,result);
        //   A  其中source是一个实现了Source接口的类可以是DOMSource,SAXSource或者StreamSource.
        //      本例中我们不是从一个现有的XML文件中开始工作,而是解析一个文本文件产生SAX事件
        //   B  result则是实现了Result接口的类,可以是DOMResult,SAXResult,StreamResult
        t.transform(new SAXSource(new EmployeeReader(), new InputSource(new FileInputStream("src/corejava.corejava2/ch02/p07/employee.dat"))), new StreamResult(System.out));
    }
}

/**
 * This class reads the flat file employee.dat and reports SAX parser events to act as if it was
 * parsing an XML file.
 */
class EmployeeReader implements XMLReader {
    public void parse(InputSource source) throws IOException, SAXException {
        InputStream stream = source.getByteStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String rootElement = "staff";
        AttributesImpl atts = new AttributesImpl();

        if (handler == null)
            throw new SAXException("No content handler");

        // 用这段代码产生产生一些SAX事件 
        handler.startDocument();    
        handler.startElement("", rootElement, rootElement, atts);
        String line;
        while ((line = in.readLine()) != null) {
            handler.startElement("", "employee", "employee", atts);
            StringTokenizer t = new StringTokenizer(line, "|");

            handler.startElement("", "name", "name", atts);
            String s = t.nextToken();
            handler.characters(s.toCharArray(), 0, s.length());
            handler.endElement("", "name", "name");

            handler.startElement("", "salary", "salary", atts);
            s = t.nextToken();
            handler.characters(s.toCharArray(), 0, s.length());
            handler.endElement("", "salary", "salary");

            atts.addAttribute("", "year", "year", "CDATA", t.nextToken());
            atts.addAttribute("", "month", "month", "CDATA", t.nextToken());
            atts.addAttribute("", "day", "day", "CDATA", t.nextToken());
            handler.startElement("", "hiredate", "hiredate", atts);
            handler.endElement("", "hiredate", "hiredate");
            atts.clear();

            handler.endElement("", "employee", "employee");
        }

        handler.endElement("", rootElement, rootElement);
        handler.endDocument();
    }

    public void setContentHandler(ContentHandler newValue) {
        handler = newValue;
    }

    public ContentHandler getContentHandler() {
        return handler;
    }

    // the following methods are just do-nothing implementations
    public void parse(String systemId) throws IOException, SAXException {}

    public void setErrorHandler(ErrorHandler handler) {}

    public ErrorHandler getErrorHandler() {
        return null;
    }

    public void setDTDHandler(DTDHandler handler) {}

    public DTDHandler getDTDHandler() {
        return null;
    }

    public void setEntityResolver(EntityResolver resolver) {}

    public EntityResolver getEntityResolver() {
        return null;
    }

    public void setProperty(String name, Object value) {}

    public Object getProperty(String name) {
        return null;
    }

    public void setFeature(String name, boolean value) {}

    public boolean getFeature(String name) {
        return false;
    }

    private ContentHandler handler;
}
