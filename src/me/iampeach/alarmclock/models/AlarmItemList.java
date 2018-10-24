package me.iampeach.alarmclock.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

public class AlarmItemList {
    private static final String filePath = "alarms.dat";
    private static AlarmItemList instance;

    private ObservableList<AlarmItem> alarmItems = FXCollections.observableList(new ArrayList<>());

    private AlarmItemList() {
        read();
        alarmItems.forEach(AlarmItem::activateAlarm);
    }

    public static AlarmItemList getInstance() {
        if (instance == null)
            instance = new AlarmItemList();
        return instance;
    }

    public void add(AlarmItem item) {
        item.activateAlarm();
        alarmItems.add(item);
        write();
    }

    public void remove(AlarmItem item) {
        item.cancelAlarm();
        alarmItems.remove(item);
        write();
    }

    public ObservableList<AlarmItem> getList() {
        return alarmItems;
    }

    public void cancelAlarmTasks() {
        alarmItems.forEach(AlarmItem::cancelAlarm);
    }

    private void write() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (AlarmItem item : alarmItems) {
                String line;
                if (item instanceof OnceAlarmItem)
                    line = String.format("once,%s,%s", ((OnceAlarmItem) item).getDateTime().toString(), item.getTitle());
                else
                    line = String.format("repeat,%s,%s,%s", item.getTimeText(), toDoWFormat((RepeatAlarmItem) item), item.getTitle());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toDoWFormat(RepeatAlarmItem repeatAlarmItem) {
        StringBuilder result = new StringBuilder();
        for (DayOfWeek day : repeatAlarmItem.getSortedRepeatDays())
            result.append(day.getValue());
        return result.toString();
    }

    private HashSet<DayOfWeek> toRepeatsSet(String dowFormat) {
        HashSet<DayOfWeek> repeats = new HashSet<>();
        for (int i = 0; i < dowFormat.length(); i++) {
            int dayNo = Integer.parseInt(dowFormat.charAt(i) + "");
            repeats.add(DayOfWeek.of(dayNo));
        }
        return repeats;
    }

    private void read() {
        alarmItems.clear();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    LocalDateTime dateTime = LocalDateTime.parse(parts[1]);
                    // Ignore if an alarm is passed
                    if (!dateTime.isBefore(LocalDateTime.now()))
                        alarmItems.add(new OnceAlarmItem(parts[2], dateTime));
                } else if (parts.length == 4)
                    alarmItems.add(new RepeatAlarmItem(parts[3], LocalTime.parse(parts[1]), toRepeatsSet(parts[2])));
            });
        } catch (IOException e) {
            // Do nothing if file not found
        }
    }
}
