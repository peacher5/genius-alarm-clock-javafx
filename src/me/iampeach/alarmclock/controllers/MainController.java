package me.iampeach.alarmclock.controllers;

import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private VBox alarmContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUI();
    }

    private void initUI() {
        RoundButton addButton = new RoundButton("เพิ่ม", ComponentUtil.getImagePath("alarm_add.png"));
        RoundButton settingsButton = new RoundButton(ComponentUtil.getImagePath("settings.png"));

        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);

        addButton.setOnMouseClicked(event -> onAddButtonClick());
        settingsButton.setOnMouseClicked(event -> onSettingsButtonClick());

        ObservableList<AlarmItem> alarmItems = AlarmItemList.getInstance().getList();
        alarmItems.forEach(item -> alarmContainer.getChildren().add(createAlarmListItem(item)));

        ItemListCountLabel itemListCountLabel = new ItemListCountLabel(alarmItems);
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
