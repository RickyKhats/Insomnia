package uweellibs;

import com.uweeldteam.ExceptionOccurred;

public class WaitForMills {
    public WaitForMills(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            new ExceptionOccurred(e);
        }
    }
}
