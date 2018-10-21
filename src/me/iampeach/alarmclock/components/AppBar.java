package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AppBar extends AnchorPane {
    @FXML
    private HBox buttonContainer;

    public AppBar() {
        ComponentUtil.loadFXML(this, "app_bar.fxml");
    }

    public void addButton(RoundButton button) {
        buttonContainer.getChildren().add(button);
    }
}
