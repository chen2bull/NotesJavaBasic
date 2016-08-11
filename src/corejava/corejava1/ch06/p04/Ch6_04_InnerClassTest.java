package corejava.corejava1.ch06.p04;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * 这个程序说明内部类的使用。
 * 
 * @version 1.10 2004-02-27
 * @author Cay Horstmann
 */
public class Ch6_04_InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        // keep program running until user selects "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * 一个在固定时间里打印当前时间的时钟。
 */
class TalkingClock {
    /**
     * Constructs a talking clock
     * 
     * @param interval
     *            the interval between messages (in milliseconds)
     * @param beep
     *            true if the clock should beep
     */
    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Starts the clock.
     */
    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    private int     interval;
    private boolean beep;

    /*
     * TimePrinter位于TalkingClock的内部。并不意味着每个TalkingClock都有一个TimePrinter实例域。
     * TimePrinter对象是由TalkingClock类的方法构造的。
     */
    public class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);

            // 内部类既可访问自身的数据域，也可以访问创建它的外围类的数据域。
            // （为了实现这个功能，内部类的对象总有一个隐式引用，它指向了创建它的外部类。）
            // 外围类对象的引用名称为outer。本例中也可以使用 outer.beep访问外围类的数据域。
            if (beep)
                Toolkit.getDefaultToolkit().beep();
            /*
             * 如果没有声音，可能是由于机器系统报警声音没有关联的缘故，在控制面板——声音——默认响声 里关联上一种声音文件试试。
             */
        }
    }
}
