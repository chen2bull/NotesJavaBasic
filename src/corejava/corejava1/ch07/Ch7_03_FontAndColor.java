package corejava.corejava1.ch07;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * @version 1.33 2007-04-14
 * @author Cay Horstmann
 */
public class Ch7_03_FontAndColor {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FontFrame frame = new FontFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a text message component
 */
class FontFrame extends JFrame {
    public FontFrame() {
        setTitle("FontTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add component to frame
        FontComponent component = new FontComponent();
        this.getContentPane().add(component);

    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}

/**
 * A component that shows a centered message in a box.
 */
class FontComponent extends JComponent {
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        /* Topic1: 颜色的设置 */
        g2.setPaint(Color.RED);                 // 设置Graphics2D的颜色为红色
        // Color类中有常见颜色的预定义常量
        g2.setPaint(new Color(0, 128, 128));    // 也可以用三原色的成份来创建一个Color类的对象
        // g2.setPaint(new Color(SystemColor.INFO));
        // //SystemColor类中封装了用户系统的各个元素的颜色

        g2.drawString("Warning", 20, 20);
        Rectangle2D rectangle2d = new Rectangle2D.Double(270, 20, 10, 30);
        g2.fill(rectangle2d);   // draw方法画的是空心图形,fill方法画的是用颜色填充好的封闭图形

        /* Topic2: 字体的设置 */
        String message = "Hello,World!中文";

        // 新建字体对象,将字体设置为Serif,设置为粗体大小为32
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 32);
        g2.setFont(f);  // 设置屏幕设备的字体属性

        // 测量信息的大小和位置
        FontRenderContext context = g2.getFontRenderContext(); // 得到屏幕设备字体属性的描述对象!!
        Rectangle2D bounds = f.getStringBounds(message, context); // getStringBounds返回包围字符串的矩形
        // 注意这个矩形的坐标与窗口或屏幕的坐标是无关的.这个矩形有自己独立的坐标
        // 它的坐标原点是基线的左端点(而不是矩形的顶点!!),因此getY()得到左上角的坐标为负值(加多一个负号就表示上坡度)
        double ascent = -bounds.getMinY();

        // set (x,y) = top left corner of text(为了将字符串放在正中间,计算出字符串放置的左上角)
        double x = (getWidth() - bounds.getWidth()) / 2;    // (总宽度-字符串宽度)/2
        double y = (getHeight() - bounds.getHeight()) / 2;

        // add ascent to y to reach the baseline
        double baseY = y + ascent;  // 计算出基线的Y坐标

        // 如果需要,也可以计算出下坡度和行间距
        // LineMetrics metrics = f.getLineMetrics(message, context);
        // //用这个LineMetrics对象可以获得下坡度和行间距
        // float descent = metrics.getDescent(); // 下坡度
        // float leading = metrics.getLeading(); // 行间距

        // draw the message
        g2.drawString(message, (int) x, (int) baseY);

        // 设置Graphics2D的颜色为蓝色
        g2.setPaint(Color.BLUE);

        // draw the baseline(画出基线)
        g2.draw(new Line2D.Double(x, baseY, x + bounds.getWidth(), baseY));

        // draw the enclosing rectangle(相切的矩形)
        Rectangle2D rect = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
        g2.draw(rect);
    }
}
