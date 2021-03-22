package uweellibs;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public void X(int x) {
        this.x = x;
    }

    public int Y() {
        return y;
    }

    public void Y(int y) {
        this.y = y;
    }
}
