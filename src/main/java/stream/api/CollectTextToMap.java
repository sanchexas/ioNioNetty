package stream.api;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectTextToMap {
    @SneakyThrows
    public Map<String,Integer> getWordsMap(String resource){
        return Files.lines(Paths.get(resource))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .flatMap(line -> Arrays.stream(line.split(" +")))
                .map(word -> word.replaceAll("\\W", ""))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toMap(
                        Function.identity(),
                        value->1,
                        Integer::sum
                ));

    }
}
