package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import me.iampeach.alarmclock.components.AlarmListItem;
import me.iampeach.alarmclock.components.AppBar;
import me.iampeach.alarmclock.components.RoundButton;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private VBox alarmContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoundButton addButton = new RoundButton("เพิ่ม", "me/iampeach/alarmclock/images/alarm_add.png");
        RoundButton settingsButton = new RoundButton("me/iampeach/alarmclock/images/settings.png");

        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);

        alarmContainer.getChildren().add(new AlarmListItem("ตั้งปลุก 1", "17:00", "ศุกร์ 24 สิงหา"));
        alarmContainer.getChildren().add(new AlarmListItem("ตื่นนอน", "06:30", "จ, อ, พ, พฤ, ศ"));
        alarmContainer.getChildren().add(new AlarmListItem("ตั้งปลุก 2", "18:00", "อา"));
        alarmContainer.getChildren().add(new AlarmListItem("ตั้งปลุก 3", "19:00", "ส"));
    }
}
