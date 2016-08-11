package corejava.corejava2.ch01;

import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 * This program displays all URLs in a web page by matching a regular expression that describes the
 * <a href=...> HTML tag. Start the program as <br>
 * java HrefMatch URL
 * 
 * @version 1.01 2004-06-04
 * @author Cay Horstmann
 */
public class CH01_09_HrefMatch {
    public static void main(String[] args) {
        try {
            // get URL string from command line or use default
            String urlString;
            if (args.length > 0)
                urlString = args[0];
            else
                urlString = "http://java.sun.com";

            // open reader for URL
            InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());

            // read contents into string builder
            StringBuilder input = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1)
                input.append((char) ch);

            // search for all occurrences of pattern
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            // 寻找输入中一个或者多个匹配的子串，可调用Matcher类的find方法查找下一个匹配
            // 如果它返回true，再用start和end方法来查找匹配的内容
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String match = input.substring(start, end);
                System.out.println(match);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }
}
