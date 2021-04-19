package uweellibs.graphics;

import uweellibs.MonoBehaviour;
import uweellibs.Vector2;

import java.awt.*;

public abstract class WindowComponent extends MonoBehaviour {
    //возвращаем этот обьект дабы можно было редактировать его, не перемещаясь на новую строку
    public abstract WindowComponent Size(int width, int height);
    public abstract Vector2 Size();
    //возвращаем этот обьект дабы можно было редактировать его, не перемещаясь на новую строку
    public abstract WindowComponent Position(int x, int y);
    public abstract Vector2 Position();
    public abstract Component get();
    public abstract Sprite Sprite();
    public abstract WindowComponent Theme(Themes theme);
}
