package corejava.corejava1.ch10;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * @version 1.4 2007-04-30
 * @author Cay Horstmann
 */
public class Ch10_02_ResourceTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ResourceTestFrame frame = new ResourceTestFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that loads image and text resources.
 */
@SuppressWarnings("serial")
class ResourceTestFrame extends JFrame {
    public ResourceTestFrame() {
        setTitle("ResourceTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 读取图像资源,在这个类所在的路径中找资源文件
        URL aboutURL = getClass().getResource("about.gif");
        Image img = Toolkit.getDefaultToolkit().getImage(aboutURL);
        setIconImage(img);

        // 读取文本资源
        JTextArea textArea = new JTextArea();
        InputStream stream = getClass().getResourceAsStream("about.txt");
        Scanner in = new Scanner(stream);
        while (in.hasNext())
            textArea.append(in.nextLine() + "\n");
        add(textArea);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 300;
}
