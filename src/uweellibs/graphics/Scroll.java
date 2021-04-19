package uweellibs.graphics;

import uweellibs.Vector2;

import javax.swing.*;
import java.awt.*;

public class Scroll extends WindowComponent {

    private final JScrollPane scroll;


    {
        scroll = new JScrollPane();
        scroll.setLayout(new ScrollPaneLayout());
    }

    public WindowComponent Size(int width, int height) {
        scroll.setSize(width, height);
        return this;
    }

    public Vector2 Size() {
        return new Vector2(scroll.getSize().width, scroll.getSize().height);
    }

    public WindowComponent Position(int x, int y) {
        return this;
    }

    public Vector2 Position() {
        return null;
    }

    public Component get() {
        return scroll;
    }

    public Sprite Sprite() {
        return null;
    }

    public WindowComponent Theme(Themes theme) {
        switch (theme) {
            case White:
                break;
            case Darcula:
                scroll.getVerticalScrollBar().setBackground(Color.BLACK);
                scroll.getHorizontalScrollBar().setBackground(Color.BLACK);
                scroll.getVerticalScrollBar().getFocusCycleRootAncestor().setForeground(Color.BLACK);
                scroll.getViewport().setBackground(Color.BLACK);
            break;
        }
        return this;
    }

    public void VerticalShearRate(int value) {
        scroll.getVerticalScrollBar().setUnitIncrement(value);
    }
    public void HorizontalShearRate(int value) {
        scroll.getHorizontalScrollBar().setUnitIncrement(value);
    }
    public int VerticalShearRate() {
        return scroll.getVerticalScrollBar().getUnitIncrement();
    }
    public int HorizontalShearRate() {
        return scroll.getHorizontalScrollBar().getUnitIncrement();
    }
}
