package uweellibs.graphics.view;

import uweellibs.MonoBehaviour;
import uweellibs.Vector2;
import uweellibs.graphics.Sprite;
import uweellibs.graphics.Themes;

import java.awt.*;

public abstract class View extends MonoBehaviour {
    //возвращаем этот обьект дабы можно было редактировать его, не перемещаясь на новую строку
    public abstract View Size(int width, int height);
    public abstract Vector2 Size();
    public abstract View Position(int x, int y);
    public abstract Vector2 Position();
    public abstract Component get();
    public abstract Sprite Sprite();
    public abstract View Theme(Themes theme);

    public native View Add(View component);
}
