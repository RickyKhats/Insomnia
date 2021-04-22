package uweellibs.graphics;

public class Input {
    private final KeyListener keyListener;

    public Input(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public boolean GetKeyDown(KeyCode keyCode){
        return keyCode.isPressed();
    }
    public boolean MousePressed(MouseButton mouseButton){
        return false;
    }
}
