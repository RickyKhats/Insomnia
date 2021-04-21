package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;
import uweellibs.Console;
import uweellibs.graphics.*;
import uweellibs.graphics.Window;
import uweellibs.graphics.view.ScrollView;
import uweellibs.graphics.view.TextView;

import java.awt.*;
import java.io.*;

public class Engine extends MonoBehaviour {
    static Engine.ConsoleWindow window = new Engine.ConsoleWindow("Insomnia - cmd", 800, 420);
    Game game;

    public Engine(boolean newGame) {
        if (newGame) {
            Game(new Game());
        } else {
            try{
                Game((Game) PlayerPrefs.GetObject("Game", Game.class));
            } catch (RuntimeException GameHasNotSaved){
                Game(new Game());
            }
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
        private String text = "";
        Window window;
        private String lastMessage = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                window = new Window(title, 1200, 1000);
                scroll = new ScrollView(new Vector2(weight, height),new Vector2(0, 0));
                console = new TextView(">", scroll.Position(), scroll.Size());
                        //(console, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                window.Add(scroll);
                scroll.Add(console);

                window.Size(weight, height);
                window.Resizable(false);
                window.BackgroundColor(Color.BLACK);

                console.TextColor(Color.GREEN);
                scroll.Size(window.Size().X() + 20, window.Size().Y() - 38);
                scroll.VerticalScrollBar().setValue(scroll.VerticalScrollBar().getMaximum());
                console.Size(scroll.Size());
                console.VerticalAlignment(1);
                console.HorizontalAlignment(2);
                scroll.ShearRate(16);

                try {
                    console.Font(Font.createFont(0, new FileInputStream("main_font.ttf")).deriveFont(Font.PLAIN, 13.0F));
                } catch (IOException | FontFormatException e) {
                    new ExceptionOccurred(e);
                }

                window.Show();
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
                        window.Title(title + " Сеанс: " + time.toString() + " Всего наиграно: " + allTime.toString());
                        PlayerPrefs.SetObject("allTime", allTime);
                        new WaitForSeconds(1);
                    } while (true);
                }).start();
            } catch (Exception e) {
                new ExceptionOccurred(e);
            }
        }

        void FormatText() {
            console.Text("<html>" + defaultText() + HTML.space + text.replaceAll("\n", HTML.enter + ">" + HTML.space).replaceAll(" ", HTML.space) + "</html>");
            scroll.VerticalScrollBar().setValue(scroll.VerticalScrollBar().getMaximum());
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
                Engine.window.text = String.format("%s%s", Engine.window.text, Text) + "\n";
            (new Thread(() -> {
                //вот это ожидание перед форматированием текста нужно дабы текст слайдился правильно
                new WaitForSeconds(0.07F);
                Engine.window.FormatText();
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
            this.window.Hide();
        }

        public boolean ReadKey(KeyCode keyCode) {
            return window.ReadKey(keyCode);
        }
    }
}
