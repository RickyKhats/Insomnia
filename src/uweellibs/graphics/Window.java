package uweellibs.graphics;

import uweellibs.Vector2;
import uweellibs.graphics.view.Background;
import uweellibs.graphics.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Window {
    static ArrayList<Sprite> sprites = new ArrayList<>();
    JFrame window;
    Background background;
    KeyListener keyListener;
    Input input;

    public Window(String title, int width, int height) {
        window = new JFrame(title);
        window.setUndecorated(false);
        window.setSize(width, height);
        window.setVisible(true);
        background = new Background(Color.WHITE);
        background.setLayout(new BorderLayout());
        background.setSize(width, height);
        keyListener = new KeyListener();
        input = new Input(keyListener);
        background.addKeyListener(keyListener);
        window.add(background);
    }

    public Window BackgroundColor(Color color){
        background.setBackground(color);
        return this;
    }

    public Window Add(View component) {
        background.add(component);
        return this;
    }

    public Window Size(int weight, int height) {
        window.setSize(weight, height);
        return this;
    }
    public Window Size(Vector2 size) {
        window.setSize(size.X(), size.Y());
        return this;
    }

    public Window Resizable(boolean value) {
        window.setResizable(value);
        return this;
    }

    public Window Undecorated(boolean value) {
        window.setUndecorated(value);
        return this;
    }

    public Vector2 Size() {
        return new Vector2(window.getX(), window.getY());
    }

    public Window Show() {
        window.setVisible(true);
        return this;
    }

    public boolean ReadKey(KeyCode keyCode) {
        return input.GetKeyDown(keyCode);
    }

    public void Hide() {
        window.setVisible(false);
    }

    public Window Title(String title) {
        window.setTitle(title);
        return this;
    }

    public Object Tree() {
        return Arrays.toString(window.getContentPane().getComponents());
    }
}
