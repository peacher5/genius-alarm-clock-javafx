package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class AppBar extends AnchorPane {
    @FXML
    private HBox buttonContainer;

    public AppBar() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/app_bar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButton(RoundButton button) {
        buttonContainer.getChildren().add(button);
    }
}
