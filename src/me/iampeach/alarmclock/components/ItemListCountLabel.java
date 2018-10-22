package me.iampeach.alarmclock.components;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import me.iampeach.alarmclock.models.AlarmItem;

public class ItemListCountLabel extends Label {
    public ItemListCountLabel(ObservableList<AlarmItem> alarmItems) {
        getStyleClass().add("alarm-counter-text");
        update(alarmItems.size());
        alarmItems.addListener((ListChangeListener<? super AlarmItem>) c -> update(alarmItems.size()));
    }

    public void update(int count) {
        setText(count + " รายการ");
    }
}
