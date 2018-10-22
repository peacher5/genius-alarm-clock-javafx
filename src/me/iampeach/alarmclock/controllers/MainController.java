package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.iampeach.alarmclock.components.*;
import me.iampeach.alarmclock.models.AlarmItem;
import me.iampeach.alarmclock.models.AlarmItemList;
import me.iampeach.alarmclock.models.OnceAlarmItem;
import me.iampeach.alarmclock.models.RepeatAlarmItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private VBox alarmContainer;

    private RoundButton addButton = new RoundButton("เพิ่ม", ComponentUtil.getImagePath("alarm_add.png"));
    private RoundButton settingsButton = new RoundButton(ComponentUtil.getImagePath("settings.png"));
    private ItemListCountLabel itemListCountLabel = new ItemListCountLabel();
    private ArrayList<AlarmItem> alarmItems = AlarmItemList.getInstance().getAll();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUI();
    }

    private void initUI() {
        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);

        addButton.setOnMouseClicked(event -> onAddButtonClick());
        settingsButton.setOnMouseClicked(event -> onSettingsButtonClick());

        alarmItems.forEach(item -> alarmContainer.getChildren().add(createAlarmListItem(item)));

        itemListCountLabel.update(alarmItems.size());
        alarmContainer.getChildren().add(itemListCountLabel);
    }

    private AlarmListItem createAlarmListItem(AlarmItem item) {
        String date;
        if (item instanceof OnceAlarmItem)
            date = ((OnceAlarmItem) item).getDateText();
        else
            date = ((RepeatAlarmItem) item).getRepeatsText();

        AlarmListItem alarmListItem = new AlarmListItem(item.getTitle(), item.getTimeText(), date);
        alarmListItem.setOnDeleteListener(() -> {
            alarmContainer.getChildren().remove(alarmListItem);
            AlarmItemList.getInstance().remove(item);
            itemListCountLabel.update(alarmItems.size());
        });

        return alarmListItem;
    }

    private void onAddButtonClick() {
        Stage window = (Stage) root.getScene().getWindow();
        SceneUtil.loadAlarmFormScene(window);
    }

    private void onSettingsButtonClick() {
        // TODO
    }
}
