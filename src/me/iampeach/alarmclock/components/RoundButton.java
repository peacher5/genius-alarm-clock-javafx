package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class RoundButton extends HBox {
    @FXML
    private ImageView iconView;

    private Label textLabel;

    public RoundButton(String text, Image icon, boolean isPrimary) {
        ComponentUtil.loadFXML(this, "round_button.fxml");
        if (text != null)
            setText(text);
        iconView.setImage(icon);
        if (isPrimary)
            setId("round-button-primary");
    }

    public RoundButton(String text, Image icon) {
        this(text, icon, false);
    }

    public RoundButton(Image icon) {
        this(null, icon, false);
    }

    private void setText(String text) {
        if (textLabel == null) {
            textLabel = new Label(text);
            getChildren().add(textLabel);
        } else
            textLabel.setText(text);
    }
}
