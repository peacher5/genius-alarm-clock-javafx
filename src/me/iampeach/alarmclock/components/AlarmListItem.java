package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AlarmListItem extends AnchorPane {
    @FXML
    private HBox actionButtonContainer;
    @FXML
    private Label titleLabel, timeLabel, dateLabel;

    private ActionButton editButton, deleteButton;

    public AlarmListItem(String title, String time, String date) {
        ComponentUtil.loadFXML(this, "alarm_list_item.fxml");

        deleteButton = new ActionButton("ลบ", ComponentUtil.getImage("delete.png"), "delete-button");
        editButton = new ActionButton("แก้ไข", ComponentUtil.getImage("edit.png"), "edit-button");
        actionButtonContainer.getChildren().addAll(editButton, deleteButton);

        titleLabel.setText(title);
        timeLabel.setText(time);
        dateLabel.setText(date);
    }

    public void setOnEditListener(Runnable callback) {
        editButton.setOnMouseClicked(event -> callback.run());
    }

    public void setOnDeleteListener(Runnable callback) {
        deleteButton.setOnMouseClicked(event -> callback.run());
    }
}
