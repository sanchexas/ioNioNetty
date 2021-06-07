package nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class Server {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer;
    private String dir = "serverDir";


    @SneakyThrows
    public Server(){
        buffer = ByteBuffer.allocate(100);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8189));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.debug("Сервер запущен!");

        while (serverSocketChannel.isOpen()){

            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()){
                    handleAccept(key);
                }
                if (key.isAcceptable()){
                    handleRead(key);
                }
                keyIterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            StringBuilder s = new StringBuilder();
            int r;
            while (true) {
                r = channel.read(buffer);
                if (r == -1) {
                    channel.close();
                    return;
                }
                if (r == 0) {
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    s.append((char) buffer.get());
                }
                buffer.clear();
            }
            String message = s.toString().trim();
            log.debug("Отправленные сообщения: {}", message);
            if (message.equals("ls")) {
                String files = Files.list(Paths.get(dir))
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.joining("\n")) + "\n\r";
                channel.write(ByteBuffer.wrap(files.getBytes(StandardCharsets.UTF_8)));
            } else if (message.startsWith("cat ")) {
                String fileName = message.replaceAll("cat ", "");
                String data = String.join("", Files.readAllLines(Paths.get(dir, fileName))) + "\n\r";
                channel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));

            } else {

                channel.write(ByteBuffer.wrap("Неверная команда!\n\r".getBytes(StandardCharsets.UTF_8)));
            }
        }catch (Exception e){
            log.error("Исключение при чтении: ", e);
        }
    }

    @SneakyThrows
    private void handleAccept(SelectionKey key) {
        SocketChannel channel = serverSocketChannel.accept();
        log.debug("Клиент принят");
        channel.write(ByteBuffer.wrap("Добро пожаловать на сервер!\n\r".getBytes(StandardCharsets.UTF_8)));
        channel.configureBlocking(false);

        channel.register(selector,SelectionKey.OP_READ, "HelloWorldddddd");
    }
}
