package corejava.corejava2.ch01;

import java.util.*;
import java.util.regex.*;

/**
 * This program tests regular expression matching.
 * Enter a pattern and strings to match, or hit Cancel
 * to exit. If the pattern contains groups, the group
 * boundaries are displayed in the match.
 * 
 * @version 1.01 2004-05-11
 * @author Cay Horstmann
 */
public class CH01_09_RegExTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter pattern: ");
        String patternString = in.nextLine();

        Pattern pattern = null;
        try {
            // 用字符串形式的表达式创建一个Pattern对象
            pattern = Pattern.compile(patternString);
        } catch (PatternSyntaxException e) {
            System.out.println("Pattern syntax error");
            System.exit(1);
        }

        while (true) {
            System.out.println("Enter string to match: ");
            String input = in.nextLine();
            if (input == null || input.equals(""))
                return;
            // 以待匹配的字符串为参数从模式中得到Matcher对象
            // Matcher对象描述模式和字符串的匹配情况
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int g = matcher.groupCount();
                if (g > 0) {
                    for (int i = 0; i < input.length(); i++) {
                        for (int j = 1; j <= g; j++)
                            if (i == matcher.start(j))
                                System.out.print('(');
                        System.out.print(input.charAt(i));
                        for (int j = 1; j <= g; j++)
                            if (i + 1 == matcher.end(j))
                                System.out.print(')');
                    }
                    System.out.println();
                }
            } else
                System.out.println("No match");
        }
    }
}
