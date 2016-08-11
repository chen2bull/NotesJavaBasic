package corejava.corejava1.ch07;

import java.awt.*;

import javax.swing.*;

/**
 * @version 1.32 2007-04-14
 * @author Cay Horstmann
 */
public class Ch7_01_SizedFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                SizedFrame frame = new SizedFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 定义关闭这个框架时的响应动作(此处是退出)
                frame.setVisible(true); // 将框架设为可见
            }
        }); // 退出main并没有终止程序,终止的只是主线程.事件调度线程保持激活状态,直到关闭框架或调用System.exit方法终止程序.
    }
}

class SizedFrame extends JFrame {

    public SizedFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();  // 得到默认的Toolkit对象,Toolkit类有很多与本地窗口打交道的方法
        Dimension screenSize = kit.getScreenSize();     // 获得屏幕大小
        int screenHeight = screenSize.height;           // 屏幕高
        int screenWidth = screenSize.width;             // 屏幕宽

        setSize(screenWidth / 2, screenHeight / 2); // 将框架的大小设定为上面取值的50%
        setLocationByPlatform(true); // 让窗口系统自己控制窗口的位置
        // 也可以使用setLocation(x,y)将Component类(JFrame继承自这个类)的对象重新定位
        // 另外,setBounds(x,y,width,height)方法则可以一步重定位Component
        // 对于框架来说,这些方法的坐标均相对于整个屏幕,在容器中的组件相对于容器

        Image img = kit.getImage("src/corejava.corejava1/ch7/icon.gif"); // 使用工具箱加载图像
        setIconImage(img);      // 设置框架的图标
        setTitle("SizedFrame"); // 设置框架的标题

        // setExtendedState(Frame.ICONIFIED | getExtendedState());
        // 设置程序的主框架状态
        // setExtendedState(int state); 状态是下列值之一
        // Frame.MAXIMIZED_BOTH; 最大化
        // Frame.MAXIMIZED_VERT; 垂直方向最大化
        // Frame.MAXIMIZED_HORIZ; 水平方向最大化
        // Frame.ICONIFIED; 图标化状态 可以用 | 与其它状态组合使用
        // Frame.NORMAL; 正常大小状态

        // setUndecorated(true);
        // 设置这个属性后,框架显示中将没有标题栏或关闭按钮这样的装饰.这个方法只能在框架显示之前调用

        // toFont(); 将这个窗口显示在其他窗口前面
        // toBack(); 将这个窗口显示在其他窗口后面,并重新排列所有的可见窗口
        // 以上为设置属性的方法, JFrame类提供下面方法可以获得属性
        // 如: public String getTitle() 对应 public void setTitle(String title)
        // public boolean isLocationByPlatform() 对应 public void
        // setLocationByPlatform(boolean b)
        // public boolean isUndecorated() 对应 public void setUndecorated(boolean
        // b)
        // ......
    }
}
