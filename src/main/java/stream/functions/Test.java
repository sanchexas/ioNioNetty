package stream.functions;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test {

    static void get(Foo foo, int arg1, int arg2) {
        System.out.println(foo.getClass());
        System.out.println(foo.Foo(arg1, arg2));
    }


    public static void main(String[] args) {

        Foo foo = (a, b) -> a * b;

        //1) До преобразования в лямбду
//        Foo foo = new Foo() {
//            @Override
//            public int Foo(int a, int b) {
//                return a * b;
//            }
//        };

        //2)
            //Foo sum = Integer::sum;

        get(foo, 3,5);

        // peek , foreach
        Consumer<String> consumer = arg -> {
            arg += "!!!";
            System.out.println(arg);
        };
        consumer.accept("YO ");

        // filter
        Predicate<Integer> predicate = x -> x > 5;
        // map , flatMap
        Function<Integer, String> function = x -> "1 + " + x + " = " + ++x;
        System.out.println(function.apply(3));

        Supplier<HashMap<String, Integer>> supplier = HashMap::new;
        supplier.get();




    }
}
