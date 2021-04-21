package uweellibs.graphics;

public enum KeyCode {
    enter(new Key("Enter")),
    space(new Key("Space"));

    Key key;

    KeyCode(Key key){

        this.key = key;
    }

    public String toString() {
        return key.name +
                "isPressed = " + key.isPressed;
    }

    public String keyName(){
        return key.name;
    }
    protected boolean isPressed(){
        return key.isPressed;
    }
    protected void isPressed(boolean isPressed){
        key.isPressed = isPressed;
    }
}
