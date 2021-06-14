package netty;

import lombok.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilesListResponse extends AbstractCommand{

    private List<String> files;

    @SneakyThrows
    public FilesListResponse(Path path){
        files = Files.list(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
