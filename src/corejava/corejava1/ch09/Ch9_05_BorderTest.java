package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_05_BorderTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                BorderFrame frame = new BorderFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with radio buttons to pick a border style.
 */
@SuppressWarnings("serial")
class BorderFrame extends JFrame {
    public BorderFrame() {
        setTitle("BorderTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        demoPanel = new JPanel();
        buttonPanel = new JPanel();
        group = new ButtonGroup();

        // 以下函数的第二个参数是各种边框的类型(工厂模式)
        addRadioButton("Lowered bevel", BorderFactory.createLoweredBevelBorder());
        addRadioButton("Raised bevel", BorderFactory.createRaisedBevelBorder());
        addRadioButton("Etched", BorderFactory.createEtchedBorder());
        addRadioButton("Line", BorderFactory.createLineBorder(Color.BLUE));
        addRadioButton("Matte", BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLUE));
        addRadioButton("Empty", BorderFactory.createEmptyBorder());

        // 要创建含有标题的边框,要使用一个边框对象作为"标题边框"构造器的第一个参数
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Border types");

        buttonPanel.setBorder(titled);
        setLayout(new GridLayout(2, 1));
        add(buttonPanel);
        add(demoPanel);
    }

    public void addRadioButton(String buttonName, final Border b) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                demoPanel.setBorder(b); // 将demoPanel的边框设置为b
            }
        });
        group.add(button);
        buttonPanel.add(button);
    }

    public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 200;
    private JPanel          demoPanel;
    private JPanel          buttonPanel;
    private ButtonGroup     group;
}
