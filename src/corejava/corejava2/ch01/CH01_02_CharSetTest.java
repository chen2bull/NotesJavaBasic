/**
 *  @author Chen Mingjian  creat on 2012-10-8
 *
 */
package corejava.corejava2.ch01;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * @date 2012-10-8 下午10:54:02
 * @author Chen Mingjian
 * @version 1.0
 */
public class CH01_02_CharSetTest {

    public static void main(String[] args) {
        Charset charset = Charset.forName("GBK");
        
        // 用aliases方法得到这种字符集的别名
        Set<String> aliases = charset.aliases();
        
        for (String string : aliases) {
            System.out.println(string);
        }
        System.out.println("===============");

        
        // 用以下方式确定当前系统所有可用的字符集
        Map<String, Charset> charsMap = Charset.availableCharsets();
        for (String name : charsMap.keySet()) {
            System.out.println(name);
        }
        
        String astring = "这个文本使用UTF-8作为编码";
        System.out.println(astring);
        
        // 将Unicode字符串转换成编码而成的字节序列方法如下
        ByteBuffer abuf = charset.encode(astring);
        byte[] bytes = abuf.array();
        for (byte b : bytes) {
            System.out.println(b);
        }
        
        // 解码字节序列方法如下
        ByteBuffer bbuf= ByteBuffer.wrap(bytes, 0, bytes.length);
        CharBuffer cbuf = charset.decode(bbuf);
        String str = cbuf.toString();
        
        System.out.println(str);
    }

}
