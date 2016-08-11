package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

/**
 * @version 1.23 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_14_FileChooserTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageViewerFrame frame = new ImageViewerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that has a menu for loading an image and a display area for the
 * loaded image.
 */
@SuppressWarnings("serial")
class ImageViewerFrame extends JFrame {
    public ImageViewerFrame() {
        setTitle("Ch9_14_FileChooserTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // set up menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        // use a label to display the images
        label = new JLabel();
        add(label);

        // set up file chooser
        chooser = new JFileChooser();

        // accept all image files ending with .jpg, .jpeg, .gif
        // 设置过滤器(设置Imag files对应的所有后辍名),也可以为一个文件选择器添加多个过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "gif");
        chooser.setFileFilter(filter);

        // 默认总有一个All files过滤器(如果想取消,可以调用)
        // chooser.setAcceptAllFileFilterUsed(false);

        // 如果想清除之前添加的文件过滤器(常用于加载不同类型的文件时重用一个文件选择器)
        // chooser.resetChoosableFileFilters();

        // 设置附件的预览图片的JLabel
        chooser.setAccessory(new ImagePreviewer(chooser));

        // 设置符合filter过滤器的文件使用的图标
        chooser.setFileView(new FileIconView(filter, new ImageIcon("src/corejava.corejava1/ch9/palette.gif")));
    }

    /**
     * This is the listener for the File->Open menu item.
     */
    private class FileOpenListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            chooser.setCurrentDirectory(new File(".")); // 设置当前的路径

            // show file chooser dialog
            int result = chooser.showOpenDialog(ImageViewerFrame.this);

            // if image file accepted, set it as icon of the label
            if (result == JFileChooser.APPROVE_OPTION) {
                // 调用getSelectedFile或getSelectedFiles获取用户选择的一个或多个文件
                // 用以下方法得到被选中文件的全路径名
                String name = chooser.getSelectedFile().getPath();

                // 如果想有一个作为用户默认文件名可以调用
                // chooser.setSelectedFile(file);

                // 默认情况下,只能选择文件.如果要选择目录,需要调用
                // chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                label.setIcon(new ImageIcon(name));
            }
        }
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 400;

    private JLabel          label;
    private JFileChooser    chooser;
}

/**
 * A file view that displays an icon for all files that match a file filter.
 */
class FileIconView extends FileView {
    /**
     * Constructs a FileIconView.
     * 
     * @param aFilter
     *            a file filter--all files that this filter accepts will be
     *            shown with the icon.
     * @param anIcon
     *            --the icon shown with all accepted files.
     */
    public FileIconView(FileFilter aFilter, Icon anIcon) {
        filter = aFilter;
        icon = anIcon;
    }

    public Icon getIcon(File f) {
        if (!f.isDirectory() && filter.accept(f))   // 过滤器接受这个文件
            return icon;
        else
            return null;
    }

    private FileFilter filter;
    private Icon       icon;
}

/**
 * A file chooser accessory that previews images.
 */
@SuppressWarnings("serial")
class ImagePreviewer extends JLabel {
    /**
     * Constructs an ImagePreviewer.
     * 
     * @param chooser
     *            the file chooser whose property changes trigger an image
     *            change in this previewer
     */
    public ImagePreviewer(JFileChooser chooser) {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());

        // 属性变化监听器
        chooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
                    // 用户选择一个新的文件名时
                    File f = (File) event.getNewValue();
                    if (f == null) {
                        setIcon(null);
                        return;
                    }

                    // read the image into an icon
                    ImageIcon icon = new ImageIcon(f.getPath());

                    // 如果图像太大,并图标设置为所选的图像的文件的压缩拷贝
                    if (icon.getIconWidth() > getWidth())
                        icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
                    setIcon(icon);
                }
            }
        });
    }
}
