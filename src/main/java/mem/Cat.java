package mem;

import java.util.Arrays;
import java.util.Objects;

public class Cat {
    public static void main(String[] args) {
//        Arrays.stream(Thread.currentThread().getStackTrace())
//                .forEach(System.out::println);
        System.out.println(1);
        String s = "123";
        String s2 = new String("123");
        String s3 = new String("23");
        System.out.println(s == s2);
        System.out.println(s == s2.intern());

        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s2.intern()));
        System.out.println(System.identityHashCode("23"));
        System.out.println(System.identityHashCode(s3.intern()));
    }
}
