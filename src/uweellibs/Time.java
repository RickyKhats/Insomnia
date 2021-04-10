package uweellibs;

public class Time {
    byte seconds, minutes;
    short hours;

    public Time(){
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public Time(Time time){
        this.hours = time.hours;
        this.minutes = time.minutes;
        this.seconds = time.seconds;
    }

    public void tick(){
        seconds++;
        if(seconds > 59){
            minutes++;
            seconds = 0;
        }
        if(minutes > 59){
            hours++;
            minutes = 0;
        }
    }

    public String toString(){
        return (((hours > 9) ? String.valueOf(hours) : "0" + hours) + ":" + ((minutes > 9) ? String.valueOf(minutes) : "0" + minutes) + ":" + ((seconds > 9) ? String.valueOf(seconds) : "0" + seconds));
    }
}