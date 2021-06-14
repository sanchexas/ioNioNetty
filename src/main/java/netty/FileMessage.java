package netty;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

@EqualsAndHashCode(callSuper = true)
@Data

public class FileMessage extends AbstractCommand{

    private String name;
    private byte[] bytes;

    @SneakyThrows
    public FileMessage(Path path){
        name = path.getFileName().toString();
        bytes = Files.readAllBytes(path);
    }
}
