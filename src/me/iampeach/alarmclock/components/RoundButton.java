package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class RoundButton extends HBox {
    @FXML
    private ImageView iconView;

    private Label textLabel;

    public RoundButton(String text) {
        loadFXML();
        if (text != null)
            setText(text);
    }

    public RoundButton() {
        this(null);
    }

    public void setIcon(String path) {
        iconView.setImage(new Image(path));
    }

    public void setText(String text) {
        if (textLabel == null) {
            textLabel = new Label(text);
            textLabel.setStyle("-fx-text-fill: #555555; -fx-padding: 2px 2px 0 0;");
            getChildren().add(textLabel);
        } else
            textLabel.setText(text);
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/round_button.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
