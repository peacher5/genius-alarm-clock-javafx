package me.iampeach.alarmclock.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.iampeach.alarmclock.components.*;
import me.iampeach.alarmclock.models.AlarmItem;
import me.iampeach.alarmclock.models.AlarmItemList;
import me.iampeach.alarmclock.models.OnceAlarmItem;
import me.iampeach.alarmclock.models.RepeatAlarmItem;

public class MainSceneController {
    @FXML
    private BorderPane root;
    @FXML
    private VBox alarmContainer;

    private ObservableList<AlarmItem> alarmItems = AlarmItemList.getInstance().getList();
    private Stage window;

    void initUI() {
        window = (Stage) root.getScene().getWindow();

        RoundButton addButton = new RoundButton("เพิ่ม", ComponentUtil.getImage("alarm_add.png"));
        RoundButton settingsButton = new RoundButton(ComponentUtil.getImage("settings.png"));

        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);

        addButton.setOnMouseClicked(event -> onAddButtonClick());
        settingsButton.setOnMouseClicked(event -> onSettingsButtonClick());

        alarmItems.forEach(item -> alarmContainer.getChildren().add(createAlarmListItem(item)));

        alarmContainer.getChildren().add(new ItemListCountLabel(alarmItems));
    }

    private AlarmListItem createAlarmListItem(AlarmItem item) {
        String date;
        if (item instanceof OnceAlarmItem)
            date = ((OnceAlarmItem) item).getDateText();
        else
            date = ((RepeatAlarmItem) item).getRepeatsText();

        AlarmListItem alarmListItem = new AlarmListItem(item.getTitle(), item.getTimeText(), date);
        alarmListItem.setOnEditListener(() -> SceneUtil.loadAlarmFormScene(window, item));
        alarmListItem.setOnDeleteListener(() -> removeItem(alarmListItem, item));

        if (item instanceof OnceAlarmItem)
            item.getTask().setOnTaskExecute(() -> removeItem(alarmListItem, item));

        return alarmListItem;
    }

    private void removeItem(AlarmListItem alarmListItem, AlarmItem alarmItem) {
        alarmContainer.getChildren().remove(alarmListItem);
        AlarmItemList.getInstance().remove(alarmItem);
    }

    private void onAddButtonClick() {
        SceneUtil.loadAlarmFormScene(window);
    }

    private void onSettingsButtonClick() {
        // TODO
    }
}
