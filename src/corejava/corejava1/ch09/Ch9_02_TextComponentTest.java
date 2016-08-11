package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.40 2007-04-27
 * @author Cay Horstmann
 */
public class Ch9_02_TextComponentTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                TextComponentFrame frame = new TextComponentFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with sample text components.
 */
@SuppressWarnings("serial")
class TextComponentFrame extends JFrame {
    public TextComponentFrame() {
        setTitle("TextComponentTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        final JTextField textField = new JTextField();  // 文本域,也可以指定列宽及文本
        // 如要在运行时重新设置列数,可以使用setColumns重新设置,并调用包含这个文本框的容器的revalidate方法
        final JPasswordField passwordField = new JPasswordField();  // 密码域
        // 可以使用passwordField.setEchoChar(c)选择自己的回显字符

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        // 标签JLabel没有任何修饰,也不能响应用户的输入
        northPanel.add(new JLabel("User name: ", SwingConstants.RIGHT));    // 指定对齐的方式为右
        northPanel.add(textField);  // 默认为左对齐
        northPanel.add(new JLabel("Password: ", SwingConstants.RIGHT));
        northPanel.add(passwordField);

        add(northPanel, BorderLayout.NORTH);

        // 建立一个8行,40列(一列就是当前使用的字体下一个字符的宽度)的文本区
        // 文本区只显示无格式的文本,没有字体或格式设置
        // 如果文本区的文本超出显示的范围,那么剩下的文本就会被剪裁掉.可以用setLineWrap方法来避免剪裁
        // 文本区中,换行只是一种视觉效果 没有换行符'\n\
        final JTextArea textArea = new JTextArea(8, 40);

        // 在Swing中,文本区没有滚动条.如有需要,可将文本区插入到滚动窗格(JScrollPane)中
        // 这是一种为任意组件添加滚动功能的通用机制,而不是文本区特有的.
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        // add button to append text into the text area

        JPanel southPanel = new JPanel();

        JButton insertButton = new JButton("Insert");
        southPanel.add(insertButton);
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // 在文本区中追加文本
                textArea.append("User name: " + textField.getText().trim()
                // trim是String的方法,在文本域的输入中常用,作用是去除前后的空格
                        + " Password: " + new String(passwordField.getPassword()) + "\n");
                // 获得文本域的字符及密码域的密码
            }
        });

        // add a text area with scroll bars
        add(southPanel, BorderLayout.SOUTH);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 300;
}
