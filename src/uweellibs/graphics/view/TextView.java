package uweellibs.graphics.view;

import uweellibs.Vector2;
import uweellibs.graphics.Sprite;
import uweellibs.graphics.Themes;

import javax.swing.*;
import java.awt.*;

public class TextView extends View {

    JLabel text;

    public TextView() {
        this.text = new JLabel();
        Size(15,45);
        Position(new Vector2(0, 0));
    }
    public TextView(Vector2 position) {
        this.text = new JLabel();
        Size(15, 45);
        Position(position);
    }
    public TextView(Vector2 position, Vector2 size) {
        this.text = new JLabel();
        Size(size);
        Position(position);
    }
    public TextView(String text) {
        this.text = new JLabel(text);
        Size(15, 45);
        Position(0,0);
    }
    public TextView(String text, Vector2 position) {
        this.text = new JLabel();
        Size(15, 45);
        Position(position);
    }
    public TextView(String text, Vector2 position, Vector2 size) {
        this.text = new JLabel();
        Size(size);
        Position(position);
    }


    public void Size(Vector2 size) {
        text.setSize(size.X(), size.Y());
    }

    private void Position(Vector2 position) {
        text.setLocation(position.X(), position.Y());
    }

    public TextView Size(int width, int height) {
        text.setSize(width, height);
        return this;
    }

    public Vector2 Size() {
        return new Vector2(text.getX(), text.getY());
    }

    public TextView Position(int x, int y) {
        return this;
    }

    public Vector2 Position() {
        return new Vector2(text.getX(), text.getY());
    }

    public Component get() {
        return text;
    }

    public Sprite Sprite() {
        return null;
    }

    public TextView Theme(Themes theme) {
        return this;
    }

    public TextView TextColor(Color color) {
        text.setForeground(color);
        return this;
    }

    public TextView HorizontalAlignment(int value) {
        text.setHorizontalAlignment(value);
        return this;
    }

    public TextView VerticalAlignment(int value) {
        text.setVerticalAlignment(value);
        return this;
    }

    public TextView Font(Font font) {
        text.setFont(font);
        return this;
    }

    public TextView Text(String text) {
        this.text.setText(text);
        return this;
    }
}
