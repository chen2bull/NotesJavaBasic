package corejava.corejava1.ch09;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.23 2007-05-30
 * @author Cay Horstmann
 */
public class Ch9_08_MenuTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MenuFrame frame = new MenuFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a sample menu bar.
 */
@SuppressWarnings("serial")
class MenuFrame extends JFrame {
    public MenuFrame() {
        setTitle("MenuTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new TestAction("New"));

        // demonstrate accelerators
        JMenuItem openItem = fileMenu.add(new TestAction("Open"));  // 将动作为"Open"这个动作对应的菜单项加到菜单
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));  // 设置一个加速器(组合键)
        fileMenu.addSeparator();        // 添加一个分隔线

        saveAction = new TestAction("Save");
        JMenuItem saveItem = fileMenu.add(saveAction);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        saveAsAction = new TestAction("Save As");
        fileMenu.add(saveAsAction);
        fileMenu.addSeparator();

        fileMenu.add(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        // 演示图标在菜单项中的使用
        Action cutAction = new TestAction("Cut");
        cutAction.putValue(Action.SMALL_ICON, new ImageIcon("src/corejava.corejava1/ch9/cut.gif"));
        Action copyAction = new TestAction("Copy");
        copyAction.putValue(Action.SMALL_ICON, new ImageIcon("src/corejava.corejava1/ch9/copy.gif"));
        Action pasteAction = new TestAction("Paste");
        pasteAction.putValue(Action.SMALL_ICON, new ImageIcon("src/corejava.corejava1/ch9/paste.gif"));

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);

        // JCheckBoxMenuItem菜单项的用法
        readonlyItem = new JCheckBoxMenuItem("Read-only");  // 新建一个JCheckBoxMenuItem菜单项
        readonlyItem.addActionListener(new ActionListener() {   // 为它添加相应的事件监听器
                    public void actionPerformed(ActionEvent event) {
                        boolean saveOk = !readonlyItem.isSelected();
                        saveAction.setEnabled(saveOk);
                        saveAsAction.setEnabled(saveOk);
                    }
                });
        // JRadioButtonMenuItem菜单项的用法
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem insertItem = new JRadioButtonMenuItem("Insert");
        insertItem.setSelected(true);
        JRadioButtonMenuItem overtypeItem = new JRadioButtonMenuItem("Overtype");
        group.add(insertItem);
        group.add(overtypeItem);

        // demonstrate nested menus(子菜单)
        JMenu optionMenu = new JMenu("Options");
        optionMenu.add(readonlyItem);
        optionMenu.addSeparator();
        optionMenu.add(insertItem);
        optionMenu.add(overtypeItem);

        editMenu.addSeparator();
        editMenu.add(optionMenu);

        // demonstrate mnemonics(加速键Alt + 一个键)
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');  // Alt + H 可以展开这个菜单
        JMenuItem indexItem = new JMenuItem(new TestAction("Index"));
        indexItem.setMnemonic('I'); // 在菜单展开时,按下Alt + I 相当于点击这个菜单项
        helpMenu.add(indexItem);

        // 也可以为 Action 加一个快捷键(效果同上)
        Action aboutAction = new TestAction("About");
        aboutAction.putValue(Action.MNEMONIC_KEY, new Integer('A'));
        helpMenu.add(aboutAction);

        // 将以上三个菜单加到菜单栏中
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        // 说明右键菜单的使用
        popup = new JPopupMenu();
        popup.add(cutAction);
        popup.add(copyAction);
        popup.add(pasteAction);

        JPanel panel = new JPanel();
        panel.setComponentPopupMenu(popup);
        add(panel);

        // the following line is a workaround for bug 4966109
        panel.addMouseListener(new MouseAdapter() {
        });
    }

    public static final int   DEFAULT_WIDTH  = 300;
    public static final int   DEFAULT_HEIGHT = 200;
    private Action            saveAction;
    private Action            saveAsAction;
    private JCheckBoxMenuItem readonlyItem;
    private JPopupMenu        popup;
}

/**
 * A sample action that prints the action name to System.out
 */
@SuppressWarnings("serial")
class TestAction extends AbstractAction {
    public TestAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println(getValue(Action.NAME) + " selected.");
    }
}
