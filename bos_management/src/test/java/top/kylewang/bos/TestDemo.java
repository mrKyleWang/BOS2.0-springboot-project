package top.kylewang.bos;

import org.junit.Test;

/**
 * @author Kyle.Wang
 * 2018/1/16 0016 13:50
 */
public class TestDemo {

    @Test
    public void test(){
        String s1 = "abc";		//存储在常量池
        String s2 = "abc";		//存储在常量池
        String s3 = new String ("abc");	//存储在堆

        System.out.println(s1 == s2);		// true
        System.out.println(s1 == s3);		// false
        // intern方法为本地方法:将内存转移到常量池中
        System.out.println(s1 == s3.intern());		// true
    }
}
