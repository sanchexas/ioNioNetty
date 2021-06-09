package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.client.FilesListRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class ObjectHandler extends SimpleChannelInboundHandler<AbstractCommand>{

private static final String serverRootDir = "serverDir";
private Path serverPath;

    public ObjectHandler() {
        serverPath = Paths.get(serverRootDir);
    }




    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractCommand message) throws Exception {
        log.debug("received: {}",message);
        if (message instanceof FilesListRequest){

            ctx.writeAndFlush(getFiles());
        }
        if (message instanceof FileRequest){
            ctx.writeAndFlush(getFileMessage((FileRequest) message));
        }
        if (message instanceof FileMessage){
            FileMessage fileMessage = (FileMessage) message;
            saveFile((FileMessage) message);
        }

    }

    private void saveFile(FileMessage fileMessage) throws IOException {
        Files.write(serverPath.resolve(fileMessage.getName()),

                fileMessage.getBytes(),
                StandardOpenOption.CREATE

        );
    }

    private FileMessage getFileMessage(FileRequest request){
        return new FileMessage(serverPath.resolve(request.getName()));
    }

    private FilesListResponse getFiles(){
        return new FilesListResponse(serverPath);
    }
}
