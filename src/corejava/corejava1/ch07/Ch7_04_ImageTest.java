package corejava.corejava1.ch07;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Ch7_04_ImageTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageFrame frame = new ImageFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with an image component
 */
@SuppressWarnings("serial")
class ImageFrame extends JFrame {
    public ImageFrame() {
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add component to frame
        ImageComponent component = new ImageComponent();
        add(component);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}

/**
 * A component that displays a tiled image
 */
@SuppressWarnings("serial")
class ImageComponent extends JComponent {
    public ImageComponent() {
        // acquire the image
        String filename = "src/corejava.corejava1/ch7/blue-ball.gif";    // 指定文件名
        try {
            image = ImageIO.read(new File(filename));   // 将这个文件读入
        } catch (IOException e) {
            System.err.println("找不到 " + filename + " 这个文件");
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        if (image == null)
            return;
        // 得到图像的长和宽
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        // draw the image in the upper-left corner
        g.drawImage(image, 0, 0, null);
        // tile the image across the component
        // 使用copyArea将显示图形的空间拷贝到整个窗口
        for (int i = 0; i * imageWidth <= getWidth(); i++)
            for (int j = 0; j * imageHeight <= getHeight(); j++)
                if (i + j > 0)
                    g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
    }

    private Image image;
}
