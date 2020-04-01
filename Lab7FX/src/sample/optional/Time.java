package sample.optional;

public class Time {
    int hours;
    int minutes;
    int seconds;

    public Time(int totalTime){
        seconds = totalTime%60;
        totalTime = totalTime/60;
        minutes = totalTime%60;
        totalTime = totalTime/60;
        hours = totalTime>=100?99:totalTime;
    }

    public void setTime(int totalTime){
        seconds = totalTime%60;
        totalTime = totalTime/60;
        minutes = totalTime%60;
        totalTime = totalTime/60;
        hours = totalTime>=100?99:totalTime;
    }


    private String digital(int n){
        return String.valueOf(n/10%10) + n % 10;
    }

    public String timeToString() {
        return digital(hours) + ":" + digital(minutes) + ":" + digital(seconds);
    }
}
