package corejava.corejava1.ch10;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.*;
import javax.swing.*;

/**
 * 一个演示属性设置使用方法的程序。这个程序记住框架的位置，大小及标题。
 * 
 * @version 1.02 2007-06-12
 * @author Cay Horstmann
 */
public class Ch10_04_PreferencesTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                PreferencesFrame frame = new PreferencesFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that restores position and size from user preferences and updates the
 * preferences upon exit.
 */
@SuppressWarnings("serial")
class PreferencesFrame extends JFrame {
    public PreferencesFrame() {
        // get position, size, title from preferences
        // 为了访问树中节点，要从用户或系统的根开始
        Preferences root = Preferences.userRoot();// 系统的根为Preferences.systemRoot();
        // 然后访问这个节点，需要直接提供节点的路径名
        final Preferences node = root.node("/com/chenmingjian/ch10");
        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        // node中可以存放String,Int,Long,Float,Double,Boolean,BateArray等类型的值
        setBounds(left, top, width, height);

        // 如果没有标题，要求用户输入
        String title = node.get("title", "");
        if (title.equals(""))
            title = JOptionPane.showInputDialog("Please supply a frame title:");
        if (title == null)
            title = "";
        setTitle(title);

        // set up file chooser that shows XML files
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // accept all files ending with .xml
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
            }

            public String getDescription() {
                return "XML files";
            }
        });

        // set up menus
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem exportItem = new JMenuItem("Export preferences");
        menu.add(exportItem);
        exportItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (chooser.showSaveDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        OutputStream out = new FileOutputStream(chooser.getSelectedFile());
                        // 可以用下面的方法将一个节点的全部信息输出（并以xml文件的形式保存）
                        node.putInt("left", getX());
                        node.putInt("top", getY());
                        node.putInt("width", getWidth());
                        node.putInt("height", getHeight());
                        node.put("title", getTitle());
                        node.exportSubtree(out);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        JMenuItem importItem = new JMenuItem("Import preferences");
        menu.add(importItem);
        importItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (chooser.showOpenDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        InputStream in = new FileInputStream(chooser.getSelectedFile());
                        int left = node.getInt("left", 0);
                        int top = node.getInt("top", 0);
                        int width = node.getInt("width", DEFAULT_WIDTH);
                        int height = node.getInt("height", DEFAULT_HEIGHT);
                        // node中可以存放String,Int,Long,Float,Double,Boolean,BateArray等类型的值
                        setBounds(left, top, width, height);

                        // 如果没有标题，要求用户输入
                        String title = node.get("title", "");
                        if (title.equals(""))
                            title = JOptionPane.showInputDialog("Please supply a frame title:");
                        if (title == null)
                            title = "";
                        setTitle(title);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
                node.put("title", getTitle());
                System.exit(0);
            }
        });
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}
