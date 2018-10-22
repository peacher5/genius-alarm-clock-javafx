package me.iampeach.alarmclock.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DaySelectorButton extends VBox {
    @FXML
    private Label text;

    private DayOfWeek day;
    private boolean isSelected;

    public DaySelectorButton(DayOfWeek day) {
        this.day = day;
        ComponentUtil.loadFXML(this, "day_selector_button.fxml");
        text.setText(day.getDisplayName(TextStyle.SHORT, new Locale("th")));
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        setId(selected ? "day-selector-button-selected" : null);
    }

    public DayOfWeek getDay() {
        return day;
    }
}
