/**
 * Created by 陈明键 on 2016/7/4.
 */
public class FractionToRecurringDecimal {
    public String fractionToDecimal(int numerator, int denominator) {

        if(numerator==Integer.MIN_VALUE&&denominator==-1)
            return "2147483648";
        int dummy = numerator/denominator, lef = numerator%denominator;
        String str = dummy+"";
        if(dummy==0){
            if((numerator>0&&denominator<0)||(numerator<0&&denominator>0))
                str = "-" + str; // 0 does not have a negative mark in front of it, so we have to add that.
        }
        if(lef==0) return str; // this indicates the result is an integer.

        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".");
        long left = lef<0? -lef : lef;
        long denomin = denominator<0? -denominator : denominator;
        if(denominator==Integer.MIN_VALUE){
            denomin = Integer.MAX_VALUE;
            denomin++; // without this, when the denominator is -214748648, it will cause a overflow.
        }
        long slow = mod(left,denomin), fast = mod(mod(left,denomin),denomin);
        if(slow==0){ // no need to start the loop.
            left = extend(left,denomin,sb);
            return sb.toString();
        }

        while(slow!=fast){
            if(fast==0){  // this indicates there are no cycles.
                while(left!=0)
                    left = extend(left,denomin,sb);
                return sb.toString();
            }
            slow = mod(slow,denomin);
            fast = mod(mod(fast,denomin),denomin);
        }

        slow = left;
        while(slow!=fast){
            slow = mod(slow,denomin);
            fast = mod(fast,denomin);
        }
        while(left!=slow)
            left = extend(left,denomin,sb);
        sb.append("("); // the start of the cycle.
        left = extend(left,denomin,sb);
        while(left!=slow)
            left = extend(left,denomin,sb);
        sb.append(")");
        return sb.toString();
    }

    private long mod(long left, long denominator){
        left = left * 10;
        return left%denominator;
    }

    private long extend(long left, long denominator, StringBuilder sb){
        left = left * 10;
        sb.append(left/denominator);
        return left%denominator;
    }
}
