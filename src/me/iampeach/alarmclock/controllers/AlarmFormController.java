package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import me.iampeach.alarmclock.components.AppBar;
import me.iampeach.alarmclock.components.ComponentUtil;
import me.iampeach.alarmclock.components.DaySelectorButton;
import me.iampeach.alarmclock.components.RoundButton;
import me.iampeach.alarmclock.models.AlarmItem;
import me.iampeach.alarmclock.models.AlarmItemList;
import me.iampeach.alarmclock.models.OnceAlarmItem;
import me.iampeach.alarmclock.models.RepeatAlarmItem;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class AlarmFormController {
    @FXML
    private BorderPane root;
    @FXML
    private GridPane formGrid;
    @FXML
    private HBox daysContainer;
    @FXML
    private TextField titleTextField, hourTextField, minuteTextField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private DatePicker datePicker;

    private AlarmItem alarmItem;
    private RoundButton saveButton, cancelButton;
    private DaySelectorButton[] daySelectorButtons = new DaySelectorButton[7];

    public void setAlarmItem(AlarmItem alarmItem) {
        this.alarmItem = alarmItem;
    }

    public void initUI() {
        saveButton = new RoundButton("บันทึก", ComponentUtil.getImagePath("save.png"), true);
        cancelButton = new RoundButton("ยกเลิก", ComponentUtil.getImagePath("cancel.png"));

        AppBar appBar = new AppBar();
        appBar.addButton(saveButton);
        appBar.addButton(cancelButton);
        root.setTop(appBar);

        initDatePicker();

        // Set max length of title input to 30
        titleTextField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() > 30)
                titleTextField.setText(oldValue);
        });

        if (alarmItem == null)
            titleTextField.setText("ตั้งปลุก");
        else
            titleTextField.setText(alarmItem.getTitle());

        // Init type selector
        typeChoiceBox.getItems().addAll("เตือนครั้งเดียว", "เตือนซ้ำทุกสัปดาห์");
        if (alarmItem == null || alarmItem instanceof OnceAlarmItem)
            typeChoiceBox.getSelectionModel().selectFirst();
        else
            typeChoiceBox.getSelectionModel().selectLast();
        typeChoiceBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> onTypeToggle(newValue));

        // Init form state
        onTypeToggle(typeChoiceBox.getValue());

        // Time input filter
        hourTextField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}"))
                hourTextField.setText(oldValue);
        });
        minuteTextField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}"))
                minuteTextField.setText(oldValue);
        });

        String hour = String.format("%02d", alarmItem == null ? LocalTime.now().getHour() : alarmItem.getHour());
        String minute = String.format("%02d", alarmItem == null ? LocalTime.now().getMinute() + 1 : alarmItem.getMinute() + 1);
        hourTextField.setText(hour);
        minuteTextField.setText(minute);

        for (int i = 0; i < daySelectorButtons.length; i++) {
            DayOfWeek day = DayOfWeek.of(i + 1);
            DaySelectorButton button = new DaySelectorButton(day);
            button.setOnMouseClicked(event -> button.setSelected(!button.isSelected()));
            if (alarmItem instanceof RepeatAlarmItem && ((RepeatAlarmItem) alarmItem).repeatsContains(day))
                button.setSelected(true);
            daysContainer.getChildren().add(button);
            daySelectorButtons[i] = button;
        }
    }

    private void initDatePicker() {
        // Set date picker display format
        datePicker.setConverter(new StringConverter<>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty())
                    return null;
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });

        // Disable past dates
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Button click event
        saveButton.setOnMouseClicked(event -> onSaveButtonClick());
        cancelButton.setOnMouseClicked(event -> onCancelButtonClick());
    }

    private void onSaveButtonClick() {
        // Validate time input
        int hour = Integer.parseInt(hourTextField.getText());
        int minute = Integer.parseInt(minuteTextField.getText());
        LocalTime now = LocalTime.now();
        if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
            // TODO: alert
            System.out.println("invalid time input");
            return;
        }

        String type = typeChoiceBox.getValue();
        String title = titleTextField.getText();
        if (type.equals("เตือนครั้งเดียว")) {
            LocalDate date = datePicker.getValue();
            if (date.equals(LocalDate.now()) && (hour < now.getHour() || minute <= now.getMinute())) {
                // TODO: alert
                System.out.println("invalid time input");
                return;
            }
            LocalDateTime dateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, minute);
            AlarmItemList.getInstance().add(new OnceAlarmItem(title, dateTime));
        } else {
            HashSet<DayOfWeek> repeats = new HashSet<>();
            for (DaySelectorButton button : daySelectorButtons)
                if (button.isSelected())
                    repeats.add(button.getDay());
            if (repeats.isEmpty()) {
                // TODO: alert
                System.out.println("invalid repeats");
                return;
            }
            AlarmItemList.getInstance().add(new RepeatAlarmItem(title, LocalTime.of(hour, minute), repeats));
        }

        Stage window = (Stage) root.getScene().getWindow();
        SceneUtil.loadMainScene(window);
    }

    private void onCancelButtonClick() {
        Stage window = (Stage) root.getScene().getWindow();
        SceneUtil.loadMainScene(window);
    }

    private void onTypeToggle(String type) {
        int dateRow = 4, repeatRow = 8;
        if (type.equals("เตือนครั้งเดียว")) {
            showRow(dateRow, 30);
            showRow(dateRow - 1, 20);
            hideRow(repeatRow);
        } else {
            hideRow(dateRow);
            hideRow(dateRow - 1);
            showRow(repeatRow, 40);
        }
    }

    private void toggleRow(int selectedIndex, boolean isVisible, int height) {
        RowConstraints row = formGrid.getRowConstraints().get(selectedIndex);
        row.setMaxHeight(height);
        formGrid.getChildren().forEach(node -> {
            Integer index = GridPane.getRowIndex(node);
            if (index != null && index == selectedIndex) {
                node.setVisible(isVisible);
                node.setManaged(isVisible);
            }
        });
    }

    private void hideRow(int selectedIndex) {
        toggleRow(selectedIndex, false, 0);
    }

    private void showRow(int selectedIndex, int height) {
        toggleRow(selectedIndex, true, height);
    }
}
