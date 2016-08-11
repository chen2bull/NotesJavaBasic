package corejava.corejava1.ch08;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

/**
 * 1.除了可以用这种方式修改观感,还可以修改jre/lib下的文件swing.properties (将swing.default设置为所希望的观感名).
 * 2.如果一个程序要提供改变观感的子菜单.可以用相似的方式提供改变观感的子菜单.
 */
public class Ch8_02_PlafTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                PlafFrame frame = new PlafFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a button panel for changing look and feel
 */
@SuppressWarnings("serial")
class PlafFrame extends JFrame {
    public PlafFrame() {
        setTitle("PlafTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        buttonPanel = new JPanel();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();    // 返回当前系统安装的所有观感
        for (UIManager.LookAndFeelInfo info : infos)
            makeButton(info.getName(), info.getClassName());

        add(buttonPanel);
    }

    /**
     * Makes a button to change the pluggable look and feel.
     * 
     * @param name
     *            the button name
     * @param plafName
     *            the name of the look and feel class
     */
    void makeButton(String name, final String plafName) {
        // add button to panel
        JButton button = new JButton(name);
        buttonPanel.add(button);

        // set button action(使用匿名内部类)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // button action: switch to the new look and feel
                try {
                    UIManager.setLookAndFeel(plafName); // 设置观感
                    SwingUtilities.updateComponentTreeUI(PlafFrame.this);   // 更新组件的显示
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JPanel          buttonPanel;
    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
};
