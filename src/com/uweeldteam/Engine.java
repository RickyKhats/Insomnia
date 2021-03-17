package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;
import uweellibs.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Engine extends MonoBehaviour {


    public Engine(boolean newGame) {
        if (PlayerPrefs.GetObject("Game", Game.class) == null || newGame) {
            Game(new Game());
            return;
        } else {
            Game((Game) PlayerPrefs.GetObject("Game", Game.class));
        }
        File file = new File("Engine.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(Json.ToJson(this));
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void Println(String text) {
        window.Println(text);
    }

    static class ConsoleWindow {
        JFrame window = new JFrame();
        JLabel console = new JLabel("<html>> </html>");
        JPanel background = new JPanel();
        JScrollPane scroll;
        private String lastMessage = "", text = "";

        public ConsoleWindow(String title) {
            console.setForeground(Color.GREEN);
            try {
                console.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("main_font.ttf")).deriveFont(Font.PLAIN, 12f));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            scroll = new JScrollPane(console,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setBackground(Color.BLACK);
            window.setLayout(new BorderLayout());
            background.setLayout(new BorderLayout());
            scroll.setLayout(new ScrollPaneLayout());
            window.setSize(600, 520);
            background.setSize(window.getSize());
            scroll.setSize(background.getWidth() - 15, background.getHeight() - 37);
            console.setSize(scroll.getWidth(), scroll.getHeight());
            scroll.getVerticalScrollBar().setValue((int) window.getAlignmentX());
            background.add(scroll);
            window.setName(title);
            window.add(background);
            window.addKeyListener(new KeyListener() {

                boolean CheckChar(char ch) {
                    String character = String.valueOf(ch).toLowerCase();
                    return
                            Arrays.asList(Codec.ru).contains(character)
                                    || Arrays.asList(Codec.en).contains(character)
                                    || Arrays.asList(Codec.integers).contains(character)
                                    || Arrays.asList(Codec.forbiddenCharacters).contains(character)
                                    || character.equals(String.valueOf(Codec.quote));

                }

                public void keyPressed(KeyEvent keyEvent) {
                    switch (keyEvent.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                            Print("\n");
                            switch (Game.gameState) {
                                case fight:
                                    Main.Engine().Game().fight.ReadCommand(lastMessage);
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
                            Console.Println(lastMessage);
                            lastMessage = "";
                            scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum() + 50);
                            break;
                        case KeyEvent.VK_BACK_SPACE:
                            if (lastMessage.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                                lastMessage = lastMessage.substring(0, lastMessage.length() - 1);
                            }
                            break;
                        case KeyEvent.VK_SPACE:
                            Print(" ");
                            lastMessage += keyEvent.getKeyChar();
                        default:
                            if (CheckChar(keyEvent.getKeyChar())) {
                                Print(keyEvent.getKeyChar());
                                lastMessage += keyEvent.getKeyChar();
                            }
                    }
                }

                public void keyTyped(KeyEvent keyEvent) {

                }

                public void keyReleased(KeyEvent keyEvent) {

                }
            });
            window.addComponentListener(new ComponentListener() {
                public void componentResized(ComponentEvent e) {

                }

                public void componentHidden(ComponentEvent e) {

                }

                public void componentMoved(ComponentEvent e) {

                }

                public void componentShown(ComponentEvent e) {

                }
            });
            console.setHorizontalAlignment(SwingConstants.LEFT);
            console.setVerticalAlignment(SwingConstants.NORTH);
            window.setVisible(true);
            new Thread(() -> {
                int times = 0;
                do {
                    Time.Sleep(50);
                    times++;
                    if (times > 10 && times < 20) {
                        FormatText(text);
                    } else {
                        FormatText(text + "|");
                    }
                    if (times == 20)
                        times = 0;
                } while (true);
            }).start();
        }

        public void Println(Object... objects) {
            ArrayList<String> text = new ArrayList<>();
            for (Object object : objects)
                if (!object.toString().equals(""))
                    if (!object.toString().replaceAll(" {2}", " ").equals(" "))
                        text.add(object.toString());
            for (String Text : text)
                this.text += Text + "\n";
        }

        void FormatText(String text) {
            console.setText("<html>>&nbsp;" + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "</html>");
        }

        public void Print(Object... objects) {
            ArrayList<String> text = new ArrayList<>();
            for (Object object : objects)
                if (!object.toString().equals(""))
                    text.add(object.toString());
            for (String Text : text)
                this.text += Text;
        }
    }

    Game game;

    static ConsoleWindow window = new ConsoleWindow("Insomnia - console");

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return game;
    }
}
