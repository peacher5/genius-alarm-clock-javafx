package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ActionButton extends AnchorPane {
    @FXML
    private ImageView iconView;
    @FXML
    private Label textLabel;

    public ActionButton(String text, String iconPath, String styleClass) {
        ComponentUtil.loadFXML(this, "action_button.fxml");
        textLabel.setText(text);
        iconView.setImage(new Image(iconPath));
        getStyleClass().add(styleClass);
    }
}
