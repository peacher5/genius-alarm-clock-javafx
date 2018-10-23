package me.iampeach.alarmclock.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class OnceAlarmItem extends AlarmItem {
    private LocalDateTime dateTime;

    public OnceAlarmItem(String title, LocalDateTime dateTime) {
        super(title);
        this.dateTime = dateTime;
        task = new AlarmTask(title + " - " + getTimeText());
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateText() {
        String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("th")).replace("วัน", "");
        String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("th"));

        // Not showing year if it's current year
        if (dateTime.getYear() == LocalDate.now().getYear())
            return String.format("%s %d %s", dayOfWeek, dateTime.getDayOfMonth(), month);
        else
            return String.format("%s %d %s %d", dayOfWeek, dateTime.getDayOfMonth(), month, dateTime.getYear());
    }

    @Override
    public int getHour() {
        return dateTime.getHour();
    }

    @Override
    public int getMinute() {
        return dateTime.getMinute();
    }

    @Override
    public Date getUpcomingDateTime() {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
