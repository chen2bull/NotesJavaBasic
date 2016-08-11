package corejava.corejava1.ch11;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * @date 2012-6-17 下午5:34:19
 * @author Chen Mingjian
 * @version 1.0
 */
public class Ch11_06_ConsoleWindowTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // this is the button test program from chapter 8
                ButtonFrame frame = new ButtonFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // initialize the console window--System.out will show here
                ConsoleWindow.init();
            }
        });
    }
}

/**
 * A frame with a button panel This code is identical to ButtonTest in chapter
 * 8, except for the logging in the actionPerformed method of the ColorAction
 * class
 */
class ButtonFrame extends JFrame {
    public ButtonFrame() {
        setTitle("ButtonTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // add panel to frame

        ButtonPanel panel = new ButtonPanel();
        add(panel);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}

/**
 * A panel with three buttons
 */
class ButtonPanel extends JPanel {
    public ButtonPanel() {
        // create buttons

        JButton yellowButton = new JButton("Yellow");
        JButton blueButton = new JButton("Blue");
        JButton redButton = new JButton("Red");

        // add buttons to panel

        add(yellowButton);
        add(blueButton);
        add(redButton);

        // create button actions

        ColorAction yellowAction = new ColorAction(Color.YELLOW);
        ColorAction blueAction = new ColorAction(Color.BLUE);
        ColorAction redAction = new ColorAction(Color.RED);

        // associate actions with buttons

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
            System.out.println(event); // shows in console window
            setBackground(backgroundColor);
        }

        private Color backgroundColor;
    }
}

/**
 * 一个显示命令行输出(System.out和System.err)的窗口.这个类的用法很简单,只要调用Console.init就可以了.
 * 
 * @version 1.01 2004-05-10
 * @author Cay Horstmann
 */
class ConsoleWindow {
    public static void init() {
        JFrame frame = new JFrame();
        frame.setTitle("ConsoleWindow");
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        frame.add(new JScrollPane(output));
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocation(DEFAULT_LEFT, DEFAULT_TOP);
        frame.setFocusableWindowState(false);
        frame.setVisible(true);

        // define a PrintStream that sends its bytes to the output text area
        PrintStream consoleStream = new PrintStream(new OutputStream() {
            public void write(int b) {
            } // never called

            public void write(byte[] b, int off, int len) {
                output.append(new String(b, off, len));
            }
        });

        // 将System.out和System.err重定向到consoleStream这个流中
        System.setOut(consoleStream);
        System.setErr(consoleStream);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
    public static final int DEFAULT_LEFT   = 200;
    public static final int DEFAULT_TOP    = 200;
}
