package corejava.corejava2.ch02;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * This program displays an XML document as a tree.
 * 
 * @version 1.11 2007-06-24
 * @author Cay Horstmann
 */
public class CH02_01_DOMTreeTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new DOMTreeFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * This frame contains a tree that displays the contents of an XML document.
 */
@SuppressWarnings("serial")
class DOMTreeFrame extends JFrame {
    public DOMTreeFrame() {
        setTitle("DOMTreeTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

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
        final File file = chooser.getSelectedFile();

        // 为了在打开文件的过程中，swing不死，引入SwingWorker类
        new SwingWorker<Document, Void>() {
            //在用户线程中调用
            protected Document doInBackground() throws Exception {
                if (builder == null) {  // 防止重复创建DocumentBuilderFactory的实例
                    // A 要读入一个文件首先要从DocumentBuilderFactory中获得一个Document对象
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
                    builder = factory.newDocumentBuilder();
                }
                return builder.parse(file); // B 可以从文件中读入某个文档
                // B 也可以从一个URL或者指定任意的输入流InputStream（如果是输入流的话，解析器将无法定位）
            }
            
            //done函数会在事件调度线程中调用
            protected void done() {
                try {
                    Document doc = get();   // 返回doInBackground函数执行的结果
                    JTree tree = new JTree(new DOMTreeModel(doc));
                    tree.setCellRenderer(new DOMTreeCellRenderer());

                    setContentPane(new JScrollPane(tree));
                    validate(); // 重新布局
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(DOMTreeFrame.this, e);
                }
            }
        }.execute(); // 执行线程
    }

    private DocumentBuilder  builder;
    private static final int DEFAULT_WIDTH  = 400;
    private static final int DEFAULT_HEIGHT = 400;
}

/**
 * This tree model describes the tree structure of an XML document.
 */
class DOMTreeModel implements TreeModel {
    /**
     * Constructs a document tree model.
     * 
     * @param doc
     *            the document
     */
    public DOMTreeModel(Document doc) {
        this.doc = doc;
    }

    public Object getRoot() {
        // C 获得根节点
        return doc.getDocumentElement();
    }

    public int getChildCount(Object parent) {
        Node node = (Node) parent;
        NodeList list = node.getChildNodes();
        return list.getLength();
    }

    public Object getChild(Object parent, int index) {
        Node node = (Node) parent;
        NodeList list = node.getChildNodes();   // D 根据一个节点获得其他节点
        // D 1 如果要遍历这个节点的子节点,可以用以下的方法来忽略空白字符
/*        for (int i = 0; i < list.getLength(); i++) {
            Node aChild= list.item(i);
            if (aChild instanceof Element) {
                Element childElement = (Element) aChild;
                //
            }
        }*/
        
        // D 2 如果某一个节点的子元素是Text,就可以用以下的方法来得到文本
/*        Element childElement = (Element) aChild;
        Text textNode = (Text)childElement.getFirstChild();
        String text = textNode.getData();*/
        
        // D 3 还有种遍历方法是用getNextSibling得到下一个兄弟节点
/*        for (Node childNode = element.getFirstChild(); //getLase
            childNode!= null;childNode = childNode.getNextSibling()) {
            //在这里加入各个节点的处理代码
            // 当然也可以用element.getLastChild() 和 childNode.getPreviousSibling();实现反向遍历
        }*/
        return list.item(index);
    }

    public int getIndexOfChild(Object parent, Object child) {
        Node node = (Node) parent;
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
            if (getChild(node, i) == child)
                return i;
        return -1;
    }

    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {}

    public void addTreeModelListener(TreeModelListener l) {}

    public void removeTreeModelListener(TreeModelListener l) {}

    private Document doc;
}

/**
 * This class renders an XML node.
 */
class DOMTreeCellRenderer extends DefaultTreeCellRenderer {
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        Node node = (Node) value;
        if (node instanceof Element)
            return elementPanel((Element) node);

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (node instanceof CharacterData)
            setText(characterString((CharacterData) node));
        else
            setText(node.getClass() + ": " + node.toString());
        return this;
    }

    public static JPanel elementPanel(Element e) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Element: " + e.getTagName()));
        final NamedNodeMap map = e.getAttributes(); // E 得到这个节点的所有属性(遍历方法类似)
        panel.add(new JTable(new AbstractTableModel() {
            public int getRowCount() {
                return map.getLength();
            }

            public int getColumnCount() {
                return 2;
            }

            public Object getValueAt(int r, int c) {
                return c == 0 ? map.item(r).getNodeName() : map.item(r).getNodeValue();
            }
        }));
        return panel;
    }

    // 将'\n'和'\r'用 '\\n'和 '\\r'
    public static String characterString(CharacterData node) {
        StringBuilder builder = new StringBuilder(node.getData());
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '\r') {
                builder.replace(i, i + 1, "\\r");
                i++;
            } else if (builder.charAt(i) == '\n') {
                builder.replace(i, i + 1, "\\n");
                i++;
            } else if (builder.charAt(i) == '\t') {
                builder.replace(i, i + 1, "\\t");
                i++;
            }
        }
        if (node instanceof CDATASection)
            builder.insert(0, "CDATASection: ");
        else if (node instanceof Text)
            builder.insert(0, "Text: ");
        else if (node instanceof Comment)
            builder.insert(0, "Comment: ");

        return builder.toString();
    }
}
