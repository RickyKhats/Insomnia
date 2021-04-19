package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;
import uweellibs.Console;
import uweellibs.graphics.*;
import uweellibs.graphics.Window;

import javax.swing.*;
import java.awt.*;
import java.io.*;

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

    public static boolean ReadEnter() {
        return window.ReadKey(KeyCode.enter);
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
        static TextView console;
        static Scroll scroll;
        private static String text = "";
        Window window;
        private String lastMessage = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                window = new Window("", 1200, 1000);
                console = new TextView("", scroll.Position(), scroll.Size());
                scroll = new Scroll();
                        //(console, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                window.Add(scroll, new Vector2(0, 0));
                window.Size(weight, height);
                window.Resizable(false);
                window.BackgroundColor(Color.BLACK);

                console.TextColor(Color.GREEN);
                scroll.setSize(background.getWidth() + 20, background.getHeight() - 38);
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
                console.setSize(scroll.getSize());
                console.setVerticalAlignment(1);
                console.setHorizontalAlignment(2);
                scroll.ShearRate(16);

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

        public boolean ReadKey(KeyCode keyCode) {
            return window.ReadKey(keyCode);
        }
    }
}
