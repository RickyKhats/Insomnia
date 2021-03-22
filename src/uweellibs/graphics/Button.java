package uweellibs.graphics;

import uweellibs.Vector2;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends WindowComponent {

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
    }

    public Button OnRightClick(OnClickListener onClick) {
        this.onRightClick = onClick;
        return this;
    }

    public Button OnLeftClick(OnClickListener onClick) {
        this.onLeftClick = onClick;
        return this;
    }

    public Button Size(int height, int width) {
        button.setSize(new Dimension(width, height));
        return this;
    }

    public Dimension Size() {
        return button.getSize();
    }

    public Button Position(int x, int y) {

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

    public Button BackgroundResource(String path) {
        button.setUI(new ButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.drawImage(sprite.sprite.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_DEFAULT), sprite.position.X(), sprite.position.Y(), null);
            }
        });
        return this;
    }
}
