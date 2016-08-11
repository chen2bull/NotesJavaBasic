package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_12_DialogTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogFrame frame = new DialogFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with a menu whose File->About action shows a dialog.
 */
@SuppressWarnings("serial")
class DialogFrame extends JFrame {
    public DialogFrame() {
        setTitle("Ch9_12_DialogTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // construct a File menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // add About and Exit menu items
        // The About item shows the About dialog
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (dialog == null) // first time
                    dialog = new AboutDialog(DialogFrame.this, "A Dialog", false);
                // 在调用超类构造器时,需要提供拥有者框架
                dialog.setVisible(true); // pop up dialog
            }
        });
        fileMenu.add(aboutItem);

        // The Exit item exits the program
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;
    private AboutDialog     dialog;
}

/**
 * A sample modal dialog that displays a message and waits for the user to click
 * the Ok button.
 */
@SuppressWarnings("serial")
class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner, String name, boolean isModal) {
        super(owner, name, isModal);    // 最后一个参数指定:是否为模式对话框
        NotConstructor();
    }

    public AboutDialog(JFrame owner) {
        super(owner, "About Ch9_12_DialogTest", true);
        NotConstructor();
    }

    private void NotConstructor() {
        // add HTML label to center
        add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann and Gary Cornell</html>"), BorderLayout.CENTER);

        // Ok button closes the dialog
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        // add Ok button to southern border
        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(250, 150);
    }
}
