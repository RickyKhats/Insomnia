package uweellibs;

import java.time.ZoneId;
import java.util.Date;

public class DateTime {
    short day, month;
    int year;
    short hour, minute, second;

    static Date date;

    {
        date = new Date();
        Now();
    }

    public DateTime Now() {
        year = (short) date.toInstant().atZone(ZoneId.systemDefault()).getYear();
        month = (short) date.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
        day = (short) date.toInstant().atZone(ZoneId.systemDefault()).getDayOfMonth();
        hour = (short) date.toInstant().atZone(ZoneId.systemDefault()).getHour();
        minute = (short) date.toInstant().atZone(ZoneId.systemDefault()).getMinute();
        second = (short) date.toInstant().atZone(ZoneId.systemDefault()).getSecond();
        return this;
    }
    public String toString(){
        return hour + "-" + minute + "-" + second + " " + day + "." + minute + "." + year;
    }
}
