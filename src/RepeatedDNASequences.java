import java.util.*;

/**
 * Created by 陈明键 on 2016/7/4.
 */
public class RepeatedDNASequences {
    public List<String> findRepeatedDnaSequences(String s) {
        int[] charSet = new int[256];
        charSet['A'] = 0;
        charSet['C'] = 1;
        charSet['G'] = 2;
        charSet['T'] = 3;
        List<String> resultLs = new ArrayList<String>();
        HashMap<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
        int result;
        int len = s.length() - 9;
        for (int i = 0; i < len; i++) {
            result = 0;
            int j;
            for (j = i; j < i + 10; j++) {
                result = result << 2;
                result += charSet[s.charAt(j)];
            }
            if (resultMap.containsKey(result) ) {
                if(resultMap.get(result) == 1) {
                    resultLs.add(s.substring(i, j));
                }
                resultMap.put(result, resultMap.get(result) + 1);
            } else {
                resultMap.put(result, 1);
            }
        }
        return resultLs;
    }

    public static void main(String[] args) {
//        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
//        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences(s));
        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences("AAAAAAAAAAA"));
        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences("AAAAAAAAAAAA"));
        System.out.println(new RepeatedDNASequences().findRepeatedDnaSequences("AAAAAAAAAAAAA"));

    }
}
