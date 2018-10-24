package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.iampeach.alarmclock.components.AppBar;
import me.iampeach.alarmclock.components.ComponentUtil;
import me.iampeach.alarmclock.components.RoundButton;

public class AboutSceneController implements SceneController {
    @FXML
    private BorderPane root;

    @Override
    public void initUI() {
        RoundButton backButton = new RoundButton("กลับ", ComponentUtil.getImage("back.png"));
        backButton.setOnMouseClicked(event -> onBackButtonClick());

        RoundButton mathTestButton = new RoundButton("ทดสอบโจทย์", ComponentUtil.getImage("open.png"));
        mathTestButton.setOnMouseClicked(event -> SceneUtil.launchAlarmWindow("Math Challenges"));

        AppBar appBar = new AppBar();
        appBar.addButton(backButton);
        appBar.addButton(mathTestButton);

        root.setTop(appBar);
    }

    private void onBackButtonClick() {
        Stage window = (Stage) root.getScene().getWindow();
        SceneUtil.loadMainScene(window);
    }
}
