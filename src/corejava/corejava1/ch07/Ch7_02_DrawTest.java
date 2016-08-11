package corejava.corejava1.ch07;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * @version 1.32 2007-04-14
 * @author Cay Horstmann
 */
public class Ch7_02_DrawTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                DrawFrame frame = new DrawFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that contains a panel with drawings
 */
class DrawFrame extends JFrame {
    public DrawFrame() {
        setTitle("DrawTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        DrawComponent component = new DrawComponent(); // add panel to frame
        add(component);
    }

    public static final int DEFAULT_WIDTH  = 400;
    public static final int DEFAULT_HEIGHT = 400;
}

/**
 * A component that displays rectangles and ellipses.
 */
class DrawComponent extends JPanel {

    // 窗口的重绘函数,是由窗口系统自动调用.
    // 当需要强制刷新屏幕时,就调用repaint方法,一定不能调用paintComponent这个方法.
    public void paintComponent(Graphics g) {
        // 在组件中显示文本
        int messageX = 75;
        int messageY = 100;
        g.drawString("这个程序的编码使用UTF-8", messageX, messageY); // 显示文本的方法
        Graphics2D g2 = (Graphics2D) g;

        // draw a rectangle
        double leftX = 100;
        double topY = 100;
        double width = 200;
        double height = 150;
        // 由于Rectangle2D.Float和Rectangle2D.Double都扩展于Rectangle2D类,
        // 并且子类只覆盖了Rectangle2D超类中的方法,所以可以直接使用Rectangle2D保存矩形的引用
        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.draw(rect);      // 绘制图形的方法

        // draw the enclosed ellipse(内切的椭圆)
        Ellipse2D ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect); // 使用矩形构造椭圆
        // 也可以使用ellipse.setFrameFromDiagonal指定两点
        // 或者ellipse.setFrameFromCenter指定中点和右下角的位置
        g2.draw(ellipse);

        // draw a diagonal line(对角线)
        g2.draw(new Line2D.Double(leftX, topY, leftX + width, topY + height));

        // draw a circle with the same center
        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double radius = 150;
        Ellipse2D circle = new Ellipse2D.Double();  // 注意,画图也是要用Ellipse2D这个类
        circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY + radius);
        g2.draw(circle);

        // 再画一个圆,看看Point2D(点)和Dimension2D(尺寸)的构造
        Point2D p1 = new Point2D.Double(centerX, centerY);  // 左上角是上面椭圆的中心
        Dimension2D d1 = new Dimension(150, 150);            // Dimension指定尺寸
        circle.setFrame(p1, d1);    // 指定左上角的位置和尺寸
        g2.draw(circle);
    }
}
