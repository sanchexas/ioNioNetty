package stream.api;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Degits {

    public static void main(String[] args) {

        Stream.of(1,2,3,4,5)
                .filter(x -> x % 2 == 0)
                .forEach(System.out::println);

        Integer res = Stream.of(1,2,3,4,5)
                .map(x -> x + 1)
                .reduce(0, Integer::sum);

        List<Integer> integers = Stream.of(1,2,3,4,5).collect(Collectors.toList());

        String line = Stream.of(1,2,3,4,5).map(String::valueOf).collect(Collectors.joining(","));

        Map<Integer, Integer> map = Stream.of(1,1,1,2,3,3,4,5)
                .collect(Collectors.toMap(
                        Function.identity(), value -> 1, Integer::sum
                ));
        System.out.println(map);

        System.out.println(line);

        System.out.println(integers);

        System.out.println(res);


    }
}
