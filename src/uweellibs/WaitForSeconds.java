package uweellibs;

public class WaitForSeconds {

    public WaitForSeconds(float seconds){
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
