package uweellibs.graphics;

import uweellibs.MonoBehaviour;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window {
    static ArrayList<Sprite> sprites = new ArrayList<>();
    JFrame window;
    JPanel background;

    public Window(String title, int width, int height) {
        window = new JFrame(title);
        window.setSize(width, height);
        window.setVisible(true);
        background = new Background();
        background.setLayout(new BorderLayout());
    }

    public void Add(WindowComponent object, String location) {
        background.add(object.get(), location);
    }

    static class Background extends JPanel {
        public Background(){
            this.setBackground(Color.BLACK);
        }

    }
}
