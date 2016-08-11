package corejava.corejava1.ch09;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @version 1.13 2007-06-12
 * @author Cay Horstmann
 */
public class Ch9_07_SliderTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                SliderTestFrame frame = new SliderTestFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

/**
 * A frame with many sliders and a text field to show slider values.
 */
@SuppressWarnings("serial")
class SliderTestFrame extends JFrame {
    public SliderTestFrame() {
        setTitle("SliderTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 设置为LEFT形式的流布局

        // 所有滑块的命令监听器
        listener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                // update text field when the slider value changes
                JSlider source = (JSlider) event.getSource();
                textField.setText("" + source.getValue());
            }
        };

        // add a plain slider
        JSlider slider = new JSlider(); // 默认min=0,max=100,init=50
        addSlider(slider, "默认的");

        // add a slider with major and minor ticks
        slider = new JSlider();
        slider.setPaintTicks(true);     // 将尺寸标记设为可见
        slider.setMajorTickSpacing(20);  // 设置大尺寸标记的间隔
        slider.setMinorTickSpacing(5);   // 设置小尺寸标记的间隔
        addSlider(slider, "有尺寸标记的");

        // add a slider that snaps to ticks
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);    // 强制滑块对齐(在一些平台(如Ubuntu)上不支持?)
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "强制滑块对齐的");

        // add a slider with no track
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTrack(false);    // 隐藏滑块的轨迹
        addSlider(slider, "没有轨迹的");

        // add an inverted slider
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setInverted(true);       // 将滑块设为逆向的
        addSlider(slider, "逆向的");

        // add a slider with numeric labels
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);    // 为大尺寸标记添加标签(本例中是数字标签)
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "有数字标签的");

        // add a slider with alphabetic labels
        slider = new JSlider();
        slider.setPaintLabels(true);    // 为大尺寸标记添加标签(本例中是字母标签)
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        // 先要提供键为Integer,Component的散列表
        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));
        slider.setLabelTable(labelTable);   // 设置LabelTable
        addSlider(slider, "有字母标签的");

        // add a slider with icon labels
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);

        labelTable = new Hashtable<Integer, Component>();
        // add card images
        labelTable.put(0, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/nine.gif")));
        labelTable.put(20, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/ten.gif")));
        labelTable.put(40, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/jack.gif")));
        labelTable.put(60, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/queen.gif")));
        labelTable.put(80, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/king.gif")));
        labelTable.put(100, new JLabel(new ImageIcon("src/corejava.corejava1/ch9/ace.gif")));
        slider.setLabelTable(labelTable);
        addSlider(slider, "Icon labels");   // 设置LabelTable

        // add the text field that displays the slider value
        textField = new JTextField();
        add(sliderPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
    }

    /**
     * Adds a slider to the slider panel and hooks up the listener
     * 
     * @param s
     *            the slider
     * @param description
     *            the slider description
     */
    public void addSlider(JSlider s, String description) {
        s.addChangeListener(listener);
        JPanel panel = new JPanel();
        panel.add(s);
        panel.add(new JLabel(description));
        sliderPanel.add(panel);
    }

    public static final int DEFAULT_WIDTH  = 350;
    public static final int DEFAULT_HEIGHT = 450;
    private JPanel          sliderPanel;
    private JTextField      textField;
    private ChangeListener  listener;
}
