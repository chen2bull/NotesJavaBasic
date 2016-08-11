package corejava.corejava1.ch09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_10_GridBagLayoutTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FontFrame frame = new FontFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame that uses a grid bag layout to arrange font selection components.
 */
@SuppressWarnings("serial")
class FontFrame extends JFrame {
    public FontFrame() {
        setTitle("GridBagLayoutTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        ActionListener listener = new FontAction();

        // construct components
        JLabel faceLabel = new JLabel("Face: ");
        face = new JComboBox(new String[] { "Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput" });
        face.addActionListener(listener);

        JLabel sizeLabel = new JLabel("Size: ");
        size = new JComboBox(new String[] { "8", "10", "12", "15", "18", "24", "36", "48" });
        size.addActionListener(listener);

        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        italic = new JCheckBox("Italic");
        italic.addActionListener(listener);

        sample = new JTextArea();
        sample.setText("The quick brown fox jumps over the lazy dog");
        sample.setEditable(false);
        sample.setLineWrap(true);
        sample.setBorder(BorderFactory.createEtchedBorder());

        // add components to grid, using GBC convenience class
        add(faceLabel, new GBC(0, 0).setAnchor(GBC.EAST));
        add(face, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10));
        // setFill(GBC.HORIZONTAL)表示垂直方向不拉伸
        // setInsets(1)指定组件的外部填充，即组件与其显示区域边缘之间间距的最小量(四个方向的值可以不一样)。
        add(sizeLabel, new GBC(0, 1).setAnchor(GBC.EAST));
        add(size, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(10));
        add(bold, new GBC(0, 2, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(italic, new GBC(0, 3, 2, 1).setAnchor(GBC.CENTER).setWeight(100, 100));
        add(sample, new GBC(2, 0, 1, 4).setFill(GBC.BOTH).setWeight(100, 100));
        // 使用GridBagLayout类的步骤
        // 1.在纸上画出组件布局草图,找出风格
        // 2.用0,1,2,....标识网格的行和列.
        // 3.对于每个组件,是否需要水平或者垂直填充它所在的单元格
        // 如果不希望组件拉伸至整个区域,就要设置fill约束
        // 用setAnchor设置不拉伸并且指定位置
        // 4.所有的增量都设置为100,.如果某些行或列要保持默认大小,就将这行或列中的weightx或weighty设为0
    }

    public static final int DEFAULT_WIDTH  = 300;
    public static final int DEFAULT_HEIGHT = 200;

    private JComboBox       face;
    private JComboBox       size;
    private JCheckBox       bold;
    private JCheckBox       italic;
    private JTextArea       sample;

    /**
     * An action listener that changes the font of the sample text.
     */
    private class FontAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String fontFace = (String) face.getSelectedItem();
            int fontStyle = (bold.isSelected() ? Font.BOLD : 0) + (italic.isSelected() ? Font.ITALIC : 0);
            int fontSize = Integer.parseInt((String) size.getSelectedItem());
            Font font = new Font(fontFace, fontStyle, fontSize);
            sample.setFont(font);
            sample.repaint();
        }
    }
}
