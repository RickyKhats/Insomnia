package uweellibs.graphics;

import uweellibs.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    public Image sprite = new BufferedImage(1,1,1);
    Vector2 position = new Vector2(0,0);

    public Sprite() {

    }

    public Sprite(Image image, int x, int y) {
        sprite = image;
        position = new Vector2(x, y);
    }
}
