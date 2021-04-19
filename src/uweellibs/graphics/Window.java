package uweellibs.graphics;

import uweellibs.Input;
import uweellibs.Vector2;

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
        keyListener = new KeyListener();
        input = new Input(keyListener);
        background.setSize(width, height);
    }

    public void BackgroundColor(Color color){
        background.setBackground(color);
    }

/*    public KeyListener keyListener = new KeyListener() {

        public boolean isEnterPressed = false;

        boolean CheckChar(char ch) {
            String character = String.valueOf(ch).toLowerCase();
            return Arrays.asList(Codec.ru).contains(character) || Arrays.asList(Codec.en).contains(character) || Arrays.asList(Codec.integers).contains(character) || Arrays.asList(Codec.forbiddenCharacters).contains(character) || character.equals(String.valueOf('"'));
        }

        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE:
                    if (lastMessage.length() > 0) {
                        if (keyEvent.isControlDown()) {
                            if (lastMessage.lastIndexOf(" ") != -1) {
                                text = text.substring(0, text.lastIndexOf(" "));
                                lastMessage = lastMessage.substring(0, lastMessage.lastIndexOf(" "));
                            } else {
                                text = text.substring(0, text.length() - lastMessage.length());
                                lastMessage = "";
                            }
                        } else {
                            text = text.substring(0, text.length() - 1);
                            lastMessage = lastMessage.substring(0, lastMessage.length() - 1);
                        }
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    Print("\n");
                    switch (Main.Engine().Game().gameState) {
                        case fight:
                            Fight.ReadCommand(lastMessage);
                            break;
                        case normal:
                            Main.Engine().Game().ReadCommand(lastMessage);
                            break;
                        case death:
                            if ("новая игра".equals(lastMessage)) {
                                Main.main(new String[]{"newGame"});
                                return;
                            }
                            Println("Вы мертвы, начните новую игру");
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                    text = text.substring(0, text.length() - 1) + "<br>";
                    Engine.ConsoleWindow.this.Print(defaultText());
                    Engine.ConsoleWindow.this.lastMessage = "";
                    break;
                case KeyEvent.VK_SPACE:
                    Engine.ConsoleWindow.this.Print(" ");
                    lastMessage += keyEvent.getKeyChar();
                default:
                    if (this.CheckChar(keyEvent.getKeyChar())) {
                        Print(keyEvent.getKeyChar());
                        lastMessage += keyEvent.getKeyChar();
                    }
            }

        }

        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                isEnterPressed = true;
        }

        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                isEnterPressed = true;
        }
    };*/

    public void Add(WindowComponent component, Vector2 position) {
        background.add(component, position);
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

    static class Background extends JPanel {
        public Background(Color color) {
            setLayout(null);
            this.setBackground(color);
        }
        public void add(WindowComponent component, Vector2 position){

        }
    }


}
