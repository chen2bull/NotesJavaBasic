package corejava.corejava1.ch08;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * @version 1.32 2007-06-12
 * @author Cay Horstmann
 */
public class Ch8_05_MouseTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MouseFrame frame = new MouseFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame containing a panel for testing mouse operations
 */
@SuppressWarnings("serial")
class MouseFrame extends JFrame {
    public MouseFrame() {
        setTitle("MouseTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add component to frame
        MouseComponent component = new MouseComponent();
        add(component);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}

/**
 * A component with mouse operations for adding and removing squares.
 */
@SuppressWarnings("serial")
class MouseComponent extends JComponent {
    public MouseComponent() {
        squares = new ArrayList<Rectangle2D>();
        current = null;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // draw all squares
        for (Rectangle2D r : squares)
            g2.draw(r);
    }

    /**
     * Finds the first square containing a point.
     * 
     * @param p
     *            a point
     * @return the first square that contains p
     */
    public Rectangle2D find(Point2D p) {
        for (Rectangle2D r : squares) {
            if (r.contains(p))
                return r;
        }
        return null;
    }

    /**
     * Adds a square to the collection.
     * 
     * @param p
     *            the center of the square
     */
    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        current = new Rectangle2D.Double(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
        squares.add(current);
        repaint();
    }

    /**
     * Removes a square from the collection.
     * 
     * @param s
     *            the square to remove
     */
    public void remove(Rectangle2D s) {
        if (s == null)
            return;
        if (s == current)
            current = null;
        squares.remove(s);
        repaint();
    }

    private static final int       SIDELENGTH = 10;
    private ArrayList<Rectangle2D> squares;
    private Rectangle2D            current;        // the square containing the
                                                    // mouse cursor

    private class MouseHandler extends MouseAdapter {
        //
        public void mousePressed(MouseEvent event) {    // 鼠标按下时触发
            // add a new square if the cursor isn't inside a square
            current = find(event.getPoint());
            if (current == null)
                add(event.getPoint());
        }

        public void mouseClicked(MouseEvent event) {    // 鼠标点击完了之后触发
            // remove the current square if double clicked
            current = find(event.getPoint());
            if (current != null && event.getClickCount() >= 2)  // 如果是双击的话
                remove(current);
        }
    }

    //
    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {      // 移动光标时触发
            // set the mouse cursor to cross hairs if it is inside
            // a rectangle

            if (find(event.getPoint()) == null)
                setCursor(Cursor.getDefaultCursor());
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)); // 设置光标形状
        }

        public void mouseDragged(MouseEvent event) {    // 拖动鼠标时触发(移动当前的矩形)
            if (current != null) {
                int x = event.getX();   // 获得光标位置
                int y = event.getY();

                // drag the current rectangle to center it at (x, y)
                current.setFrame(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
                repaint();
            }
        }
    }
}
/*
 * 如果要检测鼠标点击与按下键盘修饰符组合的话.可以在鼠标事件的方法中,使用以下的方法 if((event.getModifiersEx() &
 * InputEvent.BUTTON3_DOWN_MASK) != 0) { .... } BUTTON1_DOWN_MASK
 * BUTTON2_DOWN_MASK BUTTON3_DOWN_MASK SHIFT_DOWN_MASK CTRL_DOWN_MASK
 * ALT_DOWN_MASK META_DOWN_MASK //这个是super键,即Windows下的Windows键
 */
