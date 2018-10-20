package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import me.iampeach.alarmclock.components.AppBar;
import me.iampeach.alarmclock.components.RoundButton;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RoundButton addButton = new RoundButton("เพิ่ม");
        addButton.setIcon("me/iampeach/alarmclock/images/alarm_add.png");

        RoundButton settingsButton = new RoundButton();
        settingsButton.setIcon("me/iampeach/alarmclock/images/settings.png");

        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);
    }
}
