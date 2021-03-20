package uweellibs;

import com.uweeldteam.ExceptionOccurred;
import com.uweeldteam.Main;

public abstract class MonoBehaviour {

    //time to calls all updates
    public float localUpdateTime = 0.050f;
    //starts with program starts
    public synchronized native void PreInit();
    //starts with PreInit completed
    public synchronized native void Init();
    //starts with PreInit completed
    public synchronized native void PostInit();
    //starts on Init
    public native void Update();
    //starts on PostInit
    public native void PostUpdate();

    public MonoBehaviour(){
        try {
            //calls on all methods
            new Thread(() -> {
                //call PreInit
                try {
                    PreInit();
                } catch (UnsatisfiedLinkError ignored){ /* class not contains this method */ }
                //call Init
                try {
                    Init();
                } catch (UnsatisfiedLinkError ignored){ /* class not contains this method */ }
                //call Update
                Thread update = new Thread(() -> {
                    try {
                        while (true) {
                            Update();
                            new WaitForSeconds(localUpdateTime);
                        }
                    } catch (UnsatisfiedLinkError ignored) { /* class not contains this method */ }
                });
                update.setName(this.getClass().toString() + " update");
                update.setDaemon(false);
                update.start();
                //call PostInit
                try {
                    PostInit();
                } catch (UnsatisfiedLinkError ignored){ /* class not contains this method */ }
                //call PostUpdate
                Thread postUpdate = new Thread(() -> {
                    try {
                        while (true){
                            PostUpdate();
                            new WaitForSeconds(localUpdateTime);
                        }
                    } catch (UnsatisfiedLinkError ignored){ /* class not contains this method */ }
                });
                postUpdate.setName(this.getClass().toString() + " post update");
                postUpdate.setDaemon(false);
                postUpdate.start();
            }).start();
        } catch (Exception e){
            new ExceptionOccurred(e);
        }
    }
}
