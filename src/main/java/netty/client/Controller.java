package netty.client;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import netty.FileMessage;
import netty.FileRequest;
import netty.FilesListResponse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final String clientRootDir = "clientDir";
    public ListView<String> clientView;
    private Network network;
    //public TextField input;
    public ListView<String> serverView;
    private Path clientPath;

//    public void send(ActionEvent actionEvent) {
//        network.sendMessage(Message.builder()
//                .author("User")
//                .createdAt(LocalDateTime.now())
//                .content(input.getText())
//                .build());
//        input.clear();
//    }
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clientPath = Paths.get(clientRootDir);
        showClientFiles();
            network = new Network(msg -> {
                if (msg instanceof FilesListResponse){
                    updateServerSide((FilesListResponse) msg) ;
                }
                if (msg instanceof  FileMessage){
                    FileMessage fileMessage = (FileMessage) msg;
                    handleFileMessage(fileMessage);
                }


//               String result = msg.getDateFormatted() + " " + msg.getAuthor() + ": " + msg.getContent();
//                Platform.runLater(() -> listView.getItems().add(result));
            });



    }

    @SneakyThrows
    private void handleFileMessage(FileMessage fileMessage) {
        Files.write(clientPath.resolve(Paths.get(fileMessage.getName())),
                fileMessage.getBytes(),
                StandardOpenOption.CREATE);
        Platform.runLater(this::showClientFiles);
    }

    public void toServer(ActionEvent actionEvent) {

        String fileName = clientView.getSelectionModel().getSelectedItem();
        FileMessage fileMessage = new FileMessage(clientPath.resolve(fileName));
        network.sendMessage(fileMessage);
        network.sendMessage(new FilesListRequest()
        );


    }

    public void fromServer(ActionEvent actionEvent) {

        String fileName = serverView.getSelectionModel().getSelectedItem();
        FileRequest fileRequest = new FileRequest(fileName);
        network.sendMessage(fileRequest);



    }


    private void updateServerSide(FilesListResponse response){
        Platform.runLater(() ->{
            serverView.getItems().clear();
            serverView.getItems().addAll(response.getFiles());
        });

    }



    private void showClientFiles(){
        clientView.getItems().clear();
        try {
            Files.list(clientPath)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(f -> clientView.getItems().add(f));
        } catch (IOException ignored) {

        }


    }
}
