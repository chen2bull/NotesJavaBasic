package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_01_Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalculatorFrame frame = new CalculatorFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a calculator panel.
 */
@SuppressWarnings("serial")
class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        setTitle("Calculator");
        CalculatorPanel panel = new CalculatorPanel();
        add(panel);
        pack();     // !!!这个方法用于将所有的组件以最佳的高度和宽度显示在框架中
    }
}

/**
 * A panel with calculator buttons and a result display.
 */
@SuppressWarnings("serial")
class CalculatorPanel extends JPanel {
    public CalculatorPanel() {
        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start = true;

        // 如果不指定布局管理器,则使用默认的流布局管理器
        // 组件总是位于容器的中央,即使用户对框架缩放也是如此。
        // 当一行的空间不够时，会将显示在新的一行上。

        // CalculatorPanel使用边框布局,将显示结果的按钮(display)添加到北边
        // 边框布局管理器允许为每个组件选择一个放置的位置.可以是东西南北中.
        // 当容器缩放时,边缘组件的厚度不会改变(中部组件大小会发生变化)
        // 边框布局会扩展所有组件的尺寸以便填满可用空间
        display = new JButton("0");
        display.setEnabled(false);
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        // add the buttons in a 4 x 4 grid
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));  // 网格布局(4行4列)
        // 网格布局按行列排列所有的组件,每个单元大小都是一样的

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);
        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);
        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);
        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Adds a button to the center panel.
     * 
     * @param label
     *            the button label
     * @param listener
     *            the button listener
     */
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * This action inserts the button action string to the end of the display
     * text.
     */
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

    /**
     * This action executes the command that the button action string denotes.
     */
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else
                    lastCommand = command;
            } else {    // 按下除了负号以外的所有按钮都会计算结果
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }

    /**
     * Carries out the pending calculation.
     * 
     * @param x
     *            the value to be accumulated with the prior result.
     */
    public void calculate(double x) {
        if (lastCommand.equals("+"))
            result += x;
        else if (lastCommand.equals("-"))
            result -= x;
        else if (lastCommand.equals("*"))
            result *= x;
        else if (lastCommand.equals("/"))
            result /= x;
        else if (lastCommand.equals("="))
            result = x;
        display.setText("" + result);
    }

    private JButton display;
    private JPanel  panel;
    private double  result;
    private String  lastCommand;
    private boolean start;
}
