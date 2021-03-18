package com.uweeldteam;

import com.uweeldteam.game.Game;
import com.uweeldteam.game.fight.Fight;
import uweellibs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Engine extends MonoBehaviour {
    static ConsoleWindow window = new ConsoleWindow("Insomnia - cmd", 800, 420);
    Game game;

    public Engine(boolean newGame) {
        if (newGame)
            Game(new Game());
        else
            try {
                Game((Game) PlayerPrefs.GetObject("Game", Game.class));
            } catch (NullPointerException e) {
                Game(new Game());
            }
        File file = new File("Engine.json");
        boolean fileCreated = false;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileCreated)
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

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return game;
    }

    public enum GameState {
        fight, normal, death
    }

    static class ConsoleWindow {
        JFrame window;
        JLabel console;
        JPanel background;
        JScrollPane scroll;
        private String lastMessage = "", text = "";

        public ConsoleWindow(String title, int weight, int height) {
            window = new JFrame(title);
            background = new JPanel();
            window.add(background);
            console = new JLabel("<html>> </html>");
            scroll = new JScrollPane(console,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            background.add(scroll);
            background.setLayout(new BorderLayout());
            window.setLayout(new BorderLayout());
            window.setSize(weight, height);
            window.setResizable(false);
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
                            lastMessage = "";
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
                            break;
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
            console.setVerticalAlignment(SwingConstants.NORTH);
            console.setHorizontalAlignment(SwingConstants.LEFT);
            try {
                console.setFont(
                        Font.createFont(
                                Font.TRUETYPE_FONT,
                                new FileInputStream("main_font.ttf")).deriveFont(Font.PLAIN, 13));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            window.setVisible(true);
            new Thread(() -> {
                int times = 0;
                do {
                    Time.Sleep(50);
                    times++;
                    if (times > 10 && times < 20) {
                        console.setText("<html>>&nbsp;" + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "</html>");

                    } else {
                        console.setText("<html>>&nbsp;" + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "|</html>");

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
                Print(Text + "\n");
            FormatText();
        }

        void FormatText() {
            console.setText("<html>>&nbsp;" + text.replaceAll("\n", "<br>>&nbsp;").replaceAll(" ", "&nbsp;") + "</html>");
            scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
        }

        public void Print(Object... objects) {
            ArrayList<String> text = new ArrayList<>();
            for (Object object : objects)
                if (!object.toString().equals(""))
                    text.add(object.toString());
            for (String Text : text)
                this.text = String.format("%s%s", this.text, Text);
            new Thread(() -> {
                Time.Sleep(50);
                FormatText();
            }).start();
        }
    }
}
