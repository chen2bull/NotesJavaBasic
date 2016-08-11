package corejava.corejava1.ch08;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ch8_01_ButtonTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ButtonFrame frame = new ButtonFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a button panel
 */
@SuppressWarnings("serial")
class ButtonFrame extends JFrame {
    public ButtonFrame() {
        setTitle("ButtonTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // create buttons
        JButton yellowButton = new JButton("<html><i><u>Yellow</u></i></html>");    // 标签的文本也可以是HTML
        JButton blueButton = new JButton("<html><i>Blue</i></html>");
        JButton redButton = new JButton("Red");

        buttonPanel = new JPanel();
        // add buttons to panel(add方法用于将组件加到容器中)
        buttonPanel.add(yellowButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(redButton);
        // add panel to frame
        add(buttonPanel);
        // create button actions
        ColorAction yellowAction = new ColorAction(Color.YELLOW);
        ColorAction blueAction = new ColorAction(Color.BLUE);
        ColorAction redAction = new ColorAction(Color.RED);
        // 将按钮监听器与按钮关联起来
        yellowButton.addActionListener(yellowAction);
        blueButton.addActionListener(blueAction);
        redButton.addActionListener(redAction);
    }

    /**
     * An action listener that sets the panel's background color.
     */
    private class ColorAction implements ActionListener {
        public ColorAction(Color c) {
            backgroundColor = c;
        }

        public void actionPerformed(ActionEvent event) {
            buttonPanel.setBackground(backgroundColor);
        }

        private Color backgroundColor;
    }

    private JPanel          buttonPanel;
    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}
