package corejava.corejava1.ch10;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

import javax.swing.*;

/**
 * 一个演示属性设置使用方法的程序。这个程序记住框架的位置，大小及标题。
 * 
 * @version 1.00 2007-04-29
 * @author Cay Horstmann
 */
public class Ch10_03_PropertiesTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                PropertiesFrame frame = new PropertiesFrame();
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that restores position and size from a properties file and updates
 * the properties upon exit.
 */
@SuppressWarnings("serial")
class PropertiesFrame extends JFrame {
    public PropertiesFrame() {
        // get position, size, title from properties
        // System.getProperty获得系统属性
        String userDir = System.getProperty("user.home");       // 获得用户的主目录
        File propertiesDir = new File(userDir, ".corejava");    // 在主目录下创建.corejava目录下
        if (!propertiesDir.exists())
            propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "program.properties"); // 将属性映射文件设定为program.properties

        Properties defaultSettings = new Properties();  // 提供一个默认的属性
        defaultSettings.put("left", "0");
        defaultSettings.put("top", "0");
        defaultSettings.put("width", "" + DEFAULT_WIDTH);
        defaultSettings.put("height", "" + DEFAULT_HEIGHT);
        defaultSettings.put("title", "");

        settings = new Properties(defaultSettings);

        if (propertiesFile.exists())
            try {
                FileInputStream in = new FileInputStream(propertiesFile);   // 再以
                settings.load(in);          // 加载配置文件中配置文件中的设置
                                   // 用配置文件中的设置覆盖原设置
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        // 属性映射的键和值都是字符串（使用Ch10_04中的例子支持更多类型的属性）
        int left = Integer.parseInt(settings.getProperty("left"));
        int top = Integer.parseInt(settings.getProperty("top"));
        int width = Integer.parseInt(settings.getProperty("width"));
        int height = Integer.parseInt(settings.getProperty("height"));
        setBounds(left, top, width, height);

        // if no title given, ask user
        String title = settings.getProperty("title");
        if (title.equals(""))
            title = JOptionPane.showInputDialog("Please supply a frame title:");
        if (title == null)
            title = "";
        setTitle(title);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                // 保存上次关闭程序时，窗口的位置、大小和标题
                settings.put("left", "" + getX());
                settings.put("top", "" + getY());
                settings.put("width", "" + getWidth());
                settings.put("height", "" + getHeight());
                settings.put("title", getTitle());
                try {
                    FileOutputStream out = new FileOutputStream(propertiesFile);
                    settings.store(out, "Program Properties");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    private File            propertiesFile;
    private Properties      settings;

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}
