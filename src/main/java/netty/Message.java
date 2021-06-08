package netty;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
public class Message implements Serializable {

    private String content;
    private String author;
    private LocalDateTime createdAt;

    public String getDateFormatted(){
        return DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss").format(createdAt);
    }
}
