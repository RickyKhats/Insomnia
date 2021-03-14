package uweellibs;

public class Time {
    public static int globalUpdateTime = 50;

    public static void Sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
