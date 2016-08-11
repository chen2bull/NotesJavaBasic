package corejava.corejava1.ch08;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Ch8_04_ActionTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ActionFrame frame = new ActionFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a panel that demonstrates color change actions.
 */
@SuppressWarnings("serial")
class ActionFrame extends JFrame {
    public ActionFrame() {
        setTitle("ActionTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        buttonPanel = new JPanel();

        // define actions
        Action yellowAction = new ColorAction("Yellow", new ImageIcon("src/corejava.corejava1/ch8/yellow-ball.gif"), Color.YELLOW);
        Action blueAction = new ColorAction("Blue", new ImageIcon("src/corejava.corejava1/ch8/blue-ball.gif"), Color.BLUE);
        Action redAction = new ColorAction("Red", new ImageIcon("src/corejava.corejava1/ch8/red-ball.gif"), Color.RED);

        // 添加按钮(同时,将按钮与动作关联)
        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(redAction));

        // add panel to frame
        add(buttonPanel);

        // associate the Y, B, and R keys with names
        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        // 击键处理将按照下列顺序检查这些映射
        // WHEN_FOCUSED 当组件拥有键盘焦点时
        // WHEN_ANCESTOR_OF_FOCUSED_COMPONENT 当这个组件包含了拥有键盘焦点的组件时
        // WHEN_IN_FOCUSED_WINDOW 当这个组件被包含在一个拥有键盘焦点组件的窗口中时

        // 获得击键映射到动作键的输入映射
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        // associate the names with actions
        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow", yellowAction);
        amap.put("panel.blue", blueAction);
        amap.put("panel.red", redAction);
    }

    /*
     * AbstractAction是实现了Action接口中除了actionPerformed以外的所有方法,包括: 1.void
     * setEnabled(boolean b) //允许或禁用这个动作 2.boolean isEnabled() //这个动作是否处于允许状态
     * 3.void putValue(String key,Object value) //存储动作对象中的任意名字/值 4.Object
     * getValue(String key) //检索动作对象中的任意名字/值 5.void
     * addPropertyChangeListener(PropertyChangeListener listener) 6.void
     * removePropertyChangeListener(PropertyChangeListener listener)
     * 最后两个方法的作用:让其它对象在动作对象的属性发生变化时得到通知 (用于处理菜单和工具栏中的触发和禁用的关系)
     */
    public class ColorAction extends AbstractAction {
        /**
         * Constructs a color action.
         * 
         * @param name
         *            the name to show on the button
         * @param icon
         *            the icon to display on the button
         * @param c
         *            the background color
         */
        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);        // 预定义的名称,动作名称
            putValue(Action.SMALL_ICON, icon);  // 预定义的名称,图标
            putValue(Action.SHORT_DESCRIPTION,  // 预定义的名称,快捷键缩写
                    "Set panel color to " + name.toLowerCase());
            putValue("color", c);       // 用户自定义的名称
        }

        public void actionPerformed(ActionEvent event) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }

    private JPanel          buttonPanel;
    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
}
