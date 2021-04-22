package uweellibs;

import com.uweeldteam.ExceptionOccurred;

public abstract class MonoBehaviour {

    //time to calls all updates
    public float localUpdateTime = 0.050f;
    private boolean hasUpdate;
    private boolean hasPostUpdate;

    public MonoBehaviour() {
        try {
            //calls on all methods
            new Thread(() -> {
                //call PreInit
                try {
                    PreInit();
                } catch (UnsatisfiedLinkError ignored) {
                }
                //call Init
                try {
                    Init();
                } catch (UnsatisfiedLinkError ignored) {
                }
                //call Update
                try {
                    Update();
                    hasUpdate = true;
                } catch (UnsatisfiedLinkError ignored) {
                    hasUpdate = false;
                }
                //call PostInit
                try {
                    PostInit();
                } catch (UnsatisfiedLinkError ignored) { /* class not contains this method */ }
                //call PostUpdate
                try {
                    PostUpdate();
                    hasPostUpdate = true;
                } catch (UnsatisfiedLinkError ignored) {
                    hasPostUpdate = false;
                }
            }).start();

            if (hasUpdate){
                Thread update = new Thread(() -> {
                    while (true) {
                        Update();
                        new WaitForSeconds(localUpdateTime);
                    }
                });
                update.setName(this.getClass().toString() + " update update");
                update.setDaemon(true);
                update.start();
            }
            if (hasPostUpdate){
                Thread postUpdate = new Thread(() -> {
                    while (true) {
                        PostUpdate();
                        new WaitForSeconds(localUpdateTime);
                    }
                });
                postUpdate.setName(this.getClass().toString() + " post update");
                postUpdate.setDaemon(true);
                postUpdate.start();
            }

        } catch (Exception e) {
            new ExceptionOccurred(e);
        }
    }

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
}
