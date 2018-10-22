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
    private static ObservableList<AlarmItem> alarmItems = FXCollections.observableList(new ArrayList<>());
    private static AlarmItemList instance;

    private AlarmItemList() {
    }

    public static AlarmItemList getInstance() {
        if (instance == null)
            instance = new AlarmItemList();
        return instance;
    }

    public void add(AlarmItem item) {
        alarmItems.add(item);
        write();
    }

    public void remove(AlarmItem item) {
        alarmItems.remove(item);
        write();
    }

    public ObservableList<AlarmItem> getList() {
        read();
        return alarmItems;
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
        for (DayOfWeek day : DayOfWeek.values())
            if (repeatAlarmItem.repeatsContains(day))
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
                if (parts.length == 3)
                    alarmItems.add(new OnceAlarmItem(parts[2], LocalDateTime.parse(parts[1])));
                else if (parts.length == 4)
                    alarmItems.add(new RepeatAlarmItem(parts[3], LocalTime.parse(parts[1]), toRepeatsSet(parts[2])));
            });
        } catch (IOException e) {
            // File not found
        }
    }
}
