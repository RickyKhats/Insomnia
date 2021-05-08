package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;
import uweellibs.graphics.KeyCode;
import uweellibs.graphics.Window;
import uweellibs.graphics.view.ScrollView;
import uweellibs.graphics.view.TextView;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Engine extends MonoBehaviour {
    static Engine.ConsoleWindow window = new Engine.ConsoleWindow("Insomnia - cmd", 800, 420);
    Game game;

    public Engine(boolean newGame) {
        Debug.Log("Engine start initializing");
        if (newGame) {
            Game(new Game());
        } else {
            try {
                Game((Game) PlayerPrefs.GetObject("Game", Game.class));
            } catch (RuntimeException GameHasNotSaved) {
                Game(new Game());
            }
        }
        DateTime date = new DateTime();
        Debug.Log("Engine initialized");
    }

    public static void Println(Object... text) {
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
        TextView console;
        ScrollView scroll;
        Window window;
        private String text = "";
        private String lastMessage = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                new AudioPlayer(
                        new File("ambient.wav"),
                        new File("ambient_2.wav"),
                        new File("ambient_3.wav")).Looping(true).Play();
                console = new TextView("> ",
                        new Vector2(0, 0),
                        new Vector2(weight + 20, height - 38)).
                        TextColor(Color.GREEN)
                        .VerticalAlignment(1)
                        .HorizontalAlignment(2);
                scroll = new ScrollView(new Vector2(weight, height), new Vector2(5, 5)).
                        Add(console).
                        Size(weight + 20, height - 38).
                        ShearRate(16);
                window = new Window(title, weight, height)
                        .Add(scroll)
                        .Resizable(true)
                        .BackgroundColor(Color.BLACK)
                        .Show();
                FormatText();
                (new Thread(() -> {
                    int times = 0;
                    while (true) {
                        new WaitForSeconds(0.05F);
                        times++;
                        if (times > 10 && times < 20) {
                            console.Text("<html>" + defaultText() + HTML.space
                                    + text.replaceAll("\n", HTML.enter + ">" + HTML.space).replaceAll(" ", HTML.space) + "</html>");
                        } else {
                            console.Text("<html>" + defaultText() + HTML.space
                                    + text.replaceAll(" ", HTML.space).replaceAll("\n", HTML.enter + ">" + HTML.space) + "|</html>");
                        }

                        if (times == 20) {
                            times = 0;
                        }
                    }
                })).start();
                (new Thread(() -> {

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
                        window.Title(title + " Сеанс: " + time.toString() + " Всего наиграно: " + allTime.toString());
                        PlayerPrefs.SetObject("allTime", allTime);
                        new WaitForSeconds(1);
                    } while (true);
                })).start();
                Debug.Log(window.Tree());
            } catch (Exception e) {
                new ExceptionOccurred(e);
            }
        }

        static String defaultText() {
            try {
                return com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.normal ? " " : (com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.fight ? " " : " ");
            } catch (NullPointerException e) {
                return " ";
            }
        }

        public static void Println(Object... objects) {
            for (String Text : Replace(objects))
                Engine.window.text = String.format("%s%s", Engine.window.text, Text) + "\n";
            (new Thread(() -> {
                //вот это ожидание перед форматированием текста нужно дабы текст слайдился правильно
                new WaitForSeconds(0.07F);
                Engine.window.FormatText();
            })).start();
        }

        void FormatText() {
            console.Text("<html>" + defaultText() + HTML.space + text.replaceAll("\n", HTML.enter + ">" + HTML.space).replaceAll(" ", HTML.space) + "</html>");
            scroll.VerticalScrollBar().setValue(scroll.VerticalScrollBar().getMaximum());
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
            this.window.Hide();
        }

        public boolean ReadKey(KeyCode keyCode) {
            try {
                return window.ReadKey(keyCode);
            } catch (NullPointerException e) {
                Debug.WarningLog("window is " + window);
                return false;
            }
        }
    }
}
