package top.kylewang.bos;

import org.junit.Test;

/**
 * @author Kyle.Wang
 * 2018/1/16 0016 13:50
 */
public class TestDemo {

    @Test
    public void test() {
        System.out.println(inverseString("abcde"));
    }

    public String inverseString(String source) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = source.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }
}

class SingleClass {

    private SingleClass(){
    }

    private static final SingleClass singleClass= new SingleClass();

    public static SingleClass getSingleClass() {
        return singleClass;
    }
}
