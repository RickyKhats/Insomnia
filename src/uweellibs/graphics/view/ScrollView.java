package uweellibs.graphics.view;

import uweellibs.Vector2;
import uweellibs.graphics.Sprite;
import uweellibs.graphics.Themes;

import javax.swing.*;
import java.awt.*;

public class ScrollView extends View {

    private Background background = new Background(Color.WHITE);
    private final JScrollPane scroll = new JScrollPane(background, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public ScrollView(Vector2 size, Vector2 position) {
        Size(size);
        Position(position);
        scroll.setLayout(new ScrollPaneLayout());
        Theme(Themes.Darcula);
    }

    public ScrollView Add(View component) {
        background.add(component.get());
        return this;
    }

    private ScrollView Position(Vector2 position) {
        scroll.setLocation(position.X(), position.Y());
        return this;
    }

    private ScrollView Size(Vector2 size) {
        scroll.setSize(size.X(), size.Y());
        return this;
    }

    public ScrollView Size(int width, int height) {
        scroll.setSize(width, height);
        return this;
    }

    public Vector2 Size() {
        return new Vector2(scroll.getSize().width, scroll.getSize().height);
    }

    public ScrollView Position(int x, int y) {
        return this;
    }

    public Vector2 Position() {
        return new Vector2(scroll.getX(), scroll.getY());
    }

    public Component get() {
        return scroll;
    }

    public Sprite Sprite() {
        return null;
    }

    public ScrollView Theme(Themes theme) {
        switch (theme) {
            case White:
                break;
            case Darcula:
                scroll.getVerticalScrollBar().setBackground(Color.BLACK);
                scroll.getHorizontalScrollBar().setBackground(Color.BLACK);
                background.setBackground(Color.BLACK);
                break;
        }
        return this;
    }

    public ScrollView VerticalShearRate(int value) {
        scroll.getVerticalScrollBar().setUnitIncrement(value);
        return this;
    }

    public ScrollView HorizontalShearRate(int value) {
        scroll.getHorizontalScrollBar().setUnitIncrement(value);
        return this;
    }

    public int VerticalShearRate() {
        return scroll.getVerticalScrollBar().getUnitIncrement();
    }

    public int HorizontalShearRate() {
        return scroll.getHorizontalScrollBar().getUnitIncrement();
    }

    public JScrollBar VerticalScrollBar() {
        return scroll.getVerticalScrollBar();
    }

    public JScrollBar HorizontalScrollBar() {
        return scroll.getHorizontalScrollBar();
    }

    public ScrollView ShearRate(int value) {
        scroll.setAlignmentX(value);
        return this;
    }
}
