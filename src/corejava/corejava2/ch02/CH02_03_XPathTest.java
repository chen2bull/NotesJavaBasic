package corejava.corejava2.ch02;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.xml.namespace.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * This program evaluates XPath expressions
 * 
 * @version 1.01 2007-06-25
 * @author Cay Horstmann
 */
public class CH02_03_XPathTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new XPathFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/*  XPath的语法说明
 *  /gridbag/row 表示gridbag下的所有row
 *  /gridbag/row[1] 则表示第一个row(索引从1开始)
 *  使用@获得属性值
 *  /gridbag/row[1]/cell[1]/@anchor
 * 
 * */

/**
 * This frame shows an XML document, a panel to type an XPath expression, and a text field to
 * display the result.
 */
class XPathFrame extends JFrame {
    public XPathFrame() {
        setTitle("XPathTest");

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openFile();
            }
        });
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                evaluate();
            }
        };
        expression = new JTextField(20);
        expression.addActionListener(listener);
        JButton evaluateButton = new JButton("Evaluate");
        evaluateButton.addActionListener(listener);

        typeCombo = new JComboBox(new Object[] { "STRING", "NODE", "NODESET", "NUMBER", "BOOLEAN" });
        typeCombo.setSelectedItem("STRING");

        JPanel panel = new JPanel();
        panel.add(expression);
        panel.add(typeCombo);
        panel.add(evaluateButton);
        docText = new JTextArea(10, 40);
        result = new JTextField();
        result.setBorder(new TitledBorder("Result"));

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(docText), BorderLayout.CENTER);
        add(result, BorderLayout.SOUTH);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  // A 创建DocumentBuilderFactory
            builder = factory.newDocumentBuilder();     // B  从DocumentBuilderFactory得到DocumentBuilder
        } catch (ParserConfigurationException e) {
            JOptionPane.showMessageDialog(this, e);
        }

        XPathFactory xpfactory = XPathFactory.newInstance();    // 1 创建XPathFactory
        path = xpfactory.newXPath();        // 2 从XPathFactory得到一个XPath对象
        pack();
    }

    /**
     * Open a file and load the document.
     */
    public void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
            }

            public String getDescription() {
                return "XML files";
            }
        });
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION)
            return;
        File f = chooser.getSelectedFile();     // 得到选择的文件
        try {
            byte[] bytes = new byte[(int) f.length()];
            new FileInputStream(f).read(bytes); 
            docText.setText(new String(bytes)); // 设置TextArea的文本
            doc = builder.parse(f);     // C 从DocumentBuilder得到Document
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (SAXException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void evaluate() {
        try {
            String typeName = (String) typeCombo.getSelectedItem();
            QName returnType = (QName) XPathConstants.class.getField(typeName).get(null);
            // 3 然后调用evaluate方法来计算XPath表达式
            // 对于字符串,节点列表,节点和整数(浮点数的方法和整数类似)分别用以下的方法得到 
            // String string = path.evaluate(expression.getText(), doc);
            // NodeList nodeList = (NodeList)path.evaluate(expression.getText(), doc,XPathConstants.NODESET);
            // Node node = (Node)path.evaluate(expression.getText(), doc,XPathConstants.NODE);
            // int count = ( (Number)path.evaluate(expression.getText(), doc,XPathConstants.NUMBER) ).intValue();
            Object evalResult = path.evaluate(expression.getText(), doc, returnType);
            if (typeName.equals("NODESET")) {
                NodeList list = (NodeList) evalResult;
                StringBuilder builder = new StringBuilder();
                builder.append("{");
                for (int i = 0; i < list.getLength(); i++) {
                    if (i > 0)
                        builder.append(", ");
                    builder.append("" + list.item(i));
                }
                builder.append("}");
                result.setText("" + builder);
            } else
                result.setText("" + evalResult);
            // 4 另外 可以不必从根节点开始搜索 从任意节点开始也是可以的,例如
            // result = path.evaluate(expression,node);
        } catch (XPathExpressionException e) {
            result.setText("" + e);
        } catch (Exception e) // reflection exception
        {
            e.printStackTrace();
        }
    }

    private DocumentBuilder builder;
    private Document        doc;
    private XPath           path;
    private JTextField      expression;
    private JTextField      result;
    private JTextArea       docText;
    private JComboBox       typeCombo;
}
