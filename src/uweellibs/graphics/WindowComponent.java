package uweellibs.graphics;

import uweellibs.MonoBehaviour;
import uweellibs.Vector2;

import java.awt.*;

public abstract class WindowComponent extends MonoBehaviour {
    public abstract WindowComponent Size(int height, int width);
    public abstract Dimension Size();
    public abstract WindowComponent Position(int x, int y);
    public abstract Vector2 Position();

    public abstract Component get();

    public abstract Sprite Sprite();
}
