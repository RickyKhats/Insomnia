package uweellibs.graphics;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static uweellibs.graphics.KeyCode.*;

public class KeyListener implements java.awt.event.KeyListener {

    public native void keyTyped(KeyEvent keyEvent);

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode key = Convert(keyEvent);
        key.isPressed(true);
    }

    public void keyReleased(KeyEvent keyEvent) {
        KeyCode key = Convert(keyEvent);
        key.isPressed(false);
    }

    KeyCode Convert(KeyEvent key){
        KeyCode keyCode = null;
        switch (key.getKeyCode()){
            case VK_ENTER:
                keyCode = enter;
                break;
            case VK_SPACE:
                keyCode = space;
                break;
        }
        return keyCode;
    }
}
