package netty.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import netty.Message;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    private Network network;
    public TextField input;
    public ListView<String> listView;

    public void send(ActionEvent actionEvent) {
        network.sendMessage(Message.builder()
                .author("User")
                .createdAt(LocalDateTime.now())
                .content(input.getText())
                .build());
        input.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            network = new Network(msg -> {
               String result = msg.getDateFormatted() + " " + msg.getAuthor() + ": " + msg.getContent();
                Platform.runLater(() -> listView.getItems().add(result));
            });

    }
}
