package uweellibs.graphics;

import uweellibs.Vector2;
import uweellibs.graphics.view.Background;
import uweellibs.graphics.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window {
    static ArrayList<Sprite> sprites = new ArrayList<>();
    JFrame window;
    Background background;
    KeyListener keyListener;
    Input input;

    public Window(String title, int width, int height) {
        window = new JFrame(title);
        window.setSize(width, height);
        window.setVisible(true);
        background = new Background(Color.WHITE);
        background.setLayout(new BorderLayout());
        background.setSize(width, height);
        keyListener = new KeyListener();
        input = new Input(keyListener);
        window.add(background);
    }

    public void BackgroundColor(Color color){
        background.setBackground(color);
    }

    public void Add(View component) {
        background.add(component);
    }

    public void Size(int weight, int height) {
        window.setSize(weight, height);
    }
    public void Size(Vector2 size) {
        window.setSize(size.X(), size.Y());
    }

    public void Resizable(boolean value) {
        window.setResizable(value);
    }

    public Vector2 Size() {
        return new Vector2(window.getX(), window.getY());
    }

    public void Show() {
        window.setVisible(true);
    }

    public boolean ReadKey(KeyCode keyCode) {
        return false;
    }

    public void Hide() {
        window.setVisible(false);
    }

    public void Title(String title) {
        window.setTitle(title);
    }

}
