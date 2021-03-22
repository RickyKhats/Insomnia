package uweellibs.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window {
    JFrame window;
    ArrayList<Sprite> sprites = new ArrayList<>();

    public Window(String title, int width, int height) {
        window = new JFrame(title);
        window.setSize(width, height);
        window.setVisible(true);
        window.add(new Background());

    }

    public void Add(WindowComponent object) {
        sprites.add(object.Sprite());
        window.add(object.get());
    }

    class Background extends JPanel {
        /*protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            var Draw = (Graphics2D) g.create();
            for (Sprite sprite : sprites)
                Draw.drawImage(sprite.sprite, sprite.position.X(), sprite.position.Y(), null);
            Draw.dispose();
        }*/
    }
}
