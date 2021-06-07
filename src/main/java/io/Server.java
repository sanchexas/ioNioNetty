package io;





import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
@Slf4j


public class Server {


    public static void main(String[] args) throws IOException {

//        User user = User.builder()
//                .name("Petya")
//                .age(18)
//                .surname("Petrov")
//                .build();


        ServerSocket server = new ServerSocket(8189);



        log.debug("Сервер запущен");


        while (true){

            try {

                Socket socket = server.accept();

                log.debug("Клиент принят");

                Handler handler = new Handler(socket);

                new Thread(handler).start();

            }catch (Exception e){

                log.error("Соединение оборвалось");
            }
        }
    }
}
