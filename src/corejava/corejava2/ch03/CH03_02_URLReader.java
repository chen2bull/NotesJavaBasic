package corejava.corejava2.ch03;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

/**
 * @date 2012-10-10 下午9:50:04
 * @author Chen Mingjian
 * @version 1.0
 */
public class CH03_02_URLReader {

    public static void main(String[] args) {
        try {
            // 设置代理服务器及其端口
            // System.setProperty("http.proxyHost", "192.168.1.0");
            // System.setProperty("http.proxyPort", "11080");
            
            // 可以使用打开URL中的文本流
            URL tric = new URL("http://www.google.cn/");
            BufferedReader in = new BufferedReader(new InputStreamReader(tric.openStream()));
            String s;
            while ( (s = in.readLine()) != null) {
                System.out.println(s);
            }
            in.close();
        } catch (MalformedURIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
