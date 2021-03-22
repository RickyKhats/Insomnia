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
        window.Println(text);
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
        JFrame window;
        static JLabel console;
        JPanel background;
        static JScrollPane scroll;
        private String lastMessage = "";
        private static String text = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                this.window = new JFrame(title);
                this.background = new JPanel();
                this.window.add(this.background);
                this.console = new JLabel();
                this.scroll = new JScrollPane(this.console, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                this.background.add(scroll);
                this.background.setLayout(new BorderLayout());
                this.window.setLayout(new BorderLayout());
                this.window.setSize(weight, height);
                this.window.setResizable(false);
                this.window.addKeyListener(new KeyListener() {
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
                                ConsoleWindow.this.Print(ConsoleWindow.this.defaultText());
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
                this.background.setSize(window.getSize());
                this.scroll.setLayout(new ScrollPaneLayout());
                this.background.setBackground(Color.BLACK);
                this.scroll.getVerticalScrollBar().setBackground(Color.BLACK);
                this.scroll.getHorizontalScrollBar().setBackground(Color.BLACK);
                this.scroll.getVerticalScrollBar().getFocusCycleRootAncestor().setForeground(Color.BLACK);
                this.scroll.getViewport().setBackground(Color.BLACK);
                this.console.setForeground(Color.GREEN);
                this.scroll.setSize(background.getWidth() + 20, background.getHeight() - 38);
                this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
                console.setSize(scroll.getSize());
                console.setVerticalAlignment(1);
                this.console.setHorizontalAlignment(2);

                try {
                    this.console.setFont(Font.createFont(0, new FileInputStream("main_font.ttf")).deriveFont(Font.PLAIN, 13.0F));
                } catch (IOException | FontFormatException e) {
                    new ExceptionOccurred(e);
                }

                this.window.setVisible(true);
                this.FormatText();
                (new Thread(() -> {
                    int times = 0;

                    while (true) {
                        new WaitForSeconds(0.05F);
                        times++;
                        if (times > 10 && times < 20) {
                            console.setText("<html>" + defaultText() + "&nbsp;"
                                    + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "</html>");
                        } else {
                            console.setText("<html>" + defaultText() + "&nbsp;"
                                    + text.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br>>&nbsp;") + "|</html>");
                        }

                        if (times == 20) {
                            times = 0;
                        }
                    }
                })).start();
            } catch (Exception e) {
                new ExceptionOccurred(e);
            }
        }

        static void FormatText() {
            console.setText("<html>" + defaultText() + "&nbsp;" + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "</html>");
            scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
        }

        public void Print(Object... objects) {
            for (String Text : Replace(objects)) {
                this.text = String.format("%s%s", this.text, Text);
            }
            new Thread(() -> {
                new WaitForSeconds(0.05F);
                FormatText();
            }).start();
        }

        public void Close() {
            this.window.setVisible(false);
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
                new WaitForSeconds(0.05F);
                FormatText();
            })).start();
        }
    }
}
