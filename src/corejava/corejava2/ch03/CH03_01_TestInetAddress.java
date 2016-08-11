package corejava.corejava2.ch03;

import java.net.InetAddress;

/**
 * @date 2012-10-10 下午9:38:13
 * @author Chen Mingjian
 * @version 1.0
 */
public class CH03_01_TestInetAddress {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            showInfo(ia);
            ia = InetAddress.getByName("www.sina.com.cn");
            showInfo(ia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showInfo(InetAddress iaAddress) {
        String name = iaAddress.getHostName();
        String address = iaAddress.getHostAddress();
        
        System.out.println(name);
        System.out.println(address);
        System.out.println("------------------------------");
        
    }

}
