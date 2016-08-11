/**
 *  @author Chen Mingjian  creat on 2012-4-15
 *
 */
package corejava.corejava1.ch06.p03;

/**
 @version 1.00 2000-04-13
 @author Cay Horstmann
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer; /* 除了导入java.util.*和javax.swing.*以外还导入了javax.swing.Timer
消除了Timer的二义性。（ java.util.Timer是一个与调度后台任务的类 ）  */

public class Ch6_03_TimerTest {
    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        // construct a timer that calls the listener
        // once every 2 seconds（单位是1ms，第二个参数指定实现了ActionLister的类）
        Timer t = new Timer(2000, listener);
        t.start();  // 启动定时器。定时器将调用监听器的actionPerformed
        /* Timer的stop方法则停止调用监听器的actionPerformed方法 */

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TimePrinter implements ActionListener {
    // 实现ActionListener接口，并定义回调函数actionPerformed。
    public void actionPerformed(ActionEvent event) {
        // ActionEvent 提供事件的相关信息，详细内容参与第八章
        Date now = new Date();
        System.out.println("At the tone, the time is " + now);
        Toolkit.getDefaultToolkit().beep();
    }
}
