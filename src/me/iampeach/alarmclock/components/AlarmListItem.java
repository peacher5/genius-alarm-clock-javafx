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

    public AlarmListItem(String title, String time, String date) {
        ComponentUtil.loadFXML(this, "alarm_list_item.fxml");

        ActionButton deleteButton = new ActionButton(
                "ลบ",
                ComponentUtil.getImagePath("delete.png"),
                "delete-button");
        ActionButton editButton = new ActionButton(
                "แก้ไข",
                ComponentUtil.getImagePath("edit.png"),
                "edit-button");

        actionButtonContainer.getChildren().addAll(editButton, deleteButton);

        titleLabel.setText(title);
        timeLabel.setText(time);
        dateLabel.setText(date);
    }
}
