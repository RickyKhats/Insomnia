package uweellibs.graphics;

import uweellibs.Debug;

public enum KeyCode {
    enter(new Key("enter")),
    space(new Key("space"));

    Key key;

    KeyCode(Key key) {
        this.key = key;
    }

    public String toString() {
        return key.name + " isPressed = " + key.isPressed;
    }

    public String keyName() {
        return key.name;
    }

    protected boolean isPressed() {
        return key.isPressed;
    }

    protected void isPressed(boolean isPressed) {
        key.isPressed = isPressed;
        Debug.Log(toString());
    }
}
