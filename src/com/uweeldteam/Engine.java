package com.uweeldteam;

import com.uweeldteam.game.Game;
import com.uweeldteam.game.fight.Fight;
import uweellibs.Console;
import uweellibs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Arrays;

public class Engine extends MonoBehaviour {
    static Engine.ConsoleWindow window = new Engine.ConsoleWindow("Insomnia - cmd", 800, 420);
    Game game;

    public Engine(boolean newGame) {
        if (newGame) {
            Game(new Game());
        } else {
            if (PlayerPrefs.GetObject("Game", Game.class) == null)
                Game((Game) PlayerPrefs.GetObject("Game", Game.class));
            else
                Game(new Game());
        }

        File file = new File("Engine.json");
        boolean fileCreated = false;

        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileCreated) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(Json.ToJson(this));
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Println(String text) {
        ConsoleWindow.Println(text);
    }

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return this.game;
    }

    public Engine.ConsoleWindow Window() {
        return window;
    }

    public enum GameState {
        fight,
        normal,
        death
    }

    static class ConsoleWindow extends Console {
        static JLabel console;
        static JScrollPane scroll;
        private static String text = "";
        JFrame window;
        JPanel background;
        private String lastMessage = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                window = new JFrame();
                background = new JPanel();
                window.add(background);
                console = new JLabel();
                scroll = new JScrollPane(console, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                background.add(scroll);
                background.setLayout(new BorderLayout());
                window.setLayout(new BorderLayout());
                window.setSize(weight, height);
                window.setResizable(false);
                window.addKeyListener(new KeyListener() {
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
                                ConsoleWindow.this.Print(defaultText());
                                ConsoleWindow.this.lastMessage = "";
                                break;
                            case KeyEvent.VK_SPACE:
                                ConsoleWindow.this.Print(" ");
                                lastMessage += keyEvent.getKeyChar();
                            default:
                                if (this.CheckChar(keyEvent.getKeyChar())) {
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
                background.setSize(window.getSize());
                scroll.setLayout(new ScrollPaneLayout());
                background.setBackground(Color.BLACK);
                scroll.getVerticalScrollBar().setBackground(Color.BLACK);
                scroll.getHorizontalScrollBar().setBackground(Color.BLACK);
                scroll.getVerticalScrollBar().getFocusCycleRootAncestor().setForeground(Color.BLACK);
                scroll.getViewport().setBackground(Color.BLACK);
                console.setForeground(Color.GREEN);
                scroll.setSize(background.getWidth() + 20, background.getHeight() - 38);
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
                console.setSize(scroll.getSize());
                console.setVerticalAlignment(1);
                console.setHorizontalAlignment(2);
                scroll.getVerticalScrollBar().setUnitIncrement(16);

                try {
                    console.setFont(Font.createFont(0, new FileInputStream("main_font.ttf")).deriveFont(Font.PLAIN, 13.0F));
                } catch (IOException | FontFormatException e) {
                    new ExceptionOccurred(e);
                }

                window.setVisible(true);
                FormatText();
                (new Thread(() -> {
                    int times = 0;
                    while (true) {
                        new WaitForSeconds(0.05F);
                        times++;
                        if (times > 10 && times < 20) {
                            console.setText("<html>" + defaultText() + HTML.space
                                    + text.replaceAll("\n", HTML.enter + ">" + HTML.space).replaceAll(" ", HTML.space) + "</html>");
                        } else {
                            console.setText("<html>" + defaultText() + HTML.space
                                    + text.replaceAll(" ", HTML.space).replaceAll("\n", HTML.enter + ">" + HTML.space) + "|</html>");
                        }

                        if (times == 20) {
                            times = 0;
                        }
                    }
                })).start();
                new Thread(() -> {

                    Time time = new Time();
                    Time allTime;
                    {
                        try {
                            allTime = (Time) PlayerPrefs.GetObject("allTime", Time.class);
                        } catch (NullPointerException e) {
                            allTime = new Time();
                        }
                    }
                    do {
                        time.tick();
                        allTime.tick();
                        window.setTitle(title + " Сеанс: " + time.toString() + " Всего наиграно: " + allTime.toString());
                        PlayerPrefs.SetObject("allTime", allTime);
                        new WaitForSeconds(1);
                    } while (true);
                }).start();
            } catch (Exception e) {
                new ExceptionOccurred(e);
            }
        }

        static void FormatText() {
            console.setText("<html>" + defaultText() + HTML.space + text.replaceAll("\n", HTML.enter + ">" + HTML.space).replaceAll(" ", HTML.space) + "</html>");
            scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
        }

        static String defaultText() {
            try {
                return com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.normal ? "Город -> " : (com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.fight ? "Бой -> " : "Мёртв -> ");
            } catch (NullPointerException e) {
                return "Город -> ";
            }
        }

        public static void Println(Object... objects) {
            for (String Text : Replace(objects))
                text = String.format("%s%s", text, Text) + "\n";
            (new Thread(() -> {
                //вот это ожидание перед форматированием текста нужно дабы текст слайдился правильно
                new WaitForSeconds(0.07F);
                FormatText();
            })).start();
        }

        public void Print(Object... objects) {
            for (String Text : Replace(objects)) {
                text = String.format("%s%s", text, Text);
            }
            new Thread(() -> {
                new WaitForSeconds(0.05F);
                FormatText();
            }).start();
        }

        public void Close() {
            this.window.setVisible(false);
        }
    }
}
