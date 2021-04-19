package uweellibs;

public enum KeyCode {
    enter(new Key());

    Key key;

    KeyCode(Key key){

        this.key = key;
    }

    protected boolean isPressed(){
        return key.isPressed;
    }
    protected void isPressed(boolean isPressed){
        key.isPressed = isPressed;
    }
}
