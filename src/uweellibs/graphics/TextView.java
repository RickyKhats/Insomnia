package uweellibs.graphics;

import uweellibs.Vector2;

import javax.swing.*;
import java.awt.*;

public class TextView extends WindowComponent {

    JLabel text;

    public TextView (String text, Vector2 position, Vector2 size){
        this.text = new JLabel(text);
        Position(position);
        Size(size);
    }

    private void Size(Vector2 size) {
        text.setSize(size.X(), size.Y());
    }

    private void Position(Vector2 position) {
        text.setBounds(position.X(), position.Y(), text.getWidth(), text.getHeight());
    }

    public WindowComponent Size(int width, int height) {
        return this;
    }

    public Vector2 Size() {
        return null;
    }

    public WindowComponent Position(int x, int y) {
        return this;
    }

    public Vector2 Position() {
        return null;
    }

    public Component get() {
        return null;
    }

    public Sprite Sprite() {
        return null;
    }

    public WindowComponent Theme(Themes theme) {
        return this;
    }
}
