package uweellibs.graphics.view;

import com.uweeldteam.ExceptionOccurred;
import uweellibs.Vector2;
import uweellibs.graphics.OnClickListener;
import uweellibs.graphics.Sprite;
import uweellibs.graphics.Themes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Button extends View {

    JButton button = new JButton();
    private OnClickListener onRightClick, onLeftClick;
    private Sprite sprite = new Sprite();

    public Button(String text, int width, int height) {
        button.setText(text);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == 1)
                    onLeftClick.OnClick();
                else if (mouseEvent.getButton() == 3)
                    onRightClick.OnClick();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        button.setSize(new Dimension(width, height));
        button.setUI(new ButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.drawImage(sprite.sprite.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_DEFAULT),
                        button.getX(), button.getY(), null);
                g.drawString(text, button.getX(), button.getY());
                g.dispose();
            }
        });
    }

    public Button OnRightClick(OnClickListener onClick) {
        this.onRightClick = onClick;
        return this;
    }

    public Button OnLeftClick(OnClickListener onClick) {
        this.onLeftClick = onClick;
        return this;
    }

    public Button Size(int width, int height) {
        button.setSize(new Dimension(width, height));
        return this;
    }

    public Vector2 Size() {
        return new Vector2(button.getSize().width, button.getSize().height);
    }

    public Button Position(int x, int y) {
        button.setLocation(x, y);
        return this;
    }

    public Vector2 Position() {
        return new Vector2(button.getX(), button.getY());
    }

    public Component get() {
        return button;
    }

    public Sprite Sprite() {
        return sprite;
    }

    public View Theme(Themes theme) {
        return this;
    }

    public Button BackgroundResource(String path) {
        try {
            sprite.sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            new ExceptionOccurred(e);
        }
        return this;
    }
}
