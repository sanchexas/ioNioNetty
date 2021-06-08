package io;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.DataTruncation;
import java.util.ResourceBundle;
@Slf4j
public class ChatController implements Initializable {
    public TextField input;
    public ListView<String> listview;
    private String clientDir = "clientDir/";

    private ObjectInputStream is;
    private ObjectOutputStream os;

    public void send(ActionEvent actionEvent) throws IOException {
        String fileName = input.getText();
        FileObject file = new FileObject(Paths.get(clientDir, fileName));

        os.writeObject(file);
        os.writeObject(new ListRequest());
        os.flush();
        input.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try{
            Socket socket = new Socket("localhost", 8189);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            os.writeObject(new ListRequest());
            os.flush();


            Thread readThread = new Thread(()->{
                try {
                    while (true) {
                       Message message = (Message) is.readObject();
                       switch (message.getType()){
                           case LIST:
                               ListMessage list = (ListMessage) message;

                               Platform.runLater(() -> {
                                   listview.getItems().clear();
                                   listview.getItems()
                                           .addAll(list.getFiles());
                               });
                               break;
                       }


                    }
                }catch (Exception e){
                    log.error("e=", e);
                }
            });
            readThread.setDaemon(true);
            readThread.start();

        } catch (Exception e){
            log.error("e=",e);
        }
    }
}
