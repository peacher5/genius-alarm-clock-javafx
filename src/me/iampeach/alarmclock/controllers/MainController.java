package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.iampeach.alarmclock.components.AlarmListItem;
import me.iampeach.alarmclock.components.AppBar;
import me.iampeach.alarmclock.components.ComponentUtil;
import me.iampeach.alarmclock.components.RoundButton;
import me.iampeach.alarmclock.models.AlarmItem;
import me.iampeach.alarmclock.models.AlarmItemList;
import me.iampeach.alarmclock.models.OnceAlarmItem;
import me.iampeach.alarmclock.models.RepeatAlarmItem;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private VBox alarmContainer;

    private RoundButton addButton = new RoundButton("เพิ่ม", ComponentUtil.getImagePath("alarm_add.png"));
    private RoundButton settingsButton = new RoundButton(ComponentUtil.getImagePath("settings.png"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUI();
        for (AlarmItem item : AlarmItemList.getInstance().getAll()) {
            String time = String.format("%02d:%02d", item.getHour(), item.getMinute());
            String date;
            if (item instanceof OnceAlarmItem) {
                LocalDateTime dateTime = ((OnceAlarmItem) item).getDateTime();
                String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("th"));
                dayOfWeek = dayOfWeek.replace("วัน", "");
                String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("th"));
                if (dateTime.getYear() == LocalDate.now().getYear())
                    date = String.format("%s %d %s", dayOfWeek, dateTime.getDayOfMonth(), month);
                else
                    date = String.format("%s %d %s %d", dayOfWeek, dateTime.getDayOfMonth(), month, dateTime.getYear());
            } else {
                ArrayList<String> days = new ArrayList<>();
                for (DayOfWeek day : DayOfWeek.values()) {
                    if (((RepeatAlarmItem) item).repeatContains(day)) {
                        String dayThai = day.getDisplayName(TextStyle.SHORT, new Locale("th"));
                        days.add(dayThai.substring(0, dayThai.length() - 1));
                    }
                }
                date = String.join(", ", days);
            }
            AlarmListItem alarmListItem = new AlarmListItem(item.getTitle(), time, date);
            alarmListItem.setOnDeleteListener(() -> {
                alarmContainer.getChildren().remove(alarmListItem);
                AlarmItemList.getInstance().remove(item);
            });
            alarmContainer.getChildren().add(alarmListItem);
        }
//        alarmContainer.getChildren().add(new AlarmListItem("ตื่นนอน", "06:30", "จ, อ, พ, พฤ, ศ"));
    }

    private void initUI() {
        AppBar appBar = new AppBar();
        appBar.addButton(addButton);
        appBar.addButton(settingsButton);
        root.setTop(appBar);

        addButton.setOnMouseClicked(event -> onAddButtonClick());
        settingsButton.setOnMouseClicked(event -> onSettingsButtonClick());
    }

    private void onAddButtonClick() {
        Stage window = (Stage) root.getScene().getWindow();
        SceneUtil.loadAlarmFormScene(window);
    }

    private void onSettingsButtonClick() {
        // TODO
    }
}
